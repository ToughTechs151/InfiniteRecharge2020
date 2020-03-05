package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import java.util.Timer;
import java.util.TimerTask;

public class DefaultDrive extends CommandBase{
    private DriveSubsystem drive;
    private double speed;
    private boolean fin = false;
    private Timer time;
    public DefaultDrive(DriveSubsystem drive, double speed){
        this.drive=drive;
        this.speed=speed;
        addRequirements(drive);
    }
    @Override
    public void execute() {
        drive.drive(speed, speed);
        try{time.schedule(new TimerTask(){

            @Override
            public void run() {
                fin=true;

            }
            
        }, 3000);
        }
        catch(Exception e){
        
        }
    }
    @Override
    public boolean isFinished() {
        return fin;
    }
    public void execute(boolean interrupted){

    }
}