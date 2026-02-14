package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command{
    private final IntakeSubsystem m_intake;
    private final XboxController m_Controller;

    public IntakeCommand(IntakeSubsystem intake, XboxController controller) {
        m_intake = intake;
        m_Controller = controller;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        m_intake.updateWithControls(
            m_Controller.getRightTriggerAxis() > 0.8, 
            m_Controller.getLeftTriggerAxis() > 0.8,
            m_Controller.getXButton());

        m_intake.shootingTime(m_Controller.getRightBumperButton());
        m_intake.intakeTime(m_Controller.getLeftBumperButton());
        m_intake.hoodAdjustmentCoverIt();
    }
}
