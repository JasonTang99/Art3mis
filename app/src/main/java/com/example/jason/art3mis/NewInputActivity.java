package com.example.jason.art3mis;

import android.content.Intent;
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
		
		baseDir = this.getFilesDir().toString();
		csvReadWrite = new CsvReadWrite(baseDir);
		
		et_course_name = findViewById(R.id.course_name);
		sv_with_max = findViewById(R.id.scroll_view_max_height);
		ll_scroll = findViewById(R.id.scrolling_layout);
		b_work = findViewById(R.id.add_work);
		
		int et_Height = et_course_name.getHeight();
		int b_work_height = b_work.getHeight();
		totalHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
		
		sv_with_max.setMaxHeight(totalHeight - et_Height - b_work_height);
		
		moreAssignments();
		
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
				// Gets the activity view
				View content = findViewById(android.R.id.content);
				boolean flag = write(content);
				if (flag) {
					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);
				}
			default:
				return super.onOptionsItemSelected(item);
			
		}
	}
	
	public void addAssignment(View v) {
		moreAssignments();
	}
	
	public void moreAssignments() {
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		
		LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
			0,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		paramName.weight = 60;
		
		LinearLayout.LayoutParams paramWeight = new LinearLayout.LayoutParams(
			0,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		paramWeight.weight = 35;
		
		LinearLayout newAssignment = new LinearLayout(this);
		newAssignment.setLayoutParams(params);
		
		EditText assignmentName = new EditText(this);
		assignmentName.setHint(R.string.work);
		assignmentName.setLayoutParams(paramName);
		
		EditText assignmentWeight = new EditText(this);
		assignmentWeight.setHint(R.string.worth);
		assignmentWeight.setLayoutParams(paramWeight);
		assignmentWeight.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		
		// TODO: Allow fractional input
//		assignmentWeight.setKeyListener(DigitsKeyListener.getInstance("0123456789./-"));
		
		assignmentWeight.setSingleLine();
		assignmentName.setSingleLine();
		
		newAssignment.addView(assignmentName);
		newAssignment.addView(assignmentWeight);
		
		ll_scroll.addView(newAssignment);
		
		
		
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
			// Name is filled but weight isn't
			else if (!str_assignment_name.equals("") && str_assignment_weight.equals("")) {
				properFormat = false;
				Toast.makeText(this,"Weight isn't filled in", Toast.LENGTH_SHORT).show();
				// TODO: add in highlighting
			}
			// Weight is filled but name isn't
			else if (str_assignment_name.equals("") && !str_assignment_weight.equals("")) {
				properFormat = false;
				Toast.makeText(this,"Name isn't filled in", Toast.LENGTH_SHORT).show();
				// TODO: add in highlighting
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
	
	public boolean write(View v) {
		ArrayList<String[]> testArrays = getSyllabusArray(v);
		if (testArrays != null) {
			csvReadWrite.writeCsvToStorage(testArrays);
			return true;
		}
		return false;
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

