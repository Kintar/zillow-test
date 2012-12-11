package com.bernerbits.zillow.test;

/**
 * {@link TrinaryTreeNode} "holder" for Trinary tree. Since at any time, a node may be null, this class allows
 * us to more cleanly perform operations on null nodes. It also allows us to rebuild the subtree
 * during delete, without requiring a pointer back to the parent node.
 * 
 * @author derekberner
 *
 * @param <T> Type parameter for the enclosing tree.
 */
/* package-private */ class TrinaryTreeNodeHolder<T> {

	private TrinaryTreeNode<T> node;
	
	/* package-private */ TrinaryTreeNodeHolder() {
	}
	
	/* package-private */ TrinaryTreeNode<T> get() {
		return node;
	}
	
	/* package-private */ void add(TrinaryTreeNode<T> node) {
		if(this.node == null) {
			this.node = node;
			node.setOwner(this);
		} else {
			this.node.add(node);
		}
	}
	
	/* package-private */ boolean delete(T value) {
		if(this.node == null) {
			return false;
		} else {
			return this.node.delete(value);
		}
	}

	/* package-private */ void clear() {
		this.node = null;
	}
}
