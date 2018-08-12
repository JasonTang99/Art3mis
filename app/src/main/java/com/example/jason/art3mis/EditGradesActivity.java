package com.example.jason.art3mis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditGradesActivity extends AppCompatActivity {
	
	ArrayList<String[]> sent;
	
	String[] courseName;
	String[] assignmentNames;
	String[] assignmentWeights;
	String[] grades;
	
	LinearLayout ll_grades;
	
	String baseDir;
	CsvReadWrite csvReadWrite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_grades);
		
		baseDir = this.getFilesDir().toString();
		csvReadWrite = new CsvReadWrite(baseDir);
		
		sent = (ArrayList<String[]>) getIntent().getSerializableExtra("Arraylist");
		courseName = sent.get(0);
		assignmentNames = sent.get(1);
		assignmentWeights = sent.get(2);
		grades = sent.get(3);
		
		ll_grades = findViewById(R.id.ll_grades);
		fillScrollView();
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
				write(findViewById(android.R.id.content));
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
			
		}
	}
	
	public void fillScrollView(){
		for (int a = 0; a < assignmentNames.length; a++) {
			LinearLayout.LayoutParams paramEntry = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
			
			LinearLayout.LayoutParams paramQuestion = new LinearLayout.LayoutParams(
				0,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				80);
			
			LinearLayout.LayoutParams paramGrade = new LinearLayout.LayoutParams(
				0,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				20);
			
			LinearLayout ll_entry = new LinearLayout(this);
			ll_entry.setLayoutParams(paramEntry);
			
			TextView tv_question = new TextView(this);
			tv_question.setText("What did you get on " + assignmentNames[a] + "?");
			tv_question.setSingleLine();
			tv_question.setLayoutParams(paramQuestion);
			
			EditText et_grade = new EditText(this);
			et_grade.setLayoutParams(paramGrade);
			et_grade.setInputType(InputType.TYPE_CLASS_NUMBER);
			et_grade.setSingleLine();
			
			
			if (grades[a] != null && !grades[a].equals("")) {
				et_grade.setText(grades[a]);
			}
			
			ll_entry.addView(tv_question);
			ll_entry.addView(et_grade);
			
			ll_grades.addView(ll_entry);
			
		}
	}
	
	
	public ArrayList<String[]> getGrades(View v) {
//		int numGrades = ll_grades.getChildCount();
		String[] newGrades = new String[assignmentNames.length];
		
		for (int a = 0; a < assignmentNames.length; a++) {
			LinearLayout ll_inner = (LinearLayout) ll_grades.getChildAt(a);
			// The first element (index 0) is the question textview
			EditText et_grade = (EditText) ll_inner.getChildAt(1);
			String grade = et_grade.getText().toString();
			
			newGrades[a] = grade;
		}
		
		sent.remove(sent.size() - 1);
		sent.add(newGrades);
		return sent;
	}
	
	public void write(View v) {
		ArrayList<String[]> testArrays = getGrades(v);
		csvReadWrite.writeCsvToStorage(testArrays);
	}
	
}
