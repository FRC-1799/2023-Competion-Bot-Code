// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousBalanceNoMobile extends SequentialCommandGroup {
  /*
   * pseudoCode:
   * humans will position robot
   * milk crate will tip, releasing cube into lowest zone
   * robot drives forward, getting more auto points
   */

  // Subsystem to Dump Cargo then go forward over charge station
  // and then back up onto charge system to attempt balance
  public AutonomousBalanceNoMobile(DriveSubsystem drive, IntakeSubsystem intake, Bucket bucket, Gyro gyro) {
    super(
      new WaitCommand(2),
      //dump game piece
      bucket.set(DoubleSolenoid.Value.kForward),
      new WaitCommand(1),
      //pick up milk crate
      bucket.set(DoubleSolenoid.Value.kReverse),
      new WaitCommand(1),
      //go forward onto charge station
      new DriveStraight(drive, 2,Constants.auto.fwdSpeed),
      // begin balancing
      new Balance(drive, gyro)
    );
  }
}
