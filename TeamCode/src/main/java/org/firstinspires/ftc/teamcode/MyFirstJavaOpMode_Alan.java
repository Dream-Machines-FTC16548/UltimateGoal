package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

//Alan's Code for the Arm
@TeleOp
public class MyFirstJavaOpMode_Alan extends LinearOpMode{
    private DcMotor motor1;
    private DigitalChannel touchThing;//Maybe
    private Servo servo1;
    private Servo servo2;

    @Override
    public void runOpMode() {
        //Init
        motor1 = hardwareMap.get(DcMotor.class, "motor go brrrr");
        touchThing = hardwareMap.get(DigitalChannel.class, "touchy");
        servo1 = hardwareMap.get(Servo.class, "swingyThingTwin");
        servo2 = hardwareMap.get(Servo.class, "swingyThingTwin2");
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
