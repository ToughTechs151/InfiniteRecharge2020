/**
 * Coded by Seth White
 * Credit to WPI and ctre for their FIRST oriented objects.
 */
package frc.robot.subsystems;

import java.util.ArrayList;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

/**
 * Launcher subsystem
 */
public class LauncherSubsystem extends PIDSubsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static CANSparkMax launcher1 = new CANSparkMax(Constants.LAUNCHER1, MotorType.kBrushless);
  private static CANSparkMax launcher2 = new CANSparkMax(Constants.LAUNCHER2, MotorType.kBrushless);
  private static CANEncoder lEncoder = new CANEncoder(launcher1);
  private static CANEncoder lEncoder2 = new CANEncoder(launcher2);
  //private static Talon launcher1 = new Talon(Constants.Launcher1);
  //private static Talon launcher2 = new Talon(Constants.Launcher2);
  private double setpoint = 0,count=0;
  private boolean counting = true,checker=true;
  private Timer time=new Timer();
  private ArrayList<Double> prevOutput = new ArrayList<>();
  private static SpeedControllerGroup launcher = new SpeedControllerGroup(launcher1, launcher2);
  private ArrayList<Double> prevTime = new ArrayList<>();

  /**
   * constructs the launcher subsystem
   * 
   * @param inPID the pid controller for the launcher
   */
  public LauncherSubsystem(PIDController inPID) {
    super(inPID);
    launcher.setInverted(false);
    time.reset();
    for (int i = 0; i < 9; i++) {
      prevOutput.add(1.1 * i);
    }
    for (int i = 0; i < 9; i++) {
      prevTime.add(1.1 * i);
    }
  }

  /**
   * Sets the setpoint for the pid controller
   * 
   * @param set
   */
  public void setSetpoint(double set) {
    setpoint = set;
    count = 0;
    SmartDashboard.putBoolean("You may fire when ready", false);

    counting = true;
    checker = true;
    try {
      time.reset();
    } catch (Exception e) {
    }
    try {
      time.start();
    } catch (Exception e) {
    }

  }

  /**
   * runs periodically when enabled
   */
  public void periodic() {
    // setSetpoint(RobotContainer.coDriverOI.getY()); NEVER EVER DO THIS
    useOutput((lEncoder2.getVelocity() + lEncoder.getVelocity()) / 2, setpoint);
    SmartDashboard.putNumber("Launcher1Speed in RPM", lEncoder.getVelocity());
    SmartDashboard.putNumber("Launcher2Speed in RPM", lEncoder2.getVelocity());
    SmartDashboard.putNumber("Launcher Current 1", launcher1.getOutputCurrent());
    SmartDashboard.putNumber("Launcher Current 2", launcher2.getOutputCurrent());
    SmartDashboard.putNumber("LauncherSetpoint in RPM", setpoint);
    SmartDashboard.putNumber("Launcher1 get", launcher1.get());
    SmartDashboard.putNumber("Launcher2 get", launcher2.get());
  }

  private boolean check = true;

  @Override
  protected void useOutput(double output, double setpoint) {
    /*
     * if(time.get()<1.0){ setpoint=this.setpoint*time.get()/1.0; }
     */
    if (this.setpoint == 0) {
      setpoint = (getController().getSetpoint() * 0.9);
    }

    if (Math.abs(setpoint) >= Math.abs(getController().getSetpoint())) {
      setpoint = (getController().getSetpoint() * 10.0 / 9.0);
    } else if (Math.abs(setpoint) < Math.abs(getController().getSetpoint())) {
      setpoint = (getController().getSetpoint() * 0.9);
    }

    count += counting ? 1 : 0;
    if (setpoint >= this.setpoint * 0.98 && setpoint <= this.setpoint * 1.02) {
      setpoint = this.setpoint;
    } else if (setpoint <= 100 && setpoint >= -100) {
      setpoint = this.setpoint * .1;
    }

    output = getController().calculate(output, setpoint);
    launcher1.set(output * 0.9 / lEncoder.getVelocityConversionFactor());
    launcher2.set(output / lEncoder2.getVelocityConversionFactor());
    check = true;
    for (Double prev : prevOutput) {
      if (prev >= (this.setpoint) * 0.95 && prev <= (this.setpoint) * 1.05 && check) {
        check = true;
      } else {
        check = false;
      }
    }
    if (check && checker) {
      time.stop();
      SmartDashboard.putNumber("Loops", count);
      SmartDashboard.putNumber("Time to reach speed", time.get());
      SmartDashboard.putBoolean("You may fire when ready", true);
      counting = false;
      checker = false;
    }
    prevOutput.add(lEncoder.getVelocity());
    prevOutput.remove(0);
    prevTime.add(time.get());
    prevTime.remove(0);
  }

  @Override
  protected double getMeasurement() {
    return launcher.get();
  }

  /**
   * sets the launcher speed
   * 
   * @param s the speed to change to
   */
  public static void setSpeed(double s) {
    launcher.set(s / lEncoder.getVelocityConversionFactor());

  }
}
