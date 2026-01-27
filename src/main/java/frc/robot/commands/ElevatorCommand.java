package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCommand extends Command{
    private final ElevatorSubsystem m_elevator;
    private final XboxController m_Controller;

    public IntakeCommand(IntakeSubsystem intake, XboxController controller) {
        m_elevator = elevator;
        m_Controller = controller;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        m_elevator.updateWithControls();
    }
}
