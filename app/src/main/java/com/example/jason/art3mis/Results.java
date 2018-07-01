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
		
		ScrollViewWithMaxHeight sc = findViewById(R.id.max_height_scroll);
		TextView tv = findViewById(R.id.top_tv);
		Button but = findViewById(R.id.add_work_button);
		
		int tvHeight = tv.getHeight();
		int butHeight = but.getHeight();
		int totalHeight = getScreenHeight();
		
		sc.setMaxHeight(totalHeight - tvHeight - butHeight);
		
	}
	
	
	public static int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}
	
	public static int getScreenHeight() {
		return Resources.getSystem().getDisplayMetrics().heightPixels;
	}
	
	
	public void moreWork(View v) {
		TextView tv1 = new TextView(this);
		tv1.setText("Oh hi mark");
		tv1.setPadding(8,8,8,8);
		
		LinearLayout linlay = findViewById(R.id.scrolling_layout);
		linlay.addView(tv1);
	}
    
    
}




