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
    public double m_driveXSpeed;
    public double m_driveYSpeed;
    public double m_driveRotation;

    private final DriveSubsystem m_robotDrive = new DriveSubsystem();

    public OrientationSubsystem() {

    }

    @Override
    public void periodic() {
        double omegaRps = Units.degreesToRotations(m_robotDrive.getTurnRate());
        var llMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight");

        if (llMeasurement != null && getSpecialTV() && Math.abs(omegaRps) < 2.0) {
            m_robotDrive.resetOdometry(llMeasurement.pose);
        }

        if (!DriverStation.isAutonomous()) {
            scoringModeActive(m_scoringModeOn, m_scoringModeEnd, m_driveXSpeed, m_driveYSpeed, m_driveRotation);
        }
    }

    private boolean getSpecialTV() {
        LimelightHelpers.SetFiducialIDFiltersOverride("", new int[]{10, 26});
        boolean hasTarget = LimelightHelpers.getTV("");

        return hasTarget;
    }
    
    private void scoringModeActive(boolean scoringModeOn, boolean scoringModeEnd, double driveXSpeed, double driveYSpeed, double driveRotation) {
        if (scoringModeOn) {
            driveXSpeed = 
        }
    }

    public void updateWithControls(boolean scoringModeOn, boolean scoringModeEnd) {
        m_scoringModeOn = scoringModeOn;
        m_scoringModeEnd = scoringModeEnd;
    }
}