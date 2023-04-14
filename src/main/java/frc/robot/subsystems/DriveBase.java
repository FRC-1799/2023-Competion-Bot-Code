package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveBase extends SubsystemBase {

  final CANSparkMax[] leftMotors = {
    new CANSparkMax(Constants.drive.lt, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.lr, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.lf, MotorType.kBrushless)
  };

  final CANSparkMax[] rightMotors = {
    new CANSparkMax(Constants.drive.rt, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.rr, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.rf, MotorType.kBrushless)
  };

  RelativeEncoder[] leftEncoders = new RelativeEncoder[3];
  RelativeEncoder[] rightEncoders = new RelativeEncoder[3];

  final DifferentialDrive m_RobotDrive;

  public DriveBase() {
    //leftMotors.setInverted(true);
    //m_RobotDrive = new DifferentialDrive(rightMotors, leftMotors)
    m_RobotDrive = new DifferentialDrive(
      new MotorControllerGroup(leftMotors),
      new MotorControllerGroup(rightMotors)
    );

    //fill encoder array
    for(int i=0;i<rightMotors.length;i++){
      leftEncoders[i] = leftMotors[i].getEncoder();
      rightEncoders[i] = rightMotors[i].getEncoder();
    }

    addChild("Drive", m_RobotDrive);
  }

  /**
   * @return the average position of the left encoders
   */
  public double getAveragePositionLeft(){
    double tmp = 0;
    for(RelativeEncoder e : leftEncoders){
      tmp+= e.getPosition();
    }
    return tmp;
  }
  /**
   * @return the average position of the right encoders
   */
  public double getAveragePositionRight(){
    double tmp = 0;
    for(RelativeEncoder e : rightEncoders){
      tmp+= e.getPosition();
    }
    return tmp;
  }

  /**
   * @return the average position of all encoders
   */
  public double getAveragePosition(){
    return (getAveragePositionLeft() + getAveragePositionRight()) /2;
  }

  public void resetEncoders(){
    for(RelativeEncoder e : leftEncoders){
      e.setPosition(0);
    }
    for(RelativeEncoder e : rightEncoders){
      e.setPosition(0);
    }
  }


  public void drive(final double ySpeed, final double rotateValue) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }
}
