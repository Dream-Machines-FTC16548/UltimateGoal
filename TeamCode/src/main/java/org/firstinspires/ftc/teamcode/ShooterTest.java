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

        robot.intakeServoLeft.setPosition(0);
        robot.intakeServoRight.setPosition(0);

        while (opModeIsActive()) {
            // Arm for intake
            if (gamepad2.y) {
                robot.intakeServoLeft.setPosition(1);
                robot.intakeServoRight.setPosition(0);
                telemetry.addData("right position", robot.intakeServoRight.getPosition());
                telemetry.update();
            }
            else if (gamepad2.a) {
                robot.intakeServoLeft.setPosition(0);
                robot.intakeServoRight.setPosition(1);
                telemetry.addData("right position", robot.intakeServoRight.getPosition());
                telemetry.update();
            }
            // Intake and conveyor
            if (gamepad2.dpad_down) {
                robot.intakeMotor.setPower(1);
                robot.conveyor.setPower(1);
            }
            else if (gamepad2.dpad_up) {
                robot.intakeMotor.setPower(-1);
                robot.conveyor.setPower(-1);
            }
            else if  (gamepad2.b) {
                robot.intakeMotor.setPower(0);
            }
            else {
                robot.intakeMotor.setPower(0);
                robot.conveyor.setPower(0);
            }
            // Shooter
            if (gamepad1.right_bumper) {
                robot.shooter.setPower(0.9);
            }
            else if (gamepad1.left_bumper) {
                robot.shooter.setPower(-0.9);
            }
            else {
                robot.shooter.setPower(0);
            }
        }
    }
}
