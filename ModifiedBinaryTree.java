

public class ModifiedBinaryTree extends BinarySearchTree {
	
	class MNode extends Node {
		int inorder, preorder, postorder;
		MNode(int x){
			super(x);
		}
	}
	@Override
	public boolean insert(int d) {
		MNode n = new MNode(d);
		if (this.root == null) {
			this.root = n;
			return true;
		}
		
		MNode current = (MNode) root;
		MNode p = null;
		
		while (true) {
			p = current;
			if (n.data < current.data) {
				current = (MNode) current.left;
				if (current == null) {
					p.left = n;
					n.setParent(p);
					return true;
				}
			}
			else if (n.data > current.data) {
				current = (MNode) current.right;
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
	@Override
	public Node find(int x) {
		MNode current =(MNode)this.root;
		while (current !=null) {
			if (current.data == x)
				return current;
			else if (current.data > x)
				current = (MNode) current.left;
			else
				current = (MNode) current.right;
		}
		return null;
	}
	
	@Override
	public boolean delete(int x) {
		MNode p = (MNode) root;
		MNode current = (MNode) root;
		boolean isLeftChild = false;
		while (current.data != x) {
			p = current;
			if (current.data > x) {
				isLeftChild = true;
				current = (MNode) current.left;
			}
			else {
				isLeftChild = false;
				current = (MNode) current.right;
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
	@Override
	public MNode getReplacement(Node n) {
		MNode replacement = null;
		MNode replacementParent = null;
		MNode current = (MNode) n.right;
		while ( current != null) {
			replacementParent=replacement;
			replacement = current;
			current = (MNode) current.left;
		}
		if (replacement != n.right) {
			replacementParent = (MNode) replacement.right;
			replacement.right = n.right;
		}
		return replacement;
	}
	
	public MNode preorderNext(MNode n) {
		if (n.left != null)
			return (MNode) n.left;
		else if (n.right != null && n.left==null)
			return (MNode) n.right;
		else if (n.parent.left == n) {
			Node holder = n;
			while (holder.parent.right == null ) {
				holder = holder.parent;
				if (holder == super.root)
					return null;
			}
			return (MNode) holder.parent.right;
		}
		else {
			Node holder = n;
			while (holder.parent.right==holder || holder.parent.right == null) {
				holder = holder.parent;
				if (holder==super.root)
					return null;
			}
			holder=holder.parent.right;
			return (MNode) holder;
		}
	}
	
	
	
	
	public MNode inorderNext(MNode n) {
		if (n.right != null ) {
			Node holder = n.right;
			while (holder.left != null) {
				holder = holder.left;
			}
			return (MNode) holder;
		}
		if (n.parent.left == n && n.right == null) {
			return (MNode) n.parent;
		}
		if (n.parent.right ==n && n.right == null && n.left == null) {
			Node holder = n;
			while (holder.parent.right == holder) {
				holder = holder.parent;
				if (holder==super.root)
					return null;
			}
			return (MNode) holder.parent;
		}
		return null;
	}
		
	
	
	public MNode postorderNext(MNode n) {
		if (n == super.root)
			return null;
		if (n==n.parent.right)
			return (MNode) n.parent;
		if (n.parent.right == null)
			return (MNode) n.parent;
		if (n.parent.right != null) {
			Node holder = n.parent.right;
			while (holder.left != null) {
				holder = holder.left;
			}
			while (holder.right !=null) {
				holder = holder.right;
			}
			return (MNode) holder;
		}
		return null;	
	}
	
	public void preOrderNumber() {
		if (this.root == null) {
			return;
		}
		MNode n = (MNode) this.root;
		n.preorder = 1;
		int counter = 1;
		while (this.preorderNext(n) != null) {
			this.preorderNext(n).preorder=++counter;
			n = this.preorderNext(n);
		}	
	}
	
	public void inOrderNumber() {
		if (this.root == null)
			return;
		MNode n = (MNode) this.root;
		while (n.left !=null)
			n = (MNode) n.left;
		n.inorder=1;
		int counter=1;
		while (this.inorderNext(n) != null) {
			this.inorderNext(n).inorder = ++counter;
			n = this.inorderNext(n);
		}
	}
	
	public void postOrderNumbers() {
		if (this.root == null)
			return;
		MNode n = (MNode) this.root;
		while (n.left !=null)
			n = (MNode) n.left;
		while (n.right !=null)
			n = (MNode) n.right;
		n.postorder=1;
		int counter=1;
		while (this.postorderNext(n) != null) {
			this.postorderNext(n).postorder = ++counter;
			n = this.postorderNext(n);
		}
	}
	
	public static void main(String[] args) {
		ModifiedBinaryTree t = new ModifiedBinaryTree();
		t.insert(5);
		t.insert(3);
		t.insert(7);
		t.insert(2);
		t.insert(4);
		t.insert(6);
		t.insert(8);
		t.inOrderNumber();
		t.preOrderNumber();
		t.postOrderNumbers();
		MNode n = (MNode) t.root;
		do {
			System.out.println("This node has a value of: "+n.data+" preorder: "+n.preorder+" inorder: "+n.inorder+ " postorder: "+ n.postorder);
			n = t.preorderNext(n);
		}	while (n != null);
	}
	
	
}
