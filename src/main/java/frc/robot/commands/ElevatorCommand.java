package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCommand extends Command{
    private final ElevatorSubsystem m_elevator;
    private final XboxController m_Controller;

    public ElevatorCommand(ElevatorSubsystem elevator, XboxController controller) {
        m_elevator = elevator;
        m_Controller = controller;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        m_elevator.updateWithControls(
            m_Controller.getYButton(),
            m_Controller.getBButton(),
            m_Controller.getAButton()
        );
    }
}
