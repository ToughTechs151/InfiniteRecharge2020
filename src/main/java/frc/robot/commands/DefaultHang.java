package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;
import java.util.Timer;
import java.util.TimerTask;

public class DefaultHang extends CommandBase {
    private HangSubsystem hang;
    private Timer time = new Timer();
    private boolean fin;

    public DefaultHang(HangSubsystem hang) {
        this.hang=hang;
        fin=false;
        addRequirements(hang);
    }
    public void execute(){
        hang.stop();
        
    }
    @Override
    public boolean isFinished() {
        return false;
    }
    public void end(boolean interrupted){
    }
}