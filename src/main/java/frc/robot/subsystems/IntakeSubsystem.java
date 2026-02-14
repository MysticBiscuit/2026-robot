package frc.robot.subsystems;

import com.fasterxml.jackson.databind.ser.std.InetSocketAddressSerializer;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase{
    private SparkMax m_armExtenderOne;
    private SparkMax m_intakeMotor;
    private SparkMax m_rollerOne;
    private SparkMax m_shooterOne;
    private SparkMax m_shooterTwo;
    private SparkMax m_hood;

    private DigitalInput m_lowerLimit;
    private DigitalInput m_upperLimit;

    // private final Timer m_systemTimer = new Timer();
    private boolean m_boxExtendRequested = false;
    private boolean m_boxRetractRequested = false;
    private SparkAbsoluteEncoder armExtenderPosition = m_armExtenderOne.getAbsoluteEncoder();
    private double armDegrees = armExtenderPosition.getPosition();
    private boolean m_armKillSwitch = false;
    private boolean m_suckIt = false;
    private boolean m_shootIt = false;
    private int m_povDir;
    private boolean m_startScoringMode = false;
    private boolean m_endScoringMode = false;
    

    public IntakeSubsystem(){
        m_armExtenderOne = new SparkMax(Constants.DriveConstants.kIntakeExtenderOneCanId, MotorType.kBrushless);
        m_intakeMotor = new SparkMax(Constants.DriveConstants.kIntakeMechanismCanId, MotorType.kBrushless);
        m_rollerOne = new SparkMax(Constants.DriveConstants.kBallConductorOneCanId, MotorType.kBrushless);
        m_shooterOne = new SparkMax(Constants.DriveConstants.kShooterOneCanId, MotorType.kBrushless);
        m_shooterTwo = new SparkMax(Constants.DriveConstants.kShooterTwoCanId, MotorType.kBrushless);
        m_hood = new SparkMax(Constants.DriveConstants.kIntakeHoodCanId, MotorType.kBrushless);
        
        m_lowerLimit = new DigitalInput(Constants.DriveConstants.dLowerLimitSwitchPort);
        m_upperLimit = new DigitalInput(Constants.DriveConstants.dUpperLimitSwitchPort);
    }

    @Override
    public void periodic(){
        if (!DriverStation.isAutonomous()){
            extendBox(m_boxExtendRequested, m_boxRetractRequested, armDegrees);
            killSwitchOne(m_armKillSwitch, m_boxExtendRequested, m_boxRetractRequested);
            intakeTime(m_suckIt);
            shootingTime(m_shootIt);
            setHoodStateManual(m_povDir);

        }
    }

    private void extendBox(boolean boxExtendRequested, boolean boxRetractRequested, double armDegrees){
        if (boxExtendRequested && armDegrees < 90 && m_lowerLimit.get()) {
            m_armExtenderOne.set(0.2);
            
        } else if(m_boxRetractRequested && armDegrees > 0.8 && m_upperLimit.get()) {
            m_armExtenderOne.set(-0.2);
            
        } else {
            m_armExtenderOne.set(0);
            boxExtendRequested = false;
            boxRetractRequested = false;
        }
    }

    private void killSwitchOne(boolean armKillSwitch, boolean boxExtendRequested, boolean boxRetractRequested){
        if(armKillSwitch){
            m_armExtenderOne.set(0);
            boxExtendRequested = false;
            boxRetractRequested = false;
        }
    }

    public void intakeTime(boolean suckIt) {
        if(suckIt) {
            m_intakeMotor.set(0.25);
            m_rollerOne.set(0.1);
        }else {
            m_intakeMotor.set(0);
            m_rollerOne.set(0);
        }
    }

    public void shootingTime(boolean shootIt) {
        if(shootIt) {
            m_shooterOne.set(0.25);
            m_shooterTwo.set(0.25);
        } else {
            m_shooterOne.set(0);
            m_shooterTwo.set(0);
        }
    }

    public void setHoodStateManual(int povDir) {
        if (povDir == 0) {
            m_hood.set(0.1);
        } else if (povDir == 180) {
            m_hood.set(-0.1);
        } else {
            m_hood.set(0);
        }
    }

    public void updateWithControls(boolean boxExtendRequested, boolean boxRetractRequested, boolean armKillSwitch, boolean startScoringMode, boolean endScoringMode) {
        m_boxExtendRequested = boxExtendRequested;
        m_boxExtendRequested = boxRetractRequested;
        m_armKillSwitch = armKillSwitch;
        m_startScoringMode = startScoringMode;
        m_endScoringMode = endScoringMode;
    }

}
