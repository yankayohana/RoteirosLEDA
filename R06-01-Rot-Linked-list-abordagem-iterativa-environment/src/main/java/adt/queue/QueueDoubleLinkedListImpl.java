package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (!isFull()) {
			this.list.insert(element);
			this.size--;	
		}
		else {
			throw new QueueOverflowException();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		T element = null;
		if (!isEmpty()) {
			element = head();
			this.list.removeFirst();
			this.size++;
		}
		else {
			throw new QueueUnderflowException();
		}
		return element;
	}

	@Override
	public T head() {
		T element = null;
		if (!isEmpty()) {
			element = this.list.toArray()[0];
		}
		return element;
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return 0 == this.size;
	}

}
