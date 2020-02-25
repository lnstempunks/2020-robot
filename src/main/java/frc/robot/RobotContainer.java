/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ShiftGears;
import frc.robot.subsystems.ColorSpinner;
import frc.robot.subsystems.ComplexDrivetrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Shooter;
// import io.github.oblarg.oblog.annotations.Log;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriveToDistance;
import frc.robot.subsystems.Climber;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	// Subsystems
	public static ComplexDrivetrain drivetrain = new ComplexDrivetrain();
	public static ColorSpinner colorsensor = new ColorSpinner();
	public static Shooter shooter = new Shooter();
	public static Intake intake = new Intake();
	public static Climber climber = new Climber();

	// Controllers
	public static Joystick joystick = new Joystick(0);
	// Command
	private RunCommand pidTankDrive = new RunCommand(
			() -> drivetrain.pidTankDrive(joystick.getRawAxis(1), joystick.getRawAxis(5)), drivetrain);
	private RunCommand colorSensor = new RunCommand(
			() -> colorsensor.colorPeriodic(), colorsensor);

	// private RunCommand curvatureDrive = new RunCommand(
			// () -> drivetrain.curvatureDrive(joystick.getRawAxis(1), joystick.getRawAxis(2)), drivetrain);\
	// FOR TESTING PURPOSES ONLY - Runs the shooter based off of the SmartDashboard
	private RunCommand testShooter = new RunCommand(() -> shooter.shooterPeriodic(), shooter);
	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */

	public RobotContainer() {
		configureButtonBindings();
		configureDefaultCommands();
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by instantiating a {@link GenericHID} or one of its subclasses
	 * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
	 * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {
		// Circle - Intake
		new JoystickButton(joystick, 3).whenPressed(new InstantCommand(() -> intake.runIndexer(), intake));
		// Triangle - Shift Gears
		new JoystickButton(joystick, 4).whenPressed(new ShiftGears(drivetrain));
		// Left Trigger - Intake
		new JoystickButton(joystick, 7).whenPressed(new InstantCommand(() -> intake.runIntake(), intake));
		// Right Trigger - Shooter
		new JoystickButton(joystick, 8).whenPressed(new InstantCommand(() -> shooter.shooterPeriodic(), shooter));
		// Share - Color Sensor
		// D-pad??????????????????
		new JoystickButton(joystick, 9).whenPressed(new InstantCommand(
			() -> colorsensor.spin(), colorsensor));
		if (joystick.getPOV(0) == 90) {
			new InstantCommand(
			() -> climber.moveClimber(0.5), climber);
		} else if (joystick.getPOV(0) == 270) {
			new InstantCommand(
			() -> climber.moveClimber(-0.5), climber);
		} else if (joystick.getPOV(1) == 0) {
			new InstantCommand(
			() -> climber.liftBot(0.5), climber);
		} else if (joystick.getPOV(1) == 180) {
			new InstantCommand(
			() -> climber.liftBot(-0.5), climber);
		} 

	}

	private void configureDefaultCommands() {
		drivetrain.setDefaultCommand(pidTankDrive);
		colorsensor.setDefaultCommand(colorSensor);
		shooter.setDefaultCommand(testShooter);
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */

	public Command getAutonomousCommand() { 
		return new DriveToDistance(-2, drivetrain);
	}
	
}
