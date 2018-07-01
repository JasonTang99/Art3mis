package com.example.jason.art3mis;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class NewInput extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_input);
		
		
		ScrollViewWithMaxHeight sc = findViewById(R.id.scroll_view_max_height);
		EditText et_course_name = findViewById(R.id.course_name);
		Button but = findViewById(R.id.add_work_button);
		
		int etHeight = et_course_name.getHeight();
		int butHeight = but.getHeight();
		int totalHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
		
		sc.setMaxHeight(totalHeight - etHeight - butHeight);
		
	}
	
	
	
	public void moreAssignments(View v) {
		
		final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
		
		
		
		LinearLayout scrollingAssignments = findViewById(R.id.scrolling_layout);
		
		LinearLayout newAssignment = new LinearLayout(this);
		newAssignment.setLayoutParams(params1);
		
		int totalWidth = scrollingAssignments.getWidth();
		int nameSize = totalWidth * 5 / 8;
		int paddingSize = totalWidth / 8;
		int gradeSize = totalWidth * 2 / 8;
		
		final ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(
			nameSize,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		final ViewGroup.LayoutParams params3 = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		
		
		EditText assignmentName = new EditText(this);
		assignmentName.setHint(R.string.work);
		assignmentName.setLayoutParams(params2);
//		assignmentName.setPadding(0,0, 50,0);
		
		
		EditText grade = new EditText(this);
		grade.setHint(R.string.worth);
		grade.setLayoutParams(params3);
		
		
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
	
}

