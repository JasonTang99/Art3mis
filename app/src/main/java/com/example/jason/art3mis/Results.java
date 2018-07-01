package com.example.jason.art3mis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Results extends AppCompatActivity {

	@Override
  protected void onCreate(Bundle savedInstanceState) {
  	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		TextView tv = findViewById(R.id.bob_is_not_good);
		String sent = (String) getIntent().getSerializableExtra("Numbers");
		tv.setText(sent);
	}
    
    
}
