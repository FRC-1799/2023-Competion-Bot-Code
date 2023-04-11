// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // drive Constants
    public static final class drive {

        // f: front
        // t: top
        // r: rear

        // left
        public static final int lf = 3;
        public static final int lt = 4;
        public static final int lr = 9;

        // right
        public static final int rf = 7;
        public static final int rt = 5;
        public static final int rr = 8;
        
        public static final double rotationSpeedRatio= 0.6;
        public static final double driveSpeedRatio= 1;

        public static final double bodyWidthMeters = 0.4572;
        public static final double bodyLengthMeters = 0.81915;
        public static final double trackWidthMeters = 0.3556;
        
        public static final int encoderCPR = 1024;
        public static final double wheelDiameterMeters = 0.1524;
        public static final double gearRatio = 10.75;
        public static final double encoderDistancePerPulse = 
            // distancePerPulse is in meters/pulse
            ((wheelDiameterMeters * Math.PI) / (double) encoderCPR) /  gearRatio;
        
    }

    // Changing Solenoid Values idk 50/50 this'll work
    public static final class bucket {
        public static final class solenoid {
            public static final int fwdPort = 2;
            public static final int revPort = 3;

        }
    }

    public static final class intake{
        public static final class solenoid {
            public static final int fwdPort = 0;
            public static final int revPort = 1;

        }
        public static final int motor1 = 6;
        public static final int motor2 = 11;
        
        //reversed
        public static final double fwdSpeed = 0.8;
        public static final double revSpeed = -1;
    }

    public static final class auto{
        public static final double fwdSpeed = -0.6;
        public static final double revSpeed = 0.4;

        public static final class balancePID{
            public static final double kP = 0.12;
            public static final double kI = 0;
            public static final double kD = 0.1;
            public static final double outputMax = 1;
            public static final double outputMin = -1;

            public static final double positionTolerance = 2;
            public static final double velocityTolerance = 2;
        }
    }

    public static final int MOVEMENT_JOYSTICK = 0;
    public static final int MANIPULATOR_JOYSTICK = 1;
    public static double driveSpeedRatio;

}
