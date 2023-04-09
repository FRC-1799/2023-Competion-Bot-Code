package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSubsystem extends SubsystemBase {

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
  private final AHRS gyro = new AHRS();

  DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(
    Constants.drive.trackWidthMeters
  );

  DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(
    gyro.getRotation2d(), 
    getLeftDistance(),
    getRightDistance()
  );

  public DriveSubsystem() {
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

    gyro.calibrate();

    addChild("Drive", m_RobotDrive);
    addChild("Gyro", gyro);
  }

  public Pose2d getPose(){
    return odometry.getPoseMeters();
  }

  /**
   * @return the average position of the left encoders
   */
  public double getLeftDistance(){
    return leftEncoders[0].getPosition();
  }
  /**
   * @return the average position of the right encoders
   */
  public double getRightDistance(){
    return rightEncoders[0].getPosition();
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
