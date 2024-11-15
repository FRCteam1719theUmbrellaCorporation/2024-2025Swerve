package frc.robot.subsystems;
import java.io.File;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import swervelib.parser.SwerveParser;
import swervelib.SwerveDrive;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SwerveSubsystem extends SubsystemBase{


    private SwerveDrive swerveDrive;
    
    public SwerveSubsystem(File directory) {

        double maximumSpeed = Units.feetToMeters(4.5);
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

        try {
            this.swerveDrive = new SwerveParser(directory).createSwerveDrive(maximumSpeed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        zeroGyro();

        
    }
    
    public Command driveCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier headingX, DoubleSupplier headingY)
  {
    return run(() -> {
      double xInput = Math.pow(translationX.getAsDouble(), 3); // Smooth controll out
      double yInput = Math.pow(translationY.getAsDouble(), 3); // Smooth controll out
      // Make the robot move
      driveFieldOriented(swerveDrive.swerveController.getTargetSpeeds(xInput, yInput,
        headingX.getAsDouble(),
        headingY.getAsDouble(),
        swerveDrive.getYaw().getRadians(),
        swerveDrive.getMaximumVelocity())
        );
    });
  }

  /**
   * Command to drive the robot using translative values and heading as angular velocity.
   *
   * @param translationX     Translation in the X direction.
   * @param translationY     Translation in the Y direction.
   * @param angularRotationX Rotation of the robot to set
   * @return Drive command.
   */


   public Command driveCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier angularRotationX) {
    return run(() -> {
      // Make the robot move
      swerveDrive.drive(new Translation2d(
        translationX.getAsDouble() * swerveDrive.getMaximumVelocity(),
        translationY.getAsDouble() * swerveDrive.getMaximumVelocity()),
        angularRotationX.getAsDouble() * swerveDrive.getMaximumAngularVelocity(),true,false);
        });
    } //Not used

    
    public Command drivetestCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier angularRotationX) {
      
      return run(() -> {
        System.out.println("Angular"+angularRotationX.getAsDouble());
        System.out.println("Xr"+translationX.getAsDouble());
        System.out.println("Yr"+translationY.getAsDouble());
        System.out.println("Vel"+swerveDrive.getMaximumAngularVelocity());
        // Make the robot move
        swerveDrive.drive(new Translation2d(
          translationX.getAsDouble() * swerveDrive.getMaximumVelocity(),
          translationY.getAsDouble() * swerveDrive.getMaximumVelocity()),
          angularRotationX.getAsDouble() * swerveDrive.getMaximumAngularVelocity(),true,true);
          });
      }

    /**
     * Zeros gyro when called
     */
    public void zeroGyro() {
        swerveDrive.zeroGyro();
    }

    public void driveFieldOriented(ChassisSpeeds vel) {
        swerveDrive.driveFieldOriented(vel);
    }
    
}

