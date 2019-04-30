package duplicates;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author Brandon Skar
 * @version 1.0
 * This class runs different methods of detecting duplicate elements and tests run times
 * of each method. We will graph the data and determine the bigO of each method.
 */
public class CreateDuplicates
{
    private static final int ARRAY_SIZE = 20;
    private static final int NANOS_IN_SECONDS = 1000000000;
    private static final int NUMBERS_IN_ARRAY = 10000;
    private static final int ITERATIONS_FOR_AVERAGE = 100;

    /**
     * Creates arrays of random, duplicate numbers and gets times of different methods
     * while searching for duplicates.
     * @param args Main method
     */
    public static void main(String[] args)
    {
        Random rand = new Random();
        ArrayList<int[]> randomArrays = new ArrayList<>();

        //create 20 arrays from 10,000 to 200,000 random numbers, guaranteeing duplicates
        for(int i = 0, j = NUMBERS_IN_ARRAY; i < ARRAY_SIZE; i++, j += NUMBERS_IN_ARRAY) {
            int[] bigNums = new int[j];
            for(int k = 0; k < bigNums.length; k++) {
                int randomNum = rand.nextInt((j / 2) + 1);
                bigNums[k] = randomNum;
            }
            randomArrays.add(bigNums);
        }

        runTestA(randomArrays);

        runTestB(randomArrays);

        runTestC(randomArrays);

        runTestD(randomArrays);

        runTestE(randomArrays);
    }

    private static void runTestA(ArrayList<int[]> arrays)
    {
        try {
            PrintWriter writer = new PrintWriter("src/data/results_A.test", StandardCharsets.UTF_8);
            for(int[] arr : arrays) {
                String data;
                double secondsElapsed = 0;
                for(int i = 0; i < ITERATIONS_FOR_AVERAGE; i++) {
                    LocalTime before = LocalTime.now();
                    int num = findDuplicatesA(arr);

                    double nanosElapsed = Math.abs(ChronoUnit.NANOS.between(LocalTime.now(), before));
                    secondsElapsed += nanosElapsed / NANOS_IN_SECONDS;
                }

                data = arr.length + "," + (secondsElapsed / ITERATIONS_FOR_AVERAGE);
                writer.println(data);
            }
            writer.close();
        } catch(IOException e) {
            System.err.println("ERROR: " + e);
        }
    }

    private static void runTestB(ArrayList<int[]> arrays)
    {
        try {
            PrintWriter writer = new PrintWriter("src/data/results_B.test", StandardCharsets.UTF_8);
            for(int[] arr : arrays) {
                String data;
                LocalTime before = LocalTime.now();
                int num = findDuplicatesB(arr);

                double nanosElapsed = Math.abs(ChronoUnit.NANOS.between(LocalTime.now(), before));
                double secondsElapsed = nanosElapsed / NANOS_IN_SECONDS;

                data = arr.length + "," + secondsElapsed;
                writer.println(data);
            }
            writer.close();
        } catch(IOException e) {
            System.err.println("ERROR: " + e);
        }
    }

    private static void runTestC(ArrayList<int[]> arrays)
    {
        try {
            PrintWriter writer = new PrintWriter("src/data/results_C.test", StandardCharsets.UTF_8);
            for(int[] arr : arrays) {
                String data;
                double secondsElapsed = 0;
                for(int i = 0; i < ITERATIONS_FOR_AVERAGE; i++) {
                    LocalTime before = LocalTime.now();
                    int num = findDuplicatesC(arr);

                    double nanosElapsed = Math.abs(ChronoUnit.NANOS.between(LocalTime.now(), before));
                    secondsElapsed += nanosElapsed / NANOS_IN_SECONDS;
                }

                data = arr.length + "," + (secondsElapsed / ITERATIONS_FOR_AVERAGE);
                writer.println(data);
            }
            writer.close();
        } catch(IOException e) {
            System.err.println("ERROR: " + e);
        }
    }

    private static void runTestD(ArrayList<int[]> arrays)
    {
        try {
            PrintWriter writer = new PrintWriter("src/data/results_D.test", StandardCharsets.UTF_8);
            for(int[] arr : arrays) {
                String data;
                double secondsElapsed = 0;
                for(int i = 0; i < ITERATIONS_FOR_AVERAGE; i++) {
                    LocalTime before = LocalTime.now();
                    int num = findDuplicatesD(arr);

                    double nanosElapsed = Math.abs(ChronoUnit.NANOS.between(LocalTime.now(), before));
                    secondsElapsed += nanosElapsed / NANOS_IN_SECONDS;
                }

                data = arr.length + "," + (secondsElapsed / ITERATIONS_FOR_AVERAGE);
                writer.println(data);
            }

            writer.close();
        } catch(IOException e) {
            System.err.println("ERROR: " + e);
        }
    }

    private static void runTestE(ArrayList<int[]> arrays)
    {
        try {
            PrintWriter writer = new PrintWriter("src/data/results_E.test", StandardCharsets.UTF_8);
            for(int[] arr : arrays) {
                String data;
                LocalTime before = LocalTime.now();
                int num = findDuplicatesE(arr);

                double nanosElapsed = Math.abs(ChronoUnit.NANOS.between(LocalTime.now(), before));
                double secondsElapsed = nanosElapsed / NANOS_IN_SECONDS;

                data = arr.length + "," + secondsElapsed;
                writer.println(data);
            }
            writer.close();
        } catch(IOException e) {
            System.err.println("ERROR: " + e);
        }
    }

    //sort the array and compare each adjacent element to see if they are equal
    private static int findDuplicatesA(int[] arr)
    {
        Arrays.sort(arr);
        int duplicates = 0;
        for(int i = 0; i < (arr.length - 1); i++) {
            if(arr[i] == arr[i + 1]) {
                duplicates++;
            }
        }
        return duplicates;
    }

    //add each element to an arraylist after checking if it contains the element
    private static int findDuplicatesB(int[] arr)
    {
        int duplicates = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for(int el : arr) {
            if(!list.contains(el)) {
                list.add(el);
            }
            else {
                duplicates++;
            }
        }
        return duplicates;
    }

    //add elements to a TreeSet, sets do not allow duplicates
    private static int findDuplicatesC(int[] arr)
    {
        int duplicates = 0;
        TreeSet<Integer> tree = new TreeSet<>();
        for(int el : arr) {
            if(!tree.add(el)) {
                duplicates++;
            }
        }
        return duplicates;
    }

    //add elements to a HashSet, Sets do not allow duplicates
    private static int findDuplicatesD(int[] arr)
    {
        int duplicates = 0;
        HashSet<Integer> set = new HashSet<>();
        for(int el : arr) {
            if(!set.add(el)) {
                duplicates++;
            }
        }
        return duplicates;
    }

    //compare elements from beginning to end, checking all elements above the selected element
    private static int findDuplicatesE(int[] arr)
    {
        int duplicates = 0;
        for(int i = 0; i < arr.length; i++) {
            int num = arr[i];
            for(int j = i + 1; j < arr.length; j++) {
                if(num == arr[j]) {
                    duplicates++;
                    break;
                }
            }
        }
        return duplicates;
    }
}
