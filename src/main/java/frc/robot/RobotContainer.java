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
import frc.robot.subsystems.ComplexDrivetrain;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...

	// Subsystems
	public static ComplexDrivetrain drivetrain = new ComplexDrivetrain();
	// public static Shooter shooter = new Shooter();
	// Commands

	// Controllers
	private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(10);
	public static Joystick joystick = new Joystick(0);


	/*
	 * private RunCommand pidTankDrive = new RunCommand( () ->
	 * test_drivetrain.pidTestSpeed( joystick.getRawAxis(1),
	 * joystick.getRawAxis(5)), test_drivetrain); //
	 * m_speedLimiter.calculate(joystick.getRawAxis(1)) * 3.0, //
	 * m_speedLimiter.calculate(joystick.getRawAxis(5)) * 3.0),
	 */
	private RunCommand pidTankDrive = new RunCommand( () ->
	drivetrain.pidTankDrive(
		joystick.getRawAxis(1),
		joystick.getRawAxis(5)), drivetrain); //
	
	private RunCommand curvatureDrive = new RunCommand(
			() -> drivetrain.curvatureDrive(joystick.getRawAxis(1), joystick.getRawAxis(2)), drivetrain);

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */

	public RobotContainer() {
		// Configure the button bindings
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
		// new JoystickButton(joystick, 4).whenPressed(new ShiftGears(test_drivetrain));
		
		// new JoystickButton(joystick, 5).whenPressed(new RunCommand(() ->
		// shooter.shoot(1), shooter));
		// new JoystickButton(joystick, 5).whenPressed(new RunCommand(() ->
		// shooter.test_shoot(), shooter));
		// new JoystickButton(joystick, 6).whenPressed(new RunCommand(() ->
		// shooter.shoot(2), shooter));
		// new JoystickButton(joystick, 7).whenPressed(new RunCommand(() ->
		// shooter.shoot(3), shooter));
		// new JoystickButton(joystick, 8).whenPressed(new RunCommand(() ->
		// shooter.shoot(4), shooter));
	}

	private void configureDefaultCommands() {
		drivetrain.setDefaultCommand(pidTankDrive);
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	/*
	 * public Command getAutonomousCommand() { return; }
	 */
}
