package customClasses;
import java.util.Arrays;

public class MySet {
    private int initialCapacity = 10;
    private String[] elements;
    private int actualSize;

    public MySet() {
        elements = new String[initialCapacity];
        actualSize = 0;
    }

    public void add(String element) {
        if (contains(element)) {
            return;
        }

        if (actualSize == initialCapacity) {
            elements = Arrays.copyOf(elements, initialCapacity * 2);
        }

        int i = actualSize - 1;
        while (i >= 0 && elements[i].compareTo(element) > 0) {
            elements[i + 1] = elements[i];
            i--;
        }
        elements[i + 1] = element;
        actualSize++;
    }

    public boolean contains(String element) {
        int left = 0;
        int right = actualSize - 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            if (elements[middle].compareTo(element) == 0) {
                return true;
            } else if (elements[middle].compareTo(element) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return false;
    }

    public int size() {
        return actualSize;
    }

    public void remove(String element) {
        int left = 0;
        int right = actualSize - 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            if (elements[middle].compareTo(element) == 0) {
                for (int i = middle; i < actualSize - 1; i++) {
                    elements[i] = elements[i + 1];
                }
                actualSize--;
                return;
            } else if (elements[middle].compareTo(element) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        shrinkCapacity();
    }

    public void shrinkCapacity() {
        if (actualSize <= elements.length / 2) {
            int newCapacity = elements.length / 4;
            if (newCapacity < 10) {
                newCapacity = 10;
            }
            String[] newElements = Arrays.copyOf(elements, newCapacity);
            elements = newElements;
        }
    }
}
