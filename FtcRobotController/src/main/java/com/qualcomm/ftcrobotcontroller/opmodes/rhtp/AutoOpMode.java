package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;


public abstract class AutoOpMode extends LinearOpMode{
    public static final int ONEWHEELROTATION = 1220;

    //Initialize variables necessary for methods and auto code
    DcMotorController motorController;
    Servo servotop;
    //Servo servomid;
    Servo servofront;
    Servo servomidLeft;
    Servo servomidRight;
    Servo servoAngle;
    Servo servoDeliver;
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorBack;
    GyroSensor gyroSensor;
    ColorSensor colorFront;
    //ColorSensor colorBack;
    ColorSensor colorBot;
    double speed = -.5;

    int timer = 0;
    int xVal, yVal, zVal = 0;
    int heading = 0;
    int turndelay=1000;
    int encoderatstart=0;
    //double motorbackamount=-0.2;
    float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;
    float hsvValues2[] = {0F, 0F, 0F};
    final float values2[] = hsvValues2;
    float hsvValues3[] = {0F, 0F, 0F};
    final float values3[] = hsvValues3;
    Servo servoBeacon;
    boolean BeaconHasBeenPressed=false;

    double dumpamount=0;

    //end of variable initilization

    protected void initializeGyro() throws InterruptedException {
        //initialize gyro with minimum required wait time for accuracy
        while(gyroSensor.isCalibrating()) {
            Thread.sleep(50);
        }
        heading = gyroSensor.getHeading();


        /*xVal = gyroSensor.rawX();
        yVal = gyroSensor.rawY();
        zVal = gyroSensor.rawZ();*/

    }
    protected void InitializeEncoders() throws InterruptedException {
        //initialize encoders and set channels with minimum required wait time for accuracy
        motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
        Thread.sleep(50);
        motorController.setMotorChannelMode(motorRight.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
        motorController.setMotorChannelMode(motorLeft.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
        telemetry.addData("pos", -motorLeft.getCurrentPosition());
        Thread.sleep(400);
        motorController.setMotorChannelMode(motorRight.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorController.setMotorChannelMode(motorLeft.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        Thread.sleep(40);
    }

    protected void InitializeColors() throws InterruptedException {
        //initialize color sensors in correct order
        colorFront = hardwareMap.colorSensor.get("colorFront");
        colorFront.enableLed(false);
        /*colorBack = hardwareMap.colorSensor.get("colorBack");
        colorBack.enableLed(false);*/
        colorBot = hardwareMap.colorSensor.get("colorBot");
        colorBot.enableLed(true);
        waitOneFullHardwareCycle();
        colorBot.enableLed(true);
    }

    protected void MoveBackward(int moveamount) {
        Log.d("MoveBackward", "Starting To Move Backward");
        encoderatstart=-motorLeft.getCurrentPosition();
        motorLeft.setPower(-speed);
        motorRight.setPower(-speed);
        while(-motorLeft.getCurrentPosition()<= moveamount+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void MoveForward(int moveamount) {
        Log.d("MoveForward", "Starting To Move Forward");
        encoderatstart=-motorLeft.getCurrentPosition();
        motorLeft.setPower(speed);
        motorRight.setPower(speed);
        while(-motorLeft.getCurrentPosition()>= -moveamount+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void MoveForwardTilWhite() throws InterruptedException {
        motorLeft.setPower(speed / 5);
        motorRight.setPower(speed / 5);
        Color.RGBToHSV(colorBot.red() * 8, colorBot.green() * 8, colorBot.blue() * 8, hsvValues3);
        telemetry.addData("Clear", colorBot.alpha());
        telemetry.addData("Red  ", colorBot.red());
        telemetry.addData("Green", colorBot.green());
        telemetry.addData("Blue ", colorBot.blue());
        telemetry.addData("Hue", hsvValues3[0]);
        //while (colorBot.alpha()<1) {
        colorBot.enableLed(true);
        waitOneFullHardwareCycle();
        colorBot.enableLed(true);

        while (colorBot.alpha()<1) {
            timer++;
            if (timer==100) {
                colorBot.enableLed(true);
                timer=0;
            }
            Color.RGBToHSV(colorBot.red() * 8, colorBot.green() * 8, colorBot.blue() * 8, hsvValues3);
            telemetry.addData("Clear", colorBot.alpha());
            telemetry.addData("Red  ", colorBot.red());
            telemetry.addData("Green", colorBot.green());
            telemetry.addData("Blue ", colorBot.blue());
            telemetry.addData("Hue", hsvValues3[0]);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    protected void BeaconPressRed() throws InterruptedException {
        while(BeaconHasBeenPressed==false){
            Color.RGBToHSV(colorFront.red() * 8, colorFront.green() * 8, colorFront.blue() * 8, hsvValues);
            telemetry.addData("Clear", colorFront.alpha());
            telemetry.addData("Red  ", colorFront.red());
            telemetry.addData("Green", colorFront.green());
            telemetry.addData("Blue ", colorFront.blue());
            telemetry.addData("Hue", hsvValues[0]);

            /*Color.RGBToHSV(colorBack.red() * 8, colorBack.green() * 8, colorBack.blue() * 8, hsvValues2);
            telemetry.addData("Clear2", colorBack.alpha());
            telemetry.addData("Red2  ", colorBack.red());
            telemetry.addData("Green2", colorBack.green());
            telemetry.addData("Blue2 ", colorBack.blue());
            telemetry.addData("Hue2", hsvValues2[0]);
            waitOneFullHardwareCycle();*/

            if ((colorFront.red() > colorFront.blue()) /*&& colorBack.red() < colorBack.blue()*/) {
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
            if ((colorFront.red() < colorFront.blue()) /*&& colorBack.red() > colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION/4);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
        }
    }

    protected void BeaconPressBlue() throws InterruptedException {
        while(BeaconHasBeenPressed==false){
            Color.RGBToHSV(colorFront.red() * 8, colorFront.green() * 8, colorFront.blue() * 8, hsvValues);
            telemetry.addData("Clear", colorFront.alpha());
            telemetry.addData("Red  ", colorFront.red());
            telemetry.addData("Green", colorFront.green());
            telemetry.addData("Blue ", colorFront.blue());
            telemetry.addData("Hue", hsvValues[0]);

            /*Color.RGBToHSV(colorBack.red() * 8, colorBack.green() * 8, colorBack.blue() * 8, hsvValues2);
            telemetry.addData("Clear2", colorBack.alpha());
            telemetry.addData("Red2  ", colorBack.red());
            telemetry.addData("Green2", colorBack.green());
            telemetry.addData("Blue2 ", colorBack.blue());
            telemetry.addData("Hue2", hsvValues2[0]);
            waitOneFullHardwareCycle();*/

            if ((colorFront.red() < colorFront.blue())/* && colorBack.red() > colorBack.blue()*/) {
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
            if ((colorFront.red() > colorFront.blue())/* && colorBack.red() < colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION/4);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
        }
    }

    public void TurnRight(int degrees)throws InterruptedException {
        // Turn Right
        Log.d("RightTurn", "Entering Right Turn Function");
        Thread.sleep(turndelay);
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("RightTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(speed);
        motorRight.setPower(-speed);
        while ((gyroSensor.getHeading() <= degrees-3) || (gyroSensor.getHeading() >= degrees+13)) {
            Log.d("RightTurn", "Current Position: " + gyroSensor.getHeading());
        }
        Log.d("RightTurn", "End Position: "+gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnLeft(int degrees) throws InterruptedException{
        // Turn Left
        Log.d("LeftTurn", "Entering Left Turn Function");
        Thread.sleep(turndelay);
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("LeftTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(-speed);
        motorRight.setPower(speed);
        while  ((gyroSensor.getHeading() <= 360-degrees-3) || (gyroSensor.getHeading() >= 360-degrees+3)) {
            Log.d("LeftTurn", "Current Position: " + gyroSensor.getHeading());
        }
        Log.d("LeftTurn", "End Position: " + gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);

    }
    protected void initializeServos() {
        servofront.setPosition(0.6);
        servoAngle.setPosition(1);
        servomidLeft.setPosition(0.0);
        servomidRight.setPosition(0.8);
        //servomid.setPosition(0.5);
        servotop.setPosition(0.1);
        servoBeacon.setPosition(0.6);
        servoDeliver.setPosition(0.55);
    }
    protected void StartAutoOp() throws InterruptedException {
        servofront = hardwareMap.servo.get("servoFront");
        servoDeliver = hardwareMap.servo.get("servoDeliver");
        motorRight = hardwareMap.dcMotor.get("motorR");
        motorLeft = hardwareMap.dcMotor.get("motorL");
        servomidLeft= hardwareMap.servo.get("servoMidLeft");
        servomidRight= hardwareMap.servo.get("servoMidRight");
        servoAngle= hardwareMap.servo.get("servoAngle");
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        gyroSensor = hardwareMap.gyroSensor.get("gyro");
        servotop=hardwareMap.servo.get("servoTop");
        //servomid = hardwareMap.servo.get("servoMid");
        motorBack = hardwareMap.dcMotor.get("motorWheelie");
        servoBeacon = hardwareMap.servo.get("servoBeacon");
        gyroSensor.calibrate();
        //motorBack.setPower(motorbackamount);
        initializeServos();
        InitializeColors();
        waitForStart();
        InitializeEncoders();
        initializeServos();
        //motorBack.setPower(motorbackamount);
    }
    protected void hitTheBeacon(boolean hitthatbeacon,boolean red) throws InterruptedException {
        if (hitthatbeacon==true)
        {
            Thread.sleep(2000);
            servotop.setPosition(0.1);
            MoveBackward(ONEWHEELROTATION/3);
            TurnRight(80);
            MoveBackward(ONEWHEELROTATION / 2);
            MoveForwardTilWhite();
            if (red) {
                BeaconPressRed();
            }
            else
            {
                BeaconPressBlue();
            }
            Thread.sleep(1000);
            MoveBackward(ONEWHEELROTATION / 9);
        }
    }
}
