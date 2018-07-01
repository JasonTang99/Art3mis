package com.example.jason.art3mis;

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
		
	}
	
	public int numberOfAssignments = 0;
	
	public void moreAssignments(View v) {
		numberOfAssignments++;
		LinearLayout scrollingAssignments = findViewById(R.id.scrolling_layout);
		
		LinearLayout newAssignment = new LinearLayout(this);
		final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		newAssignment.setLayoutParams(params1);
		
//		EditText assignmentName = new EditText(this);
//		final ViewGroup.LayoutParams param2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
//
//		EditText grade = new EditText(this);
		TextView tv1 = new TextView(this);
		tv1.setText("Oh hi mark");
		tv1.setPadding(8,8,8,8);
		
		TextView tv2 = new TextView(this);
		tv2.setText("I did not hit her");
		tv2.setPadding(8,8,8,8);
		
		newAssignment.addView(tv1);
		newAssignment.addView(tv2);
		
		scrollingAssignments.addView(newAssignment);
	}
	
}


//<LinearLayout
//        android:layout_width="match_parent"
//					android:layout_height="wrap_content">
//<TextView
//          android:layout_width="wrap_content"
//						android:layout_height="wrap_content"
//						android:text="@string/new_input"
//						android:padding="8sp"
//						/>
//<EditText
//          android:layout_width="fill_parent"
//						android:layout_height="wrap_content"
//						android:hint="@string/grade"
//						android:inputType="numberDecimal"
//						/>
//</LinearLayout>