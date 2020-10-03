package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "AutoTest", group = "DM16548")

public class AutoTest extends LinearOpMode{
    public DMHardware robot = new DMHardware();

    public void runOpMode(){
        robot.initTeleOpIMU(hardwareMap);
        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();{}
        telemetry.clear();
        robot.setPowerOfAllMotorsToForTime(.3, 5);
        sleep(1000);
        robot.setPowerOfAllMotorsToForTime(-.3, 5);
        sleep(1000);
        robot.strafeLeftForTime(.3, 5);
        sleep(1000);
        robot.strafeRightForTime(.3, 5);
        sleep(1000);
        robot.setPowerOfAllMotorsToForTime(-.3, 5);
        while(robot.distanceFront.getDistance(DistanceUnit.INCH) <= 5){
            robot.setPowerOfAllMotorsToForTime(.3, 5);
        }


    }

}
