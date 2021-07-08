///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * 
 * @author Chi Xu
 * Description: implements the ILFShiftRegister interface.
 */

import java.util.ArrayList;

public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // the size of the register
    private int size;
    // the tap of the register
    private int tap;
    // the array to store the seed
    private int[] seed;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        this.size = size;
        this.tap = tap;
        this.seed = new int[size];
    }

    ShiftRegister(int tap) {
        this.size = 0;
        this.tap = tap;
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * 
     * @param seed 
     * Description: sets the shift register to the specified initial
     * seed. Report error when the input contains integers other than 0
     * and 1.
     */
    @Override
    public void setSeed(int[] seed) {
        for (int i = 0; i < this.size; ++i) {
            if (seed[i] == 0 || seed[i] == 1) {
                this.seed[i] = seed[i];
            } else {
                System.out.println("Error: The input contains integers other than 0 and 1.");
                break;
            }
        }
    }

    public void setSeedString(String seed) {
        ArrayList<Integer> seedArrayList = new ArrayList<>();
        for(int i = 0; i < seed.length(); ++i) {
            int temp = seed.charAt(i);
            ArrayList<Integer> tempList = toBinaryArrayList(temp);
            seedArrayList.addAll(tempList);
        }
        this.size = seedArrayList.size();
        this.seed = new int[this.size];
        for(int i = 0; i < seedArrayList.size(); ++i) {
            this.seed[i] = seedArrayList.get(i);
        }
    }

    /**
     * return binary int ArrayList
     * @param v
     * @return
     */
    private ArrayList<Integer> toBinaryArrayList(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        while(v != 0) {
            result.add(v % 2);
            v /= 2;
        }
        return result;
    }

    /**
     * shift
     * 
     * @return the least significant bit of the resulting register 
     * Description: executes one shift step
     */
    @Override
    public int shift() {
        int tapValue = this.seed[this.tap];
        int least = tapValue ^ this.seed[this.size - 1];
        for (int i = this.size - 2; i >= 0; --i) {
            this.seed[i + 1] = this.seed[i];
        }
        this.seed[0] = least;
        // getBit();

        return least;
    }

    public void getBit() {
        for (int i = this.size - 1; i >= 0; --i) {
            System.out.printf("%d ", this.seed[i]);
        }
        System.out.println("");
    }

    /**
     * generate
     * 
     * @param k
     * @return an integer derived from converting these k bits from binary into an integer 
     * Description: extracts k bits from the shift register. executes the shift operation k times, saving the k bits returned.
     */
    @Override
    public int generate(int k) {
        int result[] = new int[k];
        for (int i = 0; i < k; ++i) {
            result[i] = shift();
        }
        return toBinary(result);
    }

    /**
     * Returns the integer representation for a binary int array.
     * 
     * @param array
     * @return the integer derived from the input binary int array
     */
    private int toBinary(int[] array) {
        int result = array[0];
        for (int i = 1; i < array.length; ++i) {
            result = result * 2 + array[i];
        }
        return result;
    }
}
