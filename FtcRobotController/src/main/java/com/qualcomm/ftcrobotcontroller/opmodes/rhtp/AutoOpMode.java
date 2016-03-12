package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class AutoOpMode extends LinearOpMode{
    public static final int ONEWHEELROTATION = 1220;
    public static final double TIME_TO_STOP = 29.5;
    //Initialize variables necessary for methods and auto code
    DcMotorController motorController;
    ElapsedTime eTime =new ElapsedTime();
    Servo servotop;
    OpticalDistanceSensor DistanceSensor;
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
    double FoundDistance = 0;
    //ColorSensor colorBack;
    ColorSensor colorBot;
    double defaultSpeed = -.6;
    double defaultTurnSpeed = -.45;
    double currentposgps = 0;

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
        MoveBackward(moveamount, defaultSpeed);
    }

    protected void MoveBackward(int moveamount, double speed) {
        Log.d("MoveBackward", "Starting To Move Backward");
        encoderatstart=-motorLeft.getCurrentPosition();
        motorLeft.setPower(-speed);
        motorRight.setPower(-speed);
        encoderatstart=-motorLeft.getCurrentPosition();
        while(-motorLeft.getCurrentPosition()<= moveamount+encoderatstart) {
            if (isTimeToStop()) break;
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected boolean MoveForwardTilDistance(double Distance, int moveamount) throws InterruptedException {
        motorLeft.setPower(defaultSpeed/5);
        motorRight.setPower(defaultSpeed/5);
        FoundDistance = DistanceSensor.getLightDetected();
        Log.d("Start Distance", ((Double)FoundDistance).toString());
        while((FoundDistance< Distance)||-motorLeft.getCurrentPosition()>= -moveamount+encoderatstart){
            FoundDistance= DistanceSensor.getLightDetected();
            Thread.sleep(10);
            Log.d("Distance", ((Double)FoundDistance).toString());
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
        Log.d("End Distance", ((Double)FoundDistance).toString());
        if (FoundDistance< Distance)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    protected void MoveForward(int moveamount) {
        MoveForward(moveamount, defaultSpeed);
    }
    protected void MoveForward(int moveamount, double speed) {

        Log.d("MoveForward", "Starting To Move Forward");
        encoderatstart=-motorLeft.getCurrentPosition();
        motorLeft.setPower(speed);
        motorRight.setPower(speed);
        while(-motorLeft.getCurrentPosition()>= -moveamount+encoderatstart) {
            //MoveForwardTilDistance(.06);
            if (isTimeToStop()) break;
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void MoveForwardTilWhite() throws InterruptedException{
       MoveForwardTilWhite(defaultSpeed);
    }
    protected void MoveForwardTilWhite(double speed) throws InterruptedException {
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
            if (isTimeToStop()) break;
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
            if (isTimeToStop()) break;
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
                MoveBackward(ONEWHEELROTATION / 9);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
            if ((colorFront.red() < colorFront.blue()) /*&& colorBack.red() > colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION/2);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
        }
    }

    protected void BeaconPressBlue() throws InterruptedException {
        while(BeaconHasBeenPressed==false){
            if (isTimeToStop()) break;
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
                MoveBackward(ONEWHEELROTATION / 9);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
            if ((colorFront.red() > colorFront.blue())/* && colorBack.red() < colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION/4);
                MoveBackward(ONEWHEELROTATION / 9);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
        }
    }

    protected void TurnRight(int degrees) throws InterruptedException{
        TurnRight(degrees, defaultTurnSpeed);
    }
    public void TurnRight(int degrees, double speed)throws InterruptedException {
        // Turn Right
        Log.d("RightTurn", "Entering Right Turn Function");
        Thread.sleep(turndelay);
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("RightTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(speed);
        motorRight.setPower(-speed);
        currentposgps=gyroSensor.getHeading();
        while ((currentposgps <= degrees-1) || (currentposgps >= degrees + 13)) {
            Log.d("RightTurn", "Current Position: " + currentposgps);
            Thread.sleep(15);
            currentposgps=gyroSensor.getHeading();

            if (isTimeToStop()) break;
        }
        Log.d("RightTurn", "End Position: "+gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void TurnLeft(int degrees) throws InterruptedException {
        TurnLeft(degrees, defaultTurnSpeed);
    }
    public void TurnLeft(int degrees, double speed) throws InterruptedException{
        // Turn Left
        Log.d("LeftTurn", "Entering Left Turn Function");
        Thread.sleep(turndelay);
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("LeftTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(-speed);
        motorRight.setPower(speed);
        currentposgps=gyroSensor.getHeading();
        while  ((currentposgps <= 360-degrees-13) || (currentposgps >= 360-degrees+1)) {
            Log.d("LeftTurn", "Current Position: " + gyroSensor.getHeading());
            Thread.sleep(15);
            currentposgps=gyroSensor.getHeading();

            if (isTimeToStop()) {break;}
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
        servotop.setPosition(0.0);
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
        DistanceSensor = hardwareMap.opticalDistanceSensor.get("opticalDistanceSensor");
        gyroSensor.calibrate();
        //motorBack.setPower(motorbackamount);
        FoundDistance= DistanceSensor.getLightDetected();
        initializeServos();
        InitializeColors();
        waitForStart();
        eTime.reset();
        eTime.startTime();
        InitializeEncoders();
        initializeServos();
        //motorBack.setPower(motorbackamount);
    }
    protected void hitTheBeacon(boolean hitthatbeacon,boolean red) throws InterruptedException {
        if (hitthatbeacon==true) {

            Thread.sleep(1300);
            servotop.setPosition(0.0);
            MoveBackward(ONEWHEELROTATION / 3);
            TurnRight(85);
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
        }
    }
    protected void dump() {
        dumpamount=0;
        while (dumpamount<=0.69) {
            dumpamount=dumpamount+0.000002;
            servotop.setPosition(dumpamount);
            if (isTimeToStop()) break;
        }
    }

    private boolean isTimeToStop() {
        if (eTime.time()>=TIME_TO_STOP)
        {
            return true;
        }
        return false;
    }
}
