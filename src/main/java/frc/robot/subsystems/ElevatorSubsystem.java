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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.Servo;

public class ElevatorSubsystem extends SubsystemBase{
   private final SparkMax m_climberOne;
   
   private final DigitalInput m_elevatorTopLimit;
   private final DigitalInput m_elevatorBottomLimit;

   private final Servo m_elevatorSlider;

   private boolean m_fullClimbRequested = false;
   private boolean m_autoClimbRequested = false;
   private boolean m_elevatorSlideOutRequested = false;
   private boolean m_elevatorSlideInRequested = false;

   public ElevatorSubsystem() {
    m_climberOne = new SparkMax(Constants.DriveConstants.kClimberOneCanId, MotorType.kBrushless);

    m_elevatorTopLimit = new DigitalInput(Constants.DriveConstants.dTopElevatorLimitSwitchPort);
    m_elevatorBottomLimit = new DigitalInput(Constants.DriveConstants.dBottomElevatorLimitSwitchPort);

    m_elevatorSlider = new Servo(Constants.DriveConstants.sElevatorSliderPort);
   }

   @Override
   public void periodic() {
    if (!DriverStation.isAutonomous()){
        fullClimbCommand(m_fullClimbRequested);
        elevatorSlideCommand(m_elevatorSlideOutRequested, m_elevatorSlideInRequested);
    }
    
    if (DriverStation.isAutonomous()) {
         autoClimbCommand(m_autoClimbRequested);
    }
   }
   
   private void fullClimbCommand(boolean fullClimbRequested) {

   }

   private void autoClimbCommand(boolean autoClimbRequested) {

   }

   private void elevatorSlideCommand(boolean elevatorSlideOutRequested, boolean elevatorSlideInRequested) {
      if (elevatorSlideOutRequested) {
         m_elevatorSlider.set(1);
         elevatorSlideOutRequested = false;
      } else if (elevatorSlideInRequested) {
         m_elevatorSlider.set(0);
         elevatorSlideInRequested = false;
      }
   }

public void updateWithControls(boolean fullClimbRequested, boolean elevatorSlideOutRequested, boolean elevatorSlideInRequested) {
   m_fullClimbRequested = fullClimbRequested;
   m_elevatorSlideOutRequested = elevatorSlideOutRequested;
   m_elevatorSlideInRequested = elevatorSlideInRequested;
}
}
