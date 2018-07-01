package com.example.jason.art3mis;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
	

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    

    if (savedInstanceState != null) {
      // do stuff
    }
    setContentView(R.layout.activity_main);
  }
  
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    // do more stuff, runs after onCreate and only if theres a save state
    super.onRestoreInstanceState(savedInstanceState);
  }
  
  public void onSaveInstanceState(Bundle outState) {
    // outState.putString();
    
    // call superclass to save any view hierarchy
    super.onSaveInstanceState(outState);
  }
  
  
  
  
  public void openHomePage(View v) {
    Button but2 = (Button) findViewById(R.id.button2);
    but2.setText("Ayy you clicked me");
    String url = "https://1999jasontang.github.io/";
    Uri parsedUri = Uri.parse(url);
    Intent mIntent = new Intent(Intent.ACTION_VIEW, parsedUri);
    if (mIntent.resolveActivity(getPackageManager()) != null ) {
      startActivity(mIntent);
    }
  }

  public void shareText(View v) {
    String mimeType = "text/plain";
    String title = "Share my site";
    ShareCompat.IntentBuilder.from(this)
        .setChooserTitle(title)
        .setType(mimeType)
        .setText("https://1999jasontang.github.io/")
        .startChooser();
  }
	
  
  public void openResults(View v) {
    EditText mNumber = (EditText) findViewById(R.id.editText);
    String enterNum = mNumber.getText().toString();
  	Context context = this;
  	Class targetClass = Results.class;
  	Intent intent = new Intent(context, targetClass);
  	intent.putExtra("Numbers", enterNum);
  	startActivity(intent);
  	
  	
	}
	
	public void openNumbers(View v) {
    Intent intent = new Intent(this, NewInput.class);
    startActivity(intent);
  }






  /////////////////////
  /////////////////////
  /////////////////////
  /////////////////////



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

  public static double toDecimal(String input) {
    System.out.println(input);

    if ( !isNum(input) ) {
      System.out.println("Check that you entered the number correctly!");
      return -1.0;
    }
    else {
      if (input.contains("/")) {
        String[] splt = input.split("/");
        if (splt.length > 2) {
          System.out.println("You have too many fractions! Try again");
          return -1.0;
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
