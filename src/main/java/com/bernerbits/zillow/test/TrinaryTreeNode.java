package com.bernerbits.zillow.test;

import java.util.Comparator;

/**
 * Trinary tree node.
 * 
 * Contains the node value, a reference to the tree's comparator, references to child node holders (see 
 * {@link TrinaryTreeNodeHolder}) left, center, and right, and also its own holder. In this way, nodes 
 * can be manipulated without storing back-references to parent nodes, and also repetitive null-checks
 * can be consolidated into the holders themselves, greatly simplifying insert and delete operations.
 * 
 * @see com.bernerbits.zillow.test.TrinaryTreeNodeHolder
 * 
 * @author derekberner
 *
 * @param <T> Type parameter of the enclosing tree.
 */
/* package-private */ class TrinaryTreeNode<T> {

	private final T value;
	private final Comparator<T> comparator;
	
	private TrinaryTreeNodeHolder<T> owner;
	
	private TrinaryTreeNodeHolder<T> left = new TrinaryTreeNodeHolder<T>();
	private TrinaryTreeNodeHolder<T> center = new TrinaryTreeNodeHolder<T>();
	private TrinaryTreeNodeHolder<T> right = new TrinaryTreeNodeHolder<T>();
	
	/* package-private */ TrinaryTreeNode(T value, Comparator<T> comparator) {
		this.value = value;
		this.comparator = comparator;
	}
	
	/* package-private */ void setOwner(TrinaryTreeNodeHolder<T> owner) {
		this.owner = owner;
	}
	
	/* package-private */ void add(TrinaryTreeNode<T> newValue) {
		int comparison = comparator.compare(newValue.getValue(), this.value);
		if(comparison < 0) {
			left.add(newValue);
		} else if(comparison > 0) {
			right.add(newValue);
		} else {
			center.add(newValue);
		}
	}
	
	/* package-private */ boolean delete(T value) {
		int comparison = comparator.compare(value, this.value);
		
		// First try pushing the delete as far down the tree as we can go.
		if(comparison < 0) {
			return left.delete(value);
		} else if(comparison > 0) {
			return right.delete(value);
		} 
		
		// If here, then the value matches, so remove this node.
		remove();
		return true;
	}
	
	/* package-private */ T getValue() {
		return value;
	}

	/* package-private */ void remove() {
		// Delete this node by nulling out our holder's reference.
		// No need to explicitly delete this node. By clearing out the holder, we ensure that this node
		// will go out of scope and eventually be garbage-collected.
		TrinaryTreeNodeHolder<T> previousOwner = owner;
		previousOwner.clear();
		
		// Now we must rebalance the remaining nodes by calling add on the holder.
		TrinaryTreeNode<T> leftNode = left.get();
		TrinaryTreeNode<T> centerNode = center.get();
		TrinaryTreeNode<T> rightNode = right.get();
		
		if(centerNode != null) {
			previousOwner.add(centerNode);
		}
		if(leftNode != null) {
			previousOwner.add(leftNode);
		}
		if(rightNode != null) {
			previousOwner.add(rightNode);
		}
	}
		
	/* package-private */ TrinaryTreeNodeHolder<T> getLeft() {
		return left;
	}
	/* package-private */ TrinaryTreeNodeHolder<T> getCenter() {
		return center;
	}
	/* package-private */ TrinaryTreeNodeHolder<T> getRight() {
		return right;
	}
	
}
