package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Semi-finalDrive", group = "DreamMachines")
public class MecanumDrive extends LinearOpMode {
    public DMHardware robot = new DMHardware();

    public void runOpMode() {
        robot.initTeleOpIMU(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            robot.frontRight.setPower(0.3 * (gamepad1.left_stick_y + gamepad1.left_stick_x));
            robot.frontLeft.setPower(0.3 * (gamepad1.left_stick_y - gamepad1.left_stick_x));
            robot.backRight.setPower(0.3 * (gamepad1.left_stick_y + gamepad1.left_stick_x));
            robot.backLeft.setPower(0.3 * (gamepad1.left_stick_y - gamepad1.left_stick_x));

            /*robot.frontRight.setPower(0.5 * (gamepad1.left_stick_y + gamepad1.left_stick_x + (-1 * gamepad1.right_stick_x - 0.77)));
            robot.frontLeft.setPower(0.5 * (gamepad1.left_stick_y - gamepad1.left_stick_x + (gamepad1.right_stick_x + 0.01)));
            robot.backRight.setPower(0.5 * (gamepad1.left_stick_y + gamepad1.left_stick_x + (gamepad1.right_stick_x)));
            robot.backLeft.setPower(0.5 * (gamepad1.left_stick_y - gamepad1.left_stick_x + (-1 * gamepad1.right_stick_x)));*/


            /*robot.frontRight.setPower(0.5 * (-1 * gamepad1.right_stick_x));
            robot.frontLeft.setPower(0.5 * (gamepad1.right_stick_x));
            robot.backRight.setPower(0.5 * (gamepad1.right_stick_x));
            robot.backLeft.setPower(0.5 * (-1 * gamepad1.right_stick_x));*/


            /*robot.frontRight.setPower(0.5 * (gamepad1.right_stick_y - gamepad1.right_stick_x));
            robot.frontLeft.setPower(0.5 * (gamepad1.right_stick_y + gamepad1.right_stick_x));
            robot.backRight.setPower(0.5 * (gamepad1.right_stick_y + gamepad1.right_stick_x));
            robot.backLeft.setPower(0.5 * (gamepad1.right_stick_y - gamepad1.right_stick_x));*/


            if (gamepad2.y) {
                robot.ringGrabberArm.setPower(0.175);
            }
            else if (gamepad2.a) {
                robot.ringGrabberArm.setPower(-0.175);
            }
            else {
                robot.ringGrabberArm.setPower(0);
            }



            if (gamepad2.x) {
                robot.ringGrabberClaw.setPower(0.75);
            }
            else if (gamepad2.b) {
                robot.ringGrabberClaw.setPower(-0.75);
            }
            else {
                robot.ringGrabberClaw.setPower(0);
            }


            if (gamepad2.dpad_up) {
                robot.wobbleGoalArm.setPower(0.55);
            }
            else if (gamepad2.dpad_down) {
                robot.wobbleGoalArm.setPower(-0.55);
            }
            else {
                robot.wobbleGoalArm.setPower(0);
            }


            if (gamepad2.dpad_left) {
                robot.wobbleGoalClaw.setPosition(0.1);
            }
            else if (gamepad2.dpad_left) {
                robot.wobbleGoalClaw.setPosition(0.5);
            }
        }


    }
}