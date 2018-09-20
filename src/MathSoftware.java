/*
    ESOF 322 Homework 2 part B
    Ethan Fison and Ben Naylor
    Implementation of Strategy pattern class diagram from part A
 */

import java.util.Scanner;

abstract class MathSoftware {
    private SortMethod sMethod;
    private int[] sorted;

    public void mathSort(int[] nums) { // Calls the sorting method for the current sortMethod
        sorted = sMethod.sort(nums);
    }

    public void setSortStrategy(SortMethod s) { // Allows the user to set a new sort method for their program
        sMethod = s;
    }

    public void displayNums() { // Prints out the numbers in the sorted array
        for (int num : sorted) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

class Mathematica extends MathSoftware {
    public Mathematica() {
        setSortStrategy(new InsertionSort());
    }
}

class MTool extends MathSoftware {
    public MTool() {
        setSortStrategy(new MergeSort());
    }
}

class MyMath extends MathSoftware {
    public MyMath() {
        setSortStrategy(new BubbleSort());
    }
}

interface SortMethod {
    int[] sort(int[] nums);
}

class InsertionSort implements SortMethod {
    @Override
    public int[] sort(int[] nums) {
        System.out.println("Insertion Sort");
        int[] toSort = nums.clone(); // Clones the original array so we can reuse it when switching sort systems
        int l = toSort.length;
        for (int i = 1; i < l; i++) {
            int key = toSort[i];
            int j = i - 1;
            while (j >= 0 && toSort[j] > key) { // Shifts every item in the array that is greater than the key forward by one
                toSort[j + 1] = toSort[j];
                --j;
            }
            toSort[j + 1] = key;
        }
        return toSort;
    }
}

class BubbleSort implements SortMethod {
    @Override
    public int[] sort(int[] nums) {
        int[] toSort = nums.clone(); // Clones the original array so we can reuse it when switching sort systems
        int l = toSort.length;
        for (int i = 0; i < l - 1; i++) { // iterates forward from each item in the array, to ignore any previously sorted items
            for (int j = 0; j < l - 1 - i; j++) {
                if (toSort[j] > toSort[j + 1]) { // Flips the positions of the current and next items in the array if this condition is met
                    int temp = toSort[j];
                    toSort[j] = toSort[j + 1];
                    toSort[j + 1] = temp;
                }
            }
        }
        return toSort;
    }
}

class MergeSort implements SortMethod {
    @Override
    public int[] sort(int[] nums) {
        int[] toSort = nums.clone(); // Clones the original array so we can reuse it when switching sort systems
        beginSort(toSort, 0, toSort.length - 1); // Initial call to our recursive mergesort method
        return toSort;
    }

    public void beginSort(int[] arr, int lBound, int rBound) {
        if (lBound < rBound) {
            int middle = (lBound + rBound) / 2; // calculates the middle bound of the array fed into the function for the recursive calls

            beginSort(arr, lBound, middle);
            beginSort(arr, middle + 1, rBound);

            merge(arr, lBound, middle, rBound);
        }
    }

    void merge(int arr[], int lBound, int middle, int rBound) {
        int lHalf = middle - lBound + 1;
        int rHalf = rBound - middle;
        int L[] = new int[lHalf];
        int R[] = new int[rHalf];

        for (int i = 0; i < lHalf; ++i) // Creates temp arrays for the halves we will work with
            L[i] = arr[lBound + i];
        for (int j = 0; j < rHalf; ++j)
            R[j] = arr[middle + 1 + j];

        int i = 0;
        int j = 0;

        int sub = lBound; // Sets the initial value of the new subarray
        while (i < lHalf && j < rHalf) {
            if (L[i] <= R[j]) {  // This sorts the subarray with values from our right and left arrays
                arr[sub] = L[i];
                i++;
            } else {
                arr[sub] = R[j];
                j++;
            }
            sub++;
        }
        while (i < lHalf) { // If any items remain in L[], they get copied back to the main array
            arr[sub] = L[i];
            i++;
            sub++;
        }
        while (j < rHalf) { // If any items remain in R[], they get copied back to the main array
            arr[sub] = R[j];
            j++;
            sub++;
        }
    }
}

class Driver {
    public void start() {
        String userInput;
        do {
            MathSoftware m = null;
            int[] nums = {43, 34, 48, 99, 84, 64, 60, 88, 35, 12, 54, 44, 92, 22, 3, 95, 75, 78, 91, 87, 9, 26, 97, 24, 51,
                    59, 33, 70, 5, 30, 76, 8, 13, 42, 27, 98, 93, 21, 46, 6, 25, 4, 11, 81, 71, 17, 79, 14, 69, 28}; // Random integer set generated at https://www.random.org/integer-sets/
            Scanner s = new Scanner(System.in);
            System.out.println("Choose your math software.\nEnter 1 for Mathematica\nEnter 2 for MTool\nEnter 3 for MyMath");
            while (m == null) { // Allows the user to select their math tool
                String tool = s.nextLine();
                switch (tool) {
                    case "1":
                        m = new Mathematica();
                        break;
                    case "2":
                        m = new MTool();
                        break;
                    case "3":
                        m = new MyMath();
                        break;
                    default:
                        System.out.println("That is not an option, please try again");
                        break;
                }
            }
            m.mathSort(nums);
            m.displayNums();
            do {
                System.out.println("Which sorting method would you like to switch to?\nEnter 1 for Insertion Sort\n" +
                        "Enter 2 for Merge Sort\nEnter 3 for Bubble Sort");
                boolean sortChosen = false;
                while (!sortChosen) { // Allows the user to select their new sorting tool
                    String method = s.nextLine();
                    switch (method) {
                        case "1":
                            m.setSortStrategy(new InsertionSort());
                            sortChosen = true;
                            break;
                        case "2":
                            m.setSortStrategy(new MergeSort());
                            sortChosen = true;
                            break;
                        case "3":
                            m.setSortStrategy(new BubbleSort());
                            sortChosen = true;
                            break;
                        default:
                            System.out.println("That is not an option, please try again");
                            break;
                    }
                }
                m.mathSort(nums);
                m.displayNums();
                System.out.println("would you like to use another sort method? Y/n");
                userInput = s.nextLine().toLowerCase();
            }
            while (userInput.equals("y")); // if yes, allows the user to try additional sort methods with the math program they chose earlier
            System.out.println("Would you like to try again with a different math tool? Y/n");
            userInput = s.nextLine().toLowerCase();
        }
        while (userInput.equals("y")); // If yes, allows the user to choose a different math program, and therefore a different default sorting method

    }

    public static void main(String[] args) {
        new Driver().start();
    }
}