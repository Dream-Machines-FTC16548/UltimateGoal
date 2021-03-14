package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(name = "Concept: TensorFlow Object Detection Left", group = "Concept")
//  @Disabled
public class TensorFlowWobbleGoalRedRight extends LinearOpMode {
    public DMHardware robot = new DMHardware();
    public static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    public static final String LABEL_FIRST_ELEMENT = "Quad";
    public static final String LABEL_SECOND_ELEMENT = "Single";
    public static final String LABEL_NO_ELEMENT = "None";

    // Vuforia navigation related declarations
    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;

    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;

    VuforiaLocalizer.Parameters parameters = null;

    float hsvValues[] = {0F,0F,0F};

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    public static final String VUFORIA_KEY =
            "ATg5zNT/////AAABmQhNspqC40DCvUSavEYlElMBRSLlK9rhCxs+Jd4rqsraiBijuWYBeupEoJrKGOgaTaP2AuF8RIBHvXtJLaf6jEffF6Jfi0wxmwKfxCegMt4YezZ22wpiK2WfnvvjolMxcVFpKLo38wrA8n88Dy8G2Rg2HAu2HILsg+Sq6dfKpynpbQs8ycs46zHvZUWVp+BVdifSxoKC4RT9zwPtyykIUhiw2Nr1ueaHQKMYTda2EbhgZ/1LP4/fqSNHqZhcqbFTSL3Fcsup+a449TPBlERNWgJDoInJ4lT9iyopclF5tVKqS01xpbmEwAaDp/v5e/aV4HPupgGdRbQCVdIvHQv8XtS7VNT6+Y1wy9QX1MlonqGk";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    public VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    public TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        robot.initTeleOpIMU(hardwareMap);
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            //tfod.setZoom(2.5, 1.78);
        }

        /** Wait for the game to begin */
        telemetry.addLine("Waiting for start button to be pressed");
        telemetry.update();

        waitForStart();
        {
        }
//        telemetry.addData("red:", red());
//        telemetry.update();
//        sleep(10000);
//        telemetry.clear();
        String objectCode = LABEL_NO_ELEMENT;
        robot.strafeRightForTime(-.25, 1.5 );
        robot.setPowerOfAllMotorsToForTime(.2, 0.75 );
        if (opModeIsActive()) {
            robot.timer.reset();
            while (opModeIsActive()) {
                boolean fFound = false;
                while (robot.timer.seconds() <= 1.5) {
                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            // step through the list of recognitions and display boundary info.
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());
                                objectCode = recognition.getLabel();
                                fFound = true;
                                break;
                            }
                            telemetry.update();
                        }
                    }
                    if (fFound)
                        break;
                }

                telemetry.addData("Object found: ", objectCode);
                telemetry.update();
                robot.strafeRightForTime(.2, 2);
                if (objectCode.equalsIgnoreCase(LABEL_NO_ELEMENT)) {
                    // Move forward until white line
                    while (!isRed()) {
                        robot.setPowerOfAllMotorsTo( .3 );
                    }
                    robot.setPowerOfAllMotorsTo(0);
                    robot.timer.reset();
                    while (robot.timer.seconds() <= 1.12) {
                        robot.wobbleGoalArm.setPower(-.15);
                    }
                    robot.strafeLeftForTime(.2, 1.5);
                    while (!isWhite())
                    {
                        robot.setPowerOfAllMotorsTo(.3);
                    }                    // Turn Right
                    robot.setPowerOfAllMotorsTo(0);
                } else if (objectCode.equalsIgnoreCase(LABEL_FIRST_ELEMENT)) {
                    // Move forward until distance sensor senses the wall
                    while (!isWhite()) {
                        robot.setPowerOfAllMotorsTo(.3);
                    }
                    robot.setPowerOfAllMotorsToForTime(.3, 3.0);
//                    double dist = robot.distanceFront.getDistance(DistanceUnit.INCH);
//                      while (dist >= 5.0) {
//                        robot.setPowerOfAllMotorsTo( .1 );
//                        dist = robot.distanceFront.getDistance(DistanceUnit.INCH);
//                        telemetry.addData("distance= ", dist);
//                        telemetry.update();
//                   }
//                    robot.setPowerOfAllMotorsTo(0);
                    // Turn left
                    robot.turnRightForTime(-.4, 1 );
                    // Move forward until red line
                    while (!isRed()) {
                        robot.setPowerOfAllMotorsTo( .3 );
                    }
                    robot.setPowerOfAllMotorsTo(0);

                    stop();
                } else if (objectCode.equalsIgnoreCase(LABEL_SECOND_ELEMENT)) {
                    // Move forward until white line
                    while (!isWhite()) {
                        robot.setPowerOfAllMotorsTo(.3);
                    }
                    robot.setPowerOfAllMotorsTo(0);
                    // Move forward a little
                    robot.setPowerOfAllMotorsToForTime(.25, 1.5 );
                    // Turn left
                    robot.turnRightForTime(-.4, 1.0);
                    // Move forward until red line
                    while (!isRed()) {
                        robot.setPowerOfAllMotorsTo(.1);
                    }
                    robot.setPowerOfAllMotorsTo(0);


                    stop();
                }

                initVuforiaNavigation();

                // Move the robot backward to a position behind the white line (x co-ordinate should be correct after you have done this)

                // Loop:
                // Check current y-coordinate of the robot
                // If current y-coord is within your target range, stop
                //  else
                // Depending on the current y-coord, move the robot sideway (left or right) a little bit


            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    public void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

