// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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

  private double distanceToTarget;

  PIDController rotationController = new PIDController(.025, 0, 0);
  
  /** Creates a new Vision. */
  public Vision() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    table = NetworkTableInstance.getDefault().getTable("limelight") ;
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    tv = table.getEntry("tv");
    this.table.getEntry("ledMode").setNumber(3);
  }

  public double getDistanceToTarget(){ // old titan code
    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = Constants.VisionConstants.kLimeLightMountingDegrees;

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeight = Constants.VisionConstants.kLimeLightMountingHeight; // inches

    // distance from the target to the floor
    double targetHeight = 104.0;

    double angleToTargetDegrees = limelightMountAngleDegrees + y;
    double angleToTargetRadians = angleToTargetDegrees * (Math.PI / 180.0);

    //calculate distance
    distanceToTarget = (targetHeight - limelightLensHeight)/Math.tan(angleToTargetRadians);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightY", y);

    return distanceToTarget;
  }

  public double calculateRotationalOffsetSpeaker() { //TODO make the rotation calculation
    double rotationSpeed = 0;

    if(hasTarget()) {
      rotationSpeed = rotationController.calculate(x, 0);
    }else {
      rotationSpeed = 0;
    }

    return rotationSpeed;
  }

  public double calculateTransitionalOffsetSpeaker() { //TODO make the rotation calculation
    double rotationSpeed = 0;

    if(hasTarget()) {
      rotationSpeed = rotationController.calculate(x, 0);
    }else {
      rotationSpeed = 0;
    }

    return rotationSpeed;
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
    SmartDashboard.putBoolean("Target X Position Relative to LimeLight", hasTarget());
    SmartDashboard.putNumber("Percentage of LimeLight Feed Covered by Target", area);
  }

}
