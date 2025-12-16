import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

public class WaveSimulation {
    static final int SIZE = 30;   // How many bars in the wave, fewer bars → shorter line

    // The heights of each bar
    static double[] height = new double[SIZE];

    //How much of a bar's height it gives to neighbours each step
    //Bigger number means faster spreading and flattening
    static double TRANSFER_FRACTION = 0.6;

    //This makes all the bars flat (height = 0)
    static void resetWave() {
        for (int i = 0; i < SIZE; i++) {
            height[i] = 0.0;
        }
    }

    static void saveToFile(String filename){
     try {
        PrintWriter out = new PrintWriter(filename); //open a text file with the given name, willc reate the file if it doesnt exist

        for(int i = 0; i < SIZE; i++){
            out.print(height[i] + ", ");
        }

        out.close(); //saves everything
        System.out.println("Saved to " + filename);

        } catch(IOException  e){
            System.out.println("Error writing file."); //to catch errors before the program fails
        }
        //now this will create a file that contains all the current bar heights
    }

    static void loadFromFile(String filename) {
        try {
        java.util.Scanner in = new java.util.Scanner(new java.io.File(filename)); //opens file for reading

        for(int i = 0; i < SIZE; i++){
            if(in.hasNextDouble()){ //loop reading doubles, keeps the numbers and fills the hight[]
                height[i] = in.nextDouble();
            } else {
                height[i] = 0.0;
            }
        }

        in.close(); //closes and saves file
        System.out.println("Loaded from " + filename);

        } catch(Exception e){
            System.out.println("Error reading file.");
        }
    }

    //This directly sets the height of one bar (used when you drag)
    static void setHeight(int index, double value) {
        if (index < 0 || index >= SIZE) {
            return;
        }
        if (value < 0) {
            value = 0;  //makes it so it does not go below baseline
        }
        height[index] = value;
    }

    // One "step" of the wave: each bar shares some height with neighbours
    static void stepWave() {
        double[] newHeight = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            double current = height[i];

            // How much this bar gives away
            double give = current * TRANSFER_FRACTION;
            double remain = current - give;

            // This bar keeps some
            newHeight[i] += remain;

            // Neighbours share the given amount
            double share = give / 2.0;

            if (i > 0) {
                newHeight[i - 1] += share;
            }

            if (i < SIZE - 1) {
                newHeight[i + 1] += share;
            }
        }

        // Copy the new values back into the main array
        for (int i = 0; i < SIZE; i++) {
            height[i] = newHeight[i];
        }
    }

    // Read one bar's height
    static double getHeight(int index) {
        if (index < 0 || index >= SIZE) {
            return 0.0;
        }
        return height[index];
    }

    static void simulateNextStep() {
        //TODO
    }
}

