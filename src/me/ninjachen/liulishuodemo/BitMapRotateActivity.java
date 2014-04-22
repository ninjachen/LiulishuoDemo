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
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class BitMapRotateActivity extends Activity {

	ImageView iv1;
	ImageView iv2;
	Button rotate;
	Bitmap bm1;
	Bitmap bm2;

	String urls[] = { "http://llss.qiniudn.com/forum/image/525d1960c008906923000001_1397820588.jpg", "http://llss.qiniudn.com/forum/image/e8275adbeedc48fe9c13cd0efacbabdd_1397877461243.jpg" };

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
				bm2 = rotate(bm2, 90);
				iv2.setImageBitmap(bm2);
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
	 * 将多个Bitmap合并成一个图片。
	 * 
	 * @param int 将多个图合成多少列
	 * @param Bitmap
	 *            ... 要合成的图片
	 * @return
	 */
//	public static Bitmap combineBitmaps(int columns, Bitmap... bitmaps) {
//		if (columns <= 0 || bitmaps == null || bitmaps.length == 0) {
//			throw new IllegalArgumentException(
//					"Wrong parameters: columns must > 0 and bitmaps.length must > 0.");
//		}
//		int maxWidthPerImage = 0;
//		int maxHeightPerImage = 0;
//		for (Bitmap b : bitmaps) {
//			maxWidthPerImage = maxWidthPerImage > b.getWidth() ? maxWidthPerImage
//					: b.getWidth();
//			maxHeightPerImage = maxHeightPerImage > b.getHeight() ? maxHeightPerImage
//					: b.getHeight();
//		}
//		int rows = 0;
//		if (columns >= bitmaps.length) {
//			rows = 1;
//			columns = bitmaps.length;
//		} else {
//			rows = bitmaps.length % columns == 0 ? bitmaps.length / columns
//					: bitmaps.length / columns + 1;
//		}
//		Bitmap newBitmap = Bitmap.createBitmap(columns * maxWidthPerImage, rows
//				* maxHeightPerImage, Config.RGB_565);
//
//		for (int x = 0; x < rows; x++) {
//			for (int y = 0; y < columns; y++) {
//				int index = x * columns + y;
//				if (index >= bitmaps.length)
//					break;
//				newBitmap = mixtureBitmap(newBitmap, bitmaps[index],
//						new PointF(y * maxWidthPerImage, x * maxHeightPerImage));
//			}
//		}
//		return newBitmap;
//	}

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
