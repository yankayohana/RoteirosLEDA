package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	
	@Override
	public void insert(int key, T newValue, int height) {
		SkipListNode<T> aux = this.search(key);
		if (aux != null) {
			aux.setValue(newValue);
		} else {
			SkipListNode<T> insert = new SkipListNode<T>(key, height, newValue);
			for (int i = height - 1; i >= 0; i--) {
				SkipListNode<T> root = this.root;
				while (root.getForward()[i].getKey() < key) {
					root = root.getForward()[i];
				}
				SkipListNode<T> aux2 = root.getForward()[i];
				root.getForward()[i] = insert;
				insert.getForward()[i] = aux2;
			}
	}
	}

	@Override
	public void remove(int key) {
		if (this.search(key) != null) {
			SkipListNode<T> aux = this.root;
			int i = this.maxHeight - 1;

			while (i >= 0) {
				if (aux.getForward()[i].getKey() < key) {
					aux = aux.getForward()[i];
				} else if (aux.getForward()[i].getKey() == key) {
					aux.getForward()[i] = aux.getForward()[i].getForward()[i];
				} else {
					i--;
				}
			}
		}
	}

	@Override
	public int height() {
		int height = 0;
		while (height < this.maxHeight && this.root.getForward()[height].getKey() != Integer.MAX_VALUE) {
			height += 1;
		}
		return height;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> result = null;
		SkipListNode<T> aux = this.root;
		int i = this.maxHeight - 1;

		while (i >= 0 && result == null) {
			if (aux.getForward()[i].getKey() < key) {
				aux = aux.getForward()[i];
			} else if (aux.getForward()[i].getKey() == key) {
				result = aux.getForward()[i];
			} else {
				i -= 1;
			}
		}
		return result;
	}

	@Override
	public int size() {
		SkipListNode<T> aux = this.root.getForward()[0];
		int size = 0;
		while (aux.getKey() != Integer.MAX_VALUE) {
			size += 1;
			aux = aux.getForward()[0];
		}
		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[this.size() + 2];
		SkipListNode<T> aux = this.root;
		int i = 0;
		while (i <= this.size() + 1) {
			array[i] = aux;
			aux = aux.getForward()[0];
			i++;
		}
		return array;
	
	}

}