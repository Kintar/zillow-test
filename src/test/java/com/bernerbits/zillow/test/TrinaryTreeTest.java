package com.bernerbits.zillow.test;

import java.util.Comparator;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class TrinaryTreeTest {
	
	/**
	 * Test that insertion and iteration work.
	 */
	@Test public void insert() {
		TrinaryTree<Integer> tree = TrinaryTree.create();
		for(int value : new int[]{9, 1, 9, 2, 5, 6, 9, 8, 2, 7, 0, 4, 7, 0, 7, 6, 4, 8, 0, 9, 8}) {
			tree.insert(value);
		}
		
		// Iterate the tree and ensure that all values are present.
		int[] result = new int[21];
		int index=0;
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}
		Assert.assertArrayEquals(result, new int[]{0, 0, 0, 1, 2, 2, 4, 4, 5, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9});
	}
	
	/**
	 * Test Zillow example, testing exact tree structure.
	 * 
	 * Note that this test is somewhat unwieldy due to the non-publicly-usable nature of the package-private traversal API.
	 */
	@Test public void zillowExample() {
		TrinaryTree<Integer> tree = TrinaryTree.create();
		for(int value : new int[]{5,4,9,5,7,2,2}) {
			tree.insert(value);
		}
		
		// Assert that the members are in the right place.
		Assert.assertEquals(5, (int)tree.getRoot().get().getValue());
		Assert.assertEquals(4, (int)tree.getRoot().get().getLeft().get().getValue());
		Assert.assertEquals(2, (int)tree.getRoot().get().getLeft().get().getLeft().get().getValue());
		Assert.assertEquals(2, (int)tree.getRoot().get().getLeft().get().getLeft().get().getCenter().get().getValue());
		Assert.assertEquals(5, (int)tree.getRoot().get().getCenter().get().getValue());
		Assert.assertEquals(9, (int)tree.getRoot().get().getRight().get().getValue());
		Assert.assertEquals(7, (int)tree.getRoot().get().getRight().get().getLeft().get().getValue());

		// Then assert that everything else is null.
		Assert.assertNull(tree.getRoot().get().getLeft().get().getCenter().get());
		Assert.assertNull(tree.getRoot().get().getLeft().get().getRight().get());
		Assert.assertNull(tree.getRoot().get().getLeft().get().getLeft().get().getRight().get());
		Assert.assertNull(tree.getRoot().get().getLeft().get().getLeft().get().getLeft().get());
		Assert.assertNull(tree.getRoot().get().getLeft().get().getLeft().get().getCenter().get().getLeft().get());
		Assert.assertNull(tree.getRoot().get().getLeft().get().getLeft().get().getCenter().get().getCenter().get());
		Assert.assertNull(tree.getRoot().get().getLeft().get().getLeft().get().getCenter().get().getRight().get());
		Assert.assertNull(tree.getRoot().get().getCenter().get().getLeft().get());
		Assert.assertNull(tree.getRoot().get().getCenter().get().getCenter().get());
		Assert.assertNull(tree.getRoot().get().getCenter().get().getRight().get());
		Assert.assertNull(tree.getRoot().get().getRight().get().getCenter().get());
		Assert.assertNull(tree.getRoot().get().getRight().get().getRight().get());
		Assert.assertNull(tree.getRoot().get().getRight().get().getLeft().get().getLeft().get());
		Assert.assertNull(tree.getRoot().get().getRight().get().getLeft().get().getCenter().get());
		Assert.assertNull(tree.getRoot().get().getRight().get().getLeft().get().getRight().get());
	}

	/**
	 * Delete using value. This is ideal for individual deletes. For multiple deletes over the whole tree, use
	 *   iterator.remove(). 
	 */
	@Test public void valueDelete() {
		TrinaryTree<Integer> tree = TrinaryTree.create();
		for(int value : new int[]{3, 0, 6, 2, 2, 0, 5, 2, 4, 0, 4, 3, 5, 4, 3, 1, 8, 7, 3, 9, 5}) {
			tree.insert(value);
		}
		
		// Iterate the tree and ensure that all values are present.
		int[] result = new int[21];
		int index=0;
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}
		Assert.assertArrayEquals(result, new int[]{0, 0, 0, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 7, 8, 9});
		
		// Remove the first 4
		Assert.assertTrue(tree.delete(4));
		
		// Now check that 4 has been removed
		result = new int[20];
		index=0;
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}		
		Assert.assertArrayEquals(result, new int[]{0, 0, 0, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5, 6, 7, 8, 9});
	}
	
	/**
	 * Delete using iterator. For sparse deletes (O(log n)), this is slower, but it will be
	 *   faster on average (O(n) as opposed to O(n log n)) when many removals are needed over the 
	 *   entire tree (e.g. remove all odds).
	 */
	@Test public void iteratorDelete() {
		TrinaryTree<Integer> tree = TrinaryTree.create();
		for(int value : new int[]{1, 7, 3, 5, 0, 4, 1, 3, 3, 7, 4, 1, 4, 5, 3, 8, 8, 7, 8, 9, 1}) {
			tree.insert(value);
		}
		
		// Iterate the tree and ensure that all values are present.
		int[] result = new int[21];
		int index=0;
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}
		
		Assert.assertArrayEquals(result, new int[]{0, 1, 1, 1, 1, 3, 3, 3, 3, 4, 4, 4, 5, 5, 7, 7, 7, 8, 8, 8, 9});
		
		// Remove all odds
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			if(iter.next() % 2 == 1) {
				iter.remove();
			}
		}
		
		result = new int[7];
		index=0;
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}
		
		Assert.assertArrayEquals(result, new int[]{0, 4, 4, 4, 8, 8, 8});
	}
	
	/**
	 * Use custom comparator. Example comparator does a case-insensitive string comparison.
	 */
	@Test public void customComparator() {
		TrinaryTree<String> tree = new TrinaryTree<String>(new Comparator<String>(){
			public int compare(String o1, String o2) {
				return o1.toLowerCase().compareTo(o2.toLowerCase());
			}
		});
		for(String value : new String[]{"abc", "abcd", "ABC", "ZYX", "Abc", "zyx", "zYx", "abcd", "ABCD"}) {
			tree.insert(value);
		}
		
		// Iterate the tree and ensure that all values are present.
		String[] result = new String[9];
		int index=0;
		for(Iterator<String> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}
		
		// Trinary tree structure should ensure that "equal" strings remain in insertion order.
		Assert.assertArrayEquals(result, new String[]{"abc", "ABC", "Abc", "abcd", "abcd", "ABCD", "ZYX", "zyx", "zYx"});
		
		// Case also shouldn't matter during delete and should preserve insertion order.
		tree.delete("ABCD");
		
		result = new String[8];
		index=0;
		for(Iterator<String> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}
		
		Assert.assertArrayEquals(result, new String[]{"abc", "ABC", "Abc", "abcd", "ABCD", "ZYX", "zyx", "zYx"});
		
	}
	
	/**
	 * Test iteration on empty tree.
	 */
	@Test public void emptyTree() {
		TrinaryTree<String> tree = TrinaryTree.create();
		Iterator<String> iter = tree.iterator();
		Assert.assertFalse(iter.hasNext());
		Assert.assertTrue(iter.next() == null);
	}
	
	/**
	 * Test insertion of null value.
	 * 
	 * @throws IllegalArgumentException if test passes
	 */
	@Test(expected=IllegalArgumentException.class) public void insertNullException() {
		TrinaryTree<String> tree = TrinaryTree.create();
		tree.insert(null);
	}
	
	/**
	 * Test deletion of nonexistent value.
	 */
	/**
	 * Delete using value. This is ideal for individual deletes. For multiple deletes over the whole tree, use
	 *   iterator.remove(). 
	 */
	@Test public void nonExistentDelete() {
		TrinaryTree<Integer> tree = TrinaryTree.create();
		for(int value : new int[]{3, 0, 6, 2, 2, 0, 5, 2, 4, 0, 4, 3, 5, 4, 3, 1, 8, 7, 3, 9, 5}) {
			tree.insert(value);
		}
		
		// Iterate the tree and ensure that all values are present.
		int[] result = new int[21];
		int index=0;
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}
		Assert.assertArrayEquals(result, new int[]{0, 0, 0, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 7, 8, 9});
		
		// Delete 10, which doesn't exist
		Assert.assertFalse(tree.delete(10));
		
		// Now check that the tree is the same
		result = new int[21];
		index=0;
		for(Iterator<Integer> iter = tree.iterator(); iter.hasNext();) {
			result[index++] = iter.next();
		}		
		Assert.assertArrayEquals(result, new int[]{0, 0, 0, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 7, 8, 9});
	}
}
