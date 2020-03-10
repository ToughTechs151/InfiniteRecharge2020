package frc.robot.commands;

import java.util.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class HangReachCommand extends CommandBase{
    private HangSubsystem hang;
    private Timer time=new Timer();
    private boolean fin;
    public HangReachCommand(HangSubsystem hang){
        this.hang=hang;
        fin=false;
        addRequirements(hang);
    }
    public void execute(){
        hang.reach();
        /*if(hang.hanger.getSelectedSensorPosition()>=hang.startpoint+10000);
            fin=true;*/
        /*
            try{time.schedule(new TimerTask(){

            @Override
            public void run() {
                fin=true;

            }
            
        }, 10000);
        }
        catch(Exception e){
            
        }*/
        
    }
    @Override
    public boolean isFinished() {
        return fin;
    }
    public void end(boolean interrupted){
        hang.stop();
        try {
            time.cancel();
        } catch (Exception e) {
            //TODO: handle exception
        }
        fin=false;
    }
}