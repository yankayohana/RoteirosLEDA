package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		int size = 0;
		SingleLinkedListNode<T> aux = getHead();
		while (aux.getData() != null) {
			size += 1;
			aux = aux.getNext();
		}
		return size;
	}

	@Override
	public T search(T element) {
		T result = null;
		SingleLinkedListNode<T> aux = this.head;
		while (aux.getNext().getData() != null && aux.getData() != element) {
			aux = aux.getNext();
		}
		if (aux.getData().equals(element)) {
			result = aux.getData();
		}
		return result;
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> nil = new SingleLinkedListNode();
		if (element != null) {
			if (isEmpty()) {
				setHead(new SingleLinkedListNode<T>(element, nil));
			} else {
				lastElement().setNext(new SingleLinkedListNode<T>(element, nil));
			}
		}
	}

	private SingleLinkedListNode<T> lastElement() {
		SingleLinkedListNode<T> aux = getHead();
		while (aux.getNext().getData() != null) {
			aux = aux.getNext();
		}
		return aux;
	}

	@Override
	public void remove(T element) {
		if (element != null && search(element) != null) {
			if (element.equals(this.head.getData())) {
				this.head = this.head.getNext();
			} else {
				SingleLinkedListNode<T> aux = this.head;
				while (aux.getNext().getData() != null && aux.getNext().getData() != element) {
					aux = aux.getNext();
				}
				aux.setNext(aux.getNext().getNext());
			}

		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Object[size()];
		SingleLinkedListNode<T> aux = getHead();
		int index = 0;
		while (aux.getData() != null) {
			array[index] = aux.getData();
			aux = aux.getNext();
			index++;
		}
		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
