package com.example.howmuchdoineed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class NewInputActivity extends AppCompatActivity {

    int totalHeight;
    ScrollView sv;
    EditText et_course_name;
    LinearLayout ll_scroll;

    String baseDir;
    CsvReadWrite csvReadWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_input);

        baseDir = this.getFilesDir().toString();
        csvReadWrite = new CsvReadWrite(baseDir);

        et_course_name = findViewById(R.id.course_name);
        sv = findViewById(R.id.sv);
        ll_scroll = findViewById(R.id.scrolling_layout);

        newAssignment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_done:
                View content = findViewById(android.R.id.content);
                boolean flag = writeClass(content);
                if (flag) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            case R.id.ab_add:
                newAssignment();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

//    public void addAssignment(View v) {
//        newAssignment();
//    }

    public void newAssignment() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramName.weight = 60;

        LinearLayout.LayoutParams paramWeight = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramWeight.weight = 35;

        LinearLayout newAssignment = new LinearLayout(this);
        newAssignment.setLayoutParams(params);

        EditText assignmentName = new EditText(this);
        assignmentName.setHint(R.string.work);
        assignmentName.setLayoutParams(paramName);

        EditText assignmentWeight = new EditText(this);
        assignmentWeight.setHint(R.string.worth);
        assignmentWeight.setLayoutParams(paramWeight);
        assignmentWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        // TODO: Allow fractional input
//		assignmentWeight.setKeyListener(DigitsKeyListener.getInstance("0123456789./-"));

        assignmentWeight.setSingleLine();
        assignmentName.setSingleLine();

        newAssignment.addView(assignmentName);
        newAssignment.addView(assignmentWeight);

        ll_scroll.addView(newAssignment);


        // Scrolls to the bottom of the ScrollView
        final ScrollView sc = sv;
        sc.post((Runnable) () -> sc.fullScroll(ScrollViewWithMaxHeight.FOCUS_DOWN));
    }

    public ArrayList<String[]> getSyllabusArray(View v) {
        boolean properFormat = true;
        ArrayList<String[]> sent = new ArrayList<>();

        String[] courseName = {et_course_name.getText().toString()};
        if (courseName[0].equals("")) {
            // TODO: add in highlighting
            properFormat = false;
        }

        sent.add(courseName);

        ArrayList<String> assignmentNames = new ArrayList<>();
        ArrayList<String> assignmentWeights = new ArrayList<>();
        int numChildren = ll_scroll.getChildCount();

        for (int i = 0; i < numChildren; i++) {
            LinearLayout ll_inner = (LinearLayout) ll_scroll.getChildAt(i);

            String s_name = ((EditText) ll_inner.getChildAt(0)).getText().toString();
            String s_weight = ((EditText) ll_inner.getChildAt(1)).getText().toString();

            // Both are filled
            if (!s_name.equals("") && !s_weight.equals("")) {
                assignmentNames.add(s_name);
                assignmentWeights.add(s_weight);
            }
            // Name is filled but weight isn't
            else if (!s_name.equals("") && s_weight.equals("")) {
                properFormat = false;
                Toast.makeText(this, "Weight isn't filled in", Toast.LENGTH_SHORT).show();
                // TODO: add in highlighting
            }
            // Weight is filled but name isn't
            else if (s_name.equals("") && !s_weight.equals("")) {
                properFormat = false;
                Toast.makeText(this, "Name isn't filled in", Toast.LENGTH_SHORT).show();
                // TODO: add in highlighting
            }
        }

        // Name and weight is empty
        if (assignmentNames.size() == 0 || assignmentWeights.size() == 0) {
            // TODO: add in highlighting
            properFormat = false;
            Toast.makeText(this, "Fill in at least one assignment", Toast.LENGTH_SHORT).show();

        }
        String[] lst_names = assignmentNames.toArray(new String[0]);
        String[] lst_weights = assignmentWeights.toArray(new String[0]);

        // Check if weights add up to 100
        if (!listOf100(lst_weights)) {
            // TODO: add in highlighting
            properFormat = false;
            Toast.makeText(this, "This doesn't add up to 100", Toast.LENGTH_SHORT).show();
        }

        sent.add(lst_names);
        sent.add(lst_weights);

        // Empty array cause no grades yet
        String[] grades = new String[lst_names.length];
        Arrays.fill(grades, "");

        sent.add(grades);

        if (properFormat) {
            return sent;
        } else {
            return null;
        }
    }

    public boolean writeClass(View v) {
        ArrayList<String[]> testArrays = getSyllabusArray(v);
        if (testArrays != null) {
            csvReadWrite.writeCsvToStorage(testArrays);
            return true;
        }
        return false;
    }


    public boolean listOf100(String[] lst) {
        // Ensure weights sum up to approximately 100
        double sum = 0.0;
        for (String item : lst) {
            sum += Double.parseDouble(item);
        }
        return abs(sum - 100.0) < 0.0001;
    }

}

