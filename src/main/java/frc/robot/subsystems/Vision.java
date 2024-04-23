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
  private NetworkTableEntry ta;
  private NetworkTableEntry tv;

  public double x; //Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees / LL2: -29.8 to 29.8 degrees) TODO look into y to angle conversion
  private double y; // Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees / LL2: -24.85 to 24.85 degrees)
  private double v; // 1 if valid target exists. 0 if no valid targets exist
  private double area; // Target Area (0% of image to 100% of image)

  private double distalOffsetToSpeaker;

  PIDController rotationController = new PIDController(.25, .00, .01); // TODO tune pid
  PIDController translationController = new PIDController(.025, 0, 0); // TODO tune pid
  
  public Vision() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    tv = table.getEntry("tv");
    this.table.getEntry("ledMode").setNumber(3);
  }

  public double[] getValues() {
    return new double[] {x, y, area, v, distalOffsetToSpeaker};
  }

  public double calculateDistalOffset(double goalHeightInches) { // Limelight Documentation Code
    double targetOffsetAngle_Vertical = y;

    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = Constants.VisionConstants.kLimeLightMountingDegrees; 

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = Constants.VisionConstants.kLimeLightMountingHeight; 

    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    //calculate distance
    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

    return distanceFromLimelightToGoalInches;
  }

  public double calculateRotationalOffsetSpeaker() {
    double rotationSpeed = 0;
    double rotationOffset = Constants.VisionConstants.kSpeakerRotationalOffset;

    if(hasTarget() && (x >= -3 && x <= 3)) {
      rotationSpeed = 0;
    }else if(hasTarget()) {
      rotationSpeed = rotationController.calculate(x, rotationOffset);
    }else {
      rotationSpeed = 0;
    }

    return rotationSpeed;
  }

  public double calculateTransitionalOffset(double desiredDistance, double targetHeightInches) { // INCHES
    double translationSpeed;  

    double currentDistance = calculateDistalOffset(targetHeightInches);

    if(hasTarget() && (currentDistance >= desiredDistance-3 && currentDistance <= desiredDistance+3)) {
      translationSpeed = 0;
    }else if(hasTarget()) {
      translationSpeed = translationController.calculate(currentDistance, desiredDistance);
    }else {
      translationSpeed = 0;
    }

    return translationSpeed;
  }

  public boolean hasTarget(){
    return v == 1.0;
  }

  public void updateVals(){
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    v = tv.getDouble(0.0);   
    area = ta.getDouble(0.0);

    if(hasTarget()) {
      distalOffsetToSpeaker = calculateDistalOffset(Constants.VisionConstants.kSpeakerTargetHeight);
    }else{
      distalOffsetToSpeaker = 0;
    }
  }

  @Override
  public void periodic() {
    updateVals();

    SmartDashboard.putNumber("LimeLightX", x);
    SmartDashboard.putNumber("LimeLightY", y);
    SmartDashboard.putNumber("LimeLightV", v);
    SmartDashboard.putNumber("LimeLightA", area);
    SmartDashboard.putNumber("SpeakerDistance", distalOffsetToSpeaker);
  }

}
