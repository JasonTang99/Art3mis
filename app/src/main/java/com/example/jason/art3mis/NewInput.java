package com.example.jason.art3mis;

import android.content.Context;
import android.content.res.Resources;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewInput extends AppCompatActivity {
	
	int totalHeight;
	int totalWidth;
	ScrollViewWithMaxHeight sv_Max;
	EditText et_course_name;
	Button b_work;
	LinearLayout ll_scroll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_input);
		
		sv_Max = findViewById(R.id.scroll_view_max_height);
		et_course_name = findViewById(R.id.course_name);
		b_work = findViewById(R.id.add_work_button);
		ll_scroll = findViewById(R.id.scrolling_layout);
		
		int etHeight = et_course_name.getHeight();
		int b_work_height = b_work.getHeight();
		totalHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
		totalWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
		
		sv_Max.setMaxHeight(totalHeight - etHeight - b_work_height);
		
		moreAssignments();
	}
	
	public void addAssignment(View v) {
		moreAssignments();
	}
	
	
	public void moreAssignments() {
		
		final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		
		
		LinearLayout scrollingAssignments = ll_scroll;
		
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
		
		EditText grade = new EditText(this);
		grade.setHint(R.string.worth);
		grade.setLayoutParams(params3);
		grade.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		
		
//		grade.setKeyListener(DigitsKeyListener.getInstance("0123456789./-"));
		
		
		grade.setSingleLine();
		assignmentName.setSingleLine();
		
		newAssignment.addView(assignmentName);
		newAssignment.addView(grade);
		
		scrollingAssignments.addView(newAssignment);
		
		
		
		// Scrolls to the bottom of the ScrollView
		final ScrollViewWithMaxHeight sc = sv_Max;
		
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
		
	}
	
	public void write(View v) {
		int[] entries = {1, 1, 1, 1};
		int[] ent = {5, 6, 7, 8};
		String str = Arrays.toString(entries);
		String str2 = Arrays.toString(ent);
		ArrayList<String> arrCombined = new ArrayList<String>();
		arrCombined.add(str);
		arrCombined.add(str2);
		String strFinal = arrCombined.toString();
		
		String[] yay = {"asdas", "adqweas"};
		
		try {
			FileOutputStream fos = openFileOutput("memes.txt", Context.MODE_PRIVATE);
			OutputStreamWriter osr= new OutputStreamWriter(fos);
			CSVWriter writer = new CSVWriter(osr);
//			fos.write(str.getBytes());
//			fos.write(System.getProperty("line.separator").getBytes());
//			fos.write(str2.getBytes());
//			fos.write(strFinal.getBytes());
//			fos.close();
			writer.writeNext(yay);
			writer.close();
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
	
//	public void fancyRead(View v) {
//		// Seperate
//		// Take the read string, cut off the brackets
//		// Regex split by ", " and then make a for loop, casting them as ints
//		// then add
//		try {
//			ObjectMapper jsonMapper = new ObjectMapper();
//			String content = "[PSY100, [1,2,3], [3,2,4]]";
//			List list =  jsonMapper.readValue(content, List.class);
//			String str_list = list.toString();
//			Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
//		} catch(com.fasterxml.jackson.databind.JsonMappingException e) {
//			Toast.makeText(this, "Beep Boop Big Error", Toast.LENGTH_SHORT).show();
//		} catch (IOException e) {
//			Toast.makeText(this, "Beep Boop Big Error", Toast.LENGTH_SHORT).show();
//
//		}
//	}
	
}

