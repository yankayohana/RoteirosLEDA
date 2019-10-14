package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {
	}

	@Override
	public boolean isEmpty() {
		return this.data == null;
	}

	@Override
	public int size() {
		if (this.isEmpty()) {
			return 0;
		}
		if (this.next == null) {
			return 1;
		}
		return 1 + this.next.size();
	}

	@Override
	public T search(T element) {
		if (this.isEmpty()) {
			return null;
		}
		if (this.data.equals(element)) {
			return this.data;
		}
		if (this.next == null) {
			return null;
		}
		return this.next.search(element);
	}

	@Override
	public void insert(T element) {
		if (this.isEmpty()) {
			this.data = element;
		} else if (this.next == null) {
			this.next = new RecursiveSingleLinkedListImpl<>();
			this.next.data = element;
		} else {
			this.next.insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (this.isEmpty()) {
			return;
		}
		if (this.data.equals(element)) {
			if (this.next == null) {
				this.data = null;
			} else {
				this.data = this.next.data;
				this.next = this.next.next;
			}
		} else {
			this.next.remove(element);

		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Object[this.size()];
		return this.toArray(array, 0);
	}

	private T[] toArray(T[] array, int i) {
		if (this.isEmpty()) {
			return array;
		}
		array[i++] = this.data;
		if (this.next == null) {
			return array;
		}
		return this.next.toArray(array, i);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}