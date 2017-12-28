package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@Autonomous(name="SwingServoR", group="Red")
public class SwingServoR extends OpMode{

    int i;
    private Servo clark; //drop down servo (for color sensor)
    private Servo eddie; //swing servo (for color sensor)
    private ColorSensor roger; //right color sensor
    private ColorSensor leo; //left color sensor
    private boolean blue;
    private double waitTime;
    private int gameState;


    public void init() {

        eddie = hardwareMap.servo.get("eddie"); //swing servo
        clark = hardwareMap.servo.get("clark"); //drop down servo
        roger = hardwareMap.colorSensor.get( "roger"); //right color sensor
        leo = hardwareMap.colorSensor.get("leo"); //left color sensor
        blue = false;
        gameState = 0;
        waitTime = 0;
    }


    public void loop() {

        telemetry.addData("gameState", gameState);
        telemetry.addData("leo blue", leo.blue());
        telemetry.addData("roger blue", roger.blue());
        telemetry.addData("runtime", getRuntime());

        switch(gameState) {
            case 0: //preset variables
                clark.setPosition(1.15);
                gameState = 1;
                waitTime = getRuntime(); //get current runTime
                break;

            case 1://delay to allow servo to drop
                if(getRuntime() > waitTime + 2.0) {
                    gameState = 2;
                }
                break;

            case 2: //detect color sensor and choose direction
                if (leo.blue() < roger.blue()) {
                    eddie.setPosition(0.6);
                    //eddie.setPosition(0.5);
                    gameState = 3;
                    blue = true;
                }
                else{
                    eddie.setPosition(0.4);
                    //eddie.setPosition(0.5);
                    gameState = 3;
                    blue = false;
                }
                waitTime = getRuntime(); //get current runTime
                break;

            case 3://delay to allow turn
                if(getRuntime() > waitTime + 2.0) {
                    gameState = 4;
                }
                break;

            case 4: //stop all motors, pull servo up
                clark.setPosition(0.6);
                break;
        }
    }
}