package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name="MercuryMechanum", group="Protobot")

public class MercuryMechanum extends OpMode    {

    private Orientation angles;
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private DcMotor top;
    private DcMotor front;
    private Servo franny = null; //left servo
    private Servo mobert = null; //right servo
    private double left;
    private double right;
    private BNO055IMU imu;

    public void init()
    {
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backRight");
        franny = hardwareMap.servo.get("franny");
        mobert = hardwareMap.servo.get("mobert");
        top = hardwareMap.dcMotor.get("top");
        front = hardwareMap.dcMotor.get("front");
        left = 0.32;
        right = .60;
        BNO055IMU imu;
        //BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        //parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        //parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        //parameters.loggingEnabled = true;
        //parameters.loggingTag = "IMU";
        //parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        //imu = hardwareMap.get(BNO055IMU.class, "imu");
        //imu.initialize(parameters);
    }

    public void loop()
    {
        ////////////////
        // MAIN DRIVE //
        ////////////////

        double r = Math.hypot(-gamepad1.right_stick_x, -gamepad1.left_stick_y);
        double robotAngle = Math.atan2(-gamepad1.right_stick_x, -gamepad1.left_stick_y) - Math.PI / 4;
        double rightX = gamepad1.left_stick_x;
        final double v1 = r * Math.sin(robotAngle) + rightX;
        final double v2 = r * Math.cos(robotAngle) + rightX;
        final double v3 = r * Math.cos(robotAngle) - rightX;
        final double v4 = r * Math.sin(robotAngle) - rightX;

        motorFrontRight.setPower(v1);
        motorFrontLeft.setPower(v2);
        motorBackRight.setPower(v3);
        motorBackLeft.setPower(v4);

        /////////////////////////////
        // ORIENTATION CALIBRATION //
        /////////////////////////////


        //if (gamepad1.left_stick_button) {
            //if (imu.isGyroCalibrated()) {
                //telemetry.addData("imu gyro calib status", imu.getCalibrationStatus());
                //angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                //double P = -((Math.abs(gamepad1.left_stick_y) + Math.abs(gamepad1.left_stick_x) / 2));
                //double H = (angles.firstAngle * Math.PI) / 180;
                //double Ht = (Math.PI + Math.atan2(gamepad1.left_stick_x, gamepad1.left_stick_y));

                //motorBackRight.setPower(P * Math.sin(H - Ht));
                //motorFrontLeft.setPower(P * Math.sin(H - Ht));
                //motorBackLeft.setPower(P * Math.cos(H - Ht));
                //motorFrontRight.setPower(P * Math.cos(H - Ht));
        //    }
        //}else {
            //telemetry.addData("imu gyro calib status", imu.getCalibrationStatus());
            //motorFrontRight.setPower(v1);
            //motorFrontLeft.setPower(v2);
            //motorBackRight.setPower(v3);
            //motorBackLeft.setPower(v4);

        //}



        //if (gamepad1.left_stick_button) {
        //angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //telemetry.addData("heading", angles);

        //final double v5 = r * Math.sin(robotAngle) + rightX + angles.firstAngle;
        //final double v6 = r * Math.cos(robotAngle) + rightX + angles.firstAngle;
        //final double v7 = r * Math.cos(robotAngle) - rightX + angles.firstAngle;
        //final double v8 = r * Math.sin(robotAngle) - rightX + angles.firstAngle;

        //motorFrontRight.setPower(v5);
        //motorFrontLeft.setPower(v6);
        //motorBackRight.setPower(v7);
        //motorBackLeft.setPower(v8);

        //}

        ///////////////////////
        // COLLECTION SERVOS //
        ///////////////////////

        if (gamepad2.x) {
            if (left < 0.35 && right > 0.32) {
                left += .01;
                right -= .01;
            }
            franny.setPosition(left);
            mobert.setPosition(right);
        }
        else if (gamepad2.b) {
            if (left > 0.00 && right < 1.0) {
                left -= .01;
                right += .01;
            }
            franny.setPosition(left);
            mobert.setPosition(right);
        }

        if (gamepad2.left_bumper) {
            if (left < 0.35) {
                left += .01;
            }
            franny.setPosition(left);
        }
        else if (gamepad2.left_trigger > .7) {
            if (left > 0.0) {
                left -= .01;
            }
            franny.setPosition(left);
        }

        if (gamepad2.right_bumper) {
            if (right > 0.32) {
                right -= .01;
            }
            mobert.setPosition(right);
        }
        else if (gamepad2.right_trigger > .7) {
            if (right < 1) {
                right += .01;
            }
            mobert.setPosition(right);
        }

        telemetry.addData("Left", left);
        telemetry.addData("Right", right);
        telemetry.addData("franny", franny);
        telemetry.addData("mobert", mobert);


        ///////////////////
        // BELT CONTROLS //
        ///////////////////

        if (gamepad2.dpad_up) {
            top.setPower(-0.45);
        } else if (gamepad2.dpad_down) {
            top.setPower(0.45);
        } else {
            top.setPower(0);
        }

        if (gamepad2.y) {
            front.setPower(-0.7);
        } else if (gamepad2.a) {
            front.setPower(0.7);
        } else {
            front.setPower(0);
        }
    }
}