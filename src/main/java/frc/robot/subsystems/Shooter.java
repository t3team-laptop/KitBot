// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private CANSparkMax TopMotor;
  private CANSparkMax BottomMotor;
  
  public Shooter() {

    //define
    TopMotor = new CANSparkMax(Constants.ShooterConstants.kMotor_ID_TOP, MotorType.kBrushed);
    BottomMotor = new CANSparkMax(Constants.ShooterConstants.kMotor_ID_BOTTOM, MotorType.kBrushed);

    //restore defaults
    TopMotor.restoreFactoryDefaults();
    BottomMotor.restoreFactoryDefaults();

    //idle mode
    TopMotor.setIdleMode(IdleMode.kCoast);
    BottomMotor.setIdleMode(IdleMode.kCoast);

  }

  public void setShooterSpeed(double speed) {
    TopMotor.set(speed*-1);
    BottomMotor.set(speed*-1);
  }

  public void stopShooter() {
    TopMotor.stopMotor();
    BottomMotor.stopMotor();
  }

  public void setTopMotorSpeed(double speed) {
    TopMotor.set(speed*-1);
  }

  public void setBottomMotorSpeed(double speed) {
    BottomMotor.set(speed*-1);
  }

  public void stopTopMotor() {
    TopMotor.stopMotor();
  }

  public void stopBottomMotor() {
    BottomMotor.stopMotor();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
