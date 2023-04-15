package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;

public class DriveStraight extends RunCommand {

  private final DriveBase drive;

  static PIDController controller = new PIDController(
    Constants.drive.driveForwardsPID.kP,
    Constants.drive.driveForwardsPID.kI,
    Constants.drive.driveForwardsPID.kD
  );

  public DriveStraight(DriveBase driveSubsystem, double distance ) {
    super(
      ()->{
        double speed = MathUtil.clamp(
          controller.calculate(driveSubsystem.getAvgDistance()),
          Constants.drive.driveForwardsPID.outputMin,
          Constants.drive.driveForwardsPID.outputMax
        );
        driveSubsystem.tankDrive(
          speed*Constants.drive.leftSpeedMultiplier,
          speed*Constants.drive.rightSpeedMultiplier
        );
      }
    );
    this.drive = driveSubsystem;
    
    controller.setSetpoint(distance);
    controller.setTolerance(
      Constants.drive.driveForwardsPID.positionTolerance,
      Constants.drive.driveForwardsPID.velocityTolerance
    );
    addRequirements(driveSubsystem);
  }

  public void initialize() {
    drive.resetEncoders();
  }

  public boolean isFinished() {
    return controller.atSetpoint();
  }
}
