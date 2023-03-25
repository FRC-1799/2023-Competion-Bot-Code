// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.commands.auto.AutonomousBalanceMobile;
import frc.robot.commands.auto.AutonomousBalanceNoMobile;
import frc.robot.commands.auto.AutonomousGrab;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...


  final LimelightSubsystem limeLight = new LimelightSubsystem();
  final PneumaticsSubsytem pneumatics = new PneumaticsSubsytem();
  final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(pneumatics);
  final Bucket m_bucketSubsystem = new Bucket(pneumatics);
  final ToggleCompressor toggleCompressor = new ToggleCompressor(pneumatics);
  final Gyro gyro = new Gyro();

  final LimelightCommand limelightCommand = new LimelightCommand(limeLight);
  final IntakeCommand runIntake = new IntakeCommand(m_intakeSubsystem, Constants.intake.fwdSpeed);
  final IntakeCommand runIntakeBackward = new IntakeCommand(m_intakeSubsystem, Constants.intake.revSpeed);
  final ToggleBucketCommand toggleBucket = new ToggleBucketCommand(m_bucketSubsystem);
  final IntakeToggleCommand toggleIntake = new IntakeToggleCommand(m_intakeSubsystem);

  final AutonomousBalanceMobile m_autoCommand = new AutonomousBalanceMobile(m_driveSubsystem, m_intakeSubsystem,m_bucketSubsystem, gyro);

  final CommandXboxController movementJoystick = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController manipulatorJoystick = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    //start cameraServer
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();
    
    m_driveSubsystem.setDefaultCommand(
      new ArcadeDrive(
            m_driveSubsystem,
            () -> ((-movementJoystick.getLeftTriggerAxis() + movementJoystick.getRightTriggerAxis())),
            () -> (-movementJoystick.getLeftX() ),
            () -> (movementJoystick.x().getAsBoolean())
      ));


    limeLight.setDefaultCommand(limelightCommand);

    gyro.log();

    m_chooser.setDefaultOption("Simple Auto", m_autoBalancemobile);
    m_chooser.addOption("Complex Auto", m_complexAuto);
    m_chooser.addOption("Auto No Mobile", m_autoNoMobile);

    SmartDashboard.putData("autos: ", m_chooser);
  }

  private void configureButtonBindings() {
    manipulatorJoystick.leftBumper() //intake
    .whileTrue(runIntake);

    manipulatorJoystick.rightBumper() //outake
    .whileTrue(runIntakeBackward);

    manipulatorJoystick.x()
    .onTrue(toggleBucket);

    manipulatorJoystick.a()
    .onTrue(toggleIntake);

    manipulatorJoystick.y()
    .onTrue(toggleCompressor);
  }

  // Auto Stuff
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  private final Command m_autoBalancemobile =
  new AutonomousBalanceMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro);

// A complex auto routine that drives forward, drops a hatch, and then drives backward.
private final Command m_complexAuto =
 new AutonomousGrab(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem);

 private final Command m_autoNoMobile= 
 new AutonomousBalanceNoMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro);

// A simple auto routine that drives forward a specified distance, and then stops.
SendableChooser<Command> m_chooser = new SendableChooser<>();



  public Command getAutonomousCommand() {

    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
    return m_chooser.getSelected();
  }
}
