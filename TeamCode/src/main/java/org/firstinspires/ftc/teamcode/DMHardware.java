package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class DMHardware {

    public DcMotor frontLeft, backLeft, frontRight, backRight, wobbleGoalArm, intakeMotor, ringArm;
    public ColorSensor colorLeft, colorRight;
    public DistanceSensor distanceFront;
    public Servo wobbleGoalClaw, intakeServoLeft, intakeServoRight;
    public CRServo ringClaw;

    HardwareMap hwMap;

    public ElapsedTime timer = new ElapsedTime();
    private WebcamName webcamName = null;

    public void initTeleOpIMU(HardwareMap hwMap){

        this.hwMap = hwMap;

        timer.reset();

        backLeft = hwMap.dcMotor.get("back_left");
        backRight = hwMap.dcMotor.get("back_right");
        frontLeft = hwMap.dcMotor.get("front_left");
        frontRight = hwMap.dcMotor.get("front_right");

        colorLeft = hwMap.get(ColorSensor.class,"color_left");
        colorRight = hwMap.get(ColorSensor.class,"color_right");

        //distanceBack = hwMap.get(DistanceSensor.class, "distance_back");
        distanceFront = hwMap.get(DistanceSensor.class, "distance_front");

        wobbleGoalArm = hwMap.dcMotor.get("wobble_arm");
        wobbleGoalClaw = hwMap.servo.get("wobble_claw");
        wobbleGoalClaw.setPosition(0.5);

        intakeMotor = hwMap.dcMotor.get("intake_motor");
        intakeServoLeft = hwMap.servo.get("intake_servo_left");
        intakeServoRight = hwMap.servo.get("intake_servo_right");

        ringArm = hwMap.dcMotor.get("ring_arm");
        // ringClaw = hwMap.servo.get("ring_claw");
        ringClaw = hwMap.crservo.get("ring_claw");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        wobbleGoalClaw.setPosition(0.5);

       webcamName = hwMap.get(WebcamName.class, "Webcam 1");
    }

    public WebcamName getWebcamName() {
        return webcamName;
    }

    //Sets power of all motors to the same value, and for amount of seconds.
    // Positive values for forwards, and negative for backwards
    public void setPowerOfAllMotorsToForTime(double power, double time)
    {
     timer.reset();
     while(timer.seconds() <= time){
        backLeft.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(power);
     }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);

    }

    //Strafes right for certain amount of time
    public void strafeRightForTime(double power, double time)
    {
        timer.reset();
        while(timer.seconds() <= time){
            backLeft.setPower(-power);
            backRight.setPower(power);
            frontLeft.setPower( power + 0.01 );
            frontRight.setPower(-power - 0.07);
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }

    //strafes left for certain amount of time
    public void strafeLeftForTime(double power, double time)
    {
        timer.reset();
        while(timer.seconds() <= time){
            backLeft.setPower(power);
            backRight.setPower(-power);
            frontLeft.setPower(-power);
            frontRight.setPower(power + 0.07);
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }

    //turns left for certain amount of time
    public void turnLeftForTime(double power, double time)
    {
        timer.reset();
        while(timer.seconds() <= time){
            backLeft.setPower(power);
            backRight.setPower(-power);
            frontLeft.setPower(power);
            frontRight.setPower(-power);
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }

    //turns right for certain amount of time
    public void turnRightForTime(double power, double time)
    {
        timer.reset();
        while(timer.seconds() <= time){
            backLeft.setPower(-power);
            backRight.setPower(power);
            frontLeft.setPower(-power);
            frontRight.setPower(power);
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }

    //gives you Elapsed time
    public double getTime(){
        return timer.time();
    }

    public void setPowerOfAllMotorsTo(double power)
    {

        backLeft.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(power);

    }
}
