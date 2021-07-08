public class test {
    public static void main(String[] args) {
        // ILFShiftRegister r = new ShiftRegister(10, 7);
        // int[] seed = {0,1,0,1,1,1,0,1,0,1};
        // r.setSeed(seed);
        // for(int i = 0; i < 1024; ++i) {
        //     r.shift();
        // }
        ShiftRegister r = new ShiftRegister(18);
        String seed = "TheCowJumpedOverTheMoon";
        r.setSeedString(seed);
        r.getBit();
        r.shift();
        r.getBit();
        r.generate(100);
        r.getBit();
    }
}
