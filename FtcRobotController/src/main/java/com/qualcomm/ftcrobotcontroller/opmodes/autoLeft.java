package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Robocrusaders on 12/28/2015.
 */
public class autoLeft extends LinearOpMode {
    DcMotor motorLeft;
    DcMotor motorRight;

    boolean hasRun = false;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");

        if (!hasRun) {
            GoAutonomously();
            hasRun = true;
        }

    }
    public void GoAutonomously(){
        long t= System.currentTimeMillis();
        long end = t + 1500;
        while(System.currentTimeMillis() < end) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.62f);
            motorRight.setPower(-0.5f);
        }

        long tb= System.currentTimeMillis();
        long endb = tb + 1000;
        while(System.currentTimeMillis() < endb) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
        }

        long t2= System.currentTimeMillis();
        long end2 = t2 + 1200;
        while(System.currentTimeMillis() < end2) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.62f);
            motorRight.setPower(0.5);

        }

        long tc= System.currentTimeMillis();
        long endc = tc + 1000;
        while(System.currentTimeMillis() < endc) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
        }

        long t3= System.currentTimeMillis();
        long end3 = t3 + 2250;
        while(System.currentTimeMillis() < end3) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.62f);
            motorRight.setPower(-0.5f);
        }

        long td= System.currentTimeMillis();
        long endd = td + 1000;
        while(System.currentTimeMillis() < endd) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
        }

        long t4= System.currentTimeMillis();
        long end4 = t4 + 600;
        while(System.currentTimeMillis() < end4) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.62f);
            motorRight.setPower(0.5);

        }

        long te= System.currentTimeMillis();
        long ende = te + 1000;
        while(System.currentTimeMillis() < ende) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);
        }

        long t5= System.currentTimeMillis();
        long end5 = t5 + 2250;
        while(System.currentTimeMillis() < end5) {
            // do something
            // pause to avoid churning
            motorLeft.setPower(0.62f);
            motorRight.setPower(-0.5f);
        }

        motorLeft.setPower(0.0f);
        motorRight.setPower(0.0f);
    }


}