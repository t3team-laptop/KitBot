package frc.robot;

public final class Constants {

  public static class ControllerConstants {

    // CONTROLLER
    public static final int kDriverControllerPort = 0;
    public static final double kStickDeadband = .01;

  }

  public static class ShooterConstants {

    // IDS
    public static final int kMotor_ID_TOP = 5;
    public static final int kMotor_ID_BOTTOM = 6;

    // SPEEDS
    public static final double kShooterAmpSpeed = .1;
    public static final double kIntakerSpeed = -1;
    public static final double kShooterSpeakerSpeed = 1;
    public static final double kFeederSpeed = 1;

  }

  public static class DrivetrainConstants {
    
    // IDS
    public static final int kLeftRearID = 1;
    public static final int kLeftFrontID = 2;
    public static final int kRightRearID = 3;
    public static final int kRightFrontID = 4;

    // CURRENT LIMIT
    public static final int kCurrentLimit = 40;

  }

  public static class VisionConstants {

    // LIMELIGHT MOUNTING 
    public static final double kLimeLightMountingHeight = 17.7 ; // TODO Find height (INCHES) from center of lens to ground
    public static final double kLimeLightMountingDegrees  = 0;

    // FIELD ELEMENTS
    public static final double kSpeakerTargetHeight = 61.5;
    public static final double kSpeakerShootingDistance = 40; // TODO find optimal shooting distance from speaker

    // OFFSETS & THRESHOLDS
    public static final double kSpeakerRotationalOffset = 0;
    public static final double kTranslationalErrorThreshold = 5;
    public static final double kRotationalErrorThreshold = 3;

  }
}
