package sorting.variationsOfBubblesort;

import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * This algorithm applies two bubblesorts simultaneously. In a same iteration, a
 * bubblesort pushes the greatest elements to the right and another bubblesort
 * pushes the smallest elements to the left. At the end of the first iteration,
 * the smallest element is in the first position (index 0) and the greatest
 * element is the last position (index N-1). The next iteration does the same
 * from index 1 to index N-2. And so on. The execution continues until the array
 * is completely ordered.
 */
public class SimultaneousBubblesort<T extends Comparable<T>> extends AbstractSorting<T> {
	public void sort(T[] array, int leftIndex, int rightIndex) {

		if (array != null && leftIndex >= 0 && rightIndex < array.length && rightIndex >= leftIndex
				&& array.length != 0) {

			boolean isSwapped = true;
			int start = leftIndex;
			int end = rightIndex;

			while (isSwapped) {
				isSwapped = false;

				for (int i = start; i < end; i++) {
					if (array[i].compareTo(array[i + 1]) > 0) {
						Util.swap(array, i, i + 1);
						isSwapped = true;
					}
				}

				if (!isSwapped) {
					break;
				}

				end--;

				isSwapped = false;

				for (int j = end - 1; j >= start; j--) {
					if (array[j].compareTo(array[j + 1]) > 0) {
						Util.swap(array, j, j + 1);
						isSwapped = true;
					}
				}
				start++;
			}
			
			System.out.println(Arrays.toString(array));

		}

	}
}
