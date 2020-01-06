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

        // TODO: How to get type of object and maybe deconstruct arraylist in one go
        // TODO: Strip grades and other stuff of empties
        if (getIntent().getSerializableExtra("Arraylist") == java.util.ArrayList<java.lang.String[]>) {
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
        } else {
            return;
            // TODO: Return or something to previous activity
        }

        setContentView(R.layout.activity_file_options);

        Double current_grade = calcCurrentGrade();

        String s_current_grade = "Your current grade is: " + df.format(current_grade);
        TextView tv_current_grade = findViewById(R.id.tv_current_grade);
        tv_current_grade.setText(s_current_grade);
    }

    public void openGrades(View v) {
        Intent intent = new Intent(this, EditGradesActivity.class);
        intent.putExtra("Arraylist", sent);
        startActivity(intent);
    }

    public void showHowMuch(View v) {
        EditText et_how_much = findViewById(R.id.et_how_much);
        String how_much = et_how_much.getText().toString();
        if (!how_much.equals("")) {
            calcHowMuch(Double.parseDouble(how_much));
        }
        else {
            Toast.makeText(this,"Enter a desired final grade", Toast.LENGTH_SHORT).show();
        }

    }

    public Double calcCurrentGrade() {
        double mark = 0.0;
        double max_mark = 0.0;

        for (int a = 0; a < grades.length; a++) {
            if (!grades[a].equals("")) {
                Double grade = Double.parseDouble(grades[a]);
                Double weight = Double.parseDouble(assignmentWeights[a]);
                mark += grade * weight;
                max_mark += weight;
            }
        }

        double current_grade = 0.0;
        if (max_mark == 0) {
            Toast.makeText(this, "You don't have any weight values", Toast.LENGTH_SHORT).show();
        } else {
            current_grade = mark/max_mark;
        }

        return current_grade;
    }

    public void calcHowMuch(double final_grade) {
        Double current_grade = calcCurrentGrade();

        int num_missing = 0;
        int missing_index = 0;
        int a = 0;
        double total_grade = 0;

        // TODO: Faster indexing?
        while (a < grades.length) {
            if (grades[a].equals("")) {
                num_missing++;
                missing_index = a;
            }
            else {
                total_grade += Double.parseDouble(grades[a]) * Double.parseDouble(assignmentWeights[a]) / 100.0;
            }
            a++;
        }

        if (num_missing > 1) {
            Toast.makeText(this,"Edit grades until only one is missing", Toast.LENGTH_SHORT).show();
            Button b_how_much = findViewById(R.id.b_how_much);
            b_how_much.setClickable(false);
        }
        else if (num_missing == 0) {
            Toast.makeText(this,"All grades are already filled in", Toast.LENGTH_SHORT).show();
            Button b_how_much = findViewById(R.id.b_how_much);
            b_how_much.setClickable(false);
        }
        else {
            double howMuch = (final_grade - current_grade) * 100 / Double.parseDouble(assignmentWeights[missing_index]);
            String msg = "You need " + df.format(howMuch) + " on " + assignmentNames[missing_index] + " to get a final mark of " + df.format(final_grade) + "%";
            TextView tv_how_much = findViewById(R.id.tv_how_much);
            tv_how_much.setText(msg);
        }

    }

    public boolean isEmpty(String[] lst) {
        if (lst.length == 0)
            return true;

        boolean empty = true;
        for (String a: lst) {
            if ( a != null && !(a.equals("")) ) {
                empty = false;
                break;
            }
        }

        return empty;
    }
}


//    public void openHome(View v) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }