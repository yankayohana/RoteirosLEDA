package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {

		if (array != null && leftIndex >= 0 && rightIndex < array.length && rightIndex >= leftIndex
				&& array.length != 0) {

			countingSortExtended(array, leftIndex, rightIndex);

		}

	}

	private void countingSortExtended(Integer[] array, int leftIndex, int rightIndex) {
		
		int maior = maiorElemento(array);
		int menor = menorElemento(array);
		int tamanho = maior - menor + 1;
		
		int[] contagem = new int[tamanho];
		Integer[] arrayAux = new Integer[array.length];
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			contagem[array[i-menor]]++;
		}

		for (int i = 1; i < contagem.length; i++) {
			contagem[i] += contagem[i - 1];
		}

		for (int i = rightIndex; i >= leftIndex; i--) {
			contagem[array[i-menor]]--;
			arrayAux[contagem[array[i-menor]]] = array[i];
		}

		for (int i = 0; i <= rightIndex; i++) {
			array[i] = arrayAux[i];
		}


	}

	private int menorElemento(Integer[] array) {
		
		int menor = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] < menor) {
				menor = array[i];
			}
		}
		return menor;
	}

	private int maiorElemento(Integer[] array) {
		
		int maior = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > maior) {
				maior = array[i];
			}
		}
		return maior;
	}

}
