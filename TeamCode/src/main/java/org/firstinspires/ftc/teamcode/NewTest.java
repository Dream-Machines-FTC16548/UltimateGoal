package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "NewTest", group = "DM16548")

public class NewTest extends LinearOpMode{
    public NewBotHardware robot = new NewBotHardware();

    public void runOpMode(){
        robot.initTeleOpIMU(hardwareMap);
        telemetry.addLine("Waiting for start");
        telemetry.update();

       robot.timer.reset();
       while (robot.timer.seconds() <= 5){
           robot.backLeft.setPower(.3);
           robot.backRight.setPower(.3);
           robot.frontLeft.setPower(.3);
           robot.frontRight.setPower(.3);
        }


    }

}
