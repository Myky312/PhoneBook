package customClasses;
import java.util.Arrays;

public class MySet {
    private String[] elements;
    private int size;

    public MySet() {
        elements = new String[10];
        size = 0;
    }
    
    public void add(String element) {
        if (contains(element)) {
            return;
        }
    
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    
        elements[size++] = element;
    }
    
    public boolean contains(String element) {
        int left = 0;
        int right = size - 1;
    
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
        return size;
    }

    public void remove(String element) {
        int left = 0;
        int right = size - 1;
        
        while (left <= right) {
            int middle = (left + right) / 2;
            if (elements[middle].compareTo(element) == 0) {
                for (int i = middle; i < size - 1; i++) {
                    elements[i] = elements[i + 1];
                }
                size--;
                return;
            } else if (elements[middle].compareTo(element) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
    }
    
    
}
