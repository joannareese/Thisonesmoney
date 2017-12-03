package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;

@Autonomous(name="Red Front", group="Red")
public class RedFront extends AutonomousBase {

    double xTime;
    int i;
    private OpenGLMatrix lastLocation;
    private DcMotor motorFrontRight;
    private DcMotor motorBackLeft;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor top;
    private DcMotor front;
    private Servo franny = null; //left servo
    private Servo mobert = null; //right servo
    private Servo servo;
    private VuforiaLocalizer vuforia;
    private VuforiaTrackable relicTemplate;
    //private int startDeg;
    private int gameState;
    private ColorSensor sensorColor;
    private boolean started;
    private double waitTime;

    public void init() {
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        top = hardwareMap.dcMotor.get("top");
        front = hardwareMap.dcMotor.get("front");
        franny = hardwareMap.servo.get("franny");
        mobert = hardwareMap.servo.get("mobert");
        servo = hardwareMap.servo.get("servo");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColor");
        //startDeg = 0;
        gameState = 0;
        started = false;
        waitTime = 0;
        map.setRobot(10, 2);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void loop() {
        //super.gameState();
        if (!started) {
            started = true;
            waitTime = getRuntime() + 3;
        }
        switch (gameState) {
            case 0:
                franny.setPosition(.35);
                mobert.setPosition(.32);
                servo.setPosition(0.92);
                telemetry.addData("Time", getRuntime());

                sTime = getRuntime();
                map.setRobot(10, 2);


                if (waitTime + 1 <= sTime) {
                    waitTime = getRuntime() + .5;
                    gameState = 1;
                }

                break;
//rawrhhhh 12345678

            case 1:

                sTime = getRuntime();
                telemetry.addData("red", sensorColor.red());
                telemetry.addData("blue", sensorColor.blue());

                if (sensorColor.red() > sensorColor.blue()) {

                    motorFrontLeft.setPower(-.6);
                    motorBackRight.setPower(-.6);
                    if(getRuntime() <= sTime + 1)
                        gameState = 2;
                }
                else {

                    motorBackRight.setPower(.6);
                    motorFrontLeft.setPower(.6);
                    gameState = 2;

                }

                if(getRuntime() <= sTime + 1.5) {
                    //servo.setPosition(.5);
                    //moveState = MoveState.STOP;
                    gameState = 2;
                }

                //if (waitTime + .5 <= sTime) {
                //    gameState = 2;
                //}
                break;

// commented vuforia goes here
            case 2:
                servo.setPosition(.5);
                sTime = getRuntime() + 2;
                moveState = MoveState.STOP;
                if(sTime <= getRuntime())
                    gameState = 3;
                break;

            case 3:

                map.setGoal(11, 5);
                moveState = MoveState.STRAFE_TOWARDS_GOAL;
                if(map.distanceToGoal()<=.1) {
                    moveState = MoveState.STOP;
                    motorFrontLeft.setPower(0);
                    motorFrontRight.setPower(0);
                    motorBackLeft.setPower(0);
                    motorBackRight.setPower(0);
                }

                break;

        }
        telemetry.addData("Motor degrees", motorBackRight.getCurrentPosition());
        //telemetry.addData("Start degrees", startDeg);

    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";



    }

}