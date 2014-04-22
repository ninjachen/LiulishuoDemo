package me.ninjachen.liulishuodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button anim;
	Button recorder;
	Button network;
	Button translate;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		anim = (Button)findViewById(R.id.btn_anim);
		recorder = (Button)findViewById(R.id.btn_recorder);
		network = (Button)findViewById(R.id.btn_network);
		translate = (Button)findViewById(R.id.btn_translate);
		
		anim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, AnimActivity.class);
				startActivity(i);
			}
		});
		
		recorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, RecorderListActivity.class);
				startActivity(i);
			}
		});
		
		network.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, ImageActivity.class);
				startActivity(i);
			}
		});
	}

}
