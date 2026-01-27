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

public class ElevatorSubsystem extends SubsystemBase{
   private final SparkMax m_climberOne;
   private final SparkMax m_climberTwo;
   
   private final DigitalInput m_elevatorTopLimit;
   private final DigitalInput m_elevatorBottomLimit;

   private boolean m_fullClimbRequested = false;
   private boolean m_autoClimbRequested = false;

   public ElevatorSubsystem() {
    m_climberOne = new SparkMax(Constants.DriveConstants.kClimberOneCanId, MotorType.kBrushless);
    m_climberTwo = new SparkMax(Constants.DriveConstants.kClimberTwoCanId, MotorType.kBrushless);

    m_elevatorTopLimit = new DigitalInput(Constants.DriveConstants.dTopElevatorLimitSwitchPort);
    m_elevatorBottomLimit = new DigitalInput(Constants.DriveConstants.dBottomElevatorLimitSwitchPort);
   }

   @Override
   public void periodic() {
    if (!DriverStation.isAutonomous()){
        fullClimbCommand(m_fullClimbRequested);
    }
    
    if (DriverStation.isAutonomous()) {
         autoClimbCommand(m_autoClimbRequested);
    }
   }
   
   private void fullClimbCommand(boolean fullClimbRequested) {

   }

   private void autoClimbCommand(boolean autoClimbRequested) {

   }

public void updateWithControls(boolean fullClimbRequested, boolean autoClimbRequested) {
   m_fullClimbRequested = fullClimbRequested;
   m_autoClimbRequested = autoClimbRequested;
}
}
