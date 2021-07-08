/**
 * Game Tree class
 *
 * This class contains the basic code for implementing a Game Tree. It includes functionality to load a game tree.
 */

public class GameTree {

    // Standard enumerations for tic-tac-toe variations
    public enum Player {ONE, TWO}

    public enum Move {X, O, B}

    public enum Game {Regular, Misere, NoTie, Arbitrary}

    // Some constants for tic-tac-toe
    final static int bsize = 3;
    final static int btotal = bsize * bsize;
    final static char EMPTYCHAR = '_';
    // Specifies the values for the arbitrary game
    final int[] valArray = {1, -2, 1, -3, -2, 2, -2, -1, 1};

    /**
     * Simple TreeNode class.
     *
     * This class holds the data for a node in a game tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    static class TreeNode {
        public String name = "";
        public TreeNode[] children = null;
        public int numChildren = 0;
        public int value = Integer.MIN_VALUE;
        public boolean leaf = false;

        // Empty constructor for building an empty node
        TreeNode() {
        }

        // Simple constructor for build a node with a name and a leaf specification
        TreeNode(String s, boolean l) {
            name = s;
            leaf = l;
            children = new TreeNode[btotal];
            for (int i = 0; i < btotal; i++) {
                children[i] = null;
            }
            numChildren = 0;
        }

        // Add a child
        public void addChild(TreeNode node) {
            children[numChildren] = node;
            numChildren++;
        }
    }

    // Root of the tree - public to facilitate grading
    public TreeNode root = null;

    // Game mode
    public Game gameMode = Game.Regular;

    // Constructor for game tree.
    public GameTree() {

    }

    public GameTree(Game g) {
        this.gameMode = g;
    }

    //--------------
    // Utility Functions
    //--------------

    // Simple function for determining the other player
    private Player other(Player p) {
        if (p == Player.ONE) return Player.TWO;
        else return Player.ONE;
    }

    // Translates player to move id
    private Move move(Player p) {
        if (p == Player.ONE) return Move.X;
        else return Move.O;
    }

    // Draws a board using the game state stored as the name of the node.
    public void drawBoard(String s) {
        System.out.println("-------");
        for (int j = 0; j < bsize; j++) {
            System.out.print("|");
            for (int i = 0; i < bsize; i++) {
                char c = s.charAt(i + 3 * j);
                if (c != EMPTYCHAR)
                    System.out.print(c);
                else System.out.print(" ");
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-------");
        }
    }

    /**
     * readTree reads a game tree from the specified file. If the file does not exist, cannot be found, or there is
     * otherwise an error opening or reading the file, it prints out "Error reading file" along with additional error
     * information.
     *
     * @param fName name of file to read from
     */
    public void readTree(String fName) {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(fName));
            root = readTree(reader);
        } catch (java.io.IOException e) {
            System.out.println("Error reading file: " + e);
        }
    }

    // Helper function: reads a game tree from the specified reader
    private TreeNode readTree(java.io.BufferedReader reader) throws java.io.IOException {
        String s = reader.readLine();
        if (s == null) {
            throw new java.io.IOException("File ended too soon.");
        }
        TreeNode node = new TreeNode();
        node.numChildren = Integer.parseInt(s.substring(0, 1));
        node.children = new TreeNode[node.numChildren];
        node.leaf = (s.charAt(1) == '1');
        node.value = Integer.MIN_VALUE;
        if (node.leaf) {
            char v = s.charAt(2);
            node.value = Character.getNumericValue(v);
            node.value--;
        }
        node.name = s.substring(3);

        for (int i = 0; i < node.numChildren; i++) {
            node.children[i] = readTree(reader);
        }
        return node;
    }

    /**
     * findValue determines the value for every node in the game tree and sets the value field of each node. If the root
     * is null (i.e., no tree exists), then it returns Integer.MIN_VALUE.
     *
     * @return value of the root node of the game tree
     */
    int findValue() {
        if (this.root == null) {
            return Integer.MIN_VALUE;
        } else {
            // Player p = findPlayer(root);
            // Because the question has been updated, there is no need to find the current player
            findValue(root, Player.ONE);
        }
        return root.value;
    }

    /**
     * Helper method thats find the value of a node.
     * 
     * @param node the game node
     */
    void findValue(TreeNode node, Player p) {
        if (!node.leaf) {
            for (TreeNode child : node.children) {
                findValue(child, other(p));
            }
            if (p == Player.ONE) {
                node.value = findMaxValue(node.children);
            } else {
                node.value = findMinValue(node.children);
            }
        } else {
            switch(this.gameMode) {
                case Regular: break;
                case NoTie: node.value = notieValue(node); break;
                case Arbitrary: node.value = arbitraryValue(node); break;
                case Misere: break;
            }
        }
        // this.drawBoard(node.name);
    }

    /**
     * Return the max value of all the nodes in the node list
     * 
     * @param nodeList children nodelist
     * @return the max value of all the nodes in the node list
     */
    int findMaxValue(TreeNode[] nodeList) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nodeList.length; i++) {
            if (nodeList[i].value > max) {
                max = nodeList[i].value;
            }
        }
        return max;
    }

    /**
     * Return the min value of all the nodes in the node list
     * 
     * @param nodeList children nodelist
     * @return the min value of all the nodes in the node list
     */
    int findMinValue(TreeNode[] nodeList) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nodeList.length; i++) {
            if (nodeList[i].value < min) {
                min = nodeList[i].value;
            }
        }
        return min;
    }

    /**
     * Return the player of the specific game state
     * 
     * @param node the game node
     */
    Player findPlayer(TreeNode node) {
        int numOfX = 0;
        int numOfO = 0;
        for (int i = 0; i < btotal; i++) {
            if (node.name.charAt(i) == 'X') {
                ++numOfX;
            } else {
                if (node.name.charAt(i) == 'O') {
                    ++numOfO;
                }
            }
        }
        return numOfX - numOfO == 0 ? Player.ONE : Player.TWO;
    }

    /**
     * Returns the value of leaf Node in NoTie game.
     */
    int notieValue(TreeNode node) {
        if (node.value == 0) {
            if (node.name.charAt(4) == 'X') {
                return -1;
            } else {
                return 1;
            }
        } else {
            return node.value;
        }
    }

    /**
     * Returns the value of leaf node in Arbitary game.
     */
    int arbitraryValue(TreeNode node) {
        if (node.value == 0) {
            int result = 0;
            for (int i = 0; i < 9; ++i) {
                if ()
            }
        }
    }


    // Simple main for testing purposes
    public static void main(String[] args) {
        GameTree tree = new GameTree(Game.NoTie);
        // tree.readTree("games/tictac_9_empty.txt");
        // tree.readTree("variants/misere.txt");
        // tree.readTree("variants/arbitrary.txt");
        tree.readTree("variants/notie.txt");
        // tree.drawBoard(tree.root.name);
        System.out.println(tree.findValue());
    }

}
