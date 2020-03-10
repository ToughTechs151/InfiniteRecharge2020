package frc.robot.commands;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class LimelightCommand extends PIDCommand {
    private static LimeLightSubsystem limelight = RobotContainer.m_LimeLightSubsystem;
    private static DriveSubsystem drive = RobotContainer.m_driveSubsystem;
    private static PIDController pidController = new PIDController(0.125, 0.0001, 0);
    private static DoubleSupplier doubleSupplier=new DoubleSupplier(){
    
        @Override
        public double getAsDouble() {
            return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        }
    };
    private static DoubleConsumer doubleConsumer = new DoubleConsumer(){
    
        @Override
        public void accept(double arg0) {
            drive.adjust(arg0);
        }
    };
    private boolean fin = false;

    public LimelightCommand() {
        super(pidController,  doubleSupplier, 0, doubleConsumer, limelight);
    }

    @Override
    public void execute(){
        double adjust=pidController.calculate(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
        doubleConsumer.accept(adjust);
        if(adjust==0){
            fin=true;
        }
    }
    @Override
    public boolean isFinished(){
        return fin;
    }
    @Override
    public void end(boolean interrupted){

    }
}
