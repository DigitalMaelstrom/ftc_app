/* Copyright (c) 2015 Qualcomm Technologies Inc

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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Linear Tele Op Mode
 * <p>
 * Enables control of the robot via the gamepad.
 * NOTE: This op mode will not work with the NXT Motor Controllers. Use an Nxt op mode instead.
 */
public class TestWheelsOP extends LinearOpMode {


  DcMotor motorRight;
  DcMotor motorLeft;
  long counter=0;


  @Override
  public void runOpMode() throws InterruptedException {
    motorLeft = hardwareMap.dcMotor.get("motor_1");
    motorRight = hardwareMap.dcMotor.get("motor_2");

    motorLeft.setDirection(DcMotor.Direction.REVERSE);

    // set the starting position of the wrist and neck


    waitForStart();

    while (opModeIsActive()) {
      // throttle:  left_stick_y ranges from -1 to 1, where -1 is full up,  and 1 is full down
      // direction: left_stick_x ranges from -1 to 1, where -1 is full left and 1 is full right
      float throttle  = -gamepad1.left_stick_y;
      float direction =  gamepad1.left_stick_x;
      float right = throttle - direction;
      float left  = throttle + direction;

      // clip the right/left values so that the values never exceed +/- 1
      right = Range.clip(right, -1, 1);
      left  = Range.clip(left,  -1, 1);

      // write the values to the motors
      motorRight.setPower(1);
      motorLeft.setPower(1);

      // update the position of the neck


      // clip the position values so that they never exceed 0..1

      // set jaw position

      // write position values to the wrist and neck servo
      //Thread.sleep(1);


      telemetry.addData("Text", "K9TeleOp");
      telemetry.addData(" left motor", motorLeft.getPower());
      telemetry.addData("right motor", motorRight.getPower());
      counter+=1;
      if (counter>=1000)
      {
        motorRight.setPower(0);
        motorLeft.setPower(0);
        return;
      }
      telemetry.addData("counter",counter);
      waitOneFullHardwareCycle();
    }
  }
}
