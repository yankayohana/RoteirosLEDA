package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	@Override // null
	public void insert(T element) {
		if (this.isEmpty()) {
			this.head = new DoubleLinkedListNode<>();
		}
		DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) this.last;
		
		
		aux.setData(element);
		aux.next = new DoubleLinkedListNode<>();
		((DoubleLinkedListNode<T>) aux.next).previous = aux; 
		this.last = aux.previous;
		
	}

	@Override // null
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> aux = new DoubleLinkedListNode<>();
		aux.setData(element);
		aux.next = this.head;
		((DoubleLinkedListNode<T>) this.head).previous = aux;
		this.head = aux;
		
	}

	@Override
	public void removeFirst() { // isEmpty
		this.head = this.head.next;
		((DoubleLinkedListNode<T>) this.head).previous = null;
	}

	@Override
	public void removeLast() { // isEmpty
		this.last = this.last.previous;
		this.last.next = null;
	}

	public DoubleLinkedListNode<T> getLast() {
		return this.last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}
}
