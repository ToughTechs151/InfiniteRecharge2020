/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;
import edu.wpi.first.wpilibj.controller.PIDController;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.EncoderType;

/**
 * Launcher subsystem
 */
public class LowerLauncherSubsystem extends PIDSubsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static PIDController pid;
  //private static CANSparkMax launcher1 = new CANSparkMax(Constants.LAUNCHER1, MotorType.kBrushless);
  private static CANSparkMax launcher2 = new CANSparkMax(Constants.LAUNCHER2, MotorType.kBrushless);
  //3/13/2021 Modifications to Encoder Declaration
  //private static CANEncoder Encoder2 = new CANEncoder(launcher2, EncoderType.kQuadrature, 42);
  // Original Encoder Declaration
  private static CANEncoder Encoder2 = new CANEncoder(launcher2);
  //private static Talon launcher1 = new Talon(Constants.Launcher1);
  //private static Talon launcher2 = new Talon(Constants.Launcher2);
  private double setpoint = 0;
  private double viewOutput = 0.0;
  //private static SpeedControllerGroup launcher = new SpeedControllerGroup(launcher1, launcher2);

  /**
   * constructs the launcher subsystem
   * 
   * @param inPID the pid controller for the launcher
   */
  public LowerLauncherSubsystem(PIDController inPID) {
    super(inPID);
    launcher2.setInverted(false);
  }

  /**
   * Sets the setpoint for the pid controller
   * 
   * @param set
   */
  /*public void setSetpoint(double set) {
    setpoint = set;
    if (set != 0) {
      getController().setSetpoint(setpoint);
    }
  }*/

  /**
   * runs periodically when enabled
   */
  public void periodic() {
    // setSetpoint(RobotContainer.coDriverOI.getY()); NEVER EVER DO THIS
    //useOutput(Encoder2.getVelocity(), setpoint);
    super.periodic();
    SmartDashboard.putNumber("LowerLauncherSpeed in RPM", Encoder2.getVelocity());
    SmartDashboard.putNumber("LowerLauncher Current", launcher2.getOutputCurrent());
    SmartDashboard.putNumber("LowerLauncherSetpoint in RPM", setpoint);
    SmartDashboard.putNumber("LowerLauncher get", launcher2.get());
    SmartDashboard.putNumber("LowerLauncher getCPR", Encoder2.getCountsPerRevolution());
    SmartDashboard.putNumber("LowerLauncher getPosition", Encoder2.getPosition());
    SmartDashboard.putNumber("LowerLauncher getVelocityConversionFactor", Encoder2.getVelocityConversionFactor());
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    launcher2.set(output);
    viewOutput = output;
    SmartDashboard.putNumber("LowerLauncher output value", viewOutput);
    /*if (Math.abs(setpoint) >= Math.abs(getController().getSetpoint())) {
      setpoint = (getController().getSetpoint() * 10.0 / 9.0);
      if (setpoint >= this.setpoint - 100 && setpoint <= this.setpoint + 100) {
        setpoint = this.setpoint;
      }
    } else if (Math.abs(setpoint) < Math.abs(getController().getSetpoint())) {
      setpoint = (getController().getSetpoint() * 0.9);
      if (setpoint >= this.setpoint - 100 && setpoint <= this.setpoint + 100) {
        setpoint=this.setpoint;
      }
    }
    output=getController().calculate(output,setpoint)/Encoder2.getVelocityConversionFactor();
    //launcher1.set(output*0.95);
    launcher2.set(output);*/
    
  }
  @Override
  protected double getMeasurement() {
	  return Encoder2.getVelocity();
  }

  public void mySetSetpoint(double mySetpoint){
    m_enabled = true;
    setSetpoint(mySetpoint);
  }

  public void disableM_Enabled(){
    setSetpoint(0);
    m_enabled = false;
  }

  /**
   * sets the lausncher speed
   * @param s the speed to change to
   */
  /*public static void setSpeed(double s){
    launcher2.set(s/Encoder2.getVelocityConversionFactor());

  }*/
}
