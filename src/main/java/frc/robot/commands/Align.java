package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;

public class Align extends Command {
  
  private DriveTrain driveTrain;
  private Vision vision;
  private boolean translated = false;
  private boolean rotated = false;

  public Align(DriveTrain driveTrain, Vision vision) {
    this.driveTrain = driveTrain;
    this.vision = vision;
    addRequirements(driveTrain);
    addRequirements(vision);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
      
    // if not rotated, run rotation pid
    if(!rotated) {
      driveTrain.arcadeDrive(0, vision.calculateRotationalOffset());
    }

    // if not translated, run translation pid
    if(!translated) {
      driveTrain.arcadeDrive(vision.calculateTransitionalOffset(), 0);
    }

    // is rotation within threshold?
    if(vision.rotationController.atSetpoint()) {
      rotated = true;
    }

    // is translation within threshold?
    if(vision.translationController.atSetpoint()) {
      translated = true;
    }

  }

  @Override
  public void end(boolean interrupted) {

    // stops drivetrain
    driveTrain.arcadeDrive(0, 0);

  }

  @Override
  public boolean isFinished() {

    // stop if translated and rotated
    return translated && rotated;
    
  }

}
