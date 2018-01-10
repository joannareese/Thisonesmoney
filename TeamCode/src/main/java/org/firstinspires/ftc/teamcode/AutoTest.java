package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name="Auto Test", group ="Vuforia")

public class AutoTest extends LinearOpMode {

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorFrontRight;
    public DcMotor motorBackLeft;

    public ElapsedTime runtime = new ElapsedTime();

    public static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    public static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    public static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    public static final double DRIVE_SPEED = 0.5;
    public static final double TURN_SPEED = 0.4;

    @Override
    public void runOpMode() {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        time = getRuntime();

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                motorFrontLeft.getCurrentPosition(),
                motorBackRight.getCurrentPosition(),
                motorFrontRight.getCurrentPosition(),
                motorBackLeft.getCurrentPosition());
        telemetry.update();
        waitForStart();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AfBkGLH/////AAAAGUUS7r9Ue00upoglw/0yqTBLwhqYHpjwUK9zxmWMMFGuNGPjo/RjNOTsS8POmdQLHwe3/75saYsyb+mxz6p4O8xFwDT7FEYMmKW2NKaLKCA2078PZgJjnyw+34GV8IBUvi2SOre0m/g0X5eajpAhJ8ZFYNIMbUfavjQX3O7P0UHyXsC3MKxfjMzIqG1AgfRevcR/ONOJlONZw7YIZU3STjODyuPWupm2p7DtSY4TRX5opqFjGQVKWa2IlNoszsN0szgW/xJ1Oz5VZp4oDRS8efG0jOq1QlGw7IJOs4XXZMcsk0RW/70fVeBiT+LMzM8Ih/BUxtVVK4pcLMpb2wlzdKVLkSD8LOpaFWmgOhxtNz2M";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessa


        //telemetr.addData(">", "Press Play to start");
        //telemetry.update();
        waitForStart();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        telemetry.addData("VuMark", vuMark);

        switch (vuMark) {
            case RIGHT:
                encoderDrive(DRIVE_SPEED, -28.5, 28.5, -28.5, 28.5, 4);
                motorFrontLeft.setPower(0);
                motorBackRight.setPower(0);
                motorFrontRight.setPower(0);
                motorBackLeft.setPower(0);
                break;
            case LEFT:
                encoderDrive(DRIVE_SPEED, -42.5, 42.5, -42.4, 42.5, 4);
                motorFrontLeft.setPower(0);
                motorBackRight.setPower(0);
                motorFrontRight.setPower(0);
                motorBackLeft.setPower(0);
                break;
            case CENTER:
                encoderDrive(DRIVE_SPEED, -35.5, 35.5, -35.5, 35.5, 4);
                motorFrontLeft.setPower(0);
                motorBackRight.setPower(0);
                motorFrontRight.setPower(0);
                motorBackLeft.setPower(0);
                break;
            default:
                encoderDrive(DRIVE_SPEED, 35.5, -35.5, 35.5, -35.5, 2);
                motorFrontLeft.setPower(0);
                motorBackRight.setPower(0);
                motorFrontRight.setPower(0);
                motorBackLeft.setPower(0);
                break;

        }


        relicTrackables.activate();

        while (opModeIsActive()) {


        }

        idle();
        telemetry.update();
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    public void encoderDrive(double speed,
                             double frontLeftInches,
                             double frontRightInches,
                             double backLeftInches,
                             double backRightInches,
                             double timeoutS) {
        int newFrontRightTarget;
        int newBackRightTarget;
        int newFrontLeftTarget;
        int newBackLeftTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = motorFrontLeft.getCurrentPosition() + (int) (frontLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = motorBackRight.getCurrentPosition() + (int) (backRightInches * COUNTS_PER_INCH);
            newFrontRightTarget = motorFrontRight.getCurrentPosition() + (int) (frontRightInches * COUNTS_PER_INCH);
            newBackLeftTarget = motorBackLeft.getCurrentPosition() + (int) (backLeftInches * COUNTS_PER_INCH);

            motorFrontLeft.setTargetPosition(newFrontLeftTarget);
            motorBackRight.setTargetPosition(newBackRightTarget);
            motorFrontRight.setTargetPosition(newFrontRightTarget);
            motorBackLeft.setTargetPosition(newBackLeftTarget);

            motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            motorFrontLeft.setPower(Math.abs(speed));
            motorBackRight.setPower(Math.abs(speed));
            motorFrontRight.setPower(Math.abs(speed));
            motorBackLeft.setPower(Math.abs(speed));

            telemetry.addData("speed", speed);
            telemetry.addData("target", newBackLeftTarget);

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorFrontLeft.isBusy() && motorBackRight.isBusy() && motorBackLeft.isBusy() && motorFrontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newFrontLeftTarget, newBackRightTarget, newBackLeftTarget, newFrontRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        motorFrontLeft.getCurrentPosition(),
                        motorBackRight.getCurrentPosition(),
                        motorFrontRight.getCurrentPosition(),
                        motorBackLeft.getCurrentPosition());
            }

            if (motorFrontLeft.getCurrentPosition() == frontLeftInches &&
                    motorBackRight.getCurrentPosition() == backRightInches &&
                    motorFrontRight.getCurrentPosition() == frontRightInches &&
                    motorBackLeft.getCurrentPosition() == backLeftInches) {

                telemetry.update();
                motorFrontLeft.setPower(0);
                motorBackRight.setPower(0);
                motorFrontRight.setPower(0);
                motorBackLeft.setPower(0);

                // Turn off RUN_TO_POSITION
                motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                sleep(250);   // optional pause after each move

                telemetry.update();

            }
        }
    }
}