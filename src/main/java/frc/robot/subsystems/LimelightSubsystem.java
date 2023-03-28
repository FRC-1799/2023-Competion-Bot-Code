package frc.robot.subsystems;

/**
 * @author: @bakedPotatoLord
 * this code was blatantly stolen from team 7243. no apologies.
 */

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	NetworkTableEntry tx = table.getEntry("tx");
	NetworkTableEntry ty = table.getEntry("ty");
	NetworkTableEntry ta = table.getEntry("ta");

	public LimelightSubsystem() {
		System.out.println("Limelight started");

		setDefaultCommand(
			new RunCommand(    
				() -> {
					SmartDashboard.putNumber("x", getX());
					SmartDashboard.putNumber("y", getY());
					SmartDashboard.putNumber("a", getA());
				}
			, 
			this
		)
		);
	}

	public double getX() {
		return tx.getDouble(0);
	}

	public double getY() {
		return ty.getDouble(0);
	}

	public double getA() {
		return ta.getDouble(0);
	}
}
