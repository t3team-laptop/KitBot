package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    /* Controllers */
    private final CommandXboxController baseDriver = new CommandXboxController(Constants.ControllerConstants.kDriverControllerPort);
    private final CommandXboxController shooterOperator = new CommandXboxController(Constants.ControllerConstants.kOperatorControllerPort);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Subsystems */
    private final Shooter shooter = new Shooter();
    private final DriveTrain driveTrain = new DriveTrain();

    /* Commands */
    private final ShootAmp shootAmp;
    private final ShootSpeaker shootSpeaker;
    private final Intake intake;
    private final Feed feed;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        driveTrain.setDefaultCommand(
        new RunCommand(
            () ->
                driveTrain.arcadeDrive(
                    -baseDriver.getRawAxis(translationAxis), baseDriver.getRawAxis(rotationAxis)),
            driveTrain));

        feed = new Feed(shooter);
        feed.addRequirements(shooter);
        shootAmp = new ShootAmp(shooter);
        shootAmp.addRequirements(shooter);
        shootSpeaker = new ShootSpeaker(shooter);
        shootSpeaker.addRequirements(shooter);
        intake = new Intake(shooter);
        intake.addRequirements(shooter);

        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {         
        shooterOperator.y().whileTrue(intake);
        shooterOperator.leftTrigger(0.15).whileTrue(shootAmp);
        shooterOperator.rightTrigger(0.15).whileTrue(shootSpeaker);
        shooterOperator.leftBumper().whileTrue(feed);
    }

}