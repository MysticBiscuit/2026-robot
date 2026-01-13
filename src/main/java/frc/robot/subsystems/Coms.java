package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Coms {
    public static NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
    public static StructPublisher<Pose2d> currentPosePublisher = ntinst.getStructTopic("Pose/currentPose", Pose2d.struct).publish();
    public static StructPublisher<Pose2d> targetPosePublisher = ntinst.getStructTopic("Pose/targetPose", Pose2d.struct).publish();
    public static StructPublisher<Pose2d> currentTargetPublisher = ntinst.getStructTopic("Pose/currentTarget", Pose2d.struct).publish();
    private static final SendableChooser<String> m_autoChooser = new SendableChooser<>();
    
    public static void configureSendableChooser() {
        m_autoChooser.setDefaultOption("NO AUTO", "-");
        m_autoChooser.addOption("AUTO 1", "AUTO 1");
        m_autoChooser.addOption("AUTO 2", "AUTO 2");
        SmartDashboard.putData("AUTO_SELECT", m_autoChooser);

    }

    public static String[] getAutoChoices() {
        return new String[] {
            m_autoChooser.getSelected()
        };
    }   
}
