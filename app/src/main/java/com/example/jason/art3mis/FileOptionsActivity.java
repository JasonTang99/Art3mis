package com.example.jason.art3mis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FileOptionsActivity extends AppCompatActivity {
	
	ArrayList<String[]> sent;
	
	String[] courseName;
	String[] assignmentNames;
	String[] assignmentWeights;
	String[] grades;
	
	DecimalFormat df = new DecimalFormat("#.####");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sent = (ArrayList<String[]>) getIntent().getSerializableExtra("Arraylist");
		courseName = sent.get(0);
		assignmentNames = sent.get(1);
		assignmentWeights = sent.get(2);
		grades = sent.get(3);
		
		if (isEmpty(grades)) {
			Intent intent = new Intent(this, EditGradesActivity.class);
			intent.putExtra("Arraylist", sent);
			startActivity(intent);
		}
		
		setContentView(R.layout.activity_file_options);
		
	}
	
	public void openGrades(View v) {
		Intent intent = new Intent(this, EditGradesActivity.class);
		intent.putExtra("Arraylist", sent);
		startActivity(intent);
	}
	
	public void openHowMuch(View v) {
		// TODO 8: Change this intent to howMuchActivity once created
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("Arraylist", sent);
		startActivity(intent);
	}
	
	public void calcCurrentGrade(View v) {
		double total1 = 0.0;
		double total2 = 0.0;
		
		for (int a = 0; a < grades.length; a++) {
			if (!grades[a].equals("")) {
				total1 += Double.parseDouble(grades[a]) * Double.parseDouble(assignmentWeights[a]);
				total2 += Double.parseDouble(assignmentWeights[a]);
			}
		}
		
		double current_grade = 0.0;
		if (total2 == 0) {
			Toast.makeText(this, "Your file is broken, rip", Toast.LENGTH_SHORT).show();
		}
		else {
			current_grade = total1/total2;
		}
		
		TextView tv_current_grade = findViewById(R.id.tv_current_grade);
		tv_current_grade.setText("Your current grade is: " + df.format(current_grade));
	}
	
	
	public boolean isEmpty(String[] lst) {
		if (lst.length == 0) {
			return true;
		}
		
		boolean empty = true;
		for (String a: lst) {
			if ( a != null && !(a.equals("")) ) {
				empty = false;
				break;
			}
		}
		
		return empty;
	}
	
	
}
