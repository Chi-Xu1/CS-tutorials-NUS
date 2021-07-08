/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Count the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        TreeNode curNode = null;
        if (child == Child.LEFT) {
            curNode = node.left;
        } else {
            curNode = node.right;
        }
        if (curNode == null) {
            return 0;
        } else {
            return 1 + countNodes(curNode, Child.LEFT) + countNodes(curNode, Child.RIGHT);
        }
    }

    /**
     * Build an array of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    TreeNode[] enumerateNodes(TreeNode node, Child child) {
        int size = countNodes(node, child);
        TreeNode[] nodes = new TreeNode[size];
        if (child == Child.LEFT) {
            enumerateNodes(node.left, nodes, 0);
        } else {
            enumerateNodes(node.right, nodes, 0);
        }
        return nodes;
    }

    /**
     * Hepler method of the enumerateNodes method
     * 
     * @param node the parent node
     * @param nodeArray the array of nodes
     * @param last the index of the last element in the nodeArray
     * @return the index of the last element of the nodeArray
     */
    int enumerateNodes(TreeNode node, TreeNode[] nodeArray, int last) {
        if (node != null) {
            last = enumerateNodes(node.left, nodeArray, last);
            nodeArray[last++] = node;
            last = enumerateNodes(node.right, nodeArray, last);
        }
        return last;
    }

    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    TreeNode buildTree(TreeNode[] nodeList) {
        return buildTree(nodeList, 0, nodeList.length - 1);
    }

    /**
     * Helper method of buildTree
     * 
     * @param nodeList ordered array of nodes
     * @param low the lower bound of the segement of array to recurse
     * @param high the upper bound of the segement of array to recurse
     * @return the root node
     */
    TreeNode buildTree(TreeNode[] nodeList, int low, int high) {
        if (low > high) {
            return null;
        } else {
            if (low == high) {
                TreeNode curNode = nodeList[low];
                curNode.left = null; 
                curNode.right = null;
                return curNode;
            } else {
                int mid = low + (high - low) / 2;
                TreeNode curNode = nodeList[mid];
                curNode.left = buildTree(nodeList, low, mid - 1);
                curNode.right = buildTree(nodeList, mid + 1, high);
                return curNode;
            }
        }
    }

    /**
    * Rebuild the specified subtree of a node.
    * 
    * @param node the part of the subtree to rebuild
    * @param child specifies which child is the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
    * Insert a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }


    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root, Child.RIGHT);
        System.out.printf("%d\n", tree.countNodes(tree.root.right, Child.LEFT));
        System.out.printf("%d\n", tree.countNodes(tree.root.right, Child.RIGHT));
    }
}
