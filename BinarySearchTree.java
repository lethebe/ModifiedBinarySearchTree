
public class BinarySearchTree {

	Node root;
	
	public BinarySearchTree() {
		this.root = null;
	}
	
	
	class Node {
		int data;
		Node left;
		Node right;
		Node parent;
		
		public Node(int d) {
			this.data = d;
			left = null;
			right = null;
		}
		
		void setParent(Node p) {
			this.parent = p;
		}
		
	}
	
	public boolean insert(int d) {
		Node n = new Node(d);
		if (this.root == null) {
			this.root = n;
			return true;
		}
		
		Node current = root;
		Node p = null;
		
		while (true) {
			p = current;
			if (n.data < current.data) {
				current = current.left;
				if (current == null) {
					p.left = n;
					n.setParent(p);
					return true;
				}
			}
			else if (n.data > current.data) {
				current = current.right;
				if (current == null) {
					p.right = n;
					n.setParent(p);
					return true;
				}
			}
			
			else
				return false;
		}
		
	}
	
	public Node find(int x) {
		Node current = this.root;
		while (current !=null) {
			if (current.data == x)
				return current;
			else if (current.data > x)
				current = current.left;
			else
				current = current.right;
		}
		return null;
	}
	
	public boolean delete(int x) {
		Node p = root;
		Node current = root;
		boolean isLeftChild = false;
		while (current.data != x) {
			p = current;
			if (current.data > x) {
				isLeftChild = true;
				current = current.left;
			}
			else {
				isLeftChild = false;
				current = current.right;
			}
			if (current == null)
				return false;
		}
	
	
		if (current.left == null && current.right == null) {
			if (current == this.root) {
				this.root = null;
			}
			else if (isLeftChild)
				p.left=null;
			else
				p.right = null;
		}
	
		else if (current.right == null) {
			if (current == this.root)
				this.root = current.left;
			else if (isLeftChild)
				p.left=current.left;
			else
				p.right=current.right;
		}
		
		else if (current.left == null) {
			if (current == this.root)
				this.root = current.right;
			else if (isLeftChild)
				p.left=current.right;
			else
				p.right=current.right;
		}
		
		else if (current.left != null && current.right !=null) {
			Node replacement = getReplacement(current);
			if (current == this.root)
				this.root = replacement;
			else if (isLeftChild)
				p.left = replacement;
			else
				p.right = replacement;
			replacement.left = current.left;
		}
		return true;
	}
	
	public Node getReplacement(Node n) {
		Node replacement = null;
		Node replacementParent = null;
		Node current = n.right;
		while ( current != null) {
			replacementParent=replacement;
			replacement = current;
			current = current.left;
		}
		if (replacement != n.right) {
			replacementParent = replacement.right;
			replacement.right = n.right;
		}
		return replacement;
	
	}
	
}
