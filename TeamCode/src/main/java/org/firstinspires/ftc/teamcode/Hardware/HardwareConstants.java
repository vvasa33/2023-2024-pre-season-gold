package org.firstinspires.ftc.teamcode.Hardware;

public class HardwareConstants {
    private HardwareConstants() {}

    public enum PassChoices {
        DOOR ("Backstage DOOR"),
        TRUSS ("Truss");

        public final String val;

        PassChoices (String val) {this.val = val;}

        public String getValue() {return val;}
    }

    public enum StackChoices {
        LEFT ("LEFT Stack"),
        MIDDLE ("MIDDLE Stack"),
        RIGHT ("RIGHT Stack");

        public final String val;

        StackChoices (String val) {this.val = val;}

        public String getValue() {return val;}
    }

    public enum ParkChoices {
        LEFT ("Left Park"),
        RIGHT ("Right Park");

        public final String val;

        ParkChoices (String val) {this.val = val;}

        public String getValue() {return val;}
    }

    public enum ScoreChoices {
        LEFT ("LEFT Side"),
        MIDDLE ("MIDDLE Side"),
        RIGHT ("RIGHT Side"),
        AUTOSELECT ("Autoselected");

        public final String val;

        ScoreChoices (String val) {this.val = val;}

        public String getValue() {return val;}
    }

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

    //NOT ACTUALLY NEEDED
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


    public enum CameraAreas {
        LEFT,
        RIGHT,
        CENTER
    }
    //HARDWARE CONSTANTS

    //LIFT STUFF
    public static final double liftSpeed = 0.5;
    public static final double slowLiftSpeed = 0.3;
    public static final double noLiftSpeed = 0.0;

    public static final int threshold = 1000;

    //

    public static final String autoGroup = "Auto";
    public static final String opModeGroup = "TeleOp";
    public static final String controlGroup = "Control";
}
