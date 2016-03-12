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
//Da
//
package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TELEMainTeleopControlHidalgo extends OpMode {

	//initialization of servos and motors
    Servo servofront;
	Servo servotop;
	Servo servoBeacon;
	Servo servoDeliver;
	Servo servomidLeft;
	Servo servomidRight;
	Servo servoAngle;
	DcMotor motorBack;
	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor motorSecondary;
	DcMotor motorArm;

    //timers and servo defaults
	int timercowcatch = 0;
	int timerdumper = 0;
	int timerzipeline = 0;
	double servopos=0.55;
	double cowcatchpos = 0.81;
	double anglepos=1.0;
	double dumperpos;

    //data output
	String zipisout="";

	public TELEMainTeleopControlHidalgo() {

	}


	@Override
	public void init() {

        //hardware map names
		servofront = hardwareMap.servo.get("servoFront");
		servoDeliver = hardwareMap.servo.get("servoDeliver");
        servoAngle= hardwareMap.servo.get("servoAngle");
        servotop=hardwareMap.servo.get("servoTop");
        servoBeacon = hardwareMap.servo.get("servoBeacon");
		motorRight = hardwareMap.dcMotor.get("motorR");
		motorLeft = hardwareMap.dcMotor.get("motorL");
		motorRight.setDirection(DcMotor.Direction.REVERSE);
		servomidLeft= hardwareMap.servo.get("servoMidLeft");
		servomidRight= hardwareMap.servo.get("servoMidRight");
		motorBack = hardwareMap.dcMotor.get("motorWheelie");
		motorArm = hardwareMap.dcMotor.get("motorArm");
        motorArm.setDirection(DcMotor.Direction.REVERSE);
		motorSecondary =  hardwareMap.dcMotor.get("motorSecondary");
		motorSecondary.setDirection(DcMotor.Direction.REVERSE);

        //Assignment of servo positions
        cowcatchpos =.6;
        anglepos=1.0;
        servofront.setPosition(cowcatchpos);
        servotop.setPosition(0.0);
		servoAngle.setPosition(1);
		servoDeliver.setPosition(0.55);
		servomidLeft.setPosition(0.0);
		servomidRight.setPosition(0.8);
		servoBeacon.setPosition(0.6);
        //teleop version data
		telemetry.addData("Teleop Version", "4.99");
		telemetry.addData("Can control:", "4 motor driving, Wheelie, Arm, Guy Deliver, Cow Catcher, Secondary Lifting, Lifting Angle Control");

	}

	@Override
	public void loop() {
        // *Tank Drive
        float left = gamepad1.left_stick_y;
        float right = gamepad1.right_stick_y;
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
		right = (float)scaleInput(right);
		left =  (float)scaleInput(left);
		motorRight.setPower(right);
		motorLeft.setPower(left);

        // *Cow-catcher
		if (timercowcatch >=30) {
			if (gamepad2.a) {
				if (cowcatchpos == .60) {
					cowcatchpos = .20;
				} else {
					cowcatchpos = .60;
				}
				timercowcatch =0;
			}
		}
		timercowcatch++;
		servofront.setPosition(cowcatchpos);

		//Secondary lift angle control
		if (gamepad1.x) {
			anglepos-=0.009;
		}

		if (gamepad1.a) {
			anglepos+=0.009;
		}
		anglepos = Range.clip(anglepos, 0.01, .99);
		servoAngle.setPosition(anglepos);

		// *Wheelie Bar
		float wheelie = gamepad2.right_stick_y;
		wheelie = Range.clip(wheelie, -1, 1);
		wheelie = (float)scaleInput(wheelie);
		motorBack.setPower(wheelie);

		// *Arm Control
		float arm = gamepad2.left_stick_y;
		arm = Range.clip(arm, -1, 1);
		arm = (float)scaleInput(arm);
		motorArm.setPower(-arm);

		// *Secondary Lifting Functions
		if (gamepad2.left_trigger>=0.3) {
			motorSecondary.setPower(1);
		}
		else
		{
			motorSecondary.setPower(0);
		}
		if (gamepad2.right_trigger>=0.3) {
			motorSecondary.setPower(-1);
		}
		if (gamepad1.left_trigger>=0.3) {
			motorSecondary.setPower(1);
		}
		if (gamepad1.right_trigger>=0.3) {
			motorSecondary.setPower(-1);
		}

		// *Guy Dumper
		dumperpos = Range.clip(dumperpos, 0.01, .99);
		if (timerdumper ==0)
		{
			dumperpos =0.0;
		}
		timerdumper++;
		if (gamepad1.y) {
			dumperpos -=0.005;
		}
		if (gamepad1.b) {
			dumperpos +=0.005;
		}
		servotop.setPosition(dumperpos);

		// Zipline Delivery
		if (timerzipeline ==0)
		{
			servomidLeft.setPosition(0.0);
			servomidRight.setPosition(0.8);
		}
		timerzipeline++;
		if (gamepad2.y)
		{
			servomidLeft.setPosition(0.0);
			servomidRight.setPosition(0.8);
			zipisout="The zipline is SAFE";
		}
		if (gamepad2.b)
		{
			servomidLeft.setPosition(0.0);
			servomidRight.setPosition(0.08);
			zipisout="The zipline is out!";
		}
		if (gamepad2.x)
		{
			servomidLeft.setPosition(0.625);
			servomidRight.setPosition(0.80);
			zipisout="The zipline is out!";
		}

		//* Ball delivery
		if (gamepad1.right_bumper) {
			servopos += 0.01;
		}
		if (gamepad1.left_bumper) {
			servopos -= 0.01;
		}
		if (gamepad1.start) {
			servopos=0.55;
		}
		servopos = Range.clip(servopos, 0.45, .65);
		servoDeliver.setPosition(servopos);

		telemetry.addData("Teleop Version", "4.99");
		telemetry.addData("Can control:", "4 motor driving, Wheelie, Arm, Guy Deliver, Cow Catcher, Secondary Lifting, Lifting Angle Control");
		telemetry.addData("Zipline?", zipisout);
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
