package com.example.jason.art3mis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class FileOptionsActivity extends AppCompatActivity {
	
	String[] courseName;
	String[] assignmentNames;
	String[] assignmentWeights;
	String[] grades;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		ArrayList<String[]> sent = (ArrayList<String[]>) getIntent().getSerializableExtra("Arraylist");
		courseName = sent.get(0);
		assignmentNames = sent.get(1);
		assignmentWeights = sent.get(2);
		grades = sent.get(3);
		
		if (grades == new String[0]) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		
		setContentView(R.layout.activity_file_options);
		
		// In case of no grades, i.e. grades=[], automatically start the write grades
		// Otherwise give options to modify
		
	}
	
	
}
