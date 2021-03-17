package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.DMHardware;

@TeleOp(name = "NewArmDemo", group = "Dream Machines")
public class RingArm extends LinearOpMode {
    public DMHardware robot = new DMHardware();

    @Override
    public void runOpMode() {
        robot.initTeleOpIMU(hardwareMap);
        robot.timer.reset();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y) {
                robot.ringGrabberArm.setPower(0.25);
            }
            else if (gamepad1.a) {
                robot.ringGrabberArm.setPower(-0.25);
            }
            else {
                robot.ringGrabberArm.setPower(0);
            }



            if (gamepad1.x) {
                robot.ringGrabberClaw.setPower(0.5);
            }
            else if (gamepad1.b) {
                robot.ringGrabberClaw.setPower(-0.5);
            }
            else {
                robot.ringGrabberClaw.setPower(0);
            }

        }
    }

}
