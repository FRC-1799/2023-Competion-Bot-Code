package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveStraight extends RunCommand {

  static PIDController controller = new PIDController(
    Constants.drive.driveForwardsPID.kP,
    Constants.drive.driveForwardsPID.kI,
    Constants.drive.driveForwardsPID.kD
  );

  public DriveStraight(DriveSubsystem driveSubsystem, double distance ) {
    super(
      ()->{
        driveSubsystem.drive(
          MathUtil.clamp(
            controller.calculate(driveSubsystem.getAveragePosition()),
            Constants.drive.driveForwardsPID.outputMin,
            Constants.drive.driveForwardsPID.outputMax
          )
          ,0);
      }
    );
    
    controller.setSetpoint(distance);
    controller.setTolerance(
      Constants.drive.driveForwardsPID.positionTolerance,
      Constants.drive.driveForwardsPID.velocityTolerance
    );
    addRequirements(driveSubsystem);
  }

  public boolean isFinished() {
    return controller.atSetpoint();
  }
}
