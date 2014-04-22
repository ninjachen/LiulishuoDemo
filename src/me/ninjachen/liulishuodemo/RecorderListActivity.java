package me.ninjachen.liulishuodemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecorderListActivity extends Activity {
	/** Called when the activity is first created. */
	private ListView mylistview;
	private ArrayList<String> list = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mylistview = (ListView) findViewById(R.id.listview);
		list.add("test case 1");
		list.add("test case 2");
		list.add("test case 3");
		list.add("test case 4");
		ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		mylistview.setAdapter(myArrayAdapter);
		/*
		 * mylistview.setOnTouchListener(new OnTouchListener(){
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { // TODO
		 * Auto-generated method stub if(event.getAction() ==
		 * MotionEvent.ACTION_DOWN) { mylistview.setBackgroundColor(Color.BLUE);
		 * } return false; }
		 * 
		 * });
		 */
		mylistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (list.get(arg2).equals("test case 1")) {
					Intent intent = new Intent(RecorderListActivity.this, RecorderActivity.class);
					startActivity(intent);
				}
			}

		});
	}
}