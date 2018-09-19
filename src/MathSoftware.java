/*
    ESOF 322 Homework 2 part B
    Ethan Fison and Ben Naylor
    Implementation of Strategy pattern class diagram from part A
 */

import java.util.Scanner;

abstract class MathSoftware {
    private SortMethod sMethod;
    private int[] sorted;

    public void mathSort(int[] nums) {
        sorted = sMethod.sort(nums);
    }

    public void setSortStrategy(SortMethod s) {
        sMethod = s;
    }

    public void displayNums() {
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
        int[] toSort = nums;
        int l = toSort.length;
        for (int i = 1; i < l; i++) {
            int k = toSort[i];
            int j = i - 1;
            while (j >= 0 && toSort[j] > k) {
                toSort[j + 1] = toSort[j];
                --j;
            }
            toSort[j + 1] = k;
        }
        return toSort;
    }
}

class BubbleSort implements SortMethod {
    @Override
    public int[] sort(int[] nums) {
        int[] toSort = nums;
        int l = toSort.length;
        for (int i = 0; i < l - 1; i++) { // iterates forward from each item in the array, to ignore any previously sorted items
            for (int j = 0; j < l - 1 - i; j++) {
                if (toSort[j] > toSort[j + 1]) {
                    int temp = toSort[j];
                    toSort[j] = toSort[j + 1];
                    toSort[j + 1] = temp;
                }
            }
        }
        return toSort;
    }
}

class MergeSort implements SortMethod { // We totally forgot how to do a MergeSort, so we copied this one directly from geeksforgeeks.org
    @Override
    public int[] sort(int[] nums) {
        int[] hahalol = {1, 0, 1};
        return hahalol;
    }
    //public void merge
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
                int tool = s.nextInt();
                s.nextLine(); // Clears out the \n that nextInt didn't read
                switch (tool) {
                    case 1:
                        m = new Mathematica();
                        break;
                    case 2:
                        m = new MTool();
                        break;
                    case 3:
                        m = new MyMath();
                        break;
                    default:
                        System.out.println("That is not an option, please try entering the number for your math tool again");
                        break;
                }
            }
            m.mathSort(nums);
            m.displayNums();
            do {
                System.out.println("Which sorting method would you like to switch to?\nEnter 1 for Insertion Sort\n" +
                        "Enter 2 for Merge Sort\nEnter 3 for Bubble Sort");
                boolean sortChosen = false;
                while (sortChosen == false) { // Allows the user to select their new sorting tool
                    int method = s.nextInt();
                    s.nextLine(); // Clears out the \n that nextInt didn't read
                    switch (method) {
                        case 1:
                            m.setSortStrategy(new InsertionSort());
                            sortChosen = true;
                            break;
                        case 2:
                            m.setSortStrategy(new MergeSort());
                            sortChosen = true;
                            break;
                        case 3:
                            m.setSortStrategy(new BubbleSort());
                            sortChosen = true;
                            break;
                        default:
                            System.out.println("That is not an option, please try entering the number for your sort method again");
                            break;
                    }
                }
                m.mathSort(nums);
                m.displayNums();
                System.out.println("would you like to use another sort method? Y/n");
                userInput = s.nextLine().toLowerCase();
            } while (userInput.equals("y"));
            System.out.println("Would you like to try again with a different math tool? Y/n");
            userInput = s.nextLine().toLowerCase();
        } while (userInput.equals("y"));
    }

    public static void main(String[] args) {
        new Driver().start();
    }
}