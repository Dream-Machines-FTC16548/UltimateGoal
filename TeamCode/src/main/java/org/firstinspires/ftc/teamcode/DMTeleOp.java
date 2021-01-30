package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.DMHardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class DMTeleOp extends LinearOpMode {
    private DcMotor frontRight, frontLeft, backLeft, backRight;
    //private Servo wobbleClaw;
    private DigitalChannel lower_sensor, arm_limit;
    private float fwd, side, turn, power, lower_arm_stick, grabber_stick;
    private double clawOffset = 0.5;
    private double rotation_offset = 0.0;
    private double linearOffset = 0.0;
    private double grabberOffset = 0.0;
    private boolean btn_y, btn_a, left_bumper_1, right_bumper_1, left_bumper_2, right_bumper_2, dpad_left, dpad_right, btn_x1, btn_b1, btn_x2, btn_b2;
    private boolean clawExpanded = false;
    private boolean clawOpen = true;
    private boolean turning = false;
    private boolean gyro_assist = false;
    private boolean front_grabber_up = true;
    private boolean side_grabber_up = true;
    private double target_grabber = 0.0;
    private double constant_F = -1.0;

    // Gyro related initialization
    private BNO055IMU imu;
    private Orientation lastAngles = new Orientation();
    private double globalAngle, correction;

    // Variables
    public final static double deadzone = 0.2;              // Deadzone for bot movement
    public final static double deadzone_hi = 0.9;           // Deadzone for straight movement
    public final static double linear_lower = 0.28;         // Linear servo lower limit
    public final static double linear_upper = 0.615;        // Linear servo upper limit
    public final static double linear_initial = linear_lower;

    @Override
    public void runOpMode() {
        // Gyro initialization and calibration
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu 1");
        imu.initialize(parameters);

        // make sure the imu gyro is calibrated before continuing.
        while (!imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        telemetry.addData("Status", "1");
        telemetry.update();

        telemetry.addData("Status", "2");
        telemetry.update();

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "3");
        telemetry.update();
        telemetry.addData("Status", "4");
        telemetry.update();
        telemetry.addData("Status", "5");
        telemetry.update();
        telemetry.addData("Status", "6");
        telemetry.update();

        // Setting initial positions for claw, grabber, linear servo and arm
        // Skipping initiation at the beginning
        initializePositions();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            getGamePadValues();

            // Bot movement with Mecanum wheels
            power = fwd; //this can be tweaked for exponential power increase

            if (turn != 0) {
                // We are turing
                turning = true;
                frontLeft.setPower(Range.clip(power + turn, -1, 1));
                frontRight.setPower(Range.clip(power - turn, -1, 1));
                backLeft.setPower(Range.clip(power + turn, -1, 1));
                backRight.setPower(Range.clip(power - turn, -1, 1));
            } else {
                // Just finished turning and let it settled down
                if (turning) {
                    frontLeft.setPower(0);
                    frontRight.setPower(0);
                    backLeft.setPower(0);
                    backRight.setPower(0);
                    sleep(500);

                    turning = false;
                }
                power = -power;
                frontLeft.setPower(Range.clip(power + side + correction, -1, 1));
                frontRight.setPower(Range.clip(power - side - correction, -1, 1));
                backLeft.setPower(Range.clip(power - side + correction, -1, 1));
                backRight.setPower(Range.clip(power + side - correction, -1, 1));
            }
        }
    }

    void getGamePadValues() {
        fwd = gamepad1.left_stick_y;                // For mecanum drive forward and backward
        side = gamepad1.left_stick_x;               // For mecanum drive sideway
        turn = gamepad1.right_stick_x;              // For bot turns

        lower_arm_stick = gamepad2.left_stick_y;    // For lower arm movement
        grabber_stick = gamepad2.right_stick_y;     // For grabber

        btn_y = gamepad2.y;                         // For linear servo - up
        btn_a = gamepad2.a;                         // For linear servo - down
        btn_x2 = gamepad2.x;                        // For initialize positions
        btn_b2 = gamepad2.b;                        // For initialize positions

        left_bumper_1 = gamepad1.left_bumper;       // For front grabber
        right_bumper_1 = gamepad1.right_bumper;     // For right grabber
        left_bumper_2 = gamepad2.left_bumper;         // For claw release
        right_bumper_2 = gamepad2.right_bumper;       // For claw grip

        dpad_left = gamepad2.dpad_left;             // For rotation servo
        dpad_right = gamepad2.dpad_right;           // For rotation servo

        //updates joystick values
        if (Math.abs(fwd) < deadzone_hi) fwd = 0;
        if (Math.abs(side) < deadzone_hi) side = 0;
        if (Math.abs(turn) < deadzone) turn = 0;
        if (Math.abs(lower_arm_stick) < deadzone) lower_arm_stick = 0;
        if (Math.abs(grabber_stick) < deadzone) grabber_stick = 0;
        //checks deadzones
    }

    void initializePositions() {
        clawExpanded = false;
        grabberOffset = 0.0;
        clawOffset = 0.0;
        sleep(2000);
    }
}