package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.OrientationSubsystem;
import frc.robot.Constants.OIConstants;
import edu.wpi.first.math.MathUtil;

public class DriveCommand extends Command{
    private final DriveSubsystem m_drive;
    private final OrientationSubsystem m_orientation;
    private final XboxController m_controller;

    public DriveCommand(DriveSubsystem drive, OrientationSubsystem orientation, XboxController controller) {
        m_drive = drive;
        m_orientation = orientation;
        m_controller = controller;
        addRequirements(drive, orientation);
    }

    @Override
    public void execute() {
      m_drive.drive(-MathUtil.applyDeadband(m_controller.getLeftY(), OIConstants.kDriveDeadband), -MathUtil.applyDeadband(m_controller.getLeftX(), OIConstants.kDriveDeadband), m_orientation.m_driveRotation, true);

      m_orientation.updateWithControls(m_controller.getLeftStickButton(), m_controller.getRightStickButton());
    }
}