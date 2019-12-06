package adt.bst;

import java.util.ArrayList;
import java.util.List;

import adt.bt.BTNode;

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

	protected int height(BSTNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		}
		return Math.max(this.height((BSTNode<T>) node.getLeft()), this.height((BSTNode<T>) node.getRight())) + 1;
	}

	@Override
	public BSTNode<T> search(T element) {
		if (this.getRoot().isEmpty() || element.compareTo(this.getRoot().getData()) == 0) {
			return this.getRoot();
		} else {
			if (element.compareTo(root.getData()) < 0) {
				return search(element, (BSTNode<T>) this.getRoot().getLeft());
			} else {
				return search(element, (BSTNode<T>) this.getRoot().getRight());
			}
		}
	}

	private BSTNode<T> search(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			return node;
		} else if (element.compareTo(node.getData()) < 0) {
			return this.search(element, (BSTNode<T>) node.getLeft());
		} else if (element.compareTo(node.getData()) > 0) {
			return this.search(element, (BSTNode<T>) node.getRight());
		} else {
			return node;
		}
	}

	@Override
	public void insert(T element) {
		this.insert(this.root, element, new BSTNode<T>());
	}

	private void insert(BSTNode<T> node, T element, BSTNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		} else if (element.compareTo(node.getData()) < 0) {
			insert((BSTNode<T>) node.getLeft(), element, node);
		} else if (element.compareTo(node.getData()) > 0) {
			insert((BSTNode<T>) node.getRight(), element, node);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return this.maximum(this.root);
	}

	private BSTNode<T> maximum(BTNode<T> root) {
		BTNode<T> node = root;
		if (node.isEmpty()) {
			return null;
		} else {
			while (!node.getRight().isEmpty()) {
				node = node.getRight();
			}
			return (BSTNode<T>) node;
		}
	}

	@Override
	public BSTNode<T> minimum() {
		return this.minimum(this.root);
	}

	private BSTNode<T> minimum(BTNode<T> root) {
		BTNode<T> node = root;
		if (node.isEmpty()) {
			return null;
		} else {
			while (!node.getLeft().isEmpty()) {
				node = node.getLeft();
			}
			return (BSTNode<T>) node;
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);
		if (node.isEmpty()) {
			return null;
		} else if (!node.getRight().isEmpty()) {
			return this.minimum(node.getRight());
		} else if (node.getParent().getLeft() == node) {
			return (BSTNode<T>) node.getParent();
		}
		while (!node.isEmpty() && this.isRightChild(node, node.getParent())) {
			node = (BSTNode<T>) node.getParent();
		}
		return (BSTNode<T>) node.getParent();
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);
		if (node.isEmpty()) {
			return null;
		} else if (!node.getLeft().isEmpty()) {
			return this.maximum(node.getLeft());
		} else if (node.getParent().getRight() == node) {
			return (BSTNode<T>) node.getParent();
		}
		while (!node.isEmpty() && this.isLeftChild(node, node.getParent())) {
			node = (BSTNode<T>) node.getParent();
		}
		if (node == this.root) {
			return null;
		}
		return (BSTNode<T>) node.getParent();
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = this.search(element);
		remove(node);
	}

	private void remove(BSTNode<T> node) {
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
			} else if (hasOneChild(node)) {
				if (node.getParent() != null) {
					if (!node.getParent().getLeft().equals(node)) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						} else {
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}
				} else {
					if (node.getLeft().isEmpty()) {
						this.root = (BSTNode<T>) node.getRight();
					} else {
						this.root = (BSTNode<T>) node.getLeft();
					}
					this.root.setParent(null);
				}
			} else {
				T sucessor = sucessor(node.getData()).getData();
				remove(sucessor);
				node.setData(sucessor);
			}
		}
	}

	protected boolean hasOneChild(BSTNode<T> node) {
		return (node.getLeft().isEmpty() && !node.getRight().isEmpty())
				|| (!node.getLeft().isEmpty() && node.getRight().isEmpty());
	}

	protected boolean isLeftChild(BSTNode<T> node, BTNode<T> parent) {
		if (node == null || node.isEmpty()) {
			return false;
		}
		return parent.getLeft() == node;
	}

	protected boolean isRightChild(BSTNode<T> node, BTNode<T> parent) {
		if (node == null || node.isEmpty()) {
			return false;
		}
		return parent.getRight() == node;
	}

	@Override
	public T[] preOrder() {
		List<T> list = new ArrayList<>(this.size());
		preOrder(list, this.root);
		return list.toArray((T[]) new Comparable[this.size()]);
	}

	private void preOrder(List<T> list, BSTNode<T> node) {
		if (!node.isEmpty()) {
			list.add(node.getData());
			preOrder(list, (BSTNode<T>) node.getLeft());
			preOrder(list, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] order() {
		List<T> list = new ArrayList<>(this.size());
		order(list, this.root);
		return list.toArray((T[]) new Comparable[this.size()]);
	}

	private void order(List<T> list, BSTNode<T> node) {
		if (!node.isEmpty()) {
			order(list, (BSTNode<T>) node.getLeft());
			list.add(node.getData());
			order(list, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] postOrder() {
		List<T> list = new ArrayList<>(this.size());
		postOrder(list, this.root);
		return list.toArray((T[]) new Comparable[this.size()]);
	}

	private void postOrder(List<T> list, BSTNode<T> node) {
		if (!node.isEmpty()) {
			postOrder(list, (BSTNode<T>) node.getLeft());
			postOrder(list, (BSTNode<T>) node.getRight());
			list.add(node.getData());
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