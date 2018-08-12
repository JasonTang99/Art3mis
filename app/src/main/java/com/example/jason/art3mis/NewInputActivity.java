package com.example.jason.art3mis;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NewInputActivity extends AppCompatActivity {
	
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
		
		sv_with_max.setMaxHeight(totalHeight - etHeight - (b_work_height * 2));
		
		moreAssignments();
		
		baseDir = this.getFilesDir().toString();
		csvReadWrite = new CsvReadWrite(baseDir);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_bar_done:
				// User chose the "Settings" item, show the app settings UI...
				write(findViewById(android.R.id.content));
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
			
		}
	}
	
	public void addAssignment(View v) {
		moreAssignments();
	}
	
	public void moreAssignments() {
		
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		
		LinearLayout scrollingAssignments = ll_scroll;
		
		LinearLayout newAssignment = new LinearLayout(this);
		newAssignment.setLayoutParams(params1);
		
		LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
			0,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		paramName.weight = 60;
		
		LinearLayout.LayoutParams paramWeight = new LinearLayout.LayoutParams(
			0,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		paramWeight.weight = 35;
		
		
		EditText assignmentName = new EditText(this);
		assignmentName.setHint(R.string.work);
		assignmentName.setLayoutParams(paramName);
		
		EditText grade = new EditText(this);
		grade.setHint(R.string.worth);
		grade.setLayoutParams(paramWeight);
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
	
	public ArrayList<String[]> getSyllabusArray(View v){
		// Returns null if improperly formatted
		// TODO 4: Add highlighting for error fields (Notify user of errors)
		
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

			EditText et_assignment_name = (EditText) ll_inner.getChildAt(0);
			EditText et_assignment_weight = (EditText) ll_inner.getChildAt(1);
			
			String str_assignment_name = et_assignment_name.getText().toString();
			String str_assignment_weight = et_assignment_weight.getText().toString();
			
			// Both are filled
			if (!str_assignment_name.equals("") && !str_assignment_weight.equals("")) {
				assignmentNames.add(str_assignment_name);
				assignmentWeights.add(str_assignment_weight);
			}
			// Name is filled but weight isnt
			else if (!str_assignment_name.equals("") && str_assignment_weight.equals("")) {
				properFormat = false;
				Toast.makeText(this,"Weight isn't filled in", Toast.LENGTH_SHORT).show();
				// TODO: add in highlighting
			}
			// Weight is filled but name isnt
			else if (str_assignment_name.equals("") && !str_assignment_weight.equals("")) {
				properFormat = false;
				Toast.makeText(this,"Name isn't filled in", Toast.LENGTH_SHORT).show();
				// TODO: add in highlighting
			}
			else {
				numChildren--;
			}
		}
		
		// Name and weight is empty
		if (assignmentNames.size() == 0 || assignmentWeights.size() == 0) {
			// TODO: add in highlighting
			properFormat = false;
			Toast.makeText(this,"Fill in at least one assignment", Toast.LENGTH_SHORT).show();
			
		}
		String[] lst_names = assignmentNames.toArray(new String[0]);
		String[] lst_weights = assignmentWeights.toArray(new String[0]);
		
		// Check if weights add up to 100
		if (!listOf100(lst_weights)) {
			// TODO: add in highlighting
			properFormat = false;
			Toast.makeText(this,"This doesn't add up to 100", Toast.LENGTH_SHORT).show();
		}
		
		sent.add(lst_names);
		sent.add(lst_weights);
		
		// Empty array cause no grades yet
		String[] grades = new String[lst_names.length];
		Arrays.fill(grades, "");
		
		sent.add(grades);
		
		if (properFormat) {
			return sent;
		}
		else {
			return null;
		}
	}
	
	public void write(View v) {
		ArrayList<String[]> testArrays = getSyllabusArray(v);
		if (testArrays != null) {
			csvReadWrite.writeCsvToStorage(testArrays);
		}
	}
	
	public void read(View v) {
		// courseName is a string not a string[] here
		String courseName = et_course_name.getText().toString();
		if (courseName.equals("")) {
			Toast.makeText(this,"Enter a course name", Toast.LENGTH_SHORT).show();
		}
		else {
			ArrayList<String[]> arrayList = csvReadWrite.readCsvFromStorage(courseName);
			String txt =
				Arrays.toString(arrayList.get(0)) + "\n"
					+ Arrays.toString(arrayList.get(1)) + "\n"
					+ Arrays.toString(arrayList.get(2)) + "\n"
					+ Arrays.toString(arrayList.get(3));
			
			System.out.println(txt);
		}
	}
	
	
	public boolean listOf100(String[] lst) {
		// Checks if the list adds up to 100
		double sum = 0.0;
		for (String item: lst) {
			sum += Double.parseDouble(item);
		}
		return sum == 100.0;
	}
	
}
