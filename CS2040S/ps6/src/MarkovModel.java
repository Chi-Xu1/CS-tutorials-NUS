import java.util.Random;
import java.util.HashMap;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// The HashMap whose key is a kgram and value is an int array
	private HashMap<String, Integer[]> map;

	// The order of the HashMap
	private int order = 1;

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.map = new HashMap<>();
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		for (int i = 0; i < text.length() - this.order; i++) {
			String cur = text.substring(i, i + this.order);
			if (map.get(cur) == null) {
				// no collision:
				// initialize the int array as the value of the hashmap.
				// frequencyList[0] stores the frequency of the kgram.
				Integer[] frequencyList = new Integer[256];
				for (int j = 0; j < frequencyList.length; j++) {
					frequencyList[j] = 0;
				}
				frequencyList[0] = 1;
				int iChar = (int) (text.charAt(i + this.order));
				if (iChar != 0) {
					frequencyList[iChar]++;
				}
				map.put(cur, frequencyList);
			} else {
				// collision: update the number of kgram and the number of character after of the value.
				Integer[] temp = map.get(cur);
				map.remove(cur);
				temp[0]++;
				int iChar = (int) (text.charAt(i + this.order));
				if (iChar != 0) {
					temp[iChar]++;
				}
				map.put(cur, temp);
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (this.map.get(kgram) == null) {
			return 0;
		}
		return this.map.get(kgram)[0];
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (this.map.get(kgram) == null) {
			return 0;
		}
		return this.map.get(kgram)[(int) c];
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		// if the kgram does not exist or no valid character, return NONECHARACTER.
		if (this.map.get(kgram) == null || this.map.get(kgram)[0] == 0) {
			return NOCHARACTER;
		} else {
			int frequencyOfKgram = this.getFrequency(kgram);
			// generate a random index
			int index = generator.nextInt(frequencyOfKgram);
			Integer[] list = this.map.get(kgram);
			// find the character correspnding to the index.
			int i = 0, j = 0;
			while (i <= index && j < list.length) {
				++j;
				i += list[j];
			}
			return (char) j;
		}
	}
}
