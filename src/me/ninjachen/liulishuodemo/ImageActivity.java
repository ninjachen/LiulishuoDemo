package me.ninjachen.liulishuodemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ImageActivity extends Activity{
	 private ListView listview;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_image_list);
	        listview = (ListView) findViewById(R.id.hatchlist);
	        setAdapt();
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();

	        setAdapt();
	    }

	    private void setAdapt() {
	        List<String> l = new ArrayList<String>();
	        l.add("http://llss.qiniudn.com/forum/image/525d1960c008906923000001_1397820588.jpg");
	        l.add("http://llss.qiniudn.com/forum/image/e8275adbeedc48fe9c13cd0efacbabdd_1397877461243.jpg");
	        l.add("http://llss.qiniudn.com/uploads/forum/topic/attached_img/5350db2ffcfff258b500dcb2/_____2014-04-18___3.52.33.png");
	        ImageListAdapter pokemonListAdapter = new ImageListAdapter(this, l);
	        listview.setAdapter(pokemonListAdapter);
	    }
}
