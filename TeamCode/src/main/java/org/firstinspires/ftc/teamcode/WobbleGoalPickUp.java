package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "ArmTest", group = "FTC16548")
public class WobbleGoalPickUp extends LinearOpMode{
    public DMHardware robot = new DMHardware();
    public void runOpMode(){
        robot.initTeleOpIMU(hardwareMap);

        waitForStart();
        robot.timer.reset();
        while (robot.timer.seconds() <= 1) {
            robot.wobbleGoalArm.setPower(-.35);
        }
        robot.wobbleGoalClaw.setPosition(.5);
        sleep(2000);
        //robot.wobbleGoalClaw.setPosition(0);
        //sleep(2000);
        robot.timer.reset();
        while (robot.timer.seconds() <= 1) {
            robot.wobbleGoalArm.setPower(.2);
        }
    }
}
