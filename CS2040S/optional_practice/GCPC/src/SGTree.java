/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree<T extends Comparable<T>> {

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
    public static class TreeNode<T>{
        T key;
        int weight = 1;
        public TreeNode<T> left = null;
        public TreeNode<T> right = null;

        TreeNode(T k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode<T> root = null;

    /**
     * Count the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode<T> node, Child child) {
        TreeNode<T> curNode = null;
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
    TreeNode<T>[] enumerateNodes(TreeNode<T> node, Child child) {
        int size = (int)(child == Child.LEFT ? node.left.weight : node.right.weight);
        TreeNode<T>[] nodes = new TreeNode<>[size];
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
     * Helper method of buildTree.
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
                curNode.weight = 1;
                return curNode;
            } else {
                int mid = low + (high - low) / 2;
                TreeNode curNode = nodeList[mid];
                curNode.left = buildTree(nodeList, low, mid - 1);
                curNode.right = buildTree(nodeList, mid + 1, high);
                // update the weight of the current node
                curNode.weight = 1 + 
                    (curNode.left != null ? curNode.left.weight : 0) +
                    (curNode.right != null ? curNode.right.weight : 0);
                return curNode;
            }
        }
    }

    /**
     * Determines if a node is balanced.  If the node is balanced, this should return true.  Otherwise, it should return false.
     * A node is unbalanced if either of its children has weight greather than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        if (node == null) {
            return true;
        }
        if (node.left == null) {
            if (node.right == null) {
                return true;
            } else {
                return node.right.weight <= (double)node.weight * 2.0/3.0;
            }
        } else {
            if (node.right == null) {
                return node.left.weight <= (double)node.weight * 2.0/3.0;
            } else {
                return (node.left.weight <= (double)node.weight * 2.0/3.0 && node.right.weight <= (double)node.weight * 2.0/3.0);
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

        // serach for correct location and update the node's weight on its insert path
        while (true) {
            node.weight++;
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        // insert the key
        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }

        // rebalance the tree
        TreeNode cur = (key <= root.key ? root.left : root.right);
        TreeNode parent = root;
        Child childPointer = Child.LEFT;
        boolean flag = false;
        // Identify the highest unbalanced node on the root-to-leaf path to the newly inserted node.
        while (cur != null) {
            if (checkBalance(cur)) {
                parent = cur;
                if (key <= cur.key) {
                    if (cur.left == null) break;
                    cur = cur.left;
                } else {
                    if (cur.right == null) break;
                    cur = cur.right;
                }
            } else {
                flag = true;
                if (cur == parent.left) {
                    childPointer = Child.LEFT;
                    break;
                } else {
                    childPointer = Child.RIGHT;
                    break;
                }
            }
        }
        if (flag) {
            rebuild(parent, childPointer);
        }
    }


    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        TreeNode p = tree.root;
        while(p!=null) {
            System.out.printf("%d\n", p.key);
            p = p.right;
        }
        System.out.println("");
        p = tree.root.right;
        while(p!=null) {
            System.out.printf("%d\n", p.key);
            p = p.left;
        }
        // tree.rebuild(tree.root, Child.RIGHT);
        // while(p!=null) {
        //     System.out.printf("%.0f\n", p.weight);
        //     p = p.right;
        // }
    }
}
