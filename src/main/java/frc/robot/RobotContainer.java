package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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
    private final XboxController armDriver = new XboxController(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(baseDriver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(baseDriver, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    private final Shooter shooter = new Shooter();

    /* Commands */
    private final ExampleCommand exampleCommand;
    private final Shoot shoot;
    private final Intake intake;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        //groundIntake.setDefaultCommand(
        //    new ManualPivotIntake(
        //        groundIntake, 
        //        () -> armDriver.getRawAxis(translationAxis)));
        
        exampleCommand = new ExampleCommand(exampleSubsystem);
        exampleCommand.addRequirements(exampleSubsystem);

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
 
         // Declare Arm Controller Buttons
         AA = new JoystickButton(armDriver, 1);
         AB = new JoystickButton(armDriver, 2);
         AX = new JoystickButton(armDriver, 3);
         AY = new JoystickButton(armDriver, 4);
         ALB = new JoystickButton(armDriver, 5);
         ARB = new JoystickButton(armDriver, 6);
         AM1 = new JoystickButton(armDriver, 8);
         AM2 = new JoystickButton(armDriver, 10);

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
        //DLB.whileTrue(leftClimberDown);
        DA.whileTrue(shoot);
        DB.whileTrue(intake);
      }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return null;
       //return new PathPlannerAuto("Leave Zone");
    }
}