package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by Internet on 11/12/2015.
 */
public class WheelieBarTEST extends OpMode {

    Servo servofront;

    @Override
    public void init() {
        servofront = hardwareMap.servo.get("servo_front");
        servofront.setPosition(.35);
    }

    @Override
    public void loop() {
        if (gamepad1.y) {
            servofront.setPosition(.35);
        } else if (gamepad1.a) {
            servofront.setPosition(.04);
        }

    }
}