package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.Constants;

public class ElevatorCommand extends Command{
    private final ElevatorSubsystem m_elevator;
    private final XboxController m_controller;

    public ElevatorCommand(ElevatorSubsystem elevator, XboxController controller) {
        m_elevator = elevator;
        m_controller = controller;
        addRequirements(elevator);
    }
    
    @Override
    public void execute() {
        m_elevator.updateWithControls(
            m_controller.getPOV() == 90
        );  

        m_elevator.elevatorSlideCommand(m_controller.getBButton(), m_controller.getXButton());
        m_elevator.elevatorManualControls(m_controller.getAButton(), m_controller.getYButton());

        if (m_controller.getPOV() == 180) {
            Constants.DriveConstants.slowDownModifier = 0.5;
        } else {
            Constants.DriveConstants.slowDownModifier = 1;
        }
    }
}
