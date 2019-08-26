package sorting.simpleSorting;

import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array != null && leftIndex >= 0 && rightIndex < array.length && rightIndex >= leftIndex
				&& array.length != 0) {

			for (int i = 1; i <= rightIndex; i++) {
				int j = i;
				while (j > 0 && array[j].compareTo(array[j - 1]) < 0) {
					Util.swap(array, j, j - 1);
					j--;
					System.out.println(Arrays.toString(array));
				}
			}
		}

	}
}
