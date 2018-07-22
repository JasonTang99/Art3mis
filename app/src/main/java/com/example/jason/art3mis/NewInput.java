package com.example.jason.art3mis;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
		try{
			File csvfile = new File(Environment.getExternalStorageDirectory() + "/csvfile.csv");
			CSVReader reader = new CSVReader(new FileReader("csvfile.getAbsolutePath()"));
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				System.out.println(nextLine[0] + nextLine[1] + "etc...");
			}
		} catch(Exception e){
			e.printStackTrace();
			Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
		}
		
//		try {
//			String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
//			String fileName = courseName + ".csv";
//			String filePath = baseDir + File.separator + fileName;
//			File f = new File(filePath );
//			CSVWriter writer;
//			// File exist
//			if(f.exists() && !f.isDirectory()){
//				FileWriter mFileWriter = new FileWriter(filePath , true);
//				writer = new CSVWriter(mFileWriter);
//			}
//			else {
//				writer = new CSVWriter(new FileWriter(filePath));
//			}
//
//			writer.writeNext(assignNames);
////			writer.writeNext(assignWorths);
//
//			writer.close();
//		} catch (IOException e) {
//			display.setText("BEEP BOOP SOMETHING IS WRONG");
//		}
		
	}

	public void saveWork2(View v) {
		
		try {
			String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
			String fileName = "memes.csv";
			String filePath = baseDir + File.separator + fileName;
			File f = new File(filePath );
			CSVWriter writer;
			
			if(f.exists() && !f.isDirectory()){
				Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
				FileWriter mFileWriter = new FileWriter(filePath , true);
				writer = new CSVWriter(mFileWriter);
			}
			else {
				FileWriter mFileWriter = new FileWriter(filePath , true);
//				writer = new CSVWriter();
			}
//
//			}
//			else {
//				writer = new CSVWriter(new FileWriter(filePath));
//			}
//
//			String[] entries = "first#second#third".split("#");
//			writer.writeNext(entries);
//			writer.close();
			
		} catch (Exception e) {
			Toast.makeText(this, "Beep Boop Big Error", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void write(View v) {
		int[] entries = {1, 2, 3, 4};
		int[] ent = {5, 6, 7, 8};
		String str = Arrays.toString(entries);
		String str2 = Arrays.toString(ent);
		
		try {
			FileOutputStream fos = openFileOutput("memes.txt", Context.MODE_PRIVATE);
			fos.write(str.getBytes());
			fos.write("#".getBytes());
			fos.write(str2.getBytes());
			fos.close();
			Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this, "Beep Boop Big Error", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void read(View v) {
		String text = "";
		try {
			FileInputStream fis = openFileInput("memes.txt");
			int size = fis.available();
			byte[] buffer = new byte[size];
			fis.read(buffer);
			fis.close();
			text = new String(buffer);
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this, "Beep Boop Big Error", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void fancyRead(View v) {
	
	}
	
}

