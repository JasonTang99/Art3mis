package com.example.jason.art3mis;

import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class NewInput extends AppCompatActivity {
	
	int totalHeight;
	int totalWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_input);
		
		
		ScrollViewWithMaxHeight sc = findViewById(R.id.scroll_view_max_height);
		EditText et_course_name = findViewById(R.id.course_name);
		Button but = findViewById(R.id.add_work_button);
		
		int etHeight = et_course_name.getHeight();
		int butHeight = but.getHeight();
		totalHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
		totalWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
		
		sc.setMaxHeight(totalHeight - etHeight - butHeight);
		
	}
	
	
	
	public void moreAssignments(View v) {
		
		final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		
		
		
		LinearLayout scrollingAssignments = findViewById(R.id.scrolling_layout);
		
		LinearLayout newAssignment = new LinearLayout(this);
		newAssignment.setLayoutParams(params1);
		
		int nameSize = totalWidth * 10 / 16;
		int paddingSize = totalWidth / 16;
		int gradeSize = totalWidth * 5 / 16;
		
		final ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(
			nameSize,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		final ViewGroup.LayoutParams params3 = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		
		
		EditText assignmentName = new EditText(this);
		assignmentName.setHint(R.string.work);
		assignmentName.setLayoutParams(params2);
//		assignmentName.setPadding(0,0, paddingSize,0);
		
		
		
		EditText grade = new EditText(this);
		grade.setHint(R.string.worth);
		grade.setLayoutParams(params3);
		grade.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		
		
//		grade.setKeyListener(DigitsKeyListener.getInstance("0123456789./-"));
		
		
		grade.setSingleLine();
		assignmentName.setSingleLine();
		
		
//		grade.setHint(Integer.toString(totalWidth));
		newAssignment.addView(assignmentName);
		newAssignment.addView(grade);
		
		

		
		
		
		scrollingAssignments.addView(newAssignment);
		
		
		
		// Scrolls to the bottom of the ScrollView
		final ScrollViewWithMaxHeight sc = findViewById(R.id.scroll_view_max_height);
		
		sc.post(new Runnable() {
			@Override
			public void run() {
				sc.fullScroll(ScrollViewWithMaxHeight.FOCUS_DOWN);
			}
		});
		
		
	}
	
	
	
	public void saveWork(View v){
		EditText et_course = findViewById(R.id.course_name);
		Button save_work = findViewById(R.id.save_work);
		LinearLayout linlay = findViewById(R.id.scrolling_layout);
		TextView display = findViewById(R.id.tv_arrays);
		
		String courseName = et_course.getText().toString();
		
		int numChildren = linlay.getChildCount();
		String[] assignNames = new String[numChildren];
		double[] assignWorths = new double[numChildren];
		
		for (int i = 0; i < numChildren; i++) {
			LinearLayout innerlinlay = (LinearLayout) linlay.getChildAt(i);
			
			EditText et_assign_name = (EditText) innerlinlay.getChildAt(0);
			EditText et_assign_worth = (EditText) innerlinlay.getChildAt(1);
			
			assignNames[i] = et_assign_name.getText().toString();
			assignWorths[i] = Double.parseDouble(et_assign_worth.getText().toString());
		}
		
		String strAssignNames = Arrays.toString(assignNames);
		String strAssignWorths = Arrays.toString(assignWorths);
		
		
		display.setText(
			courseName + "\n" + strAssignNames + "\n" + strAssignWorths
		);
		
		
		
		
		// Saving to Storage
//		try {
//			File file = new File(courseName +".csv");
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(strAssignNames);
//			bw.write(strAssignWorths);
//			bw.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			display.setText("BEEP BOOP SOMETHING IS WRONG");
//		}
		
		
		try {
			String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
			String fileName = courseName + ".csv";
			String filePath = baseDir + File.separator + fileName;
			File f = new File(filePath );
			CSVWriter writer;
			// File exist
			if(f.exists() && !f.isDirectory()){
				FileWriter mFileWriter = new FileWriter(filePath , true);
				writer = new CSVWriter(mFileWriter);
			}
			else {
				writer = new CSVWriter(new FileWriter(filePath));
			}
			
			writer.writeNext(assignNames);
//			writer.writeNext(assignWorths);
			
			writer.close();
		} catch (IOException e) {
			display.setText("BEEP BOOP SOMETHING IS WRONG");
		}
		
	}

	
}

