package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "SimpleAuto", group = "Dream Machines")
public class SimpleAuto extends LinearOpMode{
    public DMHardware robot = new DMHardware();

    public void runOpMode() {
        robot.initTeleOpIMU(hardwareMap);

        waitForStart();

        while (opModeIsActive()){
            robot.setPowerOfAllMotorsToForTime(.4, 2.25);

        }
    }

}
