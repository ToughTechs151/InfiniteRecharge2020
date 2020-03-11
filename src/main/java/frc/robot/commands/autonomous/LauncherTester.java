package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ChangeLauncherSpeedCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.LauncherSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class LauncherTester extends CommandGroupBase {
    private CommandGroupBase auto;
    private boolean done = false;

    public LauncherTester(DriveSubsystem drive, LauncherSubsystem launcherSubsystem, LimeLightSubsystem lime,
            HopperSubsystem hopperSubsystem) {
      addRequirements(drive);
      addRequirements(launcherSubsystem);
      addRequirements(lime);
      addRequirements(hopperSubsystem);
      addCommands(new ChangeLauncherSpeedCommand(2750,launcherSubsystem),new WaitCommand(4),new ChangeLauncherSpeedCommand(0, launcherSubsystem));
   }

   public void inititialize(){
   }

   @Override
   public void addCommands(Command... commands) {
      auto=sequence(commands);
   }
   public void execute(){
      if(!done){
         auto.schedule();
         done=true;
      }
   }
   public boolean isFinished(){
      return done;
   }
   public void end(boolean interrupted){
      done=false;
   }

 }