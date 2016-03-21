package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

public class TESTDistanceSensor extends AutoOpMode {

    OpticalDistanceSensor DistanceSensor;


    @Override
    public void runOpMode() throws InterruptedException {

        DistanceSensor = hardwareMap.opticalDistanceSensor.get("opticalDistanceSensor");

        //double FoundDistance = DistanceSensor.getLightDetected();
        //double Distance = .06;

        while(true){
            telemetry.addData("Distance",DistanceSensor.getLightDetected());
        }

//        while(FoundDistance < Distance){
//            FoundDistance = DistanceSensor.getLightDetected();
//            telemetry.addData("Distance",DistanceSensor.getLightDetected());
//            telemetry.addData("Movement","I am still moving!");
//        }
//        telemetry.addData("Stop","I have stoped.");





    }



}


