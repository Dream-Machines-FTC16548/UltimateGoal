package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "ArmTest", group = "FTC16548")
public class ArmTest extends LinearOpMode{
    public DMHardware robot = new DMHardware();
    public void runOpMode(){
        robot.initTeleOpIMU(hardwareMap);

        waitForStart();
        robot.timer.reset();
        while(robot.timer.seconds() <= 30){
            robot.wobbleGoalClaw.setPosition(0.1);
            telemetry.addData("Servo Value", robot.wobbleGoalClaw.getPosition());
            telemetry.update();
            sleep(1000);
            robot.wobbleGoalClaw.setPosition(.9);
            telemetry.addData("Servo Value", robot.wobbleGoalClaw.getPosition());
            telemetry.update();
            sleep(1000);
        }

    }
}
