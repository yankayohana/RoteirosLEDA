package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isEmpty()) {
			return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		} else {
			return 1;
		}
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			int balance = calculateBalance(node);
			if (Math.abs(balance) > 1) {
				rotation(node);
			}
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		while (!node.isEmpty()) {
			rebalance((BSTNode<T>) node);
			node = (BSTNode<T>) node.getParent();
		}
	}

	protected void rotation(BSTNode<T> root) {
		BSTNode<T> att = new BSTNode<>();
		int balance = calculateBalance(root);
		if (balance > 0) {
			if (pendingLeft((BSTNode<T>) root.getLeft())) {
				root.setLeft(Util.leftRotation((BSTNode<T>) root.getLeft()));
			}
			att = Util.rightRotation(root);
		} else {
			if (pendingRight((BSTNode<T>) root.getRight())) {
				root.setRight(Util.rightRotation((BSTNode<T>) root.getRight()));
			}
			att = Util.leftRotation(root);
		}

		if (this.root.equals(root)) {
			this.root = att;
		} else {
			if (att.getParent().getLeft().equals(root)) {
				att.getParent().setLeft(att);
			} else {
				att.getParent().setRight(att);
			}
		}
	}

	protected boolean pendingRight(BSTNode<T> node) {
		return height(node) < 0;
	}

	protected boolean pendingLeft(BSTNode<T> node) {
		return height(node) > 0;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			insert(this.root, element, new BSTNode<>());
			this.rebalanceUp(search(element));
		}
	}

	protected void insert(BSTNode<T> node, T element, BSTNode<T> parent) {
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
			rebalanceUp(node);
		}
	}
}