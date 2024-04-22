package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class RobotContainer {

    /* Controllers */
    private final CommandXboxController baseDriver = new CommandXboxController(Constants.ControllerConstants.kDriverControllerPort);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Subsystems */
    private final Shooter shooter = new Shooter();
    private final DriveTrain driveTrain = new DriveTrain();
    private final Vision vision = new Vision();

    /* Commands */
    private final ShootAmp shootAmp;
    private final ShootSpeaker shootSpeaker;
    private final Intake intake;
    private final Feed feed;

    /* Autos */
    private final SendableChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        driveTrain.setDefaultCommand(
            new TeleopDrive(
                driveTrain, 
                () -> -baseDriver.getRawAxis(translationAxis), 
                () -> -baseDriver.getRawAxis(rotationAxis)
            )
        );

        feed = new Feed(shooter);
        feed.addRequirements(shooter);
        shootAmp = new ShootAmp(shooter);
        shootAmp.addRequirements(shooter);
        shootSpeaker = new ShootSpeaker(shooter);
        shootSpeaker.addRequirements(shooter);
        intake = new Intake(shooter);
        intake.addRequirements(shooter);

        configureButtonBindings();
        
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    private void configureButtonBindings() {         
        baseDriver.y().whileTrue(intake);
        baseDriver.leftTrigger(0.15).whileTrue(shootAmp);
        baseDriver.rightTrigger(0.15).whileTrue(shootSpeaker);
        baseDriver.leftBumper().whileTrue(feed);

        baseDriver.a().whileTrue(new TeleopDrive(
                driveTrain,
                () -> -vision.calculateTransitionalOffsetSpeaker() - baseDriver.getRawAxis(translationAxis),
                () -> -vision.calculateRotationalOffsetSpeaker() - baseDriver.getRawAxis(rotationAxis)
        ));
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

}