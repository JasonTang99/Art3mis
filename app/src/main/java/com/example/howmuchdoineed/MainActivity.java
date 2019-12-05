package com.example.howmuchdoineed;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // do more stuff, runs after onCreate and only if there's a save state
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        // outState.putString();

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }


    public void openNewFile(View v) {
        Intent intent = new Intent(this, NewInputActivity.class);
        startActivity(intent);
    }

    public void openExisting(View v) {
        Intent intent = new Intent(this, ViewFilesActivity.class);
        startActivity(intent);
    }

    public void openContact(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:1999jasontang@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About Art3mis App");

        startActivity(Intent.createChooser(emailIntent, "Email Me"));
    }


//  UNUSED METHODS THAT MIGHT BE USEFUL LATER

//  public void showPath(View v) {
//    Button button = findViewById(R.id.button);
//    File dir = this.getFilesDir();
//    String dirString = dir.toString();
//    button.setText(dirString);
//  }
//
// public void openHomePage(View v) {
//    String url = "https://1999jasontang.github.io/";
//    Uri parsedUri = Uri.parse(url);
//    Intent mIntent = new Intent(Intent.ACTION_VIEW, parsedUri);
//    if (mIntent.resolveActivity(getPackageManager()) != null ) {
//      startActivity(mIntent);
//    }
//  }
//
//  public void shareText(View v) {
//    String mimeType = "text/plain";
//    String title = "Share my site";
//    ShareCompat.IntentBuilder.from(this)
//      .setChooserTitle(title)
//      .setType(mimeType)
//      .setText("https://1999jasontang.github.io/")
//      .startChooser();
//  }


//  public static boolean isNum(String input) {
//    return input.matches("[\\d\\.\\/]*");
//  }
//
//  public static double toDecimal(String input) {
//    System.out.println(input);
//
//    if ( !isNum(input) ) {
//      System.out.println("Check that you entered the number correctly!");
//      return -1.0;
//    }
//    else {
//      if (input.contains("/")) {
//        String[] splt = input.split("/");
//        if (splt.length > 2) {
//          System.out.println("You have too many fractions! Try again");
//          return -1.0;
//        }
//        else {
//          return Double.parseDouble(splt[0]) / Double.parseDouble(splt[1]);
//        }
//      }
//      else {
//        return Double.parseDouble(input);
//      }
//    }
//  }

}
