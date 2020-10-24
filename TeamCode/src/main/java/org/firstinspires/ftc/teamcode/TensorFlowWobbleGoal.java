package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "Concept: TensorFlow Object Detection Webcam2", group = "Concept")
//  @Disabled
public class TensorFlowWobbleGoal extends LinearOpMode {
    public DMHardware robot = new DMHardware();
    public static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    public static final String LABEL_FIRST_ELEMENT = "Quad";
    public static final String LABEL_SECOND_ELEMENT = "Single";
    public static final String LABEL_NO_ELEMENT = "None";

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

    @Override public void runOpMode() {
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
        /*telemetry.addData("blue:", blue());
        telemetry.update();
        sleep(10000);*/
        telemetry.clear();
        String objectCode = LABEL_NO_ELEMENT;
        robot.strafeLeftForTime(.3, 1.5);
        robot.setPowerOfAllMotorsToForTime(.3, 1);
        if (opModeIsActive()) {
            robot.timer.reset();
            while (opModeIsActive()) {
                boolean fFound = false;
                while (robot.timer.seconds() <= 3) {
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
                    if ( fFound )
                        break;
                }

                telemetry.addData("Object found: ", objectCode);
                telemetry.update();
                robot.strafeLeftForTime(-.3, 2);
                if(objectCode.equalsIgnoreCase(LABEL_NO_ELEMENT)){
                    while(!isBlue()){
                        robot.setPowerOfAllMotorsTo(.4);
                    }
                    robot.setPowerOfAllMotorsTo(0);
                    stop();
                }else if(objectCode.equalsIgnoreCase(LABEL_FIRST_ELEMENT)){
                   while(robot.distanceFront.getDistance(DistanceUnit.INCH) <= 3){
                       robot.setPowerOfAllMotorsTo(.4);
                   }
                   stop();
                }else if(objectCode.equalsIgnoreCase(LABEL_SECOND_ELEMENT)){
                    while(!isBlue()){
                        robot.setPowerOfAllMotorsTo(.4);
                    }
                    robot.strafeRightForTime(-.3, 1);
                    while(!isBlue()){
                        robot.setPowerOfAllMotorsTo(.4);
                    }
                    stop();
                }
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
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

//        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
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

    public boolean isRed()
    {
        int red = robot.colorRight.red();
        int green = robot.colorRight.green();
        int blue = robot.colorRight.blue();

        if (red > green && red > blue && red > 1000)
            return true;

        return false;
    }

    public boolean isGreen()
    {
        int red = robot.colorRight.red();
        int green = robot.colorRight.green();
        int blue = robot.colorRight.blue();

        if (green > red && green > blue && green > 1000)
        return true;

        return false;
    }

    public boolean isBlue()
    {
        int red = robot.colorRight.red();
        int green = robot.colorRight.green();
        int blue = robot.colorRight.blue();

        if (blue > green && blue > red  && blue > 150 && blue < 250)
        return true;

        return false;
    }

    public int red()
    {
        return robot.colorRight.red();
    }

    public int green()
    {
        return robot.colorRight.green();
    }

    public int blue()
    {
        return robot.colorRight.blue();
    }
}

