package frc.robot.subsystems;

import com.fasterxml.jackson.databind.ser.std.InetSocketAddressSerializer;
// import com.revrobotics.AbsoluteEncoder;
// import com.revrobotics.spark.SparkAbsoluteEncoder;
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
    private SparkMax m_armExtenderTwo;
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
    //private SparkAbsoluteEncoder armExtenderPosition = m_armExtenderOne.getAbsoluteEncoder();
    //private double armDegrees = armExtenderPosition.getPosition();
    private boolean m_armKillSwitch = false;
    

    public IntakeSubsystem(){
        m_armExtenderOne = new SparkMax(Constants.DriveConstants.kIntakeExtenderOneCanId, MotorType.kBrushless);
        m_armExtenderTwo = new SparkMax(Constants.DriveConstants.kIntakeExtenderTwoCanId, MotorType.kBrushless);
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
            extendBox(m_boxExtendRequested, m_boxRetractRequested //** , armDegrees*/
            );
            killSwitchOne(m_armKillSwitch, m_boxExtendRequested, m_boxRetractRequested);
        }
    }

    private void extendBox(boolean boxExtendRequested, boolean boxRetractRequested //**, double armDegrees*/
    ){
        if (boxExtendRequested && //** armDegrees < 90 && */ 
        m_lowerLimit.get()) {
            m_armExtenderOne.set(0.2);
            m_armExtenderTwo.set(-0.2);
            
        } else if(m_boxRetractRequested && //** armDegrees > 0.8 && */
        m_upperLimit.get()) {
            m_armExtenderOne.set(-0.2);
            m_armExtenderTwo.set(.2);
            
        } else {
            m_armExtenderOne.set(0);
            m_armExtenderTwo.set(0);
            boxExtendRequested = false;
            boxRetractRequested = false;
        }
    }*/

    public void extendBox(boolean extendBox) {
        if(extendBox) {
            m_armExtenderOne.set(-0.05);
            m_armExtenderTwo.set(0.05);
        }
    }

    public void retractBox(boolean retractBox) {
        m_armExtenderOne.set(0.05);
        m_armExtenderTwo.set(-0.05);
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

    public void hoodAdjustmentCoverIt(boolean decreaseAngle) {
        if (decreaseAngle) {
            m_hood.set(0.1);
        } else {
            m_hood.set(0);
        }
    }

    public void hoodAdjustmentOpenIt(boolean increaseAngle) {
        if (increaseAngle) {
            m_hood.set(-0.1);
        } else {
            m_hood.set(0);
        }
    }

    public void updateWithControls(boolean armKillSwitch) {
        m_armKillSwitch = armKillSwitch;
    }

}
