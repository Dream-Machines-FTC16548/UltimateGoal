package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
public class NewBotHardware {

    public DcMotor frontLeft, backLeft, frontRight, backRight;
    HardwareMap hwMap;

    public ElapsedTime timer = new ElapsedTime();
    private WebcamName webcamName = null;

    public void initTeleOpIMU(HardwareMap hwMap) {

        this.hwMap = hwMap;

        timer.reset();

        backLeft = hwMap.dcMotor.get("back_left");
        backRight = hwMap.dcMotor.get("back_right");
        frontLeft = hwMap.dcMotor.get("front_left");
        frontRight = hwMap.dcMotor.get("front_right");


        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void setPowerOfAllMotorsTo(double power) {

        backLeft.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(power);
    }

    public void strafeLeft(double power) {

        backLeft.setPower(power);
        backRight.setPower(-power);
        frontLeft.setPower(-power);
        frontRight.setPower(power);
    }

    public void strafeRight(double power) {

        backLeft.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(-power);
    }
}

