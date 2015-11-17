/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class RhtpTankDrive2Controls4OP extends OpMode {

	boolean slowmo = false;
	//Servo servofront;
	//Servo servoback;
	//Servo servotop;
	//Servo servomid;
	DcMotor motorRight;
	DcMotor motorLeft;
	//DcMotor motorFront;
	//DcMotor motorCapture;
	//DcMotor motorArm;
	int timer = 0;
	int timer2 = 0;
	int timer3 = 0;
	int timer4 = 0;
	double servopos;
	double servopos2;
	double servopos3;

	public RhtpTankDrive2Controls4OP() {

	}


	@Override
	public void init() {

		servopos=0.5;
		servopos2=0.1;
		servopos3=0.5;
		//servofront = hardwareMap.servo.get("servo_front");
		motorRight = hardwareMap.dcMotor.get("motor_right");
		motorLeft = hardwareMap.dcMotor.get("motor_left");
		motorLeft.setDirection(DcMotor.Direction.REVERSE);
		//servomid = hardwareMap.servo.get("servo_mid");
		//motorCapture = hardwareMap.dcMotor.get("motor_collect");
		//servoback = hardwareMap.servo.get("servo_back");
		//motorArm = hardwareMap.dcMotor.get("motor_arm");
		//servotop=hardwareMap.servo.get("servo_top");

	}

	@Override
	public void loop() {

			// *Tank Drive part 1
			float left = gamepad1.left_stick_y;
			float right = gamepad1.right_stick_y;

			float left2 = gamepad1.left_stick_y;
			float right2 = gamepad1.right_stick_y;


		if (timer>=30) {
			if (gamepad1.x) {
				if (slowmo == true) {
					slowmo = false;
				} else {
					slowmo = true;
				}
				timer=0;
			}
		}
		timer++;

		// *Cow-catcher
		/*if (timer2>=30) {
			if (gamepad1.b) {
				if (servopos == 1) {
					servopos = 0;
				} else {
					servopos = 1;
				}
				timer2=0;
			}
		}
		timer2++;
		servofront.setPosition(servopos);*/

		// *Tank Drive part 2
		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);
		right2 = Range.clip(right2, -1, 1);
		left2 = Range.clip(left2, -1, 1);

		right = (float)scaleInput(right);
		left =  (float)scaleInput(left);
		right2 = (float)scaleInput(right2);
		left2 = (float)scaleInput(left2);

		motorRight.setPower(right);
		motorLeft.setPower(left);

		left2 = left2*0.2f;
		right2= right2*0.2f;

		if (slowmo==true){
			motorLeft.setPower(left2);
			motorRight.setPower(right2);
		}

		// *Wheelie Bar
		/*if (gamepad1.left_bumper) {
			servoback.setPosition(0);
		} else if (gamepad1.right_bumper) {
			servoback.setPosition(1);
		}*/

		// *Item Collector`
		/*
		if (gamepad2.y)
		{
			motorCapture.setPower(1);
		}
		else{
			motorCapture.setPower(0);
		}
		if (gamepad2.a)
		{
			motorCapture.setPower(-1);
		}*/

		// *Arm Control
		/*
		if (gamepad1.y)
		{
			motorArm.setPower(1);
		}
		else{
			motorArm.setPower(0);
		}
		if (gamepad1.a)
		{
			motorArm.setPower(-1);
		}*/

		// *Guy Dumper
		/*servopos2= Range.clip(servopos, 0.01, .99);
		if (timer3==0)
		{
			servopos2=0.1;
		}
		timer3++;
		if (gamepad2.left_bumper) {
			servopos2-=0.005;
		}
		if (gamepad2.right_bumper) {
			servopos2+=0.005;
		}
		servotop.setPosition(servopos2);*/

		// *Zipline Delivery
		/*servopos2= Range.clip(servopos, 0.01, .99);
		if (timer4==0)
		{
			servopos3=0.5;
		}
		timer4++;
		if (gamepad1.left_trigger>=0.3) {
			servopos3-=0.005;
		}
		if (gamepad1.right_trigger>=0.3) {
			servopos3+=0.005;
		}
		servomid.setPosition(servopos3);*/

		telemetry.addData("Slowmo?", slowmo);
		telemetry.addData("Timer", timer);
		telemetry.addData("Teleop Version", "1.3");
		telemetry.addData("Can control:","2 motor driving (slowmo), 1 servo");
	}


	@Override
	public void stop() {
	}







	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);

		// index should be positive.
		if (index < 0) {
			index = -index;
		}

		// index cannot exceed size of array minus 1.
		if (index > 16) {
			index = 16;
		}

		// get value from the array.
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		// return scaled value.
		return dScale;
	}

}