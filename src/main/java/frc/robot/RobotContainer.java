package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Button Labels */
    JoystickButton DA, DB, DX, DY, DLB, DRB, DRT, DLT, DM1, DM2;
    JoystickButton AA, AB, AX, AY, ALB, ARB, ALT, ART, AM1, AM2;

    /* Controllers */
    private final XboxController baseDriver = new XboxController(0);
    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */    
    private final JoystickButton intakeButton = new JoystickButton(baseDriver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton outtakeButton = new JoystickButton(baseDriver, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private final Shooter shooter = new Shooter();
    private final DriveTrain driveTrain = new DriveTrain();

    /* Commands */
    private final Shoot shoot;
    private final Intake intake;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        driveTrain.setDefaultCommand(
        new RunCommand(
            () ->
                driveTrain.arcadeDrive(
                    -baseDriver.getRawAxis(translationAxis), baseDriver.getRawAxis(rotationAxis)),
            driveTrain));

        shoot = new Shoot(shooter);
        shoot.addRequirements(shooter);
        intake = new Intake(shooter);
        intake.addRequirements(shooter);

         // Declare Driver Controller Buttons
        DA = new JoystickButton(baseDriver, 1);
        DB = new JoystickButton(baseDriver, 2);
        DX = new JoystickButton(baseDriver, 3);
        DY = new JoystickButton(baseDriver, 4);
        DLB = new JoystickButton(baseDriver, 5);
        DRB = new JoystickButton(baseDriver, 6);
        DM1 = new JoystickButton(baseDriver, 7);
        DM2 = new JoystickButton(baseDriver, 8);
 
        //NamedCommands.registerCommand("shoot", shootIntoSpeaker);


        // Configure the button bindings
        configureButtonBindings();
/* 
        SmartDashboard.putData("On-the-fly path", Commands.runOnce(() ->{
            Pose2d currentPose = s_Swerve.getPose();

            Pose2d startPos = new Pose2d(currentPose.getTranslation(), new Rotation2d());
            Pose2d endPos = new Pose2d(currentPose.getTranslation().plus(new Translation2d(2,0)), new Rotation2d());
            List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(startPos, endPos);

            PathPlannerPath path = new PathPlannerPath(
            bezierPoints,
             new PathConstraints(3, 3, 2*Math.PI, 4*Math.PI),
             new GoalEndState(0, Rotation2d.fromDegrees(0))
            );
            path.preventFlipping = true;

            AutoBuilder.followPath(path).schedule();
        }));
        */
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {         
        intakeButton.whileTrue(intake);
        outtakeButton.whileTrue(shoot);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return Autos.exampleAuto(driveTrain);
    }
}