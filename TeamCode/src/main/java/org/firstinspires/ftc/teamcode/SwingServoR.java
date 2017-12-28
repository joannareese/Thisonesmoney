package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="SwingServoR", group="Red")
public class SwingServoR extends OpMode{

    int i;
    private Servo eddie; //drop down servo (for color sensor)
    private Servo clark; //swing servo (for color sensor)
    private ColorSensor roger; //right color sensor
    private ColorSensor leo; //left color sensor

    private double waitTime;
    private int gameState;


    public void init() {

        eddie = hardwareMap.servo.get("eddie"); //drop down servo
        clark = hardwareMap.servo.get("clark"); //swing servo
        roger = hardwareMap.colorSensor.get( "roger"); //right color sensor
        leo = hardwareMap.colorSensor.get("leo"); //left color sensor

        gameState = 0;
        waitTime = 0;
    }


    public void loop() {
        //super.gameState();

        switch(gameState) {
            case 0: //preset variables
                waitTime = getRuntime(); //get current runTime
                clark.setPosition(1.15);
                gameState = 1;
                break;

            case 1://delay to allow servo to drop
                if(getRuntime() > waitTime + 2) {
                    gameState = 2;
                }
                break;
            case 2: //detect color sensor and choose direction
                waitTime = getRuntime(); //get current runTime
                if (leo.blue()> 0) {
                    eddie.setPosition(0.25);
                    waitTime = getRuntime();
                    eddie.setPosition(0.5);
                    gameState = 3;
                }
                else{
                    eddie.setPosition(0.75);
                    waitTime = getRuntime();
                    eddie.setPosition(0.5);
                    gameState = 3;
                }
                break;
            case 3://delay to allow turn
                if(getRuntime() > waitTime + 2.0) {
                    gameState = 4;
                }
                break;
            case 4: //stop all motors, pull servo up
                clark.setPosition(0.5);
                break;

        }
        telemetry.addData("State", gameState);
        telemetry.addData("Color value blue2", leo.blue());
        telemetry.addData("Current runtime", getRuntime());

    }


}


