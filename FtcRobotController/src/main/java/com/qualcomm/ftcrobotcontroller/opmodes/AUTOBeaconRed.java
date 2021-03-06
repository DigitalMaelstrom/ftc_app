package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Internet on 11/16/2015.
 */
public class AUTOBeaconRed extends OpMode{

    int timer = 0;
    DcMotor motorR;
    DcMotor motorL;


    @Override
    public void init() {
        motorL = hardwareMap.dcMotor.get("motorR");
        motorR = hardwareMap.dcMotor.get("motorL");
        motorR.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        timer++;
        telemetry.addData("Timer",timer);
        if (timer==20){
            motorL.setPower(1);
            motorR.setPower(1);
        }
        if(timer==180){
            motorL.setPower(0);
            motorR.setPower(0);
        }
        if(timer==181){
            motorL.setPower(1);
            motorR.setPower(0);
        }
        if(timer==260){
            motorL.setPower(0);
            motorR.setPower(0);
        }
        if(timer==261)
        {
            motorL.setPower(1);
            motorR.setPower(1);
        }
        if(timer==340)
        {
            motorL.setPower(1);
            motorR.setPower(0);
        }
        if(timer==480)
        {
            motorL.setPower(1);
            motorR.setPower(1);
        }
        if(timer==500){
            motorL.setPower(0);
            motorR.setPower(0);
        }
    }
}
