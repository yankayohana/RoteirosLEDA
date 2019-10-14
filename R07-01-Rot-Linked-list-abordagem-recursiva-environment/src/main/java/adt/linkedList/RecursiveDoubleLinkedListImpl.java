package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends
		RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}
	
	@Override
	public void insert(T element) {
		if (this.isEmpty()) {
			this.data = element;
		} else if (this.next == null) {
			this.next = new RecursiveDoubleLinkedListImpl<>();
			this.next.data = element;
			((RecursiveDoubleLinkedListImpl<T>) this.next).previous = this;
		} else {
			this.next.insert(element);
		}
	}

	@Override
	public void insertFirst(T element) {
		if (this.isEmpty()) {
			this.data = element;
		}else {
			RecursiveDoubleLinkedListImpl<T> node = new RecursiveDoubleLinkedListImpl<>();
			node.data = this.data;
			node.previous = this;
			node.next = this.next;
			this.data = element;
			this.next = node;
			if (this.next.next != null) {
				((RecursiveDoubleLinkedListImpl<T>) node.next).previous = node;
			}
		}
	}

	@Override
	public void removeFirst() {
		if (this.isEmpty()) {
			return;
		}
		if(this.next == null) {
			this.data = null;
			return;
		}
		this.data = this.next.data;
		this.next = this.next.next;
		if (this.next != null) {
			((RecursiveDoubleLinkedListImpl<T>) this.next).previous = this;
		}
	}

	@Override
	public void removeLast() {
		if (this.isEmpty()) {
			return;
		}
		
		if (this.next == null) {
			this.data = null;
		}
		
		if (this.next.next == null) {
			this.next = null;
		}else {
			((RecursiveDoubleLinkedListImpl<T>) this.next).removeLast();
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}