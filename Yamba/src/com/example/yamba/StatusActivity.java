package com.example.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity implements OnClickListener {

	Button postStatusButton;
	EditText status_editText;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//this line uses R to lookup activity_status.xml
		//reads XML and creates java classes and set's properties
		//after this line all widgets (buttons and controls) are in memory
		setContentView(R.layout.activity_status);
		
		
		//gets references to widgets
		postStatusButton = (Button)findViewById(R.id.postStatusButton);
		status_editText = (EditText)findViewById(R.id.editStatusText);
		
		
		//sets this class as the click listener
		postStatusButton.setOnClickListener(this);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		String text = status_editText.getText().toString();
		
		
		
		Log.d("StatusActivity","Button Clicked! Test is "+text );
		
		
		
		
	
		
		
		
	}

}
