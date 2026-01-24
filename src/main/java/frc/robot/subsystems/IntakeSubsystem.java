package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

public class IntakeSubsystem extends SubsystemBase{
    private SparkMax m_armExtender;
    private SparkMax m_intakeMotor;
    private SparkMax m_shooter;

    private boolean boxExtendRequested = false;
    

    public IntakeSubsystem(){
        XboxController m_controller = new XboxController(Constants.OIConstants.kDriverControllerPort);
        m_armExtender = new SparkMax(Constants.DriveConstants.kIntakeExtenderCanId, MotorType.kBrushless);
        m_intakeMotor = new SparkMax(Constants.DriveConstants.kIntakeMechanismCanId, MotorType.kBrushless);
        m_shooter = new SparkMax(Constants.DriveConstants.kShooterCanId, MotorType.kBrushless);
    }
}