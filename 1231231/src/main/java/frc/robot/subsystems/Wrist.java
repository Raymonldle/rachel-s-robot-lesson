// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WristConstants;;


public class Wrist extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */


  


  public enum WristState {
    OFF,
    JOG,
    POSITION,
    ZERO
  }

  double jogValue = 0;
  Rotation2d setpoint = new Rotation2d();

  WristState state = WristState.OFF;


  


  public static Wrist m_instance = new Wrist();
  public final CANSparkMax m_leftMotor = new CANSparkMax(WristConstants.kLeftMotorID, MotorType.kBrushless);
  public final CANSparkMax m_rightMotor = new CANSparkMax(WristConstants.kRightMotorID, MotorType.kBrushless);
  public final AbsoluteEncoder m_leftEncoder = m_leftMotor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
  public DigitalInput m_limitSwitch = new DigitalInput(0);




  public Wrist() {
    configMotors();
  }



  public void OFF(){
    setJogValue(0);
  }

  public void ZERO(){
      if(m_limitSwitch.get()){
        setJogValue(0);
      }
      else{
         setJogValue(0.1);
      }
  }
  
  
  public static Wrist getInstance(){
      return m_instance;
  }
  
  public double getJogValue(){
    return jogValue;
  }

  public Rotation2d getSetpoint(){
    return setpoint;
  }
  
  public WristState getState(){
    return state;
  }


  public void setJogValue(double jogValue){
    this.jogValue = jogValue;
  }

  public void setSetpoint(Rotation2d setpoint){
    this.setpoint = setpoint;
  }


  public void setState(WristState state){
    this.state = state;
  }
  
  public void logData(){
    
  }

 




   


  public void configMotors(){
      m_leftMotor.follow(m_rightMotor);

      m_leftMotor.setInverted(true);
      m_rightMotor.setInverted(m_leftMotor.getInverted());

      m_leftMotor.restoreFactoryDefaults();
      m_rightMotor.restoreFactoryDefaults();

      m_leftMotor.setIdleMode(IdleMode.kBrake);
      m_rightMotor.setIdleMode(m_leftMotor.getIdleMode());

      m_leftMotor.setSmartCurrentLimit(WristConstants.kStallLimit,WristConstants.kFreeLimit);
      m_rightMotor.setSmartCurrentLimit(WristConstants.kStallLimit,WristConstants.kFreeLimit);

      SparkMaxPIDController m_PIDController = m_leftMotor.getPIDController();
      
      m_PIDController.setP(WristConstants.kP);
      m_PIDController.setD(WristConstants.kD);
      m_PIDController.setFF(WristConstants.kFF);

  }

  

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
    Switch(WristState){
      Case OFF:
          OFF();
          break;
      Case JOG: 
          setJogValue(jogValue);
          break;
      Case POSITION:
          setPosition(); //not real yet
          break;
      Case ZERO:
          ZERO();
          break;
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
