package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import java.lang.AutoCloseable;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.PowerDistribution;

public class DriveSubsystem extends SubsystemBase {

  final MotorControllerGroup leftMotors = new MotorControllerGroup(
      new CANSparkMax(Constants.drive.lt, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.lr, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.lf, MotorType.kBrushless));

  final MotorControllerGroup rightMotors = new MotorControllerGroup(
      new CANSparkMax(Constants.drive.rt, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.rr, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.rf, MotorType.kBrushless));

  final DifferentialDrive m_RobotDrive;
  PowerDistribution PDP = new PowerDistribution();

  public DriveSubsystem() {
    leftMotors.setInverted(true);
    //m_RobotDrive = new DifferentialDrive(rightMotors, leftMotors)
    m_RobotDrive = new DifferentialDrive(leftMotors, rightMotors);

    addChild("Drive", m_RobotDrive);
    
  }
public void CheckPull() {
    for (long canID = 12; canID<19; canID++){
        if (PDP.getCurrent((int) (canID))>45){
          c_SparkMax_SetSmartCurrentLimit​(canID, 45, 45, 45);
        }
    }
}
  public void drive(final double ySpeed, final double rotateValue) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }
}
