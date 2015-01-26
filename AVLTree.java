
public class AVLTree<T extends Comparable<T>> {

	private Node<T> root; 
	private int size;
	
	public AVLTree() {
		size = 0;
	}
	
	public Node<T> getRoot() {
		return root;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getDistance(T lower, T upper) {
		Node<T> current = root;
		
		if (current == null) {
			return 0;
		} else if (lower.compareTo(current.value) > 0) {
			return sumWeights(current.right, lower, upper);
		} else if (upper.compareTo(current.value) <= 0) {
			return sumWeights(current.left, lower, upper);
		} else {
			return sumWeights(current.right, lower, upper) + 
					sumWeights(current.left, lower, upper) + 1;
		}
	}
	
	private int sumWeights(Node<T> current, T lower, T upper) {
		if (current == null) {
			return 0;
		} else {
			if (lower.compareTo(current.value) > 0) {
				return sumWeights(current.right, lower, upper);
			} else if (upper.compareTo(current.value) <= 0) {
				return sumWeights(current.left, lower, upper);
			} else {
				return sumWeights(current.right, lower, upper) + 
						sumWeights(current.left, lower, upper) + 1;
			}
		}
	}
	
	public void add(T val) {
		Node<T> newNode = new Node<T>(val); 
		if (size == 0) {
			root = newNode;
			newNode.parent = null;
			newNode.height = 0;
			newNode.balance = 0;
			size++;
		} else {
			insertNode(root,newNode);
		}
	}
	
	public void remove(T val) {
		
	}
	
	private void insertNode(Node<T> current, Node<T> newNode) {
		if (current.compareTo(newNode) > 0) {
			//Go left
			if (current.left == null) {
				current.left = newNode;
				newNode.parent = current;
				size++;
				updateParents(current);
				balanceTree(current);
			} else {
				insertNode(current.left, newNode);
			}
		} else {
			//Go right
			if (current.right == null) {
				current.right = newNode;
				newNode.parent = current;
				size++;
				updateParents(current);
				balanceTree(current);
			} else {
				insertNode(current.right, newNode);
			}
		}
		
	}
	
	private void updateParents(Node<T> node) {
		
		int leftHeight = 0;
		int rightHeight = 0;
		int leftWeight = 0;
		int rightWeight = 0;
		if (node.left != null) {
			leftHeight = node.left.height;
			leftWeight = node.left.weight;
		}
		if (node.right != null) {
			rightHeight = node.right.height;
			rightWeight = node.right.weight;
		}
		
		node.weight = rightWeight + leftWeight + 1;
		node.height = Math.max(leftHeight, rightHeight) + 1;
		node.balance = leftHeight - rightHeight;
		
		if(node.parent != null) {
			updateParents(node.parent);
		}
	}
	
	private void balanceTree(Node<T> node) {
		
		if (node.balance > 1) {
			//Left too tall
			rotateTreeRight(node);
		} else if (node.balance < -1) {
			//Right too tall
			rotateTreeLeft(node);
		}
		
		if (node.parent != null) {
			balanceTree(node.parent);
		}
	}

	private void rotateTreeRight(Node<T> node) {
		//Check if left node is right heavy first
		//Left MUST not be empty
		
		Node<T> checkNode = node.left;
		
		if (checkNode.balance < 0) {
			rotateLeft(checkNode);
		}
		rotateRight(node);
	}
	
	private void rotateTreeLeft(Node<T> node) {
		//check if right node is left heavy first
		//Right MUST not be empty
		
		Node<T> checkNode = node.right;
		
		if (checkNode.balance > 0) {
			rotateRight(checkNode);
		}
		rotateLeft(node);
	}
	
	private void rotateRight(Node<T> node) {
		if (node.left != null) {
			Node<T> newNode = node.left;
			Node<T> parent = node.parent;
			
			if (parent != null) {
						
				//Parent-node relations
				if (parent.left != null && parent.left == node) {
					parent.left = newNode;
				} else if (parent.right != null && parent.right == node) {
					parent.right = newNode;
				} 
				newNode.parent = parent;
			} else {
				newNode.parent = null;
				root = newNode;
			}
			//shift-node relations
			if (newNode.right != null) {
				newNode.right.parent = node;
				node.left = newNode.right;
			} else {
				node.left = null;
			}
			
			//node-old node relations
			node.parent = newNode;
			newNode.right = node;
			
			//update weights and heights
			updateParents(node);
			
		}
	}
	
	private void rotateLeft(Node<T> node) {
		if (node.right != null) {
			Node<T> newNode = node.right;
			Node<T> parent = node.parent;

			if (parent != null) {
					
				//Parent-node relations
				if (parent.left != null && parent.left == node) {
					parent.left = newNode;
				} else if (parent.right != null && parent.right == node) {
					parent.right = newNode;
				} 
				newNode.parent = parent;
			} else {
				newNode.parent = null;
				root = newNode;
			}
			
			//shift-node relations
			if (newNode.left != null) {
				newNode.left.parent = node;
				node.right = newNode.left;
			} else {
				node.right = null;
			}
			
			//node-old node relations
			node.parent = newNode;
			newNode.left = node;
			
			//update weights and heights
			updateParents(node);
			
		} 
	}
	
	
	public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
		private T value;
		Node<T> left;
		Node<T> right;
		Node<T> parent;
		private int weight;
		int height;
		int balance;
		
		public Node(T val) {
			value = val;
			weight = 1;
			height = 0;
			balance = 0;
		}
		
		public void setWeight(int newWeight) {
			weight = newWeight;
		}
		
		public int getWeight() {
			return weight;
		}
		
		public T getValue() {
			return value;
		}

		public void setValue(T val) {
			value = val;
		}
		
		@Override
		public int compareTo(Node<T> other) {
			return value.compareTo(other.getValue());
		}
		
	}

}

