package frc.robot.commands.auto;

import java.util.HashMap;

import com.pathplanner.lib.auto.RamseteAutoBuilder;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveBase;


public class AutoTest extends RamseteAutoBuilder{
  public AutoTest(DriveBase drive) {
    super(
      drive::getPose, 
      drive::resetOdometry,
      new RamseteController(),
      drive.kinematics,
      drive::tankDriveVolts,
      createMap(
      ),
      drive
      
    );
  }

  static HashMap<String,Command> createMap(){
    HashMap<String,Command> map = new HashMap<>();
    map.put("halfway",new InstantCommand(()->System.out.println("halfway")));
    return map;
  }
}