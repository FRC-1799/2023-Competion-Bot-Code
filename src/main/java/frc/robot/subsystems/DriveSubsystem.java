package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSubsystem extends SubsystemBase {

  public final Gyro gyro;

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

  RelativeEncoder[] leftEncoders = {
    leftMotors[0].getEncoder(),
    leftMotors[1].getEncoder(),
    leftMotors[2].getEncoder()
  };
  RelativeEncoder[] rightEncoders = {
    rightMotors[0].getEncoder(),
    rightMotors[1].getEncoder(),
    rightMotors[2].getEncoder()
  };

  final DifferentialDrive m_RobotDrive = new DifferentialDrive(
    new MotorControllerGroup(leftMotors),
    new MotorControllerGroup(rightMotors)
  );

  public DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(
    Constants.drive.trackWidthMeters
  );

  public DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(
    new Rotation2d(), 
    getLeftDistance(),
    getRightDistance()
  );

  public final PIDController leftPID = new PIDController(
    Constants.drive.kP_left,
    Constants.drive.kI_left,
    Constants.drive.kD_left
  );

  public final PIDController rightPID = new PIDController(
    Constants.drive.kP_right,
    Constants.drive.kI_right,
    Constants.drive.kD_right
  );

  public DriveSubsystem(Gyro gyro) {
    this.gyro = gyro;
    addChild("Drive", m_RobotDrive);

  }

  public Pose2d getPose(){
    return odometry.getPoseMeters();
  }


  public void resetOdometry(Pose2d pose){
    odometry.resetPosition(
      gyro.getHeading(),
      getLeftDistance(),
      getRightDistance(),
      pose
    );
  }

  public void updateOdometry(){
    odometry.update(
      gyro.getHeading(),
      getLeftDistance(),
      getRightDistance()
    );
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

  public void drive(double ySpeed, double rotateValue) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_RobotDrive.tankDrive(
      leftSpeed,
      rightSpeed
    );
  }

  public void tankDriveVolts(double leftSpeed, double rightSpeed) {
    m_RobotDrive.tankDrive(
      leftSpeed/12,
      rightSpeed/12
    );
  }

  public void periodic(){
    updateOdometry();
  }
}
