package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class TestAutoCommand extends Command{
    private final ElevatorSubsystem m_elevator;
    private final IntakeSubsystem m_intake;

    private Command getPutArmDown() {
        return new RunCommand(
            () -> m_intake.intakeArmDown(0.2)
        ).withTimeout(3);
}

    public TestAutoCommand(ElevatorSubsystem elevator, IntakeSubsystem intake, XboxController controller) {
        m_elevator = elevator;
        m_intake = intake;
        addRequirements(intake, elevator);
    }

    @Override
    public void execute() {
        getPutArmDown();
    }
}
