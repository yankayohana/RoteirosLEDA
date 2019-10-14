package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	private int hash(T element, int probe) {
		return ((HashFunctionLinearProbing) this.getHashFunction()).hash(element, probe);
	}

	@Override
	public void insert(T element) {
		if (isFull()) {
			throw new HashtableOverflowException();
		}
		if (element != null) {
			int probe = 0;
			int hash = this.hash(element, probe);
			while (this.table[hash] != null && !this.table[hash].equals(element)
					&& !this.table[hash].equals(this.deletedElement)) {
				probe = probe + 1;
				hash = hash(element, probe);
				this.COLLISIONS = this.COLLISIONS + 1;
			}
			this.table[hash] = element;
			this.elements = this.elements + 1;
		}
	}

	@Override
	public void remove(T element) {
		int probe = 0;
		int hash = this.hash(element, probe);
		if (!isEmpty() && element != null && search(element) != null) {
			while (!this.table[hash].equals(element)) {
				probe = probe + 1;
				hash = hash(element, probe);
			}
			this.table[hash] = this.deletedElement;
			this.elements = this.elements - 1;
		}
	}

	@Override
	public T search(T element) {
		T result = null;
		if (element != null && !isEmpty()) {
			int probe = 0;
			int hash = hash(element, probe);
			while ((this.table[hash] == null || this.table[hash].equals(this.deletedElement) || !this.table[hash].equals(element)) && probe != capacity()) {
				probe = probe + 1;
				hash = hash(element, probe);
			}
			if (this.table[hash].equals(element)) {
				result = (T) this.table[hash];
			}
		}
		return result;
	}

	@Override
	public int indexOf(T element) {
		int result = -1;
		int probe = 0;
		int hash = hash(element, probe);
		if (element != null && search(element) != null) {
			while (!this.table[hash].equals(element)) {
				probe = probe + 1;
				hash = hash(element, probe);
			}
			result = hash;
		}
		return result;
	}
}