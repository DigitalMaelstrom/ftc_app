package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Internet on 11/16/2015.
 */
public class AUTORedSideBlueRamp extends OpMode{

    int timer = 0;
    DcMotor motorR;
    DcMotor motorL;
    DcMotorController motorController;


    @Override
    public void init() {
        motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
        motorL = hardwareMap.dcMotor.get("motorR");
        motorR = hardwareMap.dcMotor.get("motorL");
        motorR.setDirection(DcMotor.Direction.REVERSE);
        motorController.setMotorChannelMode(motorR.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
        motorController.setMotorChannelMode(motorR.getPortNumber(), DcMotorController.RunMode.RUN_TO_POSITION);

    }

    @Override
    public void loop() {
        timer++;
        telemetry.addData("Timer",timer);
        if (timer==20){

            motorL.setPower(1);
            motorR.setPower(1);
        }
        motorR.setTargetPosition(-2440);
       /* if(motorL.getCurrentPosition()>=1000){
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
        if(timer==298)
        {
            motorL.setPower(1);
            motorR.setPower(0);
        }
        if(timer==395)
        {
            motorL.setPower(1);
            motorR.setPower(1);
        }
        if(timer==(490)){
            motorL.setPower(0);
            motorR.setPower(0);
        }*/
        telemetry.addData("pos", motorR.getCurrentPosition());
    }
}
