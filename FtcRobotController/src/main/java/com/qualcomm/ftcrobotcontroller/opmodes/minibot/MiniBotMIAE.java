

package com.qualcomm.ftcrobotcontroller.opmodes.minibot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class MiniBotMIAE extends OpMode {

	//DcMotor motorArm;
	DcMotor motorRight;
	DcMotor motorLeft;
	//int timerslowmode =0;
	//Servo servo1;
	//double servopos=0;
	//boolean slow=false;

	public MiniBotMIAE() {

	}

	@Override
	public void init() {
//		servo1 = hardwareMap.servo.get("servo_1");
		motorRight = hardwareMap.dcMotor.get("motor_2");
		motorLeft = hardwareMap.dcMotor.get("motor_1");
		motorLeft.setDirection(DcMotor.Direction.REVERSE);
//		motorArm=hardwareMap.dcMotor.get("motor_3");
	}

	@Override
	public void loop() {

//		if (timerslowmode >=30) {
//			if (gamepad1.a) {
//				if (slow == true) {
//					slow = false;
//				} else {
//					slow = true;
//				}
//				timerslowmode =0;
//			}
//		}
//		timerslowmode++;
		// note that if y equal -1 then joystick is pushed all of the way forward.
		float left = -gamepad1.left_stick_y;
		float right = -gamepad1.right_stick_y;
//		if (slow)
//		{
//			right=right/2;
//			left=left/2;
//		}
		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);
		right = (float)scaleInput(right);
		left =  (float)scaleInput(left);

		motorRight.setPower(right);
		motorLeft.setPower(left);
//
//		if (gamepad1.y) {
//			motorArm.setPower(0.4);
//		}
//		else
//		{
//			motorArm.setPower(0);
//		}
//		if (gamepad1.b) {
//			motorArm.setPower(-0.4);
//		}
//
//		if (gamepad1.left_trigger>=0.3) {
//			servopos-=0.01;
//		}
//		if (gamepad1.right_trigger>=0.3) {
//			servopos+=0.01;
//		}
//		servopos = Range.clip(servopos, 0.00, 1);
//
//		servo1.setPosition(servopos);
		telemetry.addData("Text", "*** Robot Data***");
		//telemetry.addData("left drive pwr",  "left  pwr: " + String.format("%.2f", left));
		//telemetry.addData("right drive pwr", "right pwr: " + String.format("%.2f", right));
	}

	@Override
	public void stop() {

	}

	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

		int index = (int) (dVal * 16.0);

		if (index < 0) {
			index = -index;
		}

		if (index > 16) {
			index = 16;
		}

		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}
		return dScale;
	}

}
