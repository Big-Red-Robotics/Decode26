package org.firstinspires.ftc.teamcode.utility.teaminfo;

public enum AprilTagLibrary { //do i even need this????
    APRIL_TAG_PATTERNGPP(21),
    APRIL_TAG_PATTERNPGP(22),
    APRIL_TAG_PATTERNPPG(23),
    APRIL_TAG_COLORB(20),
    APRIL_TAG_COLORR(24);

    private final int value;

    private AprilTagLibrary(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Example static method to demonstrate usage
    public static void demo() {
        AprilTagLibrary var = AprilTagLibrary.APRIL_TAG_PATTERNGPP;
        String name = var.name();           // returns "APRIL_TAG_PATTERNGPP"
        int val = var.getValue();           // returns 21
        System.out.println(name + ": " + val);
    }
}
