package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Internet on 12/8/2015.
 */
public class TESTAUTOmove extends LinearOpMode {

    DcMotor motorLeft;
    DcMotor motorRight;

    @Override
    public void runOpMode() throws InterruptedException {

        int time = 0;
        motorRight = hardwareMap.dcMotor.get("motorR");
        motorLeft = hardwareMap.dcMotor.get("motorL");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        telemetry.addData("Time,", time);

        motorLeft.setPower(1);
        motorRight.setPower(1);
        Thread.sleep(500);
        motorLeft.setPower(0);
        motorRight.setPower(0);
        Thread.sleep(1000);
        motorRight.setPower(-1);
        motorLeft.setPower(1);
        Thread.sleep(2000);
        stopmotors();
    }
public void stopmotors() {
    motorRight.setPower(0);
    motorLeft.setPower(0);
}
}


