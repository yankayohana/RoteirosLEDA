package adt.bst;

import java.util.ArrayList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return this.height(this.root);
	}

	private int height(BSTNode<T> node) {
		int result = -1;
		if (!node.isEmpty()) {
			int left = height((BSTNode<T>) node.getLeft());
			int right = height((BSTNode<T>) node.getRight());

			result = Math.max(left, right) + 1;
		}
		return result;
	}

	@Override
	public BSTNode<T> search(T element) {
		return this.search(this.root, element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		BSTNode<T> result = null;
		if (element != null) {
			if (node.isEmpty() || node.getData().compareTo(element) == 0) {
				result = node;
			} else if (node.getData().compareTo(element) > 0) {
				result = search((BSTNode<T>) node.getLeft(), element);
			} else {
				result = search((BSTNode<T>) node.getRight(), element);
			}
		}
		return result;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(element, this.root, new BSTNode<>());
		}
	}

	private void insert(T element, BSTNode<T> node, BSTNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<>());
			node.setRight(new BSTNode<>());
			node.setParent(parent);
		} else if (element.compareTo(node.getData()) > 0) {
			insert(element, (BSTNode<T>) node.getRight(), node);
		} else {
			insert(element, (BSTNode<T>) node.getLeft(), node);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return treeMaximum(this.root);
	}

	private BSTNode<T> treeMaximum(BSTNode<T> aux) {
		BSTNode<T> result = null;
		BSTNode<T> node = aux;
		while (!node.isEmpty()) {
			result = node;
			node = (BSTNode<T>) node.getRight();
		}
		return result;
	}

	@Override
	public BSTNode<T> minimum() {
		return treeMinimum(this.root);
	}

	private BSTNode<T> treeMinimum(BSTNode<T> aux) {
		BSTNode<T> result = null;
		BSTNode<T> node = aux;
		while (!node.isEmpty()) {
			result = node;
			node = (BSTNode<T>) node.getLeft();
		}
		return result;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> node = search(element);
		if (!node.isEmpty() && !node.equals(this.maximum())) {
			if (!node.getRight().isEmpty()) {
				result = this.treeMinimum((BSTNode<T>) node.getRight());
			} else {
				result = (BSTNode<T>) node.getParent();
				while (!result.isEmpty() && node.equals(result.getRight())) {
					node = result;
					result = (BSTNode<T>) result.getParent();
				}
			}
		}
		return result;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> node = search(element);
		if (!node.isEmpty() && !node.equals(this.minimum())) {
			if (!node.getLeft().isEmpty()) {
				result = this.treeMaximum((BSTNode<T>) node.getLeft());
			} else {
				result = (BSTNode<T>) node.getParent();
				while (!result.isEmpty() && node.equals(result.getLeft())) {
					node = result;
					result = (BSTNode<T>) result.getParent();
				}
			}
		}
		return result;
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (node != null && !node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				node.setLeft(null);
				node.setRight(null);
				node.setParent(null);
			} else if (numberOfChildren(node) == 1) {
				if (!node.equals(this.root)) {
					if (node.getParent().getLeft().equals(node)) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}
				} else {
					T newData = null;
					if (!node.getLeft().isEmpty()) {
						newData = treeMaximum((BSTNode<T>) node.getLeft()).getData();
					} else {
						newData = treeMinimum((BSTNode<T>) node.getRight()).getData();
					}
					remove(newData);
					this.root.setData(newData);
				}
			} 
			else {
				BSTNode<T> sucessor = sucessor(node.getData());
				T newData = sucessor.getData();
				remove(sucessor.getData());
				node.setData(newData);
			}
		}
	}

	private int numberOfChildren(BSTNode<T> node) {
		int result = 0;
		if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			result = 2;
		} else if ((node.getLeft().isEmpty() && !node.getRight().isEmpty())
				|| (!node.getLeft().isEmpty() && node.getRight().isEmpty())) {
			result = 1;
		}
		return result;
	}

	@Override
	public T[] preOrder() {
		ArrayList<Comparable> array = new ArrayList<Comparable>();
		preOrder(array, this.root);
		return (T[]) array.toArray(new Comparable[size()]);
	}

	private void preOrder(ArrayList<Comparable> array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			array.add(node.getData());
			preOrder(array, (BSTNode<T>) node.getLeft());
			preOrder(array, ((BSTNode<T>) node.getRight()));
		}
	}

	@Override
	public T[] order() {
		ArrayList<Comparable> array = new ArrayList<Comparable>();
		order(array, this.root);
		return (T[]) array.toArray(new Comparable[size()]);

	}

	private void order(ArrayList<Comparable> array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			order(array, (BSTNode<T>) node.getLeft());
			array.add(node.getData());
			order(array, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] postOrder() {
		ArrayList<Comparable> array = new ArrayList<Comparable>();
		postOrder(array, this.root);
		return (T[]) array.toArray(new Comparable[size()]);
	}

	private void postOrder(ArrayList<Comparable> array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			postOrder(array, (BSTNode<T>) node.getLeft());
			postOrder(array, (BSTNode<T>) node.getRight());
			array.add(node.getData());
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand how
	 * it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}
}