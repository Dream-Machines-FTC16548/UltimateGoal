package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "NewTest", group = "DM16548")

public class NewTest extends LinearOpMode{
    public DMHardware robot = new DMHardware();

    public void runOpMode(){
        robot.initTeleOpIMU(hardwareMap);
        telemetry.addLine("Waiting for start");
        telemetry.update();
waitForStart();{}

    robot.timer.reset();
    while (robot.timer.seconds() <= 5) {
        robot.strafeRight(.3);
        robot.intakeServoLeft.setPosition(1);
    }

    }

}
