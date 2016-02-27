

package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class TESTServoControl extends OpMode {


	Servo servo;
	//Servo servo1;
	double servopos=1;

	int timer = 0;

	public TESTServoControl() {

	}

	@Override
	public void init() {
		double servopos=0.5;
		timer = 0;

		servo = hardwareMap.servo.get("servoToTest");

	}

	@Override
	public void loop() {
		if (timer == 0) {
			servopos = .5;
		}
			timer++;


			if (gamepad1.y) {
				servopos += 0.01;
			}
			if (gamepad1.a) {
				servopos -= 0.01;
			}
		servopos = Range.clip(servopos, 0.0, .99);

			servo.setPosition(servopos);


			//servo1.setPosition(servopos1);

			telemetry.addData("jnakfn", servo.getPosition());

	}


		@Override
		public void stop () {

		}
	}

