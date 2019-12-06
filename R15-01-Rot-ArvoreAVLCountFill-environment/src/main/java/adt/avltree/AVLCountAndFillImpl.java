package adt.avltree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {

	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		if (array != null) {
			T[] rootPreOrder = this.preOrder();
			List<T> allElements = new ArrayList<>();
			Collections.addAll(allElements, array);
			Collections.addAll(allElements, rootPreOrder);
			Collections.sort(allElements);
			removeRepeated(allElements);
			this.root = new BSTNode<>();
			T[] add = getArrayInsert((T[]) allElements.toArray(new Comparable[allElements.size()]));
			for (int i = 0; i < add.length; i++) {
				this.insert(add[i]);
			}
		}
	}

	private T[] getArrayInsert(T[] array) {
		ArrayList<ArrayList<T>> matrix = new ArrayList<>();
		T[] support = (T[]) new Comparable[array.length];
		matrix.add(new ArrayList<T>(Arrays.asList(array)));
		int i = 0;
		while (i < array.length) {
			int middle = matrix.get(i).size() / 2;
			support[i] = matrix.get(i).get(middle);
			matrix.add(newArrayList(matrix.get(i), 0, middle));
			matrix.add(newArrayList(matrix.get(i), middle + 1, matrix.get(i).size()));
			i += 1;
		}
		return support;
	}

	private ArrayList<T> newArrayList(ArrayList<T> array, int a, int f) {
		ArrayList<T> aux = new ArrayList<>();
		for (int i = a; i < f; i++) {
			aux.add(array.get(i));
		}
		return aux;
	}

	private void removeRepeated(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size() && list.get(i).equals(list.get(j)); j++) {
				list.remove(list.get(j));
			}
		}
	}

	@Override
	protected void rotation(BSTNode<T> root) {
		BSTNode<T> att = new BSTNode<>();
		boolean dualRotation = false;
		int balance = calculateBalance(root);
		if (balance > 0) {
			if (calculateBalance((BSTNode<T>) root.getLeft()) < 0) {
				root.setLeft(Util.leftRotation((BSTNode<T>) root.getLeft()));
				this.LRcounter += 1;
				dualRotation = true;
			}
			if (!dualRotation) {
				this.LLcounter += 1;
			}
			att = Util.rightRotation(root);
		} else {
			if (calculateBalance((BSTNode<T>) root.getRight()) > 0) {
				root.setRight(Util.rightRotation((BSTNode<T>) root.getRight()));
				this.RLcounter += 1;
				dualRotation = true;
			}
			if (!dualRotation) {
				this.RRcounter += 1;
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
}