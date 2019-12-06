package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		SingleLinkedListNode<T> aux = this.head;
		int contador = 0;
		
		while (!aux.isNIL()) {
			contador++;
			aux = aux.next;
		}
		
		return contador;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> aux = this.head;
		
		while (!aux.isNIL()) {
			if (aux.getData().equals(element)) {
				return aux.getData();
			}
			aux = aux.next;
		}
		return null;
	}

	@Override
	public void insert(T element) {

		SingleLinkedListNode<T> aux = this.lastElement();
		aux.setData(element);
		aux.next = new SingleLinkedListNode<>();
	}

	private SingleLinkedListNode<T> lastElement() {
		SingleLinkedListNode<T> aux = this.head;
		
		while (!aux.isNIL()) {
			aux = aux.next;
		}
		return aux;
	}

	@Override
	public void remove(T element) {
		this.insert(element);
		SingleLinkedListNode<T> aux = this.head;
		
		while (!aux.isNIL()) {
			if (aux.next.getData().equals(element)) {
				aux.next = aux.next.next;
				break;
			}
			
			aux = aux.next;
		}
	}
	
	public void doIt(T element) {
		SingleLinkedListNode<T> auxNext = new SingleLinkedListNode<>();
		SingleLinkedListNode<T> auxPrevious = new SingleLinkedListNode<>();
		SingleLinkedListNode<T> auxCurrent = this.head;
		
		while (auxCurrent != null) {
			auxNext = auxCurrent.next;
			auxCurrent.next = auxPrevious;
			auxPrevious = auxCurrent;
			auxCurrent = auxNext;			
			
		}
		
		auxCurrent = auxPrevious;
	}
	
	
	public void deleteAlternador() {
		SingleLinkedListNode<T> aux = this.head;
		
		while(!aux.isNIL()) {
			aux.next = aux.next.next;
			aux = aux.next;
		}
		
	}

	@Override
	public T[] toArray() {
		
		T[] array = (T[]) new Object[this.size()];
		
		SingleLinkedListNode<T> aux = this.head;
		
		int i = 0;
		while (!aux.isNIL()) {
			array[i] = aux.getData();
			i++;
			aux = aux.next;
		}
		
		return array;
	}

}
