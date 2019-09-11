package problems;

/**
 * Calcula o floor e ceil de um numero em um array usando a estrategia de busca
 * binaria.
 * 
 * Restricoes: 
 * - Algoritmo in-place (nao pode usar memoria extra a nao ser variaveis locais) 
 * - O tempo de seu algoritmo deve ser O(log n).
 * 
 * @author Adalberto
 *
 */
public class FloorCeilBinarySearch implements FloorCeil {

	@Override
	public Integer floor(Integer[] array, Integer x) {
		return floor(array, x, 0, array.length - 1);
	}

	private Integer floor(Integer[] array, Integer x, int b, int t) {
		int mid = (t + b) / 2;

		if (b > t) {
			if (t < 0)
				return null;
			return array[t];
		} else if (array[mid] == x)
			return x;
		else if (array[mid] > x)
			return floor(array, x, b, mid - 1);
		else
			return floor(array, x, mid + 1, t);
	}

	@Override
	public Integer ceil(Integer[] array, Integer x) {
		return ceil(array, x, 0, array.length - 1);
	}

	private Integer ceil(Integer[] array, Integer x, int b, int t) {
		int mid = (t + b) / 2;

		if (b > t) {
			if (b >= array.length)
				return null;
			return array[b];
		} else if (array[mid] == x)
			return x;
		else if (array[mid] > x)
			return ceil(array, x, b, mid - 1);
		else
			return ceil(array, x, mid + 1, t);
	}
}