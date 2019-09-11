package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

   @Override
   public void sort(Integer[] array, int leftIndex, int rightIndex) {

      if (array != null && leftIndex >= 0 && rightIndex < array.length && rightIndex >= leftIndex && array.length != 0) {

         countingSort(array, leftIndex, rightIndex);

      }

   }

   private void countingSort(Integer[] array, int leftIndex, int rightIndex) {

      int[] contagem = new int[maiorElemento(array) + 1];
      Integer[] arrayAux = new Integer[array.length];

      for (int i = leftIndex; i <= rightIndex; i++) {
         contagem[array[i]]++;
      }

      for (int i = 1; i < contagem.length; i++) {
         contagem[i] += contagem[i - 1];
      }

      for (int i = rightIndex; i >= leftIndex; i--) {
         contagem[array[i]]--;
         arrayAux[contagem[array[i]]] = array[i];
      }

      for (int i = 0; i <= rightIndex; i++) {
         array[i] = arrayAux[i];
      }

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
