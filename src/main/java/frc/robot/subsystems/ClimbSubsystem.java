// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private DoubleSolenoid p;
  //new Translation2d(1,0);

  public ClimbSubsystem() {
    p = new DoubleSolenoid(Constants.SolenoidPin, PneumaticsModuleType.REVPH, 1, 0);
    p.set(DoubleSolenoid.Value.kReverse);
  }

  public void raise() {
    p.toggle();
  }

  public void lower(){
    p.set(DoubleSolenoid.Value.kReverse);
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
