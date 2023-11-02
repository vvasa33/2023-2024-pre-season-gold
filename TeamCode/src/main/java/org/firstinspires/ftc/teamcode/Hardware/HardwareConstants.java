package org.firstinspires.ftc.teamcode.Hardware;

public class HardwareConstants {
    private HardwareConstants() {}

    //TODO take measurements for all physical values
    //motors, drive speeds, lift speeds, claw and servo consts, presets
    //TODO tune these values for the robot
    public enum LiftPositions {
        GROUND (0),
        SETLINE_1 (1000),
        SETLINE_2 (2000),
        SETLINE_3 (3000),
        MANUAL_UP (500),
        MANUAL_DOWN (500);

        private final int val;

        LiftPositions(int val) {
            this.val = val;
        }

        public int getValue() {
            return val;
        }
    }

    public static LiftPositions currentLiftPosition = LiftPositions.GROUND;

    public enum LiftStates {
        WAITING,
        EXTEND,
        DEPOSIT,
        RETRACT;
    }
    public static LiftStates currentLiftState = LiftStates.WAITING;

    public enum ClawStates {
        OPEN(0),
        CLOSED(1);

        private final int val;

        ClawStates(int val) {this.val = val;}

        public int getValue() {return val;}
    }
    public static ClawStates currentLeftClawState = ClawStates.CLOSED;
    public static ClawStates currentRightClawState = ClawStates.CLOSED;

    //HARDWARE CONSTANTS

    //LIFT STUFF
    public static final double liftSpeed = 0.5;
    public static final double slowLiftSpeed = 0.3;
    public static final double noLiftSpeed = 0.0;

    //

    public static final String autoGroup = "Auto";
    public static final String opModeGroup = "TeleOp";
    public static final String controlGroup = "Control";
}
