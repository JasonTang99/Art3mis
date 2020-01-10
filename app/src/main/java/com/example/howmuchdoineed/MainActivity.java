package com.example.howmuchdoineed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tv_choose_file;
    LinearLayout ll_files;

    String baseDir;
    CsvReadWrite csvReadWrite;
    Boolean deleting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_choose_file = findViewById(R.id.tv_choose_file);
        ll_files = findViewById(R.id.ll_files);

        baseDir = this.getFilesDir().toString();
        csvReadWrite = new CsvReadWrite(baseDir);

        ArrayList<String> files = new ArrayList<>();
        for (String class_name : this.fileList())
            if (class_name.contains(".csv"))
                files.add(class_name.replaceAll(".csv", ""));

        if (files.size() == 0) {
            tv_choose_file.setText(R.string.no_files);

            Button b_new_input = new Button(new ContextThemeWrapper(this, R.style.MainClassButton), null, 0);
            b_new_input.setText(R.string.new_class);
            b_new_input.setOnClickListener(overrideOnClickNewClass());
            b_new_input.setTag("b_new_class");
            ll_files.addView(b_new_input);
        } else {
            for (String class_name : files) {
                Button button= new Button(new ContextThemeWrapper(this, R.style.MainClassButton), null, 0);
                button.setText(class_name);
                button.setOnClickListener(overrideOnClick(class_name));
                button.setTag("b_new_class");
                ll_files.addView(button);

                View div = new View(new ContextThemeWrapper(this, R.style.Divider), null, 0);
                ll_files.addView(div);
            }
        }
    }

    // Gives Button with class name function to open that class
    View.OnClickListener overrideOnClick(final String class_name) {
        return v -> startIntentClassOptions(csvReadWrite.readCsvFromStorage(class_name));
    }

    // Turns Class Button to Delete
    View.OnClickListener overrideOnClickDelete(final String class_name) {
        return v -> deleteClass(class_name);
    }

    // Gives Button when there are no classes function to create a new class
    View.OnClickListener overrideOnClickNewClass() {
        return v -> startIntentNewClass();
    }

    // Adds "Delete" to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.del_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_add:
                startIntentNewClass();
                break;
            case R.id.ab_delete:
                if (deleting) {
                    for (int a = 1; a < ll_files.getChildCount(); a++) {
                        View child = ll_files.getChildAt(a);
                        if ( (child instanceof Button) && (child.getTag() == "b_new_class") ) {
                            Button button = (Button) ll_files.getChildAt(a);
                            String className = button.getText().toString().substring(7);

                            button.setText(className);
                            button.setOnClickListener(overrideOnClick(className));
                        }
                    }
                    item.setTitle(R.string.delete);
                    item.setIcon(R.drawable.ic_action_delete);
                    deleting = false;
                } else {
                    for (int a = 1; a < ll_files.getChildCount(); a++) {
                        View child = ll_files.getChildAt(a);
                        System.out.println(child.getTag());
                        if ( (child instanceof Button) && (child.getTag() == "b_new_class") ) {
                            Button button = (Button) ll_files.getChildAt(a);
                            String className = button.getText().toString();
                            String del = "Delete " + className;

                            button.setText(del);
                            button.setOnClickListener(overrideOnClickDelete(className));
                        }
                    }
                    item.setTitle(R.string.done);
                    item.setIcon(null);
                    deleting = true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public void startIntentClassOptions(ArrayList<String[]> sent) {
        if (isEmpty(sent.get(3))) {
            Intent intent = new Intent(this, EditGradesActivity.class);
            intent.putExtra("Arraylist", sent);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ClassOptionsActivity.class);
            intent.putExtra("Arraylist", sent);
            startActivity(intent);
        }
    }

    public void startIntentNewClass() {
        startActivity(new Intent(this, NewClassActivity.class));
    }

    public void deleteClass(String className) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_msg) + " " + className + "?");
        builder.setPositiveButton(R.string.yes, (dialog, id) -> {
            this.deleteFile(className + ".csv");
            dialog.dismiss();
            this.recreate();
        });
        builder.setNegativeButton(R.string.no, (dialog, id) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
