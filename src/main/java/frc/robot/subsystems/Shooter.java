// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private CANSparkMax Motor0;
  //private CANSparkMax Motor1;
  
  public Shooter() {
    Motor0 = new CANSparkMax(Constants.ShooterConstants.kMotor_ID_0, MotorType.kBrushless);
    //Motor1 = new CANSparkMax(Constants.ShooterConstants.kMotor_ID_1, MotorType.kBrushed);
    Motor0.restoreFactoryDefaults();
    //Motor1.restoreFactoryDefaults();
    Motor0.setIdleMode(IdleMode.kCoast);
    //Motor1.setIdleMode(IdleMode.kCoast);

  }

  public void setShooterSpeed(double speed) {
    Motor0.set(speed);
    //Motor1.set(speed);
  }

  public void stopShooter() {
    Motor0.stopMotor();
    //Motor1.stopMotor();
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
