// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {

    // CONTROLLER
    public static final int kDriverControllerPort = 0;
    public static final double kStickDeadband = .01;

  }

  public static class ShooterConstants {

    // IDS
    public static final int kMotor_ID_TOP = 5; // TODO Get Shooter ids
    public static final int kMotor_ID_BOTTOM = 6;

    // SPEEDS
    public static final double kShooterAmpSpeed = .7;
    public static final double kIntakerSpeed = -1;
    public static final double kShooterSpeakerSpeed = 1;
    public static final double kFeederSpeed = 1;

  }

  public static class DrivetrainConstants {
    
    // IDS
    public static final int kLeftRearID = 1; // TODO Get MotorTrain ids
    public static final int kLeftFrontID = 2;
    public static final int kRightRearID = 3;
    public static final int kRightFrontID = 4;

    // CURRENT LIMIT
    public static final int kCurrentLimit = 40; // TODO IF NOT ENOUGH, GO TO 60

  }
}