//        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        parameters.cameraName = robot.getWebcamName();
        // Make sure extended tracking is disabled for this example.
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    public void initVuforiaNavigation() {
        if (parameters == null)
            return;

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsUltimateGoal = this.vuforia.loadTrackablesFromAsset("UltimateGoal");
        VuforiaTrackable blueTowerGoalTarget = targetsUltimateGoal.get(0);
        blueTowerGoalTarget.setName("Blue Tower Goal Target");
        VuforiaTrackable redTowerGoalTarget = targetsUltimateGoal.get(1);
        redTowerGoalTarget.setName("Red Tower Goal Target");
        VuforiaTrackable redAllianceTarget = targetsUltimateGoal.get(2);
        redAllianceTarget.setName("Red Alliance Target");
        VuforiaTrackable blueAllianceTarget = targetsUltimateGoal.get(3);
        blueAllianceTarget.setName("Blue Alliance Target");
        VuforiaTrackable frontWallTarget = targetsUltimateGoal.get(4);
        frontWallTarget.setName("Front Wall Target");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsUltimateGoal);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        //Set the position of the perimeter targets with relation to origin (center of field)
        redAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        blueAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
        frontWallTarget.setLocation(OpenGLMatrix
                .translation(-halfField, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        // The tower goal targets are located a quarter field length from the ends of the back perimeter wall.
        blueTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
        redTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    public void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public boolean isRed() {
        int red = robot.colorLeft.red();
        int green = robot.colorLeft.green();
        int blue = robot.colorLeft.blue();

        Color.RGBToHSV(robot.colorLeft.red() * 8, robot.colorLeft.green() * 8, robot.colorLeft.blue() * 8, hsvValues);

//        telemetry.addData("RED= ", red);
//        telemetry.addData("GREEN= ", green);
        telemetry.addData("hsv= ", hsvValues[0]);
        telemetry.update();

//        if ( red > 100 && red < 150 )
        if ( hsvValues[0] < 50 )
            return true;

        return false;
    }

    public boolean isGreen() {
        int red = robot.colorRight.red();
        int green = robot.colorRight.green();
        int blue = robot.colorRight.blue();

        if (green > red && green > blue && green > 1000)
            return true;

        return false;
    }

    public boolean isBlue() {
        int red = robot.colorRight.red();
        int green = robot.colorRight.green();
        int blue = robot.colorRight.blue();

        if (blue > green && blue > red && blue > 150 && blue < 250)
            return true;

        return false;
    }

    public boolean isWhite() {
        int alphaValue = robot.colorLeft.alpha();
        telemetry.addData("Alpha= ", alphaValue);
        telemetry.update();
        return (alphaValue >= 3000);
    }

    public int red() {
        return robot.colorRight.red();
    }

    public int green() {
        return robot.colorRight.green();
    }

    public int blue() {
        return robot.colorRight.blue();
    }

}

