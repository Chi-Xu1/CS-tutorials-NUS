import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * This is the main class for your Markov Model.
 *
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class WordBasedMarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// The HashMap whose key is a kgram and value is an int array
	private HashMap<ArrayList<String>, Node> map;

	// The order of the HashMap
	private int order = 1;

	// This is a special symbol to indicate no string
	public static final String NOSTRING = "";

	private final class Node {
		// the frequency of the words pattern.
		public int frequency = 0;
		// records all the possible nextWord.
		public ArrayList<String> nextWordList;
		// key is the nextWord, value is the frequency of the given word.
		public HashMap<String, Integer> nextWordMap;

		public Node(int count, ArrayList<String> list, HashMap<String, Integer> map) {
			this.frequency = count;
			this.nextWordList = list;
			this.nextWordMap = map;
		}
	}

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public WordBasedMarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.map = new HashMap<>();
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string array.
	 */
	public void initializeText(String[] text) {
		// build an ArrayList based on the input text
		ArrayList<String> textList = new ArrayList<>();
		for (String s : text) {
			textList.add(s);
		}
		// Build the Markov model here
		for (int i = 0; i < text.length - this.order; i++) {
			ArrayList<String> cur = new ArrayList<String>(textList.subList(i, i + this.order));
			String nextWord = textList.get(i + this.order);
			if (map.get(cur) == null) {
				// no collision:
				// initialize the Node as the value of the hashmap.
				ArrayList<String> tempList = new ArrayList<>();
				tempList.add(nextWord);
				HashMap<String, Integer> tempMap = new HashMap<>();
				tempMap.put(nextWord, 1);
				Node tempNode = new Node(1, tempList, tempMap);
				map.put(cur, tempNode);
			} else {
				// collision: find the same word kgrams
				// update kgram's frequency
				Node tempNode = map.get(cur);
				map.remove(cur);
				tempNode.frequency++;
				if (tempNode.nextWordMap.get(nextWord) == null) {
					// No collision: no same next word, add the new one into map
					tempNode.nextWordList.add(nextWord);
					tempNode.nextWordMap.put(nextWord, 1);
					map.put(cur, tempNode);
				} else {
					// Collision: update the nextWordMap
					int count = tempNode.nextWordMap.get(nextWord);
					tempNode.nextWordMap.remove(nextWord);
					tempNode.nextWordMap.put(nextWord, ++count);
					map.put(cur, tempNode);
				}
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(ArrayList<String> kgram) {
		if (this.map.get(kgram) == null) {
			return 0;
		}
		return this.map.get(kgram).frequency;
	}

	/**
	 * Returns the number of times the word s appears immediately after the specified kgram.
	 */
	public int getFrequency(ArrayList<String> kgram, String s) {
		if (this.map.get(kgram) == null || this.map.get(kgram).nextWordMap.get(s) == null) {
			return 0;
		}
		return this.map.get(kgram).nextWordMap.get(s);
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOSTRING if the kgram is not in the table, or if there is no
	 * valid word following the kgram.
	 */
	public String nextWord(ArrayList<String> kgram) {
		// if the kgram does not exist or no valid character, return NONECHARACTER.
		if (this.map.get(kgram) == null || this.map.get(kgram).frequency == 0) {
			return NOSTRING;
		} else {
			int frequencyOfKgram = this.getFrequency(kgram);
			// generate a random index
			int index = generator.nextInt(frequencyOfKgram);
			ArrayList<String> list = this.map.get(kgram).nextWordList;
			HashMap<String, Integer> fMap = this.map.get(kgram).nextWordMap;
			// find the string correspnding to the index.
			int i = 0, j = -1;
			while (i <= index && j < list.size()) {
				++j;
				i += fMap.get(list.get(j));
			}
			return list.get(j);
		}
	}
}
