/*
 *  Program #3
 *  Cipher program
 *  CS108-1
 *  February 20, 2020
 *  Rogelio Schevenin Jr.
 */

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Cipher {

    public static final int NUM_LETTERS = 26;
    public static final int ENCODE = 1;
    public static final int DECODE = 2;

    public static void main(String[] args) throws FileNotFoundException {

        // letters
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        //Check args for errors
        if (args.length > 3) {
            System.out.println("More args than necessary.");
            return;
        }
        if (Integer.parseInt(args[2]) == 0) {
            System.out.println("Option 0 is not valid");
            return;
        }

        // Extract input args to variables
        String inputFilename = args[0];
        String key = args[1];
        int action = Integer.parseInt(args[2]);
        //Assigns output file name
        String outputFilename = getOutputFilename(inputFilename, action);
        //Opens file
        Scanner input = openInput(inputFilename);
        //Opens output
        PrintWriter output = openOutput(outputFilename);

        while(input.hasNextLine()) {
            String lineToChange = input.nextLine();
            String newLine = "";

            while (lineToChange.length() > key.length()) {
                key = key.concat(key);
            }

            char newChar = lineToChange.charAt(0);

            if (action == ENCODE) {
                for (int i = 0; i < lineToChange.length(); ++i) {
                    newChar = shiftUpByK(lineToChange.charAt(i), alphabet.indexOf(key.charAt(i)));
                    newLine = newLine + Character.toString(newChar);
                }
            } else if (action == DECODE) {
                for (int i = 0; i < lineToChange.length(); ++i) {
                    newChar = shiftDownByK(lineToChange.charAt(i), alphabet.indexOf(key.charAt(i)));
                    newLine = newLine + Character.toString(newChar);
                }
            } else {
                System.out.println("Action not specified.");
            }

            output.write(newLine.toLowerCase());
        }
        
        // Close streams
        input.close();
        output.close();
    }

    /**
    *  openInput method
    *
    *  @param filename
    *  opens filename.txt
    *  throws FileNotFoundException
    */
    public static Scanner openInput(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner input = new Scanner(file);
        return input;
    }
    /**
     *  printWriter method
     *
     *  @param filename
     *  opens output file, with correct ext.
     *  throws FileNotFoundException
     */
    public static PrintWriter openOutput(String filename) throws FileNotFoundException {
        File file = new File(filename);
        PrintWriter output = new PrintWriter(file);
        return output;
    }
    /**
     * Encode letter by some offset distance
     *
     * @param c  input character
     * @param distance  amount to shift character value
     * @return char  encoded character
     */
    public static char shiftUpByK(char c, int distance) {
        //lowercase letters
        if ('a' <= c && c <= 'z') {
            return (char) ('a' + (c - 'a' + distance) % NUM_LETTERS);
        }
        //uppercase letters
        if ('A' <= c && c <= 'Z') {
            return (char) ('A' + (c - 'A' + distance) % NUM_LETTERS);
        }
        return c; // don't encrypt if not an ic character
    }
    //FIXME: method for decoding
    /**
     * Decode letter by some offset d
     *
     * @param c  input character
     * @param distance  amount to shift character value
     * @return char  decoded character
     */
    public static char shiftDownByK(char c, int distance) {
        //lowercase letters
        if ('a' <= c && c <= 'z') {
            while (distance > 0) {
                if (c == 'a') {
                    distance--;
                    c = 'z';
                } else {
                    distance--;
                    c--;
                }
            }
            return c;
        }
        //uppercase letters
        if ('A' <= c && c <= 'Z') {
            while (distance > 0) {
                if (c == 'A') {
                    distance--;
                    c = 'Z';
                } else {
                    distance--;
                    c--;
                }
            }
            return c;
        }
        return c; // don't encrypt if not an ic character
    }
    /**
     * Changes file extension to ".coded" or ".decoded"
     *
     * @param filename .txt
     * @param action to decode/encode
     * @return String new filename or null if action is illegal
     */
    public static String getOutputFilename(String filename, int action) {
        switch (action) {
            case 1:
                filename = filename.concat(".coded");
                break;
            case 2:
                filename = filename.concat(".decoded");
                break;
            default:
                filename = filename;
                break;
        }
        return filename;
    }
    /**
     * getInfo()
     */
    public String getInfo() {
        return "Program 3, Rogelio Schevenin Jr.";
    }
}