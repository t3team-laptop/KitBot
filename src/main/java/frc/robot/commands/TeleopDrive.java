package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class TeleopDrive extends Command {
  
  private DriveTrain driveTrain;
  private DoubleSupplier translationSup;
  private DoubleSupplier rotationSup;

  public TeleopDrive(DriveTrain driveTrain, DoubleSupplier translationSup, DoubleSupplier rotationSup) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);

    this.translationSup = translationSup;
    this.rotationSup = rotationSup;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    driveTrain.arcadeDrive(translationSup.getAsDouble(), rotationSup.getAsDouble());
  }
  
}
