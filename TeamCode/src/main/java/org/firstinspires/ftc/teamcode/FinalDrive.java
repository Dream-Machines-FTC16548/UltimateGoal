package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.DMHardware;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp (group = "DreamMachines", name = "FinalDriveIThinkidrk")
public class FinalDrive extends LinearOpMode {
    public DMHardware robot = new DMHardware();

    public void runOpMode() {
        robot.initTeleOpIMU(hardwareMap);
        waitForStart();

        // robot.intakeServoLeft.setPosition(0);
        // robot.intakeServoRight.setPosition(0);

        while (opModeIsActive()) {

            robot.frontRight.setPower(-0.3 * ((gamepad1.left_stick_y + gamepad1.left_stick_x) - gamepad1.right_stick_x));
            robot.frontLeft.setPower(-0.3 * ((gamepad1.left_stick_y - gamepad1.left_stick_x) + gamepad1.right_stick_x));
            robot.backRight.setPower(-0.3 * ((gamepad1.left_stick_y - gamepad1.left_stick_x) - gamepad1.right_stick_x));
            robot.backLeft.setPower(-0.3 * ((gamepad1.left_stick_y + gamepad1.left_stick_x) + gamepad1.right_stick_x));


            robot.wobbleGoalArm.setPower(gamepad2.right_stick_y);

            // Arm for intake
            if (gamepad2.y) {
                robot.intakeServoLeft.setPosition(0.7);
                robot.intakeServoRight.setPosition(0.3);
                telemetry.addData("right position", robot.intakeServoRight.getPosition());
                telemetry.update();
            }
            else if (gamepad2.a) {
                robot.intakeServoLeft.setPosition(0.3);
                robot.intakeServoRight.setPosition(0.7);
                telemetry.addData("right position", robot.intakeServoRight.getPosition());
                telemetry.update();
            }

            // Intake and Conveyor
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

            // Wobble Goal Claw
            if (gamepad2.dpad_left) {
                robot.wobbleGoalClaw.setPosition(0.1);
            }
            else if (gamepad2.dpad_left) {
                robot.wobbleGoalClaw.setPosition(0.5);
            }
        }
    }
}
