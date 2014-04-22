package me.ninjachen.liulishuodemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private static Handler handler;
	private static final int HIDE_IV_MAIN = 0;
	private ImageView ivMain;
	private Animation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ivMain = (ImageView)findViewById(R.id.iv_main);
		
//		AnimationSet animationSet = new AnimationSet(true);
//		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
//		alphaAnimation.setDuration(1000);
//		alphaAnimation.setStartOffset(10000);
//		animationSet.addAnimation(alphaAnimation);
//		//animationSet.setStartOffset(10000);
//		animationSet.setFillBefore(false);
//		animationSet.setFillAfter(true);
		
		
		animation = AnimationUtils.loadAnimation(this, R.anim.slide_top_to_bottom);
		
		ivMain.setAnimation(animation);
//		ivMain.startAnimation(animation);
		new MyCountDownTimer(3000, 1000).start();
		
		handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case HIDE_IV_MAIN:
					ivMain.setVisibility(View.GONE);
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	
	class MyCountDownTimer extends CountDownTimer {

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
//			MainActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);   
			Message msg = new Message();
			msg.what = HIDE_IV_MAIN;
			handler.sendMessage(msg);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			ivMain.startAnimation(animation);
			int i  = 0 ;
			Log.i("Demo", ++i + "次");
		}
		
	}

}
