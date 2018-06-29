package com.example.jason.art3mis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Numbers extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numbers);
		
		String[] textArray = {"One", "Two", "Three", "Four", "Five", "6", "7", "8", "9", "10"};
		
		
//		LinearLayout linearLayout = new LinearLayout(this);
//		linearLayout.setOrientation(LinearLayout.VERTICAL);
//		setContentView(linearLayout);
//		ScrollView sv = new ScrollView(this);
		LinearLayout linlay = (LinearLayout) findViewById(R.id.linlay);
		
		for( int i = 0; i < textArray.length; i++ )
		{
			TextView textView = new TextView(this);
			textView.setTextSize(50);
			textView.setText(textArray[i]);
			linlay.addView(textView);
		}
	}
}
