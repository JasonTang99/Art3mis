package com.example.jason.art3mis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public static boolean listOf100(double[] lst) {
    // Checks if the list adds up to 100
    double sum = 0.0;
    for (double item: lst) {
      sum += item;
    }
    return sum == 100.0;
  }

  public static boolean isNum(String input) {
    return input.matches("[\\d\\.\\/]*");
  }

  public static double toDecimal() { // String input
    // Gets input from user
//    Scanner scan = new Scanner(System.in);
//    System.out.println("Enter a number: ");
//    String input = scan.next();
    String input = "hi";

    System.out.println(input);

    if ( !isNum(input) ) {
      System.out.println("Check that you entered the number correctly!");
      return toDecimal();
    }
    else {
      if (input.contains("/")) {
        String[] splt = input.split("/");
        if (splt.length > 2) {
          System.out.println("You have too many fractions! Try again");
          return toDecimal();
        }
        else {
          return Double.parseDouble(splt[0]) / Double.parseDouble(splt[1]);
        }
      }
      else {
        return Double.parseDouble(input);
      }
    }
  }

  public static void howMuchDoINeed(String[] work, String[] weights, String[] grades, double fin) {
    int counter = 0;
    int missing_index = 0;
    int a = 0;
    double total_grade = 0;

    while (a < grades.length) {
      if (grades[a].equals("")) {
        counter++;
        missing_index = a;
      }
      else {
        total_grade += Double.parseDouble(grades[a]) * Double.parseDouble(weights[a]) / 100.0;
      }
      a++;
    }

    if (counter > 1) {
      System.out.println("Fill up the grades until only one is left blank please");
      // Fill in method
    }
    else {
      double howMuch = (fin - total_grade) * 100 / Double.parseDouble(weights[missing_index]);
      System.out.println("You need " + howMuch + " on your " + work[missing_index] + " to get a final mark of " + fin);
    }


  }

//    Functions
//    Reading from stored files, making new files (auto make new files if no old files?)
//    --------------------
//    Properties of the files
//    - Name of class
//    - Names of assignments/tests
//    - Weights of assignments/tests
//    - Values of previously entered marks
//    Types
//    - String
//    - String[]
//    - String[]
//    - String[]
//
//    --------------------
//    What we can do with the files
//    - If there are no grades then get new ones
//    - Fill in missing grades
//    - Calculate current grade
//    - Rewrite grades
//    - Calculate how much you need
}
