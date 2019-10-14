package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (element == null) {
			return;
		}
		if (this.isFull()) {
			throw new StackOverflowException();
		}
		this.top.insertFirst(element);
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (this.isEmpty()) {
			throw new StackUnderflowException();
		}
		T element = ((RecursiveDoubleLinkedListImpl<T>) top).getData();
		this.top.removeFirst();
		return element;
	}

	@Override
	public T top() {
		return ((RecursiveDoubleLinkedListImpl<T>) top).getData();
	}

	@Override
	public boolean isEmpty() {
		return this.top.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.top.size() == size;
	}

}