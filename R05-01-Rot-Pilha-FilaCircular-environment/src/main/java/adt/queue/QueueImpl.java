package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int total;
	private int head;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = 0;
		head = 0;
		total = 0;
	}

	@Override
	public T head() {
		if (isEmpty()) {
			return null;
		}
		return array[head];
	}

	@Override
	public boolean isEmpty() {
		return total == 0;
	}

	@Override
	public boolean isFull() {
		return total == array.length;
		
	}

	private void shiftLeft() {
		for (int i = 1; i < tail; i++) {
			array[i - 1] = array[i];
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		}else if(element != null) {
			array[tail] = element;
			tail = (tail + 1) % array.length;
			total++;
		}
		
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		}
		T aux = array[head];
		this.shiftLeft();
		head = (head + 1) % array.length;
		total--;
		return aux;
		
	}

}
