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

public class ClassOptionsActivity extends AppCompatActivity {
    ArrayList<String[]> sent;
    String[] courseName;
    String[] assignmentNames;
    String[] assignmentWeights;
    String[] grades;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_options);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        sent = (ArrayList<String[]>) getIntent().getSerializableExtra("Arraylist");
        courseName = sent.get(0);
        assignmentNames = sent.get(1);
        assignmentWeights = sent.get(2);
        grades = sent.get(3);

        TextView tv_current_grade = findViewById(R.id.tv_current_grade);

        if (grades.length != 0) {
            Double current_grade = calcCurrentGrade();
            String s_current_grade = "Current Grade: " + df.format(current_grade) + "%";
            tv_current_grade.setText(s_current_grade);
        } else {
            tv_current_grade.setText(getResources().getString(R.string.curr_grade_0));
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void openGrades(View v) {
        Intent intent = new Intent(this, EditGradesActivity.class);
        intent.putExtra("Arraylist", sent);
        startActivity(intent);
    }

    public void showHowMuch(View v) {
        EditText et_how_much = findViewById(R.id.et_how_much);
        String s_desired_grade = et_how_much.getText().toString();

        if (s_desired_grade.equals("")) {
            Toast.makeText(this, "Enter a desired final grade", Toast.LENGTH_SHORT).show();
        } else {
            double desired_grade = Double.parseDouble(s_desired_grade);
            double current_grade = calcCurrentGrade();
            int num_missing, missing_index;
            num_missing = 0;
            missing_index = 0;

            for (int a = 0; a < grades.length; a++) {
                if (grades[a].equals("")) {
                    num_missing++;
                    missing_index = a;
                }
            }

            if (num_missing == 0) {
                Toast.makeText(this, "All grades are already filled in", Toast.LENGTH_SHORT).show();
                Button b_how_much = findViewById(R.id.b_how_much);
                b_how_much.setClickable(false);
            } else if (num_missing > 1) {
                Toast.makeText(this, "Edit grades until only one is missing", Toast.LENGTH_SHORT).show();
                Button b_how_much = findViewById(R.id.b_how_much);
                b_how_much.setClickable(false);
            } else {
                double howMuch = (desired_grade -
                    (current_grade * (100 - Double.parseDouble(assignmentWeights[missing_index])) / 100)
                ) / Double.parseDouble(assignmentWeights[missing_index]) * 100;

                String msg = "You need " + df.format(howMuch) + "% on " + assignmentNames[missing_index] + " to get a final mark of " + df.format(desired_grade) + "%";
                TextView tv_how_much = findViewById(R.id.tv_how_much);
                tv_how_much.setText(msg);
                tv_how_much.setVisibility(View.VISIBLE);
            }
        }
    }

    public double calcCurrentGrade() {
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

        if (mark == 0.0 && max_mark == 0) {
            return 0.0;
        }

        double current_grade = 0.0;
        if (max_mark == 0) {
            Toast.makeText(this, "You don't have any weight values", Toast.LENGTH_SHORT).show();
        } else {
            current_grade = mark / max_mark;
        }
        return current_grade;
    }
}