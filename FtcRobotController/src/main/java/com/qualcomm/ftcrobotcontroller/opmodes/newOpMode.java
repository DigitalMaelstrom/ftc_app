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
//hi lucas doduhdododo
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
public class newOpMode extends LinearOpMode {
    //nuah le mumbler
    boolean ud = false;
    boolean ur = false;
    boolean rd = false;
    boolean rr = false;
    boolean dd = false;
    boolean dr = false;
    boolean ld = false;
    boolean lr = false;
    boolean ad = false;
    boolean ar = false;
    boolean bd = false;
    boolean br = false;


    int codeStage = 0;
    //noah's acceleration variables
    double cs = 0; //current speed
    double ts = 0; //target speed
    double dif = 0; //speed difference
    double ac = 0.2; //acceleration constant

    double lcs = 0; //current speed
    double lts = 0; //target speed
    double ldif = 0; //speed difference
    double lac = 0.2; //acceleration constant

    double rcs = 0; //current speed
    double rts = 0; //target speed
    double rdif = 0; //speed difference
    double rac = 0.2; //acceleration constant

    // position of the neck servo
    double neckPosition;
    double jawPosition;

    // amount to change the neck servo position by
    double neckDelta = 0.01;

    DcMotor motorRight;
    DcMotor motorLeft;
    //LUcas
    DcMotor motorArmLeft;
    DcMotor motorArmRight;
    DcMotor linearSlide;
    // no lucas
    Servo neck;
    Servo jaw;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        //Lucas
        motorArmLeft = hardwareMap.dcMotor.get("Arm_1");
        motorArmRight = hardwareMap.dcMotor.get("Arm_2");
        //sync motor rotation for arms

        //lucas linearslide motor
        linearSlide = hardwareMap.dcMotor.get("slide");

// no lucas
        neck = hardwareMap.servo.get("servo_1");
        jaw = hardwareMap.servo.get("servo_6");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        // set the starting position of the wrist and neck
        neckPosition = 0.5;

        waitForStart();

