package com.example.jason.art3mis;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class NewInput extends AppCompatActivity {
	
	int totalHeight;
	int totalWidth;
	ScrollViewWithMaxHeight sv_with_max;
	EditText et_course_name;
	Button b_work;
	LinearLayout ll_scroll;
	String baseDir;
	CsvReadWrite csvReadWrite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_input);
		
		sv_with_max = findViewById(R.id.scroll_view_max_height);
		et_course_name = findViewById(R.id.course_name);
		b_work = findViewById(R.id.add_work_button);
		ll_scroll = findViewById(R.id.scrolling_layout);
		
		int etHeight = et_course_name.getHeight();
		int b_work_height = b_work.getHeight();
		totalHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
		totalWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
		
		sv_with_max.setMaxHeight(totalHeight - etHeight - b_work_height);
		
		moreAssignments();
		
		baseDir = this.getFilesDir().toString();
		csvReadWrite = new CsvReadWrite(baseDir);
		
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
		final ScrollViewWithMaxHeight sc = sv_with_max;
		sc.post(new Runnable() {
			@Override
			public void run() {
				sc.fullScroll(ScrollViewWithMaxHeight.FOCUS_DOWN);
			}
		});
		
	}
	
	public String[][] getSyllabusArray(){
		// TODO 1: Add in check for all fields filled
		// TODO 2: Ignore fields with blank entries
		// TODO 3: Check that weights add up to 100
		// TODO 4: Add highlighting for error fields (Notify user of errors)
		
		String[] courseName = {et_course_name.getText().toString()};
		
		int numChildren = ll_scroll.getChildCount();
		String[] assignmentNames = new String[numChildren];
		String[] assignmentWeights = new String[numChildren];
		
		// Empty array cause no grades yet
		String[] grades = new String[0];
		
		for (int i = 0; i < numChildren; i++) {
			LinearLayout ll_inner = (LinearLayout) ll_scroll.getChildAt(i);
			
			EditText et_assignment_name = (EditText) ll_inner.getChildAt(0);
			EditText et_assignment_weight = (EditText) ll_inner.getChildAt(1);
			
			assignmentNames[i] = et_assignment_name.getText().toString();
			assignmentWeights[i] = et_assignment_weight.getText().toString();
		}

		return new String[][] {courseName, assignmentNames, assignmentWeights, grades};
	}
	
	public void write(View v) {
		String[][] testArrays = {
			{"TEST101"},
			{"Labs", "Final"},
			{"123123", "71"},
			{"23", "123"},
		};
//	String[][] testArrays = getSyllabusArray();
		csvReadWrite.writeCsvToStorage(testArrays);
	}
	
	public void read(View v) {
		ArrayList<String[]> arrayList = csvReadWrite.readCsvFromStorage("TEST101");
		String txt =
			Arrays.toString(arrayList.get(0)) + "\n"
			+ Arrays.toString(arrayList.get(1)) + "\n"
			+ Arrays.toString(arrayList.get(2)) + "\n"
			+ Arrays.toString(arrayList.get(3));
		
		System.out.println(txt);
	}
	
}

