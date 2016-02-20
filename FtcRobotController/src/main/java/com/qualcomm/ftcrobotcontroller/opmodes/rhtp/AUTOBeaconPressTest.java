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

package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class AUTOBeaconPressTest extends LinearOpMode {
  //ColorSensor sensorRGB2;
  ColorSensor sensorRGB1;
 // Servo servoa;

  public void runOpMode() throws InterruptedException {
    sensorRGB1 = hardwareMap.colorSensor.get("colorFront");
    sensorRGB1.enableLed(false);
    //sensorRGB2 = hardwareMap.colorSensor.get("colorBack");
    //sensorRGB2.enableLed(false);
    //servoa = hardwareMap.servo.get("servoColor");
    waitOneFullHardwareCycle();
    waitForStart();



    /*float hsvValues2[] = {0F, 0F, 0F};
    final float values2[] = hsvValues2;*/

    float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;
    //servoa.setPosition(.6);
    while (true) {
      Color.RGBToHSV(sensorRGB1.red() * 8, sensorRGB1.green() * 8, sensorRGB1.blue() * 8, hsvValues);
      telemetry.addData("Clear", sensorRGB1.alpha());
      telemetry.addData("Red  ", sensorRGB1.red());
      telemetry.addData("Green", sensorRGB1.green());
      telemetry.addData("Blue ", sensorRGB1.blue());
      telemetry.addData("Hue", hsvValues[0]);
      /*
      Color.RGBToHSV(sensorRGB2.red() * 8, sensorRGB2.green() * 8, sensorRGB2.blue() * 8, hsvValues2);
      telemetry.addData("Clear2", sensorRGB2.alpha());
      telemetry.addData("Red2  ", sensorRGB2.red());
      telemetry.addData("Green2", sensorRGB2.green());
      telemetry.addData("Blue2 ", sensorRGB2.blue());
      telemetry.addData("Hue2", hsvValues2[0]);
      waitOneFullHardwareCycle();


      //.6  is middle pos for servo
      //.85 is left turn
      /*
      if ((sensorRGB1.red() > sensorRGB1.blue()) && sensorRGB2.red() < sensorRGB2.blue()) {
        servoa.setPosition(0.6);
      }
      if ((sensorRGB1.red() < sensorRGB1.blue()) && sensorRGB2.red() > sensorRGB2.blue()) {
        servoa.setPosition(.3);
      }*/
    }
  }
}
