package adt.rbtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return this.blackHeight((RBNode<T>) this.root) - 1;
	}

	private int blackHeight(RBNode<T> node) {
		int blackHeight = 0;

		while (!node.isEmpty()) {
			if (node.getColour().equals(Colour.BLACK)) {
				blackHeight += 1;
			}
			node = (RBNode<T>) node.getLeft();
		}
		return blackHeight;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed by
	 * the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must be
	 * BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return this.verifyChildrenOfRedNodes((RBNode<T>) this.root);

	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		boolean result = true;
		boolean aux = true;
		if (!node.isEmpty()) {
			if (node.getColour().equals(Colour.RED)) {
				aux = ((RBNode<T>) node.getRight()).getColour().equals(Colour.BLACK)
						&& ((RBNode<T>) node.getLeft()).getColour().equals(Colour.BLACK);
			}
			result = aux && verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
					&& verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
		}
		return result;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		RBNode<T>[] aux = this.rbPreOrder();
		Set<Integer> blackHeight = new HashSet<>();
		for (int i = 1; i < aux.length; i++) {
			if (aux[i].getColour().equals(Colour.BLACK)) {
				blackHeight.add(this.blackHeight(aux[i]));
			}
		}
		if (blackHeight.size() > 1) {
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public void insert(T value) {
		if (value != null) {
			this.insert(value, (RBNode<T>) this.root, new RBNode<>());
			this.fixUpCase1((RBNode<T>) this.search(value));
		}
	}

	private void insert(T element, RBNode<T> node, RBNode<T> parent) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new RBNode<>());
			node.setRight(new RBNode<>());
			node.setParent(parent);
			node.setColour(Colour.RED);
		} else if (element.compareTo(node.getData()) > 0) {
			insert(element, (RBNode<T>) node.getRight(), node);
		} else {
			insert(element, (RBNode<T>) node.getLeft(), node);
		}
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		List<RBNode<T>> list = new ArrayList<>();
		this.rbPreOrder(list, (RBNode<T>) this.root);
		RBNode<T>[] array = new RBNode[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	private void rbPreOrder(List<RBNode<T>> list, RBNode<T> node) {
		if (!node.isEmpty()) {
			list.add(node);
			rbPreOrder(list, (RBNode<T>) node.getLeft());
			rbPreOrder(list, (RBNode<T>) node.getRight());
		}
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(this.root)) {
			node.setColour(Colour.BLACK);
		} else {
			this.fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (((RBNode<T>) node.getParent()).getColour().equals(Colour.RED)) {
			fixUpCase3(node);
		}
	}

	private RBNode<T> getUncle(RBNode<T> node) {
		RBNode<T> grandma = (RBNode<T>) node.getParent().getParent();
		RBNode<T> uncle = null;
		if (grandma.getLeft().equals(node.getParent())) {
			uncle = (RBNode<T>) grandma.getRight();
		} else {
			uncle = (RBNode<T>) grandma.getLeft();
		}
		return uncle;
	}

	protected void fixUpCase3(RBNode<T> node) {
		if (this.getUncle(node).getColour().equals(Colour.RED)) {
			((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
			this.getUncle(node).setColour(Colour.BLACK);
			((RBNode<T>) node.getParent().getParent()).setColour(Colour.RED);
			this.fixUpCase1((RBNode<T>) node.getParent().getParent());
		} else {
			this.fixUpCase4(node);
		}
	}

	private boolean leftChildren(RBNode<T> node) {
		return node.getParent().getLeft().equals(node);
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		RBNode<T> aux = null;
		RBNode<T> root = null;
		if (!this.leftChildren(node) && this.leftChildren((RBNode<T>) node.getParent())) {
			aux = (RBNode<T>) Util.leftRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
			root = (RBNode<T>) aux.getLeft();
		} else if (this.leftChildren(node) && !this.leftChildren((RBNode<T>) node.getParent())) {
			aux = (RBNode<T>) Util.rightRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
			root = (RBNode<T>) aux.getRight();
		}
		if (aux != null) {
			this.verification(root, aux);
		}
		this.fixUpCase5(next);
	}

	private void verification(RBNode<T> node, RBNode<T> aux) {
		if (this.root.equals(node)) {
			this.root = aux;
		} else {
			if (this.leftChildren(node)) {
				aux.getParent().setLeft(aux);
			} else {
				aux.getParent().setRight(aux);
			}
		}
	}

	protected void fixUpCase5(RBNode<T> node) {
		((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
		((RBNode<T>) node.getParent().getParent()).setColour(Colour.RED);

		RBNode<T> aux = null;
		RBNode<T> root = null;
		if (this.leftChildren(node)) {
			aux = (RBNode<T>) Util.rightRotation((RBNode<T>) node.getParent().getParent());
			root = (RBNode<T>) aux.getRight();
		} else {
			aux = (RBNode<T>) Util.leftRotation((RBNode<T>) node.getParent().getParent());
			root = (RBNode<T>) aux.getLeft();
		}
		this.verification(root, aux);
	}
}