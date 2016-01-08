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
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class SolaBotEncoderTest extends OpMode {


    DcMotorController motorController;
	DcMotor motorBack;
	boolean gototop=false;
	boolean gotobot=true;
    int assignmenttimer;
	public SolaBotEncoderTest() {

	}


	@Override
	public void init() {

        // Encoders



	}

	@Override
	public void loop() {

        assignmenttimer++;
        if (assignmenttimer==2)
        {
            motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
            motorBack = hardwareMap.dcMotor.get("motorBack");
        }
        if (assignmenttimer==20)
        {
            motorController.setMotorChannelMode(motorBack.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
        }
        if (assignmenttimer==40)
        {
            motorController.setMotorChannelMode(motorBack.getPortNumber(), DcMotorController.RunMode.RUN_TO_POSITION);
        }
        if (assignmenttimer>=60) {
            if (gototop == true) {
                if (motorBack.getCurrentPosition() > 0) {
                    motorBack.setPower(-1);
                }
                if (motorBack.getCurrentPosition() < 0) {
                    motorBack.setPower(1);
                }
                motorBack.setTargetPosition(0);
            }
            if (gotobot == true) {
                if (motorBack.getCurrentPosition() > 1220) {
                    motorBack.setPower(-1);
                }
                if (motorBack.getCurrentPosition() < 1220) {
                    motorBack.setPower(1);
                }
                motorBack.setTargetPosition(1220);
                if (motorBack.getCurrentPosition()==1220) {
                    gototop=true;
                    gotobot=false;
                }
            }
        }
	}
	@Override
	public void stop() {
	}
}
