package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class Feed extends Command {

  private Shooter shooter;
  public Feed(Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    shooter.setBottomMotorSpeed(Constants.ShooterConstants.kFeederSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stopBottomMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
