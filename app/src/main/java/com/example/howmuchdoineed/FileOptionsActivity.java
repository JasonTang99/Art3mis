package com.example.howmuchdoineed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FileOptionsActivity extends AppCompatActivity {

    ArrayList<String[]> sent;

    String[] courseName;
    String[] assignmentNames;
    String[] assignmentWeights;
    String[] grades;

    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sent = (ArrayList<String[]>) getIntent().getSerializableExtra("Arraylist");
        courseName = sent.get(0);
        assignmentNames = sent.get(1);
        assignmentWeights = sent.get(2);
        grades = sent.get(3);

        if (isEmpty(grades)) {
            Intent intent = new Intent(this, EditGradesActivity.class);
            intent.putExtra("Arraylist", sent);
            startActivity(intent);
        }

        setContentView(R.layout.activity_file_options);


    }

    public void openGrades(View v) {
        Intent intent = new Intent(this, EditGradesActivity.class);
        intent.putExtra("Arraylist", sent);
        startActivity(intent);
    }

    public void openHome(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void calcCurrentGrade(View v) {
        double total1 = 0.0;
        double total2 = 0.0;

        for (int a = 0; a < grades.length; a++) {
            if (!grades[a].equals("")) {
                total1 += Double.parseDouble(grades[a]) * Double.parseDouble(assignmentWeights[a]);
                total2 += Double.parseDouble(assignmentWeights[a]);
            }
        }

        double current_grade = 0.0;
        if (total2 == 0) {
            Toast.makeText(this, "You don't have any weight values", Toast.LENGTH_SHORT).show();
        }
        else {
            current_grade = total1/total2;
        }

        TextView tv_current_grade = findViewById(R.id.tv_current_grade);
        tv_current_grade.setText("Your current grade is: " + df.format(current_grade));
    }

    public void calcHowMuch(View v) {
        EditText et_how_much = findViewById(R.id.et_how_much);
        String how_much = et_how_much.getText().toString();
        if (!how_much.equals("")) {
            howMuchDoINeed(Double.parseDouble(how_much));
        }
        else {
            Toast.makeText(this,"Enter a desired final grade", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isEmpty(String[] lst) {
        if (lst.length == 0) {
            return true;
        }

        boolean empty = true;
        for (String a: lst) {
            if ( a != null && !(a.equals("")) ) {
                empty = false;
                break;
            }
        }

        return empty;
    }

    public void howMuchDoINeed(double final_grade) {
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
                total_grade += Double.parseDouble(grades[a]) * Double.parseDouble(assignmentWeights[a]) / 100.0;
            }
            a++;
        }

        if (counter > 1) {
            System.out.println("Fill up the grades until only one is left blank please");
            Toast.makeText(this,"Fill up the grades until only one is left", Toast.LENGTH_SHORT).show();
            Button b_how_much = findViewById(R.id.b_how_much);
            b_how_much.setClickable(false);
        }
        else if (counter == 0) {
            System.out.println("All your grades are already filled in");
            Toast.makeText(this,"All your grades are already filled in", Toast.LENGTH_SHORT).show();
            Button b_how_much = findViewById(R.id.b_how_much);
            b_how_much.setClickable(false);
        }
        else {
            double howMuch = (final_grade - total_grade) * 100 / Double.parseDouble(assignmentWeights[missing_index]);
            String msg = "You need " + df.format(howMuch) + " on " + assignmentNames[missing_index] + " to get a final mark of " + df.format(final_grade);
            System.out.println(msg);
            TextView tv_how_much = findViewById(R.id.tv_how_much);
            tv_how_much.setText(msg);
        }

    }
}
