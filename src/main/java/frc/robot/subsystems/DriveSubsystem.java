package frc.robot.subsystems;

import frc.robot.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Watchdog;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The subsystem for the main drivetrain
 */
public class DriveSubsystem extends SubsystemBase {
  private WPI_VictorSPX frontRight;
  private WPI_VictorSPX backRight;
  private WPI_VictorSPX frontLeft;
  private WPI_VictorSPX backLeft;
  private SpeedControllerGroup left;
  private SpeedControllerGroup right;
  /**
   * The subsystem for the main drivetrain's constructor
   */
  public DriveSubsystem(){
    //Assign drive motors
    frontRight = new WPI_VictorSPX(Constants.FRONT_RIGHT);
    backRight = new WPI_VictorSPX(Constants.BACK_RIGHT);
    frontLeft = new WPI_VictorSPX(Constants.FRONT_LEFT);
    backLeft = new WPI_VictorSPX(Constants.BACK_LEFT);
    //Group drive motors based on location  
    right = new SpeedControllerGroup(frontRight, backRight);
    left = new SpeedControllerGroup(frontLeft, backLeft);

    driveTrain = new DifferentialDrive(right, left);
    //adjust for which side of the robot should be front.
    left.setInverted(true);
    right.setInverted(true);
    
  }
  private static double mechDeadband = 0.1;
  private static double softwareDeadband = 0.05;
  public DifferentialDrive driveTrain = null;
  static double speedMultiplier = 1.0;
  static double normal = 1.0;
  static double crawl = .5;
  static double rightForward = 0.94;
  static double rightBackward = 0.9941439284;
  static double leftForward = 0.9662447257;
  static double leftBackward = 1.0;

  /**
   * called to drive the robot
   * 
   * @param x left wheels
   * @param y right wheels
   */
  public void drive(double x, double y) {
    
    driveTrain.tankDrive(deadzone(x),deadzone(y));
  }

  public void periodic(){
    //for(int c=0;c<1;c++){
    //  SmartDashboard.putNumber("Port "+0, pdp.getCurrent(0));
    //}
  }
  private double leftDrive;
  private double rightDrive;

  /**
   * A set of instructions to interpret input
   * 
   * @param oi the driver
   */
  public void driveTank(Joystick oi) {
    // check to see if the robot should drive slower

    speedMultiplier = oi.getRawButton(Constants.RIGHT_BUMPER) ? crawl : normal;

    leftDrive = deadzone(Math.sqrt(0.6)*oi.getRawAxis(Constants.LEFT_JOYSTICK_Y)+oi.getRawAxis(3)-oi.getRawAxis(2));
    rightDrive = deadzone(Math.sqrt(0.6)*oi.getRawAxis(Constants.RIGHT_JOYSTICK_Y)+oi.getRawAxis(3)-oi.getRawAxis(2));    
    if (deadzone(leftDrive) > 0.875)
      leftDrive*=leftBackward;
      //leftDrive = (1 - mechDeadband) / Math.pow(1 - softwareDeadband, 2) * Math.pow(leftDrive - softwareDeadband, 2)+ mechDeadband;
    else if (deadzone(leftDrive) < -0.875)
      leftDrive*=leftForward;
      //leftDrive = (-1 + mechDeadband) / Math.pow(-1 + softwareDeadband, 2) * Math.pow(leftDrive + softwareDeadband, 2)- mechDeadband;
    if (deadzone(rightDrive) > 0.875)
      rightDrive*=rightBackward;
      //rightDrive = (1 - mechDeadband) / Math.pow(1 - softwareDeadband, 2) * Math.pow(rightDrive - softwareDeadband, 2)+ mechDeadband;
    else if (deadzone(rightDrive) < -0.875)
      rightDrive*=rightForward;
      //rightDrive = (-1 + mechDeadband) / Math.pow(-1 + softwareDeadband, 2) * Math.pow(rightDrive + softwareDeadband, 2)- mechDeadband;
    // */
    drive(leftDrive * speedMultiplier, rightDrive * speedMultiplier);
  }
  public void adjust(double adjust){
    leftDrive*=.5;
    rightDrive*=.5;
    adjust=adjust < 0.3 ? adjust : 0.3;
    adjust=adjust > -0.3 ? adjust : -0.3;
    leftDrive+=adjust;
    rightDrive-=adjust;
    
  }

  /**
   * checks value against software deadband to avoid minor variations in joystick
   * position
   * 
   * @param val The input of the joystick
   */
  public static double deadzone(double val) {
    if (Math.abs(val) > softwareDeadband && Math.abs(val) < mechDeadband) {
      val*=mechDeadband/Math.abs(val);
    }
    else if(val>1){
      val=1;
    }
    else if(val<-1){
      val=-1;
    }
    return Math.abs(val) > softwareDeadband ? val : 0;
}


}
