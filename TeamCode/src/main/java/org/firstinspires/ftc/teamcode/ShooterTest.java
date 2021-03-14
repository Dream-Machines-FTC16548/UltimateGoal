package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.DMHardware;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp (group = "DreamMachines", name = "shooterTest")
public class ShooterTest extends LinearOpMode {
    public DMHardware robot = new DMHardware();

    public void runOpMode() {
        robot.initTeleOpIMU(hardwareMap);
        waitForStart();
        // Arm for intake
        if (gamepad2.y) {
            robot.intakeServoLeft.setPosition(0);
            robot.intakeServoRight.setPosition(0);
        }
        else if (gamepad2.a) {
            robot.intakeServoLeft.setPosition(0.5);
            robot.intakeServoRight.setPosition(0.5);
        }
        // Intake
        if (gamepad2.dpad_down) {
            robot.intakeMotor.setPower(0.5);
        }
        else if (gamepad2.dpad_up) {
            robot.intakeMotor.setPower(-0.5);
        }
        // Conveyor
        if (gamepad2.right_bumper) {
            robot.conveyor.setPower(0.5);
        }
        else if (gamepad2.left_bumper) {
            robot.conveyor.setPower(-0.5);
        }
        // Shooter
        if (gamepad1.right_bumper) {
            robot.shooter.setPower(0.75);
        }
        else if (gamepad1.left_bumper) {
            robot.shooter.setPower(-0.75);
        }
    }
}
