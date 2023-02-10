import java.util.Arrays;

public class Subscriber {
    private String name;
    private int initialCapacity = 10;
    private int actualSize = 0;
    private String[] numbers = new String[initialCapacity];

    //I need this constructor for add and delete methods
    public Subscriber(String name, String[] numbers){
        setName(name);
        for(String n:   numbers){   
            setNumber(n);
        }
    }

    //basic constructor
    public Subscriber(String name, String number) {
        setName(name);
        setNumber(number);
    }

    //sets name
    public void setName(String name) {
        this.name = name;
    }
    
    //returns name
    public String getName() {
        return name;
    }
    
    //returns numbers
    public String[] getNumbers() {
        return Arrays.copyOf(numbers, actualSize);
    }

    //Keeps numbers sorted when adding to array
    //if there is not enough space it increases the size of it by 2
    //sets number if it doesn't already exist
    public void setNumber(String number) {
        boolean existsThisNum = exists(numbers, number, actualSize);
        if(existsThisNum){
            System.out.println("This number already registered to this person! ");
            return;
        }
        else{
            if (actualSize + 1 >= initialCapacity) {
                numbers = increaseCapacity();
            }
            int i = actualSize - 1;
            while (i >= 0 && numbers[i].compareTo(number) > 0) {
                numbers[i + 1] = numbers[i];
                i--;
            }
            numbers[i + 1] = number;
            actualSize++;
        }
    }

    //Keeps numbers sorted when deleting from array
    //if there is more than half of space it decreases the size of it by 4
    //sets number if it doesn't already exist
    public void removeNumber(String number) {
        int index = -1;
        for (int i = 0; i < actualSize; i++) {
            if (numbers[i].equals(number)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < actualSize - 1; i++) {
                numbers[i] = numbers[i + 1];
            }
            actualSize--;
            shrinkCapacity();
        }
    }

    //increase  the size of the array by 2
    public String[] increaseCapacity() {
        int newCapacity = initialCapacity * 2;
        String[] newArr = new String[newCapacity];
        for (int i = 0; i < actualSize; i++) {
            newArr[i] = numbers[i];
        }
        return newArr;
    }

    //decreases the size of array but if it is less then 10 it sets it for 10
    public void shrinkCapacity() {
        if (actualSize <= (initialCapacity / 2)) {
            int newCapacity = initialCapacity / 4;
            if (newCapacity < 10) {
                newCapacity = 10;
            }
            String[] newArr = new String[newCapacity];
            for (int i = 0; i < actualSize; i++) {
                newArr[i] = numbers[i];
            }
            numbers = newArr;
            initialCapacity = newCapacity;
        }
    }

    //It seems to be wrong having two almost same funcions but I needed both of them :)
    //This one I need when adding number to this object
    public boolean exists(String[] numbers, String number, int high) {
        int low = 0;
        high--;
        while (low <= high) {
            int mid = (low + high) / 2;
            int result = numbers[mid].compareTo(number);
    
            if (result == 0) {
                return true;
            } else if (result < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    //they are similar almost but this one I use for search by Phone Number function to make that function faster
    //it goes with log n speed 
    public boolean containsNumber(String number) {
        int low = 0;
        int high = actualSize - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison = numbers[mid].compareTo(number);
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }    

    //basic equals function that came to my mind
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Subscriber person = (Subscriber) other;
        if (!name.equals(person.name)) return false;
        return Arrays.equals(numbers, person.numbers);
    }
    
    //easiest to String function that came to my mind
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Subscriber { ")
                .append("Name='").append(name).append('\'')
                .append(", Phone numbers= ").append(Arrays.toString(getNumbers()))
                .append(" }");
        return sb.toString();
    }
}
