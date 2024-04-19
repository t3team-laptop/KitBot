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

  private double x; // Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees / LL2: -29.8 to 29.8 degrees)
  private double y; // Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees / LL2: -24.85 to 24.85 degrees)
  private double v; // 1 if valid target exists. 0 if no valid targets exist
  private double area; // Target Area (0% of image to 100% of image)

  private double distalOffsetToTarget;

  PIDController rotationController = new PIDController(.025, .01, 0); // TODO tune pid
  PIDController translationController = new PIDController(.025, 0, 0); // TODO tune pid
  
  public Vision() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    table = NetworkTableInstance.getDefault().getTable("limelight") ;
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    tv = table.getEntry("tv");
    this.table.getEntry("ledMode").setNumber(3);
  }

  public double calculateDistalOffset(double targetHeight) { // TODO find inches correlation through testing

    double angleToTargetDegrees = Constants.VisionConstants.kLimeLightMountingDegrees + y;
    double angleToTargetRadians = angleToTargetDegrees * (Math.PI / 180.0);

    distalOffsetToTarget = (targetHeight - Constants.VisionConstants.kLimeLightMountingHeight)/Math.tan(angleToTargetRadians);

    return distalOffsetToTarget;
  }

  public double calculateRotationalOffsetSpeaker() {
    double rotationSpeed = 0;

    if(hasTarget()) {
      rotationSpeed = rotationController.calculate(x, 0);
    }else {
      rotationSpeed = 0;
    }

    return rotationSpeed;
  }

  public double calculateTransitionalOffsetSpeaker(double desiredDistanceInches) {
    double translationSpeed = 0;

    double distanceInInches = calculateDistalOffset(Constants.VisionConstants.kSpeakerTapeHeight) * 1; //TODO Correlate x incorperated calculation to inches

    if(hasTarget()) {
      translationSpeed = translationController.calculate(distanceInInches, desiredDistanceInches);
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
  }

  @Override
  public void periodic() {
    updateVals();

    SmartDashboard.putNumber("Target X Position Relative to LimeLight", x);
    SmartDashboard.putNumber("Target Y Position Relative to LimeLight", y);
    SmartDashboard.putBoolean("Limelight has Target", hasTarget());
    SmartDashboard.putNumber("Percentage of LimeLight Feed Covered by Target", area);
    SmartDashboard.putNumber("Distance to Target", distalOffsetToTarget);
  }

}
