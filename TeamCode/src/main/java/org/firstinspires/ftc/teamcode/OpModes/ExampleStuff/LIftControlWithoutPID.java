package org.firstinspires.ftc.teamcode.OpModes.ExampleStuff;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Hardware.HardwareConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name="liftcontrolnopid")
public class LIftControlWithoutPID extends LinearOpMode {
    SampleMecanumDrive drive;
    DcMotorEx lift;

    //new claw stuff i guess

    ServoImplEx arm1, arm2;
    ServoImplEx joint;
    ServoImplEx claw1, claw2;

    public static int target;
    @Override
    public void runOpMode() throws InterruptedException {
        //claw instantiation
        arm1 = hardwareMap.get(ServoImplEx.class, "arm1");
        arm2 = hardwareMap.get(ServoImplEx.class, "arm2");
        joint = hardwareMap.get(ServoImplEx.class, "joint");
        claw1 = hardwareMap.get(ServoImplEx.class, "claw1");
        claw2 = hardwareMap.get(ServoImplEx.class, "claw2");


        lift = hardwareMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //HardwareConstants.currentLiftPosition = HardwareConstants.LiftPositions.GROUND;
        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.WAITING;

        drive = new SampleMecanumDrive(hardwareMap);

        PhotonCore.experimental.setMaximumParallelCommands(6);
        PhotonCore.start(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.a && HardwareConstants.currentLiftState != HardwareConstants.LiftStates.WAITING) {
                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.RETRACT;
            } else if (gamepad1.b) {
                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
                target = HardwareConstants.LiftPositions.SETLINE_1.getValue();
            } else if (gamepad1.x) {
                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
                target = HardwareConstants.LiftPositions.SETLINE_2.getValue();
            }
//            } else if (gamepad1.y) {
//                HardwareConstants.currentLiftState = HardwareConstants.LiftStates.EXTEND;
//                target = HardwareConstants.LiftPositions.SETLINE_3.getValue();
//            }

            switch (HardwareConstants.currentLiftState) {
                case WAITING:
                    break;
                case EXTEND:
                    lift.setTargetPosition(target);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(0.7);
                    if (!lift.isBusy()) {
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.DEPOSIT;
                    }
                    break;
                case DEPOSIT:
                    //code for the claw goes here



                    if (HardwareConstants.currentRightClawState == HardwareConstants.ClawStates.OPEN && HardwareConstants.currentLeftClawState == HardwareConstants.ClawStates.OPEN) {
                        sleep(200); //wait a little bit so that the robot can score
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.RETRACT;
                    }
                    break;

                case RETRACT:
                    lift.setTargetPosition(HardwareConstants.LiftPositions.GROUND.getValue());
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setPower(0.7);
                    if (!lift.isBusy()) {
                        HardwareConstants.currentLiftState = HardwareConstants.LiftStates.WAITING;
                    }
                    break;
            }

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y / ((gamepad1.left_bumper) ? 3.0 : 1),
                            -gamepad1.left_stick_x / ((gamepad1.left_bumper) ? 3.0 : 1),
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();


        }
    }
}
