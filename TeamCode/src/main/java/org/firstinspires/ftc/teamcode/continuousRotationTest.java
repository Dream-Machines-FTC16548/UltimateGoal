package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "continuousRotationTest", group = "Dream Machines")
public class continuousRotationTest extends LinearOpMode{
    HardwareMap hwMap = hardwareMap;
    public CRServo servoTest = hwMap.crservo.get("ring_claw");;
    public ElapsedTime timer = new ElapsedTime();


    public void runOpMode() {
        waitForStart();

        while (opModeIsActive()) {
            servoTest.setPower(0.5);

            while (timer.seconds() < 5) {

            }

            servoTest.setPower(0);
        }
    }

}
