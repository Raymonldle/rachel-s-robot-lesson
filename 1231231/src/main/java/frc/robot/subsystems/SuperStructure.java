// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SuperStructure extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
 

  /*=======================================================================
                                    OBJECTS
    ======================================================================= */
    
  private SuperStructureState state = SuperStructureState.OFF;
  private static final SuperStructure m_instance = SuperStructure();
  private final Drivebase m_db = Drivebase.getInstance();
  private final Wrist m_wrist = Wrist.getInstance();

   private enum SuperStructureState {
    HIGH,
    MEDIUM,
    LOW,
    STOW
  }



  /*=======================================================================
                                  Constructor
    ======================================================================= */

   private SuperStructure() {

   }

  /*=======================================================================
                                     METHOD
    ======================================================================= */

  public static SuperStructure getInstance() {       
      return m_instance;
  }

   public static SuperStructure getInstance() {
      return m_instance;
  }
  
  
  public void setState(SuperStructureState state){
      this.state = state;

      switch(state){
        case HIGH: 
        goToSetPoint(Rotation2d.fromDegrees(90));    
        break;

        case MEDIUM:
        goToSetPoint(Rotation2d.fromDegrees(0));     
        break;

        case LOW:
        goToSetPoint(Rotation2d.fromDegrees(-90))
        break;

        case STOW:
        m_wrist.setState(SuperStructureState.OFF);
        break;

      }
  }

  public void goToSetPoint(Rotation2d angle){
      m_wrist.setSetpoint(angle);
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
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
