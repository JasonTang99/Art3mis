package com.example.jason.art3mis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewFilesActivity extends AppCompatActivity {
	
	TextView tv_choose_file;
	LinearLayout ll_file_buttons;
	
	String baseDir;
	CsvReadWrite csvReadWrite;
	
	Boolean deleting = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_files);
		
		tv_choose_file = findViewById(R.id.tv_choose_file);
		ll_file_buttons = findViewById(R.id.ll_file_buttons);
		
		baseDir = this.getFilesDir().toString();
		csvReadWrite = new CsvReadWrite(baseDir);
		
		String[] files = this.fileList();
		System.out.println(Arrays.toString(files));
		
		ArrayList<String> actual_files = new ArrayList<>();
		for (String class_name: files) {
			if (class_name.contains(".csv")) {
				actual_files.add(class_name.replaceAll(".csv",""));
			}
		}
		
		
		if (actual_files.size() == 0) {
			tv_choose_file.setText(R.string.no_files);
			Button b_new_input = new Button(this);
			ll_file_buttons.addView(b_new_input);
			b_new_input.setText("Make a new file");
			b_new_input.setOnClickListener(overrideOnClickNewFile());
		}
		else {
			for (String class_name: actual_files) {
				final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
				
				Button button = new Button(this);
				button.setText(class_name);
				button.setLayoutParams(params);
				button.setGravity(Gravity.CENTER);
				button.setOnClickListener(overrideOnClick(class_name));
				
				ll_file_buttons.addView(button);
			}
		}
		
	}
	
	// Gives Button with class name function to open that class
	View.OnClickListener overrideOnClick(final String class_name)  {
		return new View.OnClickListener() {
			public void onClick(View v) {
				startIntentMain(csvReadWrite.readCsvFromStorage(class_name));
			}
		};
	}
	
	// Turns Class Button to Delete
	View.OnClickListener overrideOnClickDelete(final String class_name)  {
		return new View.OnClickListener() {
			public void onClick(View v) {
				deleteClass(class_name);
			}
		};
	}
	
	// Gives Button when there are no classes function to create a new class
	View.OnClickListener overrideOnClickNewFile()  {
		return new View.OnClickListener() {
			public void onClick(View v) {
				startIntentNewInput();
			}
		};
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
					for (int a = 1; a < ll_file_buttons.getChildCount(); a++) {
						Button button = (Button) ll_file_buttons.getChildAt(a);
						String className = button.getText().toString();
						
						button.setText("Delete " + className);
						button.setOnClickListener(overrideOnClickDelete(className));
					}
					item.setTitle(R.string.view_files);
					deleting = false;
				}
				else {
					for (int a = 1; a < ll_file_buttons.getChildCount(); a++) {
						Button button = (Button) ll_file_buttons.getChildAt(a);
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
