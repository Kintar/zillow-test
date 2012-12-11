package com.bernerbits.zillow.test;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Trinary Tree, for question 2.
 * 
 * See {@link TrinaryTreeNodeHolder} and {@link TrinaryTreeNode} for how individual nodes
 * are structured. Each node is contained within a node holder, which is pre-initialized on 
 * the node itself. In this way, nodes can be moved around and deleted without maintaining 
 * back-references to parent nodes. Further, the node holder eliminates repetitive null checks
 * by consolidating them into a single location. 
 * 
 * @see com.bernerbits.zillow.test.TrinaryTreeNode
 * @see com.bernerbits.zillow.test.TrinaryTreeNodeHolder
 * @see com.bernerbits.zillow.test.TrinaryTreeIterator
 * 
 * @author derekberner
 *
 * @param <T> The type parameter
 */
public class TrinaryTree<T> {
	
	private TrinaryTreeNodeHolder<T> root = new TrinaryTreeNodeHolder<T>();
	
	private final Comparator<T> comparator;
	
	/**
	 * Constructs a new TrinaryTree using a default comparator for Comparable class.
	 * Due to type erasure in Java, we can't know at construction time if T is comparable. 
	 * Requiring the class to be comparable spares us a manual runtime type check, but
	 * it can't be done with a normal constructor. This factory method helps us get around 
	 * that, creating a new tree of any comparable type, which will be inferred at runtime.  
	 */
	public static <C extends Comparable<C>> TrinaryTree<C> create() {
		return new TrinaryTree<C>(new Comparator<C>() {
			public int compare(C o1, C o2) {
				return o1.compareTo(o2);
			}
		});
	}

	/**
	 * Constructs a new TrinaryTree using a custom comparator. 
	 *
	 * @param comparator the Comparator to use.
	 */
	public TrinaryTree(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public void insert(T value) {
		// Don't permit null values. Saves us from doing null checks later.
		if(value == null) {
			throw new IllegalArgumentException("Attempted null insert detected.");
		}
		root.add(new TrinaryTreeNode<T>(value, comparator));
	}
	
	public boolean delete(T value) {
		return root.delete(value);
	}
	
	public Iterator<T> iterator() {
		return new TrinaryTreeIterator<T>(root);
	}
	
	/* package-private */ TrinaryTreeNodeHolder<T> getRoot() {
		return root;
	}
}
