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
                robot.ringArm.setPower(-0.5);
            }
            else if (gamepad1.a) {
                robot.ringArm.setPower(0.5);
            }
            else {
                robot.ringArm.setPower(0);
            }



            if (gamepad1.x) {
                robot.ringClaw.setPosition(0.973);
            }
            else if (gamepad1.b) {
                robot.ringClaw.setPosition(0.3);
            }
            else {
                robot.ringClaw.setPosition(0);
            }

        }
    }

}
