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

package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class TELEMainTeleopControl extends OpMode {


    //DcMotorController motorController;
    Servo servofront;
	Servo servotop;
	Servo servomid;
	DcMotor motorBack;
	DcMotor motorRight;
	DcMotor motorLeft;
	//DcMotor motorCapture;
	DcMotor motorArm;
    boolean gototop=false;
    boolean gotobot=false;
    boolean gotonone=true;
	boolean slowmode=false;
	int timerarmcontrol=0;
	int timercowcatch = 0;
	int timerdumper = 0;
	int timerzipeline = 0;
	double cowcatchpos = 0.81;
	double dumperpos;
	double ziplinepos;
	String zipisout="";

	public TELEMainTeleopControl() {

	}


	@Override
	public void init() {

		cowcatchpos =.85;
		dumperpos =0.1;
		ziplinepos =0.5;

       // motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
		servofront = hardwareMap.servo.get("servoFront");
		motorRight = hardwareMap.dcMotor.get("motorR");
		motorLeft = hardwareMap.dcMotor.get("motorL");
		motorLeft.setDirection(DcMotor.Direction.REVERSE);
		servomid = hardwareMap.servo.get("servoMid");
		//motorCapture = hardwareMap.dcMotor.get("motorCollect");
		motorBack = hardwareMap.dcMotor.get("motorWheelie");
		motorArm = hardwareMap.dcMotor.get("motorArm");
		servotop=hardwareMap.servo.get("servoTop");
		servofront.setPosition(cowcatchpos);
		servomid.setPosition(0.5);
		servotop.setPosition(0.0);
        // Encoders
        //motorController.setMotorChannelMode(motorBack.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
        //motorController.setMotorChannelMode(motorBack.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
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


		motorRight.setPower(-right);
		motorLeft.setPower(-left);

        // *Cow-catcher

        //Push A to push out/pull in the cow catcher

		if (timercowcatch >=30) {
			if (gamepad1.a) {
				if (cowcatchpos == .85) {
					cowcatchpos = .5;
				} else {
					cowcatchpos = .85;
				}
				timercowcatch =0;
			}
		}
		timercowcatch++;
		servofront.setPosition(cowcatchpos);


		// *Wheelie Bar
		float wheelie = gamepad2.right_stick_y;
		wheelie = Range.clip(wheelie, -1, 1);
		wheelie = (float)scaleInput(wheelie);
		if (timerarmcontrol >=30) {
			if (gamepad2.a) {
				if (slowmode=true) {
					slowmode=false;
				} else {
					slowmode=true;
				}
				timerarmcontrol =0;
			}
		}
		timerarmcontrol++;
		slowmode=false;
		if (slowmode==true)
		{
			wheelie=wheelie*0.15f;
		}
		motorBack.setPower(wheelie);
        /*if (gamepad2.y)
        {
            gotonone=true;
            gototop=false;
            gotobot=false;
        }
        if (gamepad2.x)
        {
            gototop=true;
            gotonone=false;
            gotobot=false;
        }
        if (gamepad2.b)
        {
            gotobot=true;
            gototop=false;
            gotonone=false;
        }*/


        /*if (gototop==true)
        {
            if (motorBack.getCurrentPosition()>0)
            {
                motorBack.setPower(-1);
            }
            if (motorBack.getCurrentPosition()<0)
            {
                motorBack.setPower(1);
            }
            motorBack.setTargetPosition(0);
        }
        if (gotobot==true)
        {
            if (motorBack.getCurrentPosition()>310)
            {
                motorBack.setPower(-1);
            }
            if (motorBack.getCurrentPosition()<310)
            {
                motorBack.setPower(1);
            }
            motorBack.setTargetPosition(310);
        }*/

		// *Arm Control
		//Left joystick on gamepad 2
		float arm = gamepad2.left_stick_y;
		arm = Range.clip(arm, -1, 1);
		arm = (float)scaleInput(arm);
		motorArm.setPower(-arm);

		// *Guy Dumper
		dumperpos = Range.clip(dumperpos, 0.01, .99);
		if (timerdumper ==0)
		{
			dumperpos =0.0;
		}
		timerdumper++;
		//Use the left trigger to lower the guy dumper, right trigger to raise the guy dumper
		if (gamepad2.left_trigger>=0.3) {
			dumperpos -=0.005;
		}
		if (gamepad2.right_trigger>=0.3) {
			dumperpos +=0.005;
		}
		servotop.setPosition(dumperpos);

		// *Zipline Delivery
		ziplinepos = Range.clip(ziplinepos, 0.01, .99);
		if (timerzipeline ==0)
		{
			ziplinepos =0.5;
		}
		timerzipeline++;
		if (gamepad1.y)
		{
			ziplinepos=0.5;
			zipisout="The zipline is SAFE";
		}
		if (gamepad1.x)
		{
			ziplinepos=1;
			zipisout="The zipline is out!";
		}
		if (gamepad1.b)
		{
			ziplinepos=0.1;
			zipisout="The zipline is out!";
		}
		servomid.setPosition(ziplinepos);
		telemetry.addData("AAAA", wheelie);
		telemetry.addData("Teleop Version", "4.2");
		telemetry.addData("Can control:","2 motor driving, Zip-line, Dumper, Arm, Guy Deliver, Cow carcher");
		telemetry.addData("Zipline?", zipisout);
		telemetry.addData("Wheelie Bar Slowmode?",slowmode);
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
