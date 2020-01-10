package com.example.howmuchdoineed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class NewClassActivity extends AppCompatActivity {
    ScrollView sv;
    EditText et_course_name;
    LinearLayout ll_scroll;

    String baseDir;
    CsvReadWrite csvReadWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

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
                ArrayList<String[]> sent = getSyllabusArray(content);
                if (sent != null) {
                    csvReadWrite.writeCsvToStorage(sent);
                    Intent intent = new Intent(this, EditGradesActivity.class);
                    intent.putExtra("Arraylist", sent);
                    startActivity(intent);
                }
                break;

            case R.id.ab_add:
                newAssignment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newAssignment() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams paramWeight = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        paramName.weight = 60;
        paramWeight.weight = 35;

        LinearLayout ll_assign = new LinearLayout(this);
        ll_assign.setLayoutParams(params);

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

        ll_assign.addView(assignmentName);
        ll_assign.addView(assignmentWeight);

        ll_scroll.addView(ll_assign);

        // Scrolls to the bottom of the ScrollView
        final ScrollView sc = sv;
        sc.post(() -> sc.fullScroll(ScrollView.FOCUS_DOWN));
    }

    public ArrayList<String[]> getSyllabusArray(View v) {
        String[] courseName = {et_course_name.getText().toString()};
        if (courseName[0].equals("")) {
            // TODO: add in highlighting
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                et_course_name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.error)));
            }
            Toast.makeText(this, "Add a class name", Toast.LENGTH_SHORT).show();

            return null;
        }

        ArrayList<String> assignmentNames = new ArrayList<>();
        ArrayList<String> assignmentWeights = new ArrayList<>();
        int numChildren = ll_scroll.getChildCount();

        for (int i = 0; i < numChildren; i++) {
            LinearLayout ll_inner = (LinearLayout) ll_scroll.getChildAt(i);

            EditText et_name = (EditText) ll_inner.getChildAt(0);
            EditText et_weight = (EditText) ll_inner.getChildAt(1);

            String s_name = et_name.getText().toString();
            String s_weight = et_weight.getText().toString();
            if (s_weight.equals("") && s_name.equals("")) {
                continue;
            }

            if (s_weight.equals("") || s_name.equals("")) {
                Toast.makeText(this, "Field not filled in", Toast.LENGTH_SHORT).show();

                // TODO: add in highlighting
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (s_weight.equals("")) {
                        et_weight.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.error)));
                    } else {
                        et_name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.error)));
                    }
                }
                return null;
            } else {
                assignmentNames.add(s_name);
                assignmentWeights.add(s_weight);
            }
        }

        if (assignmentNames.size() == 0 || assignmentWeights.size() == 0) {
            // TODO: add in highlighting
            // Highlight First Entry
            LinearLayout ll_inner = (LinearLayout) ll_scroll.getChildAt(0);

            EditText et_name = (EditText) ll_inner.getChildAt(0);
            EditText et_weight = (EditText) ll_inner.getChildAt(1);

            Toast.makeText(this, "Fill in at least one assignment", Toast.LENGTH_SHORT).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                et_weight.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.error)));
                et_name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.error)));
            }

            return null;
        }

        String[] lst_names = assignmentNames.toArray(new String[0]);
        String[] lst_weights = assignmentWeights.toArray(new String[0]);

        if (!listOf100(lst_weights)) {
            // TODO: add in highlighting
            Toast.makeText(this, "Weights don't add up to 100", Toast.LENGTH_SHORT).show();

            // Highlights all weights
            for (int i = 0; i < numChildren; i++) {
                LinearLayout ll_inner = (LinearLayout) ll_scroll.getChildAt(i);
                EditText et_weight = (EditText) ll_inner.getChildAt(1);
                String s_weight = et_weight.getText().toString();

                if (!s_weight.equals("") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    et_weight.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.error)));
                }
            }
            return null;
        }

        // Empty array cause no grades yet
        String[] grades = new String[lst_names.length];
        Arrays.fill(grades, "");

        ArrayList<String[]> sent = new ArrayList<>();
        sent.add(courseName);
        sent.add(lst_names);
        sent.add(lst_weights);
        sent.add(grades);

        return sent;
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

