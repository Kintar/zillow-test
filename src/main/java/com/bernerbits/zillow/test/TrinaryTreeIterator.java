package com.bernerbits.zillow.test;

import java.util.Iterator;

/**
 * Iterator for trinary tree.
 * 
 * Not part of the requirements, but provided to simplify testing. A correctly designed 
 * iterator for a correctly designed trinary tree gives all the tree elements in comparator 
 * order, with "equal" elements (according to the comparator) appearing in insertion order.
 * 
 * Tracks iteration by creating sub-iterators on the current node's children,
 * and iterates on elements in "left child-current-center child-right child" order.
 * 
 * @author derekberner
 *
 * @param <T> Type parameter of the source tree.
 */
/* package-private */ class TrinaryTreeIterator<T> implements Iterator<T> {

	private final TrinaryTreeNodeHolder<T> root;
	
	private final TrinaryTreeIterator<T> left;
	private final TrinaryTreeIterator<T> center;
	private final TrinaryTreeIterator<T> right;
	
	private final boolean empty;
	
	public TrinaryTreeIterator(TrinaryTreeNodeHolder<T> root) {
		this.root = root;
		if(root.get() == null) {
			empty = true;
			left = null;
			center = null;
			right = null;
		} else {
			empty = false;
			left = new TrinaryTreeIterator<T>(root.get().getLeft());
			center = new TrinaryTreeIterator<T>(root.get().getCenter());
			right = new TrinaryTreeIterator<T>(root.get().getRight());
		}
	}
	
	public boolean hasNext() {
		if(next == null) {
			next = nextNode();
		}
		return next != null;
	}
	
	private TrinaryTreeNode<T> current;
	private TrinaryTreeNode<T> next;
	private boolean localChecked = false;
	
	public T next() {
		if(hasNext()) {
			current = next;
			next = null;
			return current.getValue();
		}
		return null;
	}
	
	private TrinaryTreeNode<T> nextNode() {
		if(empty) {
			return null;
		}
		
		TrinaryTreeNode<T> next = left.nextNode();
		if(next != null) {
			return next;
		}
		
		if(!localChecked) {
			localChecked = true;
			return root.get();
		}
		
		next = center.nextNode();
		if(next != null) {
			return next;
		}
		next = right.nextNode();
		if(next != null) {
			return next;
		}
		
		return null;
	}

	public void remove() {
		current.remove();
	}
	
}
