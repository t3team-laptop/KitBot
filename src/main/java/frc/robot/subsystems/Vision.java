package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {

  private NetworkTable table;
  private NetworkTableEntry tx;
  private NetworkTableEntry ty;
  private NetworkTableEntry tv;
  private NetworkTableEntry tid;

  public double x; // Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees / LL2: -29.8 to 29.8 degrees)
  private double y; // Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees / LL2: -24.85 to 24.85 degrees)
  private double v; // 1 if valid target exists. 0 if no valid targets exist
  private double id; // ID of the primary in-view AprilTag

  private double distanceToTarget; // inches
  private double rotationSetpoint;
  private double translationSetpoint;

  public PIDController rotationController;
  public PIDController translationController;
  
  public Vision() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    tv = table.getEntry("tv");
    tid = table.getEntry("tid");
    this.table.getEntry("ledMode").setNumber(3);

    rotationController = new PIDController(
      Constants.VisionConstants.kRotateP, 
      Constants.VisionConstants.kRotateI,
      Constants.VisionConstants.kRotateD);
    rotationController.setTolerance(Constants.VisionConstants.kRotateTolerance);

    translationController = new PIDController(
      Constants.VisionConstants.kTranslateP, 
      Constants.VisionConstants.kTranslateI,
      Constants.VisionConstants.kTranslateD);
    translationController.setTolerance(Constants.VisionConstants.kTranslateTolerance);
  }


  public double[] getValues() {
    return new double[] {x, y, v, distanceToTarget};
  }

  public double calculateDistalOffset() { // from limelight docs
    double targetOffsetAngle_Vertical = y;

    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = Constants.VisionConstants.kLimeLightMountingDegrees; 

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = Constants.VisionConstants.kLimeLightMountingHeight; 

    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);

    //calculate distance
    double distanceFromLimelightToGoalInches = (getTargetInformation()[1] - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

    return distanceFromLimelightToGoalInches;
  } 

  public double calculateRotationalOffset() {
    double rotationSpeed = 0;
    rotationSetpoint = getTargetInformation()[2];

    if(hasTarget()) {
      rotationSpeed = rotationController.calculate(x, rotationSetpoint);
    }else {
      rotationSpeed = 0;
    }

    return rotationSpeed;
  }

  public double calculateTransitionalOffset() { // INCHES
    double translationSpeed;  
    double desiredDistance = getTargetInformation()[1];

    if(hasTarget()) {
      translationSpeed = translationController.calculate(distanceToTarget, desiredDistance);
    }else {
      translationSpeed = 0;
    }

    return translationSpeed;
  }

  public double[] getTargetInformation() {

    double desiredDistance = 0.0;
    double rotationSetpoint = 0.0;
    double targetHeight = 0.0;

    if(id == 8 || id == 4) { // target is speaker
      desiredDistance = Constants.VisionConstants.kSpeakerShootingDistance;
      targetHeight = Constants.VisionConstants.kSpeakerTargetHeight;
      rotationSetpoint = Constants.VisionConstants.kSpeakerRotationalOffset;

    }else if(id == 5 || id == 6) { // target is amp
      desiredDistance = Constants.VisionConstants.kAmpShootingDistance;
      targetHeight = Constants.VisionConstants.kAmpTargetHeight;
      rotationSetpoint = Constants.VisionConstants.kAmpRotationalOffset;
    }

    return new double[] {desiredDistance, targetHeight, rotationSetpoint};
  }

  public boolean hasTarget(){
    return v == 1.0;
  }

  public void updateVals(){
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    v = tv.getDouble(0.0);   
    id = tid.getDouble(0.0);


    if(hasTarget()) {
      distanceToTarget = calculateDistalOffset();
    }else{
      distanceToTarget = 0;
    }
  }

  @Override
  public void periodic() {
    updateVals();

    SmartDashboard.putBoolean("Target Detected", hasTarget());
    SmartDashboard.putNumber("Target Distance", distanceToTarget);
    SmartDashboard.putNumber("Target ID", id);

    SmartDashboard.putNumber("Rotation Setpoint", rotationSetpoint);
    SmartDashboard.putNumber("Translation Setpoint", translationSetpoint);
  }

}
