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
    // Constants
    public static final int ONEWHEELROTATION = 1220;
    public static final double TIME_TO_STOP = 29.5;
    //Initialize variables necessary for methods and auto code
    DcMotorController motorController;
    ElapsedTime eTime =new ElapsedTime();
    Servo servotop;
    OpticalDistanceSensor DistanceSensor;
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
    ColorSensor colorBot;
    double defaultSpeed = -.6;
    double defaultSlowSpeed = -.45;
    double currentposgps = 0;
    int timer = 0;
    int xVal, yVal, zVal = 0;
    int heading = 0;
    int turndelay=1000;
    int encoderatstart=0;
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
        colorBot = hardwareMap.colorSensor.get("colorBot");
        colorBot.enableLed(true);
        waitOneFullHardwareCycle();
        colorBot.enableLed(true);
    }

    protected void MoveBackward(int moveamount) {
        MoveBackward(moveamount, defaultSpeed);
    }

    protected void MoveBackward(int moveamount, double speed) {
        //log movebackward
        Log.d("RHTP MoveBackward", "Starting To Move Backward");
        encoderatstart=-motorLeft.getCurrentPosition();
        motorLeft.setPower(-speed);
        motorRight.setPower(-speed);
        encoderatstart=-motorLeft.getCurrentPosition();
        //drive until desired position or time is up
        while(-motorLeft.getCurrentPosition()<= moveamount+encoderatstart) {
            if (isTimeToStop()) break;
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected boolean MoveForwardTilDistance(double Distance, int moveamount) throws InterruptedException {
        motorLeft.setPower(defaultSlowSpeed);
        motorRight.setPower(defaultSlowSpeed);
        FoundDistance = DistanceSensor.getLightDetected();
        //log move forward
        Log.d("RHTP Start Distance", ((Double)FoundDistance).toString());
        //drive until you are desired distance from wall or you reach maximum allowed distance
        encoderatstart=-motorLeft.getCurrentPosition();
        while((FoundDistance< Distance)&&-motorLeft.getCurrentPosition()>= -moveamount+encoderatstart){
            FoundDistance= DistanceSensor.getLightDetected();
            Thread.sleep(10);
            Log.d("RHTP Distance", ((Double)FoundDistance).toString());
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
        Log.d("RHTP End Distance", ((Double)FoundDistance).toString());
        //if you are desired distance from wall, return true
        if (FoundDistance>= Distance)
        {
            motorLeft.setPower(0);
            motorRight.setPower(0);
            return true;
        }
        else
        {
            motorLeft.setPower(0);
            motorRight.setPower(0);
            return false;
        }
    }
    protected void MoveForward(int moveamount) {
        MoveForward(moveamount, defaultSpeed);
    }
    protected void MoveForward(int moveamount, double speed) {
        //log move forward
        Log.d("RHTP MoveForward", "Starting To Move Forward");
        encoderatstart=-motorLeft.getCurrentPosition();
        motorLeft.setPower(speed);
        motorRight.setPower(speed);
        //move forward until correct distance is reached
        while(-motorLeft.getCurrentPosition()>= -moveamount+encoderatstart) {
            if (isTimeToStop()) break;
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void MoveForwardTilWhite() throws InterruptedException{
       MoveForwardTilWhite(defaultSpeed);
    }
    protected void MoveForwardTilWhite(double speed) throws InterruptedException {
        motorLeft.setPower(-0.3);
        motorRight.setPower(-0.3);
        //activate color sensors
        Color.RGBToHSV(colorBot.red() * 8, colorBot.green() * 8, colorBot.blue() * 8, hsvValues3);
        telemetry.addData("Clear", colorBot.alpha());
        telemetry.addData("Red  ", colorBot.red());
        telemetry.addData("Green", colorBot.green());
        telemetry.addData("Blue ", colorBot.blue());
        telemetry.addData("Hue", hsvValues3[0]);
        colorBot.enableLed(true);
        waitOneFullHardwareCycle();
        colorBot.enableLed(true);
//move forward until alpha reading is greater than 1
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

        //if reading red, hit red
            if ((colorFront.red() > colorFront.blue()) /*&& colorBack.red() < colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION / 9);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
            //if reading blue, back up and hit red
            if ((colorFront.red() < colorFront.blue()) /*&& colorBack.red() > colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION-500);
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


            //if reading blue, hit blue
            if ((colorFront.red() < colorFront.blue())/* && colorBack.red() > colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION / 9);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
            //if reading red, back up and hit blue
            if ((colorFront.red() > colorFront.blue())/* && colorBack.red() < colorBack.blue()*/) {
                MoveBackward(ONEWHEELROTATION-500);
                servoBeacon.setPosition(0.28);
                BeaconHasBeenPressed = true;
            }
        }
    }

    protected void TurnRight(int degrees) throws InterruptedException{
        TurnRight(degrees, defaultSlowSpeed);
    }
    public void TurnRight(int degrees, double speed)throws InterruptedException {
        // log turn right and restart gyro
        Log.d("RHTP RightTurn", "Entering Right Turn Function");

        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("RHTP RightTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(speed);
        motorRight.setPower(-speed);
        currentposgps=gyroSensor.getHeading();
        //turn until desired position
        while ((currentposgps <= degrees-1) || (currentposgps >= degrees + 13)) {
            Log.d("RHTP RightTurn", "Current Position: " + currentposgps);
            Thread.sleep(15);
            currentposgps=gyroSensor.getHeading();

            if (isTimeToStop()) break;
        }
        Log.d("RHTP RightTurn", "End Position: "+gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void TurnLeft(int degrees) throws InterruptedException {
        TurnLeft(degrees, defaultSlowSpeed);
    }
    public void TurnLeft(int degrees, double speed) throws InterruptedException{
        // log turn left and restart gyro
        Log.d("RHTP LeftTurn", "Entering Left Turn Function");

        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("RHTP LeftTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(-speed);
        motorRight.setPower(speed);
        currentposgps=gyroSensor.getHeading();
        //turn until desired position
        while  ((currentposgps <= 360-degrees-13) || (currentposgps >= 360-degrees+1)) {
            Log.d("RHTP LeftTurn", "Current Position: " + gyroSensor.getHeading());
            Thread.sleep(15);
            currentposgps=gyroSensor.getHeading();
            if (isTimeToStop()) {break;}
        }
        Log.d("RHTP LeftTurn", "End Position: " + gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void initializeServos() {//initialization of servo positions
        servofront.setPosition(0.6);
        servoAngle.setPosition(1);
        servomidLeft.setPosition(0.0);
        servomidRight.setPosition(0.8);
        servotop.setPosition(0.0);
        servoBeacon.setPosition(0.7);
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
        motorBack = hardwareMap.dcMotor.get("motorWheelie");
        servoBeacon = hardwareMap.servo.get("servoBeacon");
        DistanceSensor = hardwareMap.opticalDistanceSensor.get("opticalDistanceSensor");
        gyroSensor.calibrate();
        FoundDistance= DistanceSensor.getLightDetected();
        initializeServos();
        InitializeColors();
        waitForStart();
        eTime.reset();
        eTime.startTime();
        InitializeEncoders();
        initializeServos();
    }
    protected void hitTheBeacon(boolean hitthatbeacon,boolean red) throws InterruptedException {//button for navigation to beacon position after dumping climbers
        if (hitthatbeacon==true) {
            Thread.sleep(1300);
            servotop.setPosition(0.0);
            MoveBackward(ONEWHEELROTATION / 3);
            if (red) {
                TurnRight(85);
            }
            else
            {
                TurnRight(86);
            }
            servoBeacon.setPosition(0.6);
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
    protected void dump() {//method for dumping slowly
        dumpamount=0;
        while (dumpamount<=0.69) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
            if (isTimeToStop()) break;
        }
    }

    private boolean isTimeToStop() { //shut down everything at 29.5 seconds
        if (eTime.time()>=TIME_TO_STOP)
        {
            return true;
        }
        return false;
    }
}