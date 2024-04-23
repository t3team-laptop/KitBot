package frc.robot.subsystems;

import static frc.robot.Constants.DrivetrainConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  
  DifferentialDrive driveTrain;
  private CANSparkMax leftFront;
  private CANSparkMax leftRear;
  private CANSparkMax rightFront;
  private CANSparkMax rightRear;

  public DriveTrain() {
    leftFront = new CANSparkMax(kLeftFrontID, MotorType.kBrushed);
    leftRear = new CANSparkMax(kLeftRearID, MotorType.kBrushed);
    rightFront = new CANSparkMax(kRightFrontID, MotorType.kBrushed);
    rightRear = new CANSparkMax(kRightRearID, MotorType.kBrushed);

    leftFront.setSmartCurrentLimit(kCurrentLimit);
    leftRear.setSmartCurrentLimit(kCurrentLimit);
    rightFront.setSmartCurrentLimit(kCurrentLimit);
    rightRear.setSmartCurrentLimit(kCurrentLimit);

    leftRear.follow(leftFront);
    rightRear.follow(rightFront);

    leftFront.setInverted(true);
    rightFront.setInverted(false);

    driveTrain = new DifferentialDrive(leftFront, rightFront);
  }

  public void arcadeDrive(double speed, double rotation) {
    driveTrain.arcadeDrive(speed, rotation*-1);
  }

  @Override
  public void periodic() {}

}
