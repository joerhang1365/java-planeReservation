// Name: Joseph Hanger
// Date Created: November 7, 2022
// Date Modified: November 7, 2022
// Description: This program displays an array of seats
// that are either available or taken.

import java.util.Scanner;
import javax.lang.model.util.ElementScanner6;
import java.awt.Point;
import java.net.SocketTimeoutException;

public class Reservations 
{
    private static final int NUM_ROWS = 20;
    private static final int NUM_LEFT_COL = 5;
    private static final int NUM_RIGHT_COL = 5;
    private static final int AISLE_OFFSET = 1;

    private static final char OPEN_SEAT = 'O';
    private static final char TAKEN_SEAT = 'T';
    private static final char AISLE = ' ';

    public static void main(String[] args) 
    {
        Point selectedSeat = new Point();
        Scanner stdin = new Scanner(System.in);
        char[][] seat = new char[NUM_ROWS][NUM_LEFT_COL + NUM_RIGHT_COL +
                AISLE_OFFSET];

        initializeArray(seat);

        do 
        {
            displayMenu(seat, selectedSeat, stdin);

            if (!selectedSeat.equals(new Point(-1, -1)))
                updateArray(seat, selectedSeat);

        } while (!selectedSeat.equals(new Point(-1, -1)));
    }

    /**
     * Takes a two-dimensional array and initializes it to the specified
     * character of AVAILABLE_SEAT. Also adds a space for the aisle.
     * @param array char[][] array
     * @return char[][] array
     */
    public static char[][] initializeArray(char[][] array) 
    {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[i].length; j++)
                if (j == NUM_LEFT_COL)
                    array[i][j] = AISLE;
                else
                    array[i][j] = OPEN_SEAT;
        return array;
    }

    /**
     * Displays the columns, rows, and current seat configuration. Prompts for
     * a coordinate and checks if it is valid using function isValid();
     * @param array char[][] array
     * @param point Point point
     * @param stdin Scanner stdin
     */
    public static void displayMenu(char[][] array, Point point, Scanner stdin) 
    {
        String input = "";
        char rowLetter = 'A';
        char columnLetter = 'A';
        int offset = 0;

        // Print Title
        System.out.println("----Airline Reservations----");
        System.out.println("\nO) Open Seat");
        System.out.println("T) Taken Seat\n");
        System.out.println("Current Seat Configuration\n");

        // Print Column Letters
        System.out.print("  ");
        for (int i = 0; i < array[0].length; i++)
            if (i == NUM_LEFT_COL) 
            {
                System.out.print(" ");
                offset = AISLE_OFFSET;
            } 
            else
                System.out.print((char) (columnLetter + i - offset));
        System.out.println();

        // Current seat configuration
        for (char[] c : array) 
        {
            // Print Row letters
            System.out.print(rowLetter + " ");
            rowLetter = (char) (rowLetter + 1);

            // Seat Grid
            for (char v : c)
                System.out.print(v);
            System.out.println();
        }

        // Prompt and Store Selection
        System.out.println("\nSelect coordinate to reserve." + 
                           "\nEx) CD: C=x D=y" +
                           "\n@@ to quit...");
        do 
        {
            System.out.print("Coordinate: ");
            input = stdin.nextLine().toUpperCase();
        } 
        while (!isValid(array, input));

        // Account for aisle
        if (input.charAt(0) - 65 > NUM_LEFT_COL - AISLE_OFFSET)
            point.x = input.charAt(0) - 65 + AISLE_OFFSET;
        else
            point.x = input.charAt(0) - 65;

        point.y = input.charAt(1) - 65;

        System.out.println();
    }

    /**
     * Checks whether the input entered is a valid input.
     * @param array char[][] array
     * @param s String s, input
     * @return boolean
     */
    public static boolean isValid(char[][] array, String s) 
    {
        boolean result = true;
        int offset = 0;
        if(s.equals("@@"))
            return true;
        else if (s.length() != 2)
            result = false;
        else if (s.charAt(0) - 65 > array[0].length - 2|| s.charAt(0) - 65 < 0)
           result = false;
        else if (s.charAt(1) - 65 > array.length - 1|| s.charAt(1) - 65 < 0)
           result = false;

        if(!result)
            System.out.println("ERROR: Invalid input...");

        if(result)
        {
            if(s.charAt(0) - 65 > NUM_LEFT_COL - 1)
                offset = AISLE_OFFSET;
            else
                offset = 0;

            if(array[s.charAt(1) - 65][s.charAt(0) - 65 + offset] == TAKEN_SEAT)
            {
                result = false;
                System.out.println("Error: Seat already taken...");
            }
        }
                
        return result;
    }

    /**
     * Updates the arrary's character at Point provided
     * @param array char[][] array
     * @param p Point p
     */
    public static void updateArray(char[][] array, Point point)
    { 
        array[point.y][point.x] = TAKEN_SEAT;
    }
}