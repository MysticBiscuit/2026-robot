package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.DriveCommand;

public class OrientationSubsystem extends SubsystemBase{
    private boolean m_scoringModeOn = false;
    private boolean m_scoringModeEnd = false;
    public double m_driveRotation;
    private XboxController m_controller;


    private final DriveSubsystem m_robotDrive = new DriveSubsystem();

    public OrientationSubsystem(XboxController controller) {
        m_controller = controller;
    }

    @Override
    public void periodic() {
        double omegaRps = Units.degreesToRotations(m_robotDrive.getTurnRate());
        var llMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight");

        if (llMeasurement != null && getSpecialTV() && Math.abs(omegaRps) < 2.0) {
            m_robotDrive.resetOdometry(llMeasurement.pose);
        }

        if (DriverStation.isTeleop()) {
            scoringModeActive(m_scoringModeOn, m_scoringModeEnd);
        }
    }

    private boolean getSpecialTV() {
        LimelightHelpers.SetFiducialIDFiltersOverride("", new int[]{10, 26});
        boolean hasTarget = LimelightHelpers.getTV("");

        return hasTarget;
    }
    
    private void scoringModeActive(boolean scoringModeOn, boolean scoringModeEnd) {
        if (scoringModeOn && getSpecialTV()) {
            m_driveRotation = LimelightHelpers.getTX("limelight") * -0.05;
        } else if (DriverStation.isTeleop()) {
            m_driveRotation = -MathUtil.applyDeadband(m_controller.getRightX(), OIConstants.kDriveDeadband);
        }

        if (scoringModeEnd) {
            m_scoringModeOn = false;
            m_scoringModeEnd = false;
        }
    }

    public void updateWithControls(boolean scoringModeOn, boolean scoringModeEnd) {
        m_scoringModeOn = scoringModeOn;
        m_scoringModeEnd = scoringModeEnd;
    }
}