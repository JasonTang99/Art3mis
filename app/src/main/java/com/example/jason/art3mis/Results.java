package com.example.jason.art3mis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class Results extends AppCompatActivity {

	@Override
  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

//		TextView tv = findViewById(R.id.top_tv);
//		String sent = (String) getIntent().getSerializableExtra("Numbers");
//		tv.setText(sent);
		
		TextView tv = findViewById(R.id.top_tv);
		ArrayList<String[]> sent = (ArrayList<String[]>) getIntent().getSerializableExtra("Numbers");
		tv.setText(Arrays.toString(sent.get(2)));
		
	}
	
	
	public void moreWork(View v) {
		TextView tv1 = new TextView(this);
		tv1.setText("Oh hi mark");
		tv1.setPadding(8,8,8,8);
		
		LinearLayout linlay = findViewById(R.id.scrolling_layout);
		linlay.addView(tv1);
	}
    
    
}




