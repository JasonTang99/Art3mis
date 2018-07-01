package com.example.jason.art3mis;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Results extends AppCompatActivity {

	@Override
  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

//		TextView tv = findViewById(R.id.bob_is_not_good);
//		String sent = (String) getIntent().getSerializableExtra("Numbers");
//		tv.setText(sent);
		
	}
	
	
	public void moreWork(View v) {
		TextView tv1 = new TextView(this);
		tv1.setText("Oh hi mark");
		tv1.setPadding(8,8,8,8);
		
		LinearLayout linlay = findViewById(R.id.scrolling_layout);
		linlay.addView(tv1);
	}
    
    
}




