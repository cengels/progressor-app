package com.cengels.progressor;

public abstract class ArrayExtensions {
    /**
     * Checks whether the specified array contains the specified element.
     * @return True if the value was found, otherwise false.
     */
    public static <T> boolean contains(T[] array, T searchElement) {
        for (T element : array) {
            if (element.equals(searchElement)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines the index of the specified item in the array. If the array does not contain the element, returns -1.
     * @return The index of the element in the target array or -1 if it wasn't found.
     */
    public static <T> int indexOf(T[] array, T searchElement) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(searchElement)) {
                return i;
            }
        }

        return -1;
    }
}
