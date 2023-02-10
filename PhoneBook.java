import java.util.Arrays;

import customClasses.MySet;
import customExceptions.CustomException;

public class PhoneBook {
    private int initialCapacity = 10;
    private int actualSize = 0;
    private Subscriber[] subscribers = new Subscriber[initialCapacity];
    private boolean isEmpty = true;

    private MySet setOfNumbers = new MySet();
    private MySet setOfNames = new MySet();
    
    // it adds subsciber to a right place by name so I don't have to sort entire array again
    // it helps me to find Subscriber faster using binary search
    // if capacity is not enough it will increase it by ^ 2
    //Adds new subscriber if (Name and Numbers are correct by format)
    public void add(Subscriber newSub) throws CustomException {
        if (newSub.getName() == null || newSub.getNumbers() == null) {
            throw new CustomException("Provide a correct name and number");
        }
        for (Subscriber x : subscribers) {
            if (x != null) { // Check if x is not null
                String[] nums = x.getNumbers();
                setOfNames.add(x.getName());
                for (String y : nums) {
                    setOfNumbers.add(y);
                }
            }
        }
        boolean numbersDontExist = true;
        String[] newNumbers = newSub.getNumbers();
        String newName = newSub.getName();

        if (isEmpty) {
            subscribers[0] = newSub;
            actualSize++;
            isEmpty = false;
            setOfNames.add(newName);
            for (String x : newNumbers) {
                setOfNumbers.add(x);
            }
        } else {

            // Check if any of the numbers already exist in the subscriber list
            for (String x : newNumbers) {
                if (setOfNumbers.contains(x)) {
                    numbersDontExist = false;
                    throw new CustomException("Number/s of the new subscriber are already registered");
                }
            }

            if (numbersDontExist) {

                if (setOfNames.contains(newName)) {
                    // add Numbers to existing subscriber
                    throw new CustomException(
                            "Person with this name already exists would you like to update thier numbers? ");
                } else {
                    // Check if there's enough room in the array to add a new subscriber
                    if (actualSize + 1 >= initialCapacity) {
                        subscribers = increaseCapacity();
                    }
                    // Insert the new subscriber in the correct place in the list (sorted by name)
                    int i = actualSize - 1;
                    while (i >= 0 && newName.compareTo(subscribers[i].getName()) < 0) {
                        subscribers[i + 1] = subscribers[i];
                        i--;
                    }
                    subscribers[i + 1] = newSub;
                    actualSize++;
                }
            }
        }
    }

    // it deletes subscriber and keeps them in sorted order by their name so I don't have to sort entire array again
    // it helps me to find Subscriber faster using binary search
    // if half of the subscribers array is empty then it will free 1/4 of it from the back
    // aka make it more memory efficient
    //Deletes subscriber if (Name and Numbers are correct by format)
    public void delete(Subscriber target) throws CustomException {
        if (target.getName() == null || target.getNumbers() == null) {
            throw new CustomException("Provide a correct name and number");
        }

        boolean userExists = false;
        boolean numbersExist = false;
        int index = -1;

        for (int i = 0; i < actualSize; i++) {
            if (subscribers[i].getName().equals(target.getName())) {
                userExists = true;
                index = i;
                break;
            }
        }

        if (userExists) {
            for (String x : target.getNumbers()) {
                if (Arrays.asList(subscribers[index].getNumbers()).contains(x)) {
                    numbersExist = true;
                    setOfNumbers.remove(x);
                } else {
                    numbersExist = false;
                    break;
                }
            }

            if (numbersExist) {

                int j = index;
                while (j < actualSize - 1) {
                    subscribers[j] = subscribers[j + 1];
                    j++;
                }
                subscribers[j] = null;
                actualSize--;
                subscribers = shrinkCapacity();
                setOfNames.remove(target.getName());
            } else {
                throw new CustomException("Numbers does not exist");
            }
        } else {
            throw new CustomException("User does not exist");
        }
    }

    //Appends numbers to existing subscriber
    public void appendNumberIn(int index, String newNumber) throws CustomException {
        if (newNumber == null) {
            throw new CustomException("Provide a correct name and number");
        }
        if(actualSize > 0 && index < actualSize){
            subscribers[index].setNumber(newNumber);
        } else {
            throw new CustomException("No subscriber with that name!");
        }
    }

    //Clears numbers and then adds them if user exists
    public void updateNumbersIn(String name, String[] numbers) throws CustomException {
        int index = searchByNameAndReturnIndex(name);
        for (String x : subscribers[index].getNumbers()) {
            subscribers[index].removeNumber(x);
        }
        for (String x : numbers) {
            subscribers[index].setNumber(x);
        }
    }

    //increases capacity of Subscriber array by *2
    private Subscriber[] increaseCapacity() {
        int newCapacity = initialCapacity * 2;
        Subscriber[] newArr = new Subscriber[newCapacity];
        for (int i = 0; i < actualSize; i++) {
            newArr[i] = subscribers[i];
        }
        return newArr;
    }

    //decreases capacity of Subscriber array by 1/4 if half is empty
    private Subscriber[] shrinkCapacity() {
        if (actualSize <= (initialCapacity / 2)) {
            int newCapacity = initialCapacity / 4;
            if (newCapacity < 10) {
                newCapacity = 10;
            }
            Subscriber[] newSubscriber = new Subscriber[newCapacity];
            for (int i = 0; i < actualSize; i++) {
                newSubscriber[i] = subscribers[i];
            }
            initialCapacity = newCapacity;
            return newSubscriber;
        }else{
            return subscribers;
        }
    }

    //(N log n) tima complexity If subscriber exists then it will print name of subscriber
    //(N lon n) because containsNumber is implemented by bynary search
    public String searchByPhoneNumber(String phoneNumber) throws CustomException {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.containsNumber(phoneNumber)) {
                return "Subscriber found: " + subscriber.getName();
            }
        }
        throw new CustomException("There is no subscriber with the given phone number");
    }

    //binary search log N time complexity Subscriber array is sorted by their Names
    public String[] searchBySubscriber(String name) throws CustomException {
        int left = 0;
        int right = actualSize - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (subscribers[middle] == null) {
                right = middle - 1;
            } else if (subscribers[middle].getName().equals(name)) {
                String[] numbers = subscribers[middle].getNumbers();
                return Arrays.copyOf(numbers, numbers.length);
            } else if (subscribers[middle].getName().compareTo(name) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        throw new CustomException("There is no subscriber with given name! ");
    }

    //Binary search again. This function return index of subscriber with given name if that subscriber exists
    public int searchByNameAndReturnIndex(String name) {
        int left = 0;
        int right = actualSize - 1;
    
        while (left <= right) {
            int middle = (left + right) / 2;
            int comparison = subscribers[middle].getName().compareTo(name);
            if (comparison == 0) {
                return middle;
            } else if (comparison < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }
    
    //To String method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < actualSize; i++) {
            sb.append(subscribers[i].toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}