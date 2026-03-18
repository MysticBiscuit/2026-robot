package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class OrientationSubsystem extends SubsystemBase{


    private boolean m_scoringModeOn = false;
    private boolean m_scoringModeEnd = false;

    private final DriveSubsystem m_robotDrive = new DriveSubsystem();

    public OrientationSubsystem() {

    }

    @Override
    public void periodic() {
        double omegaRps = Units.degreesToRotations(m_robotDrive.getTurnRate());

        if (!DriverStation.isAutonomous()) {
            scoringModeActive(m_scoringModeOn, m_scoringModeEnd);
        }
    }

    private void scoringModeActive(boolean scoringModeOn, boolean scoringModeEnd) {

    }

    public void updateWithControls(boolean scoringModeOn, boolean scoringModeEnd) {
        m_scoringModeOn = scoringModeOn;
        m_scoringModeEnd = scoringModeEnd;
    }
}