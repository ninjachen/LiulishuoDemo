package me.ninjachen.liulishuodemo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class BitMapRotateActivity extends Activity {

	ImageView iv1;
	ImageView iv2;
	Button rotate;
	Bitmap bm1;
	Bitmap bm2;

	String urls[] = {
			"http://llss.qiniudn.com/forum/image/525d1960c008906923000001_1397820588.jpg",
			"http://llss.qiniudn.com/forum/image/e8275adbeedc48fe9c13cd0efacbabdd_1397877461243.jpg" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rotate_image);
		iv1 = (ImageView) findViewById(R.id.first_iv);
		iv2 = (ImageView) findViewById(R.id.seconde_iv);
		rotate = (Button) findViewById(R.id.btn_merge_rotate);
		try {
			bm1 = loadImage(urls[0]);
			iv1.setImageBitmap(bm1);
			bm2 = loadImage(urls[1]);
			iv2.setImageBitmap(bm2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rotate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iv1.setImageBitmap(rotate(bm1, 90));
			}
		});

		iv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bm1 = rotate(bm1, 90);
				iv1.setImageBitmap(bm1);
			}
		});

		iv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap conBinedBitmap = combineBitmap(bm1, bm2);
				conBinedBitmap = rotate(conBinedBitmap, 90);
				 final DisplayMetrics displayMetrics = new DisplayMetrics();
			        BitMapRotateActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			        final int newHeight = displayMetrics.heightPixels;
			        final int newWidth = displayMetrics.widthPixels;
			        
			        float scaleWidth = ((float) newWidth) / conBinedBitmap.getWidth();
				    float scaleHeight = ((float) newHeight) / conBinedBitmap.getHeight();
				    // 取得想要缩放的matrix参数
				    Matrix matrix = new Matrix();
				    matrix.postScale(scaleWidth, scaleHeight);
				    // 得到新的图片
				    Bitmap fullbm = Bitmap.createBitmap(conBinedBitmap, 0, 0, conBinedBitmap.getWidth(), conBinedBitmap.getHeight(), matrix,
				      true);
//				    BitMapRotateActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
				    fullbm = rotate(fullbm, 90);
				    iv1.setScaleType(ScaleType.FIT_XY);
				    iv1.setImageBitmap(fullbm);
				    iv2.setVisibility(View.GONE);
				    rotate.setVisibility(View.GONE);
				    
				    iv1.setOnClickListener(null);
				    iv2.setOnClickListener(null);
				// Intent i = new Intent(BitMapRotateActivity.this,
				// ImageDetailActivity.class);
				// Bundle extras = new Bundle();
				// // extras.p
				// i.putExtras(extras);
				// startActivity(i);
				// bm2 = rotate(bm2, 90);
				// iv2.setImageBitmap(bm2);
			}
		});
	}

	public Bitmap loadImage(String url) throws IOException {

		URL aURL = new URL(url);
		URLConnection conn = aURL.openConnection();
		conn.connect();
		InputStream is = conn.getInputStream();

		BufferedInputStream bis = new BufferedInputStream(is);
		Bitmap bm = BitmapFactory.decodeStream(bis);
		bis.close();
		is.close();
		return bm;
	}

	/**
	 * 合并两张bitmap为一张
	 * 
	 * @param firstBitmap
	 * @param secondBitmap
	 * @return Bitmap
	 */
	public static Bitmap combineBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
		if (firstBitmap == null) {
			return null;
		}
		int bgWidth = firstBitmap.getWidth();
		int bgHeight = firstBitmap.getHeight();
		int fgWidth = secondBitmap.getWidth();
		int fgHeight = secondBitmap.getHeight();
		
		float scaleWidth = ((float) bgWidth) / fgWidth;
//	    float scaleHeight = scaleWidth;
	    // 取得想要缩放的matrix参数
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, 1);
	    // 得到新的图片
	    Bitmap newbm = Bitmap.createBitmap(secondBitmap, 0, 0, fgWidth, fgHeight, matrix,
	      true);
	    
		int maxWidth = bgWidth ;
//		int maxWidth = bgWidth > fgWidth ? bgWidth : fgWidth;
		Bitmap newmap = Bitmap
				.createBitmap(maxWidth, bgHeight + fgHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(newmap);
		canvas.drawBitmap(firstBitmap, 0, 0, null);
		canvas.drawBitmap(newbm, 0,
				bgHeight, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return newmap;
	}

	// 旋转图片
	// Rotates the bitmap by the specified degree.
	// If a new bitmap is created, the original bitmap is recycled.
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
						m, true);

			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}
}
