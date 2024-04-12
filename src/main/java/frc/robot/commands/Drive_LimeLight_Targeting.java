// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.limelight.LimeLight;
import frc.robot.subsystems.DriveTrain;

public class Drive_LimeLight_Targeting extends Command {
  private final DriveTrain m_subsystem;
  private DoubleSupplier m_speed;
  private DoubleSupplier m_turn;
  private LimeLight m_limelight;

  /** Creates a new Drive_LimeLight_Targeting. */
  public Drive_LimeLight_Targeting(DoubleSupplier speed, DoubleSupplier turn ,LimeLight limelight ,DriveTrain subsystem) {
    m_speed = speed;
    m_turn = turn;
    m_limelight = limelight;
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double kp = .05;
    double xSpeed = m_speed.getAsDouble();
    double zRotation;
    if (m_limelight.getIsTargetFound()){
      zRotation = m_limelight.getdegRotationToTarget() * kp;
      SmartDashboard.putBoolean("Auto Alignment", true);
    }else{
      zRotation = m_turn.getAsDouble();
      SmartDashboard.putBoolean("Auto Alignment", false);
    }
    m_subsystem.arcadeDrive(xSpeed, zRotation);
    SmartDashboard.putNumber("Degree Rotation To Target", zRotation);
    SmartDashboard.putNumber("Speed", xSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.arcadeDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
