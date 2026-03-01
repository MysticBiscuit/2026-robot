package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.Servo;

public class ElevatorSubsystem extends SubsystemBase{
   private final SparkMax m_climber;
   
   private final DigitalInput m_elevatorTopLimit;
   private final DigitalInput m_elevatorBottomLimit;

   private final SparkMax m_elevatorSlider;

   private final DigitalInput m_elevatorSliderFrontLimit;
   private final DigitalInput m_elevatorSliderBackLimit;

   private boolean m_fullClimbRequested = false;
   private boolean m_autoClimbRequested = false;

   public ElevatorSubsystem() {
    m_climber = new SparkMax(Constants.DriveConstants.kClimberCanId, MotorType.kBrushless);

    m_elevatorTopLimit = new DigitalInput(Constants.DriveConstants.dTopElevatorLimitSwitchPort);
    m_elevatorBottomLimit = new DigitalInput(Constants.DriveConstants.dBottomElevatorLimitSwitchPort);

    m_elevatorSlider = new SparkMax(Constants.DriveConstants.kSliderCanId, MotorType.kBrushless);

    m_elevatorSliderFrontLimit = new DigitalInput(Constants.DriveConstants.dFrontElevatorSliderLimitSwitchPort);
    m_elevatorSliderBackLimit = new DigitalInput(Constants.DriveConstants.dBackElevatorSliderLimitSwitchPort);
   }

   @Override
   public void periodic() {
    if (!DriverStation.isAutonomous()){
        fullClimbCommand(m_fullClimbRequested, m_autoClimbRequested);
    }
    
    if (DriverStation.isAutonomous()) {
         autoClimbCommand(m_fullClimbRequested, m_autoClimbRequested);
    }
   }
   
   private void fullClimbCommand(boolean fullClimbRequested, boolean m_autoClimbRequested) {
      if (fullClimbRequested) {
      climbPartOne()
      .andThen(
         climbPartTwo()
         ).andThen(
         climbPartThree()
      ).andThen(
         climbPartOne()
      ).andThen(
         climbPartTwo()
      ).andThen(
         climbPartOne()
      ).andThen(
         climbPartFour(fullClimbRequested, m_autoClimbRequested)
      );
      } else {
         m_climber.set(0);
      }
   }

   private void autoClimbCommand(boolean m_autoClimbRequested, boolean fullClimbRequested) {
      if (m_autoClimbRequested) {
         climbPartOne()
         .andThen(climbPartFour(fullClimbRequested, m_autoClimbRequested));
      }
   }

   private Command climbPartOne() {
      return new Command() {
         
         @Override
         public boolean isFinished() {
            return bigArmsAreUp();
         }

         @Override
         public void initialize() {}

         @Override
         public void execute() {
            m_climber.set(0.25);
         }

         @Override
         public void end(boolean interrupted) {
            m_climber.set(0);
         }
      };
   }

   private Command climbPartThree() {
      return new RunCommand(
         () -> m_elevatorSlider.set(-0.25))
         .withTimeout(2);
   }

   private Command climbPartTwo() {
      return new Command() {
         
         @Override
         public boolean isFinished() {
            return rungTwoReached();
         }

         @Override
         public void initialize() {}

         @Override
         public void execute() {
            m_climber.set(-0.25);
         }

         @Override
         public void end(boolean interrupted) {
            m_climber.set(0);
         }
      };
   }

   private Command climbPartFour(boolean fullClimbRequested, boolean m_autoClimbRequested) {
      return new Command() {
         
         @Override
         public boolean isFinished() {
            return bigArmsAreUp();
         }

         @Override
         public void initialize() {}

         @Override
         public void execute() {
            m_climber.set(0.25);
         }

         @Override
         public void end(boolean interrupted) {
            m_climber.set(0);
            m_fullClimbRequested = false;
         }
      };
   }

   public void elevatorSlideCommand(boolean elevatorSlideOutRequested, boolean elevatorSlideInRequested) {
      if (elevatorSlideOutRequested && !m_elevatorSliderFrontLimit.get()) {
         m_elevatorSlider.set(0.25);

      } else if (elevatorSlideInRequested && !m_elevatorSliderBackLimit.get()) {
         m_elevatorSlider.set(-0.25);
      } else {
         m_elevatorSlider.set(0);
      }

      if (m_elevatorSliderFrontLimit.get()) {
         elevatorSlideOutRequested = false;

      }

      if (m_elevatorSliderBackLimit.get()) {
         elevatorSlideInRequested = false;
      }
   }

public void updateWithControls(boolean fullClimbRequested) {
   m_fullClimbRequested = fullClimbRequested;
}

public boolean bigArmsAreUp() {
   return !m_elevatorTopLimit.get();
}

public boolean rungTwoReached() {
   return !m_elevatorBottomLimit.get();
}
}
