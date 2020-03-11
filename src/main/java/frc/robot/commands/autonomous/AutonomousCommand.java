package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.AdjustLauncherCommand;
import frc.robot.commands.ChangeLauncherSpeedCommand;
import frc.robot.commands.DriveSoloCommand;
import frc.robot.commands.HopperCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.LauncherSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class AutonomousCommand extends CommandGroupBase {
   private DriveSoloCommand solo;
   private CommandGroupBase auto;
   private boolean done=false;
   public AutonomousCommand(DriveSubsystem drive, LauncherSubsystem launcherSubsystem,LimeLightSubsystem lime, HopperSubsystem hopperSubsystem) {      
      addRequirements(drive);
      addRequirements(launcherSubsystem);
      addRequirements(lime);
      addRequirements(hopperSubsystem);
      solo=new DriveSoloCommand(drive, lime, 0.5, 0.5, 60);
      addCommands(new ChangeLauncherSpeedCommand(2750,launcherSubsystem),solo,new AdjustLauncherCommand(launcherSubsystem, lime),new WaitCommand(4),new HopperCommand(hopperSubsystem, Constants.HOPPER_SPEED));
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