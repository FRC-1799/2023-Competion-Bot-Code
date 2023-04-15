package frc.robot.commands.auto;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.commands.PPRamseteCommand;

import edu.wpi.first.math.controller.RamseteController;

import frc.robot.subsystems.DriveBase;

public class AutoPath0 extends PPRamseteCommand {
  


  public AutoPath0(DriveBase drive) {
    super(
      PathPlanner.loadPath(
  "curve-test",
        new PathConstraints(4, 3)
      ),
      drive::getPose,
      new RamseteController(),
      drive.kinematics, 
      drive::tankDriveVolts,
      drive
    );

  }
}