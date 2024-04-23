package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;

public class SpeakerAlign extends Command {
  
  private DriveTrain driveTrain;
  private Vision vision;

  public SpeakerAlign(DriveTrain driveTrain, Vision vision) {
    this.driveTrain = driveTrain;
    this.vision = vision;
    addRequirements(driveTrain);
    addRequirements(vision);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    double desiredDistance = Constants.VisionConstants.kSpeakerShootingDistance;
    double targetHeight = Constants.VisionConstants.kSpeakerTargetHeight;
    
    driveTrain.arcadeDrive(
      vision.calculateTransitionalOffset(desiredDistance, targetHeight), 
      vision.calculateRotationalOffsetSpeaker());

  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    double translationalErrorThreshold = Constants.VisionConstants.kTranslationalErrorThreshold;
    double rotationalErrorThreshold = Constants.VisionConstants.kRotationalErrorThreshold;

    // Gets updated values froom vision
    double[] values = vision.getValues();
    double roationalOffset = values[0];
    double distanceToSpeaer = values[4];

    //Check if values within error threshold
    return (roationalOffset >= -rotationalErrorThreshold && roationalOffset <= rotationalErrorThreshold) && 
    (distanceToSpeaer >= -translationalErrorThreshold && distanceToSpeaer <= translationalErrorThreshold);
    
  }
}