        while (opModeIsActive()) {


            // throttle:  left_stick_y ranges from -1 to 1, where -1 is full up,  and 1 is full down
            // direction: left_stick_x ranges from -1 to 1, where -1 is full left and 1 is full right
            //float throttle = -gamepad1.left_stick_y;
            //float direction = gamepad1.right_stick_y;
            //LINEAR SLIDE NEEDS TO BE PROGRAMED MONDAY.
            float lin = gamepad2.right_stick_y;
            //NOAH TEMPORARILY COMMENTED THIS OUT
            //float dRight = -gamepad2.left_stick_x;
            //float dLeft = gamepad2.right_stick_y;
            ts = gamepad2.left_stick_y;
            //Lucas temporarily commented this out
            //float right = throttle - direction;
            //float left  = throttle + direction;
            rts = gamepad1.left_stick_y;
            lts = gamepad1.right_stick_y;
            // clip the right/left values so that the values never exceed +/- 1
            rts = Range.clip(rts, -1, 1);
            lts = Range.clip(lts, -1, 1);

            rdif = rcs - rts;
            if (rdif > rac) {
                rcs -= rac;
            }
            if (rdif < -rac) {
                rcs += rac;
            }
            if (rdif < rac && rdif > -rac) {
                rcs = rts;
            }

            ldif = lcs - lts;
            if (ldif > lac) {
                lcs -= lac;
            }
            if (ldif < -lac) {
                lcs += lac;
            }
            if (ldif < lac && ldif > -lac) {
                lcs = lts;
            }
            //Lucas
            //dRight = Range.clip(dRight, -1, 1);
            //dLeft = Range.clip(dLeft, -1, 1);
            ts = Range.clip(ts, -1, 1);
            // write the values to the motors
            motorRight.setPower(rts);
            motorLeft.setPower(lts);
            //linearslide
            linearSlide.setPower(lin * 0.15f);
            //Lucas
            //motorArmLeft.setPower(dLeft * .10);
            //motorArmRight.setPower(dRight * .10);
            //noah's acceleration stuff

            dif = cs - ts;
            if (dif > ac) {
                cs -= ac;
            }
            if (dif < -ac) {
                cs += ac;
            }
            if (dif < ac && dif > -ac) {
                cs = ts;
            }


            motorArmLeft.setPower(ts * 0.5);
            motorArmRight.setPower(-ts * 0.5f);

            // motorArmRight.setPower(0.3f);
            // motorArmLeft.setPower(0.3f);


            // update the position of the neck
      /*if (gamepad2.x) {
        neckPosition -= neckDelta;
      } else if (gamepad2.b) {
        neckPosition += neckDelta;
      } else if (gamepad2.right_trigger > 0) {
        neckPosition = 0.5;
      } else if (gamepad2.left_trigger > 0) {
        neckPosition = 1.0;
      } else if (gamepad2.left_bumper) {
        neckPosition = 0.0;
      }
*/
            if (gamepad2.x) {
                neckPosition = 0.60f;
            }
            if (gamepad2.b) {
                neckPosition = 0.40f;
            }
            if (!gamepad2.x && !gamepad2.b && codeStage == 0) {
                neckPosition = 0.5f;
            }
            // clip the position values so that they never exceed 0..1
            //neckPosition = Range.clip(neckPosition, 0, 1);

            // SECOND SERVO set jaw position yes no maybe what is a story? what is in a name a rose by any other name would smell as sweet.
            //jawPosition = 1 - Range.scale(gamepad1.right_trigger, 0.0, 1.0, 0.3, 1.0);

            // write position values to the wrist and neck servo ben likes boys
            neck.setPosition(neckPosition);
            jaw.setPosition(jawPosition);



            if(gamepad1.dpad_up){
                ud = true;
                ur = false;
            }
            else if (!gamepad1.dpad_up && ud) {
                ur = true;
                CheckEverything();
                ud = false;
                ur = false;
            }

            if(gamepad1.dpad_right){
                rd = true;
                rr = false;
            }
            else if (!gamepad1.dpad_right && rd) {
                rr = true;
                CheckEverything();
                rd = false;
                rr = false;
            }

            if(gamepad1.dpad_down){
                dd = true;
                dr = false;
            }
            else if (!gamepad1.dpad_down && dd) {
                dr = true;
                CheckEverything();
                dd = false;
                dr = false;
            }

            if(gamepad1.dpad_left){
                ld = true;
                lr = false;
            }
            else if (!gamepad1.dpad_left && ld) {
                lr = true;
                CheckEverything();
                ld = false;
                lr = false;
            }
            if(gamepad1.a){
                ad = true;
                ar = false;
            }
            else if (!gamepad1.a && ad) {
                ar = true;
                CheckEverything();
                ad = false;
                ar = false;
            }
            if(gamepad1.b){
                bd = true;
                br = false;
            }
            else if (!gamepad1.b && bd) {
                br = true;
                CheckEverything();
                bd = false;
                br = false;
            }
            if (codeStage != 10) {
                if (gamepad1.x || gamepad1.y || gamepad1.left_bumper || gamepad1.right_bumper || gamepad1.start || gamepad1.back){
                    codeStage = 0;
                }
            }
            if (codeStage == 10) {
                //MUAHAHAHAHAHAHAHA
                motorRight.setPower(1);
                motorLeft.setPower(1);
            }
            if (codeStage == 9) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 1f;
            }
            if (codeStage == 8) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 0.5f;
            }
            if (codeStage == 7) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 1f;
            }
            if (codeStage == 6) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 0.5f;
            }
            if (codeStage == 5) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 1f;
            }
            if (codeStage == 4) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 0.5f;
            }
            if (codeStage == 3) {
                //MUAHAHAHAHAHAHAHA doth thou bite your thumb at me!?!?
                neckPosition = 1f;
            }
            if (codeStage == 2) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 0.5f;
            }
            if (codeStage == 1) {
                //MUAHAHAHAHAHAHAHA
                neckPosition = 1f;
            }
            if (codeStage == 0) {
                //MUAHAHAHAHAHAHAHA tee hee
                neckPosition = 0.5f;
            }






            if (gamepad1.right_bumper) {
                codeStage = 0;
            }

            waitOneFullHardwareCycle();
        }
    }
    public void CheckEverything() {
        if (codeStage == 0 && ur) {
            codeStage = 1;
        } else if (codeStage == 1 && ur) {
            codeStage = 2;
        } else if (codeStage == 2 && dr) {
            codeStage = 3;
        } else if (codeStage == 3 && dr) {
            codeStage = 4;
        } else if (codeStage == 4 && lr) {
            codeStage = 5;
        } else if (codeStage == 5 && rr) {
            codeStage = 6;
        } else if (codeStage == 6 && lr) {
            codeStage = 7;
        } else if (codeStage == 7 && rr) {
            codeStage = 8;
        } else if (codeStage == 8 && br) {
            codeStage = 9;
        } else if (codeStage == 9 && ar) {
            codeStage = 10;
        }

        if (codeStage == 0 && !ur) {
            codeStage = 0;
        } else if (codeStage == 1 && !ur) {
            codeStage = 0;
        } else if (codeStage == 2 && !dr) {
            codeStage = 0;
        } else if (codeStage == 3 && !dr) {
            codeStage = 0;
        } else if (codeStage == 4 && !lr) {
            codeStage = 0;
        } else if (codeStage == 5 && !rr) {
            codeStage = 0;
        } else if (codeStage == 6 && !lr) {
            codeStage = 0;
        } else if (codeStage == 7 && !rr) {
            codeStage = 0;
        } else if (codeStage == 8 && !br) {
            codeStage = 0;
        } else if (codeStage == 9 && !ar) {
            codeStage = 0;
        }
    }
}

