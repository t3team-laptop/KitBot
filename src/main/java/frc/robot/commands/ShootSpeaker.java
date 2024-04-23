package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class ShootSpeaker extends Command {

  private Shooter shooter;

  public ShootSpeaker(Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    shooter.setTopMotorSpeed(Constants.ShooterConstants.kShooterSpeakerSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stopTopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
  
}
