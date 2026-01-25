package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase{
    private SparkMax m_armExtender;
    private SparkMax m_intakeMotor;
    private SparkMax m_shooter;

    private DigitalInput m_lowerLimit;
    private DigitalInput m_upperLimit;

    private final Timer m_systemTimer = new Timer();
    private boolean m_boxExtendRequested = false;
    private boolean m_boxRetractRequested = false;
    private SparkAbsoluteEncoder armExtenderPosition = m_armExtender.getAbsoluteEncoder();
    private double armDegrees = armExtenderPosition.getPosition();
    private boolean m_armKillSwitch = false;
    

    public IntakeSubsystem(){
        m_armExtender = new SparkMax(Constants.DriveConstants.kIntakeExtenderCanId, MotorType.kBrushless);
        m_intakeMotor = new SparkMax(Constants.DriveConstants.kIntakeMechanismCanId, MotorType.kBrushless);
        m_shooter = new SparkMax(Constants.DriveConstants.kShooterCanId, MotorType.kBrushless);
        
        m_lowerLimit = new DigitalInput(Constants.DriveConstants.dLowerLimitSwitchPort);
        m_upperLimit = new DigitalInput(Constants.DriveConstants.dUpperLimitSwitchPort);
    }

    @Override
    public void periodic(){
        if (!DriverStation.isAutonomous()){
            extendBox(m_boxExtendRequested, m_boxRetractRequested, armDegrees);
            killSwitchOne(m_armKillSwitch, m_boxExtendRequested, m_boxRetractRequested);
        }
    }

    private void extendBox(boolean boxExtendRequested, boolean boxRetractRequested, double armDegrees){
        if (boxExtendRequested && armDegrees < 90 && m_lowerLimit.get()) {
            m_armExtender.set(0.2);
            boxExtendRequested = false;
        } else if(m_boxRetractRequested && armDegrees > 0.8 && m_upperLimit.get()) {
            m_armExtender.set(-0.2);
            boxRetractRequested = false;
        } else {
            m_armExtender.set(0);
            boxExtendRequested = false;
            boxRetractRequested = false;
        }
    }

    private void killSwitchOne(boolean armKillSwitch, boolean boxExtendRequested, boolean boxRetractRequested){
        if(armKillSwitch){
            m_armExtender.set(0);
            boxExtendRequested = false;
            boxRetractRequested = false;
        }
    }

    public void intakeTime(boolean suckIt) {
        if(suckIt) {
            m_intakeMotor.set(0.25);
        }else {
            m_intakeMotor.set(0);
        }
    }

    public void shootingTime(boolean shootIt) {
        if(shootIt) {
            m_shooter.set(0.25);
        } else {
            m_shooter.set(0);
        }
    }

    public void updateWithControls(boolean boxExtendRequested, boolean boxRetractRequested, boolean armKillSwitch) {
        m_boxExtendRequested = boxExtendRequested;
        m_boxExtendRequested = boxRetractRequested;
        m_armKillSwitch = armKillSwitch;
    }

}
