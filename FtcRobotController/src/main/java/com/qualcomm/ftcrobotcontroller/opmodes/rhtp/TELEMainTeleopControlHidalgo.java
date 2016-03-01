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

	//initialization of variables, motors, and servos
    Servo servofront;
	Servo servotop;
	//Servo servomid;
	Servo servoDeliver;
	Servo servomidLeft;
	Servo servomidRight;
	Servo servoAngle;
	DcMotor motorBack;
	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor motorSecondary;
	//DcMotor motorCapture;
	DcMotor motorArm;
	int Timer=0;
	int Stage=0;

	int timercowcatch = 0;
	int timerdumper = 0;
	int timerzipeline = 0;
	int encoderstart=0;
	double servopos=0.55;

	double cowcatchpos = 0.81;
	double anglepos=1.0;
	double dumperpos;
	//double ziplinepos;
	String zipisout="";

	public TELEMainTeleopControlHidalgo() {

	}


	@Override
	public void init() {
		//Assignment of servo positions and hardwaremap registration
		cowcatchpos =.6;
		//dumperpos =0.1;
		//ziplinepos =0.5;
		anglepos=1.0;

		servofront = hardwareMap.servo.get("servoFront");
		servoDeliver = hardwareMap.servo.get("servoDeliver");
		motorRight = hardwareMap.dcMotor.get("motorR");
		motorLeft = hardwareMap.dcMotor.get("motorL");
		motorRight.setDirection(DcMotor.Direction.REVERSE);
		//servomid = hardwareMap.servo.get("servoMid");
		servomidLeft= hardwareMap.servo.get("servoMidLeft");
		servomidRight= hardwareMap.servo.get("servoMidRight");
		servoAngle= hardwareMap.servo.get("servoAngle");
		motorBack = hardwareMap.dcMotor.get("motorWheelie");
		motorArm = hardwareMap.dcMotor.get("motorArm");
		motorSecondary =  hardwareMap.dcMotor.get("motorSecondary");
		motorSecondary.setDirection(DcMotor.Direction.REVERSE);
		servotop=hardwareMap.servo.get("servoTop");
		servofront.setPosition(cowcatchpos);
		//servomid.setPosition(0.5);
		servotop.setPosition(0.0);
		servoAngle.setPosition(1);
		servoDeliver.setPosition(0.55);

		/*while (Stage!=2) {
			motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
			if ((Stage == 0) && Timer == 200) {
				motorController.setMotorChannelMode(motorArm.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
				Stage++;
				Timer = 0;
			}
			if ((Stage == 1) && Timer == 200) {
				motorController.setMotorChannelMode(motorArm.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
				Stage++;
			}
			Timer++;
		}*/

	}

	@Override
	public void loop() {
		Timer++;
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

        //Push A to push out/pull in the cow catcher

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
		//Left joystick on gamepad 2
		float arm = gamepad2.left_stick_y;
		arm = Range.clip(arm, -1, 1);
		arm = (float)scaleInput(arm);
		motorArm.setPower(-arm);

		// Hard Stop for Lift

		//if ()

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
		//Use the left trigger to lower the guy dumper, right trigger to raise the guy dumper
		if (gamepad1.y) {
			dumperpos -=0.005;
		}
		if (gamepad1.b) {
			dumperpos +=0.005;
		}
		servotop.setPosition(dumperpos);

		// *Zipline Delivery Type 1
		/*ziplinepos = Range.clip(ziplinepos, 0.01, .99);
		if (timerzipeline ==0)
		{
			ziplinepos =0.5;
		}
		timerzipeline++;
		if (gamepad2.y)
		{
			ziplinepos=0.5;
			zipisout="The zipline is SAFE";
		}
		if (gamepad2.x)
		{
			ziplinepos=1;
			zipisout="The zipline is out!";
		}
		if (gamepad2.b)
		{
			ziplinepos=0.1;
			zipisout="The zipline is out!";
		}
		servomid.setPosition(ziplinepos);*/

		// *Zipline Delivery Type 2
		if (timerzipeline ==0)
		{
			servomidLeft.setPosition(0.0);
			servomidRight.setPosition(0.0);
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
			servomidRight.setPosition(0.125);
			zipisout="The zipline is out!";
		}
		if (gamepad2.x)
		{
			servomidLeft.setPosition(0.625);
			servomidRight.setPosition(0.80);
			zipisout="The zipline is out!";
		}
		//*ball delevery
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

		telemetry.addData("AAAA", wheelie);
		telemetry.addData("Teleop Version", "4.99");
		telemetry.addData("Can control:", "4 motor driving, Wheelie, Arm, Guy Deliver, Cow Catcher, Secondary Lifting, Lifting Angle Control");
		telemetry.addData("ArmPos",motorArm.getCurrentPosition());
		//telemetry.addData("Zipline?", zipisout);
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
