package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

/*Alan's Code for the Arm
C:\Users\chuan>cd AppData

C:\Users\chuan\AppData>cd Local

C:\Users\chuan\AppData\Local>cd Android

C:\Users\chuan\AppData\Local\Android>cd sdk

C:\Users\chuan\AppData\Local\Android\Sdk>cd platform-tools

C:\Users\chuan\AppData\Local\Android\Sdk\platform-tools>adb connect 192.168.43.1:5555
failed to connect to 192.168.43.1:5555

C:\Users\chuan\AppData\Local\Android\Sdk\plat form-tools>adb connect 192.168.43.1:5555
already connected to 192.168.43.1:5555

C:\Users\chuan\AppData\Local\Android\Sdk\platform   -tools>adb disconnect
disconnected everything

C:\Users\chuan\AppData\Local\Android\Sdk\platform-tools>adb connect 192.168.43.1:5555
connected to 192.168.43.1:5555
*/
@TeleOp
public class MyFirstJavaOpMode_Alan extends LinearOpMode{
    public DMHardware roboto = new DMHardware();
    private DcMotor motor1;
    private DigitalChannel touchThing;//Maybe
    private Servo servo1;
    private Servo servo2;

    @Override
    public void runOpMode() {
        //Init
        motor1 = hardwareMap.get(DcMotor.class, "intake_motor");
        servo1 = hardwareMap.get(Servo.class, "intake_servo_left");
        servo2 = hardwareMap.get(Servo.class, "intake_servo_right");
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            roboto.timer.reset();

            motor1.setPower(0.5);
            servo1.setPosition(0.0);
            servo2.setPosition(1.0);

            telemetry.addLine("Servo1 =" + servo1.getPosition());
            telemetry.addLine("Servo2 =" + servo2.getPosition());
            telemetry.update();

            sleep(3000);
            motor1.setPower(0);
            servo1.setPosition(1.0);
            servo2.setPosition(0.0);
            telemetry.addLine("Servo1 =" + servo1.getPosition());
            telemetry.addLine("Servo2 =" + servo2.getPosition());
            telemetry.update();

            sleep(5000);
            telemetry.addData("Status", "Terminated");
            telemetry.update();

            stop();
        }
    }
}
