import java.util.Scanner;
import java.util.regex.Pattern;

import customExceptions.CustomException;

public class ConsoleApp {
    static Scanner sc = new Scanner(System.in);
    static PhoneBook phoneBook = new PhoneBook();

    public static void main(String[] args) {
        String numberFormat = null;
        do {
            System.out.print("\033c");
            System.out.println(
                    "Berfore we start please write down format of each phone number that will be writtern to this application");
            System.out.print("Enter a string example(+36 20 52 10 918) : ");
            numberFormat = sc.nextLine();
            System.out.println("You entered: " + numberFormat);
            System.out.println("Please make sure you follow theat format!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        } while (numberFormat.equals("") || numberFormat.length() < 1 || numberFormat.equals(null));
        System.out.print("\033c");
        boolean exit = false;
        while (!exit) {
            System.out.println("Phone Book Application");
            System.out.println("1. Add Subscriber");
            System.out.println("2. Delete Subscriber");
            System.out.println("3. Append Multiple Numbers to existsing Subscriber");
            System.out.println("4. Append Number to existing Subscriber");
            System.out.println("5. Update Numbers of Subscriber");
            System.out.println("6. Search by Name");
            System.out.println("7. Search by Number");
            System.out.println("8. Display All Subscribers");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addSubscriber(numberFormat);
                    break;
                case 2:
                    deleteSubscriber(numberFormat);
                    break;
                case 3:
                    appendNumbers(numberFormat);
                    break;
                case 4:
                    appendNumber(numberFormat);
                    break;
                case 5:
                    updateNumbers(numberFormat);
                    break;
                case 6:
                    searchByName();
                    break;
                case 7:
                    searchByNumber();
                    break;
                case 8:
                    displayAllSubscribers();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
                    break;
            }
        }
        System.out.println("Exiting Phone Book Application. Have a great day!");
    }

    private static void addSubscriber(String numberFormat) {
        System.out.print("Enter subscriber name: ");
        String name = sc.nextLine();
        System.out.print("Enter number of numbers: ");
        int n = sc.nextInt();
        sc.nextLine();
        String[] numbers = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            numbers[i] = sc.nextLine();
        }
        try {
            for (String x : numbers) {
                if (!matchesFormat(numberFormat, x)) {
                    throw new CustomException(" Please enter correct number with format " + numberFormat);
                }
            }
            Subscriber subscriber = new Subscriber(name, numbers);
            phoneBook.add(subscriber);
            System.out.print("\033c");
            System.out.println("Subscriber added successfully!");
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteSubscriber(String numberFormat) {
        System.out.print("Enter subscriber name: ");
        String name = sc.nextLine();
        System.out.print("Enter number of numbers: ");
        int n = sc.nextInt();
        sc.nextLine();
        String[] numbers = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            numbers[i] = sc.nextLine();
        }
        try {
            for (String x : numbers) {
                if (!matchesFormat(numberFormat, x)) {
                    throw new CustomException(" Please enter correct number with format " + numberFormat);
                }
            }
            Subscriber target = new Subscriber(name, numbers);
            phoneBook.delete(target);
            try {
                Thread.sleep(1000);
                System.out.print("\033c");
                System.out.println("Subscriber deleted successfully!");
            } catch (InterruptedException e) {
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void appendNumbers(String numberFormat) {
        System.out.print("Enter subscriber name: ");
        String name = sc.nextLine();
        int index = -1;
        index = phoneBook.searchByNameAndReturnIndex(name);
        if (index != -1) {
            System.out.print("Enter number of numbers to append: ");
            int n = sc.nextInt();
            sc.nextLine();
            String[] numbers = new String[n];
            for (int i = 0; i < n; i++) {
                System.out.print("Enter number " + (i + 1) + "th number to append: ");
                numbers[i] = sc.nextLine();
            }
            try {
                for (String x : numbers) {
                    if (!matchesFormat(numberFormat, x)) {
                        throw new CustomException(" Please enter correct number with format " + numberFormat);
                    }
                }
                for (String x : numbers) {
                    phoneBook.appendNumberIn(index, x);
                }
                System.out.print("\033c");
                System.out.println("Numbers updated successfully!");
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void appendNumber(String numberFormat) {
        System.out.print("Enter subscriber name: ");
        String name = sc.nextLine();
        int index = -1;
        index = phoneBook.searchByNameAndReturnIndex(name);
        if (index != -1) {
            System.out.print("Enter number: ");
            String number = sc.nextLine();
            try {
                if (!matchesFormat(numberFormat,number)) {
                    throw new CustomException(" Please enter correct number with format " + numberFormat);
                }
                phoneBook.appendNumberIn(index, number);
                System.out.print("\033c");
                System.out.println("Number appended successfully!");
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    
    private static void updateNumbers(String numberFormat) {
        System.out.print("Enter subscriber name that you want to update: ");
        String name = sc.nextLine();
        try {
            // int index = phoneBook.searchBySubscriberName(name);
            System.out.print("Enter number of numbers: ");
            int n = sc.nextInt();
            sc.nextLine();
            String[] numbers = new String[n];
            for (int i = 0; i < n; i++) {
                System.out.print("Enter number " + (i + 1) + ": ");
                numbers[i] = sc.nextLine();
            }
            if (name == null || numbers == null || numbers.length == 0) {
                throw new CustomException("Provide a correct name and number");
            }
            for (String x : numbers) {
                if(!matchesFormat(numberFormat,x)){
                    throw new CustomException(" Please enter correct number with format " + numberFormat);
                }
            }
            phoneBook.updateNumbersIn(name, numbers);
            // phoneBook.subscribers[index] = new Subscriber(name, numbers);
            System.out.print("\033c");
            System.out.println("Numbers of " + name + " are updated");

        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void searchByName() {
        System.out.print("Enter subscriber name: ");
        String name = sc.nextLine();

        try {
            String[] nums = phoneBook.searchBySubscriber(name);
            if (nums.equals(null)) {
                System.out.print("\033c");
                System.out.println("There is no person with that name! ");
            } else {
                System.out.print("\033c");
                System.out.println("Name: " + name);
                System.out.println("Numbers: ");
                for (String number : nums) {
                    System.out.println(number);
                }
                System.out.println();
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchByNumber() {
        System.out.print("Enter number: ");
        String number = sc.nextLine();
        try {
            String name = phoneBook.searchByPhoneNumber(number);
            if (name.equals(null)) {
                System.out.print("\033c");
                System.out.println(number + " is not registered");
            } else {
                System.out.print("\033c");
                System.out.println("Number: " + number + " belongs to: " + name);
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayAllSubscribers() {
        System.out.print("\033c");
        System.out.println(phoneBook.toString());
    }

    public static boolean matchesFormat(String phoneNumberFormat, String numberToCheck) {
        StringBuilder regex = new StringBuilder();
        String numForm = cutFirstCharacter(phoneNumberFormat);
        String numChek = cutFirstCharacter(numberToCheck);
        for (char c : numForm.toCharArray()) {
            if (Character.isDigit(c)) {
                regex.append("\\d");
            } else {
                regex.append(c);
            }
        }
        Pattern pattern = Pattern.compile(regex.toString());
        return pattern.matcher(numChek).matches();
    }

    public static String cutFirstCharacter(String input) {
        if (input.length() > 0 && input.charAt(0) == '+') {
            return removeSpecialCharacters(input.substring(1));
        } else {
            return removeSpecialCharacters(input);
        }
    }

    public static String removeSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
}
