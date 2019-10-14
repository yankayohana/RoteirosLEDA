package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	@Override
	public void insert(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> aux = new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<>(),
					new DoubleLinkedListNode<>());
			if (!isEmpty()) {
				aux.setPrevious(getLast());
				getLast().setNext(aux);
				setLast(aux);
			} else {
				setHead(aux);
				setLast(aux);
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> aux = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<>(),
				new DoubleLinkedListNode<>());
		if (element != null) {
			if (isEmpty()) {
				this.head = aux;
				this.last = aux;
			} else {
				DoubleLinkedListNode<T> head = (DoubleLinkedListNode<T>) getHead();
				aux.setNext(head);
				head.setPrevious(aux);
				setHead(aux);
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			this.head = this.head.getNext();
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			getLast().getPrevious().setNext(new DoubleLinkedListNode<>());
			setLast(last.getPrevious());
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return this.last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}
}
