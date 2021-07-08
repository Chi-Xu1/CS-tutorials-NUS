import static org.junit.Assert.*;

import org.junit.Test;

/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {

    /**
     * getRegister returns a shiftregister to test
     * @param size
     * @param tap
     * @return a new shift register
     * Description: to test a shiftregister, update this function
     * to instantiate the shift register
     */
    ILFShiftRegister getRegister(int size, int tap){
        return new ShiftRegister(size, tap);
    }

    /**
     * Test shift with simple example
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0,1,0,1,1,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {1,1,0,0,0,1,1,1,1,0};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Test generate with simple example
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0,1,0,1,1,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {6,1,7,2,2,1,6,6,2,3};
        for (int i=0; i<10; i++){
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    /**
     * Test register of length 1
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = {1};
        r.setSeed(seed);
        int[] expected = {0,0,0,0,0,0,0,0,0,0,};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.generate(3));
        }
    }

    /**
     * Test with erroneous seed.
     */
    @Test
    public void testError() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = {1,0,0,0,1,1,0};
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    }
    /* 
        Answer to 2b
        I think an exception should be thrown when confronted when the seed is larger than the specified register
        The right way to test this case is that we should test whether an exception is thrown
    */

    /**
     * More test for shift
     */
    @Test
    public void testShift2() {
        ILFShiftRegister r = getRegister(6, 0);
        int[] seed = {0,1,0,1,0,1};
        r.setSeed(seed);
        int[] expected = {1,1,0,0,1,1};
        for(int i = 0; i < 6; ++i) {
            assertEquals("ShiftTest", expected[i], r.shift());
        }
    }

    /**
     * More test for generate
     */
    @Test
    public void testGenerate2() {
        ILFShiftRegister r = getRegister(6, 0);
        int[] seed = {0,1,0,1,0,1};
        r.setSeed(seed);
        int[] expected = {12,13,13,10};
        for(int i = 0; i < 4; ++i) {
            assertEquals("GenerateTest", expected[i], r.generate(4));
        }
    }

    /**
     * Test 1 length register and big k parameter for generate (corner case)
     */
    @Test
    public void testBigk() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = {1};
        r.setSeed(seed);
        int[] expected = {0,0,0,0,0,0,0,0,0,0};
        for (int i=0; i<10; i++){
            assertEquals("CornerCase", expected[i], r.generate(100000));
        }
    }

    /**
     * Test register of length 0
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testZero() throws ArrayIndexOutOfBoundsException{
        ILFShiftRegister r = getRegister(0, 0);
        int[] seed = {};
        r.setSeed(seed);
        r.shift();
    }
}
