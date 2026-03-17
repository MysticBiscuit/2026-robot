 package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class OrientationSubsystem extends SubsystemBase{


    private boolean m_scoringModeOn = false;
    private boolean m_scoringModeEnd = false;

    public OrientationSubsystem() {

    }

    @Override
    public void periodic() {
        if (!DriverStation.isAutonomous()) {
            scoringModeActive(m_scoringModeOn, m_scoringModeEnd);
        }
    }

    private void scoringModeActive(boolean ScoringModeOn, boolean scoringModeEnd) {

    }
}