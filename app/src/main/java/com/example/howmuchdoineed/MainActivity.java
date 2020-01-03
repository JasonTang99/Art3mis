package com.example.howmuchdoineed;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView tv_choose_file;
    LinearLayout ll_files;

    String baseDir;
    CsvReadWrite csvReadWrite;
    Boolean deleting = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        tv_choose_file = findViewById(R.id.tv_choose_file);
        ll_files = findViewById(R.id.ll_files);

        baseDir = this.getFilesDir().toString();
        csvReadWrite = new CsvReadWrite(baseDir);

        ArrayList<String> files = new ArrayList<>();
        for (String class_name : this.fileList()) {
            if (class_name.contains(".csv")) {
                files.add(class_name.replaceAll(".csv", ""));
            }
        }

        if (files.size() == 0) {
            tv_choose_file.setText(R.string.no_files);
            Button b_new_input = new Button(this);
            ll_files.addView(b_new_input);
            b_new_input.setText(R.string.new_file);
            b_new_input.setOnClickListener(overrideOnClickNewFile());
        } else {

            final LinearLayout.LayoutParams b_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            final LinearLayout.LayoutParams v_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            for (String class_name : files) {
                Button button = new Button(this);
                button.setText(class_name);
                button.setLayoutParams(b_params);
                button.setGravity(Gravity.CENTER);
                button.setOnClickListener(overrideOnClick(class_name));

                ll_files.addView(button);

//                View view = new View(this);
//                view.setStyle
//                <View
//                android:layout_width="match_parent"
//                android:layout_height="1dp"
//                android:background="@android:color/darker_gray"/>
            }


        }

    }

    // Gives Button with class name function to open that class
    View.OnClickListener overrideOnClick(final String class_name) {
        return v -> startIntentMain(csvReadWrite.readCsvFromStorage(class_name));
    }

    // Turns Class Button to Delete
    View.OnClickListener overrideOnClickDelete(final String class_name) {
        return v -> deleteClass(class_name);
    }

    // Gives Button when there are no classes function to create a new class
    View.OnClickListener overrideOnClickNewFile() {
        return v -> startIntentNewInput();
    }

    // Adds "Delete" to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

    // Adds "Delete" to action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_delete:
                if (deleting) {
                    for (int a = 1; a < ll_files.getChildCount(); a++) {
                        Button button = (Button) ll_files.getChildAt(a);
                        String className = button.getText().toString();

                        button.setText("Delete " + className);
                        button.setOnClickListener(overrideOnClickDelete(className));
                    }
                    item.setTitle(R.string.view_files);
                    deleting = false;
                } else {
                    for (int a = 1; a < ll_files.getChildCount(); a++) {
                        Button button = (Button) ll_files.getChildAt(a);
                        String className = button.getText().toString().substring(7);

                        button.setText(className);
                        button.setOnClickListener(overrideOnClick(className));
                    }
                    item.setTitle(R.string.delete);
                    deleting = true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startIntentMain(ArrayList<String[]> arrList) {
        Intent intent = new Intent(this, FileOptionsActivity.class);
        intent.putExtra("Arraylist", arrList);
        startActivity(intent);
    }

    public void startIntentNewInput() {
        startActivity(new Intent(this, NewInputActivity.class));
    }

    public void deleteClass(String className) {
        this.deleteFile(className + ".csv");
        // TODO: Add in Dialog Confirmation Box
        this.recreate();
    }

}
