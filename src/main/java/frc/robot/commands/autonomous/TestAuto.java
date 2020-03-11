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

public class TestAuto extends CommandGroupBase {
    private DriveSubsystem drive;
    private LauncherSubsystem launcherSubsystem;
    private LimeLightSubsystem lime;
    private HopperSubsystem hopperSubsystem;
    private DriveSoloCommand solo;
    private CommandGroupBase auto;
    private boolean done = false;

    public TestAuto( LauncherSubsystem launcherSubsystem,
            HopperSubsystem hopperSubsystem) {
      this.launcherSubsystem = launcherSubsystem;
      this.hopperSubsystem=hopperSubsystem;
      addRequirements(launcherSubsystem);
      addRequirements(hopperSubsystem);
      addCommands(new ChangeLauncherSpeedCommand(2750,launcherSubsystem),new HopperCommand(hopperSubsystem, Constants.HOPPER_SPEED),new ChangeLauncherSpeedCommand(0, launcherSubsystem));
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