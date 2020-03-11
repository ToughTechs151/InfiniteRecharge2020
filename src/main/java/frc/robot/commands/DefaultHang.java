/**
 * Coded by Seth White
 * Credit to WPI and ctre for their FIRST oriented objects.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HangSubsystem;

public class DefaultHang extends CommandBase {
    private HangSubsystem hang;

    public DefaultHang(HangSubsystem hang) {
        this.hang=hang;
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