/**
 * AVL-Tree with weight and rank augmentation.
 *
 * @author Shinobu
 * @author Chi Xu
 */
public class AVLTree<T extends Comparable<T>> {

    private static final int MAX_HEIGHT_DIFFERENCE = 1;

    private Node<T> root;

    class Node<KT> {

        KT key;

        Node<KT> left;

        Node<KT> right;

        Node<KT> parent;

        int height = 1;

        int weight = 1;

        public Node(KT key, Node<KT> left, Node<KT> right, Node<KT> parent) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    public AVLTree() {
        this.root = null;
    }

    public AVLTree(T... keys) {
        if (keys == null || keys.length < 1) {
            throw new NullPointerException();
        }

        root = new Node<>(keys[0], null, null, null);
        for (int i = 1; i < keys.length && keys[i] != null; i++) {
            root = insert(root, keys[i], null);
        }
    }

    public T find(T key) {
        if (key == null || root == null) {
            return null;
        }
        return find(root, key, key.compareTo(root.key));
    }

    private T find(Node<T> node, T key, int cmp) {
        if (node == null) {
            return null;
        }

        if (cmp == 0) {
            return node.key;
        }

        return find((node = cmp > 0 ? node.right : node.left), key, node == null ? 0 : key.compareTo(node.key));
    }

    public void insert(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = insert(root, key, null);
    }

    private Node<T> insert(Node<T> node, T key, Node<T> parent) {
        if (node == null) {
            return new Node<>(key, null, null, parent);
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            node.left = insert(node.left, key, node);
        } else {
            node.right = insert(node.right, key, node);
        }

        if (Math.abs(height(node.left) - height(node.right)) > MAX_HEIGHT_DIFFERENCE) {
            node = balance(node);
        }
        refreshHeight(node);
        refreshWeight(node);
        return node;
    }

    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int weight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.weight;
    }

    private void refreshHeight(Node<T> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private void refreshWeight(Node<T> node) {
        node.weight = weight(node.left) + weight(node.right) + 1;
    }

    private Node<T> rightRotate(Node<T> node) {
        Node<T> node1;
        node1 = node.left;
        node.left = node1.right;
        node1.right.parent = node;
        node1.right = node;
        node1.parent = node.parent;
        node.parent = node1;

        refreshHeight(node);
        refreshWeight(node);
        return node1;
    }

    private Node<T> leftRotate(Node<T> node) {
        Node<T> node1;
        node1 = node.right;
        node.right = node1.left;
        node1.left.parent = node;
        node1.left = node;
        node1.parent = node.parent;
        node.parent = node1;

        refreshHeight(node);
        refreshWeight(node);
        return node1;
    }

    private Node<T> balance(Node<T> node) {
        // Node<T> node1, node2;
        // ll & l
        if (height(node.left) > height(node.right) && height(node.left.left) >= height(node.left.right)) {
            return rightRotate(node);
            // node1 = node.left;
            // node.left = node1.right;
            // node1.right.parent = node;
            // node1.right = node;
            // node1.parent = node.parent;
            // node.parent = node1;

            // refreshHeight(node);
            // refreshWeight(node);
            // return node1;
        }
        // lr
        if (height(node.left) > height(node.right) && height(node.left.right) > height(node.left.left)) {
            leftRotate(node.left);
            return rightRotate(node);
            // node1 = node.left;
            // node2 = node.left.right;
            // node.left = node2.right;
            // node1.right = node2.left;
            // node2.left = node1;
            // node2.right = node;

            // refreshHeight(node);
            // refreshHeight(node1);
            // refreshWeight(node);
            // refreshWeight(node1);
            // return node2;
        }
        // rr & r
        if (height(node.right) > height(node.left) && height(node.right.right) >= height(node.right.left)) {
            return leftRotate(node);
            // node1 = node.right;
            // node.right = node1.left;
            // node1.left = node;

            // refreshHeight(node);
            // refreshWeight(node);
            // return node1;
        }
        // rl
        if (height(node.right) > height(node.left) && height(node.right.left) > height(node.right.right)) {
            rightRotate(node.right);
            return leftRotate(node);
            // node1 = node.right;
            // node2 = node.right.left;
            // node.right = node2.left;
            // node1.left = node2.right;
            // node2.left = node;
            // node2.right = node1;

            // refreshHeight(node);
            // refreshHeight(node1);
            // refreshWeight(node);
            // refreshWeight(node1);
            // return node2;
        }
        return node;
    }

    public void remove(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = remove(root, key);
    }

    private Node<T> remove(Node<T> node, T key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        }
        if (cmp > 0) {
            node.right = remove(node.right, key);
        }
        if (cmp == 0) {
            if (node.left == null || node.right == null) {
                return node.left == null ? node.right : node.left;
            }
            var successorKey = successorOf(node).key;
            node = remove(node, successorKey);
            node.key = successorKey;
        }

        if (Math.abs(height(node.left) - height(node.right)) > MAX_HEIGHT_DIFFERENCE) {
            node = balance(node);
        }
        refreshHeight(node);
        refreshWeight(node);
        return node;
    }

    private Node<T> successorOf(Node<T> node) {
        if (node == null) {
            throw new NullPointerException();
        }
        if (node.left == null || node.right == null) {
            return node.left == null ? node.right : node.left;
        }

        return height(node.left) > height(node.right) ? findMax(node.left, node.left.right, node.left.right == null)
                : findMin(node.right, node.right.left, node.right.left == null);
    }

    private Node<T> findMax(Node<T> node, Node<T> right, boolean rightIsNull) {
        if (rightIsNull) {
            return node;
        }
        return findMax((node = right), node.right, node.right == null);
    }

    private Node<T> findMin(Node<T> node, Node<T> left, boolean leftIsNull) {
        if (leftIsNull) {
            return node;
        }
        return findMin((node = left), node.left, node.left == null);
    }

    public Integer getRank(T key) {
        if (key == null || root == null) {
            return null;
        }
        Node<T> p = root;
        int result = 0;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp == 0) {
                return result + weight(p.left) + 1;
            }
            if (cmp == -1) {
                p = p.left;
            } else {
                result += weight(p.left) + 1;
                p = p.right;
            }
        }
        return null;
    }

    public T lessThanKey(T key) {
        if (key == null || root == null) {
            return null;
        }
        Node<T> cur = root;
        Node<T> parent = root;
        while (cur.left != null || cur.right != null) {
            if (cur.key.compareTo(key) == -1) {
                parent = cur;
                cur = cur.left;
            } else {
                if (cur.key.compareTo(key) == 1) {
                    parent = cur;
                    cur = cur.right;
                } else {
                    return cur.left.key;
                }
            }
        }
        // cur is the right child of the parent, just findmax on parent's left subtree or return parent
        if (cur == parent.right) {
            return findMin(parent, parent.left, parent.left == null).key;
        } else {
            
        }
    }

}
