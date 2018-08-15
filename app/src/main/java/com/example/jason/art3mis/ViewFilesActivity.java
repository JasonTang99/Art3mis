package com.example.jason.art3mis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
			// TODO: Add button to go make a new file (make a new onClick override method)
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
	
	View.OnClickListener overrideOnClick(final String class_name)  {
		return new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<String[]> arrayList = csvReadWrite.readCsvFromStorage(class_name);
				String txt =
					Arrays.toString(arrayList.get(0)) + "\n"
						+ Arrays.toString(arrayList.get(1)) + "\n"
						+ Arrays.toString(arrayList.get(2)) + "\n"
						+ Arrays.toString(arrayList.get(3));

				System.out.println(txt);
				
				startIntent(arrayList);
				
			}
		};
	}
	
	public void startIntent(ArrayList<String[]> arrList) {
		Intent intent = new Intent(this, FileOptionsActivity.class);
		intent.putExtra("Arraylist", arrList);
		startActivity(intent);
	}
	
	// TODO: Implement delete function
	
}
