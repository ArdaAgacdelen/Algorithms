public class Sort {

    private static <T extends Comparable<T>> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void combSort(Long[] array) {
        int gap = array.length;
        float shrink = 1.3f;
        boolean sorted = false;
        while (!sorted) {
            gap = Math.max(1, (int) Math.floor(gap / shrink));
            sorted = (gap == 1);
            for (int i = 0; i < array.length - gap; i++) {
                if (array[i] > array[i + gap]) {
                    swap(array, i, i + gap);
                    sorted = false;
                }
            }
        }
    }

    public static void insertionSort(Long[] array) {
        Long key;
        int i;

        for (int j = 1; j < array.length; j++) {
            key = array[j];
            i = j - 1;

            while (i >= 0 && array[i] > key) {
                array[i + 1] = array[i];
                i = i - 1;
            }
            array[i + 1] = key;
        }
    }

    public static void shakerSort(Long[] array) {
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
            swapped = false;
            for (int i = array.length - 2; i >= 0; i--) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
            }
        }
    }

    public static void shellSort(Long[] array) {
        int n = array.length;
        int gap = n / 2;
        int j;
        Long key;

        while (gap > 0) {

            for (int i = gap; i < n; i++) {
                key = array[i];
                j = i;

                while (j >= gap && array[j - gap] > key) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = key;
            }
            gap /= 2;
        }
    }

    public static Long[] radixSort(Long[] array, int d) {
        for (int pos = 1; pos <= d; pos++) {
            array = countingSort(array, pos);
        }
        return array;
    }

    private static Long[] countingSort(Long[] array, int pos) {
        int size = array.length;
        long digitNumber = (long) Math.pow(10, pos - 1);
        int[] count = new int[10];
        Long[] output = new Long[size];

        for (int i = 0; i < size; i++) {
            int digit = getDigit(array[i], digitNumber);
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = size - 1; i >= 0; i--) {
            int digit = getDigit(array[i], digitNumber);
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }
        return output;
    }

    private static int getDigit(Long number, long digitNumber) {
        return (int) ((number / digitNumber) % 10);
    }
}
