package me.ninjachen.liulishuodemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


/**
 * Created by ninja_chen on 13-12-30.
 */
public class ImageListAdapter extends BaseAdapter {
	
	 private static final String TAG = "Ninja_ImageFetcher";
	    private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
	    private static final String HTTP_CACHE_DIR = "http";
	    private static final int IO_BUFFER_SIZE = 8 * 1024;
	    
	private static final String IMAGE_CACHE_DIR = "images";
	public static final String EXTRA_IMAGE = "extra_image";
	Activity activity;
	List<String> picUrlList;

	// The ImageFetcher takes care of loading images into our ImageView children
	// asynchronously

	public ImageListAdapter(Activity a, List<String> picList) {
		this.activity = a;
		this.picUrlList = picList;

//		final DisplayMetrics displayMetrics = new DisplayMetrics();
//		a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//		final int height = displayMetrics.heightPixels;
//		final int width = displayMetrics.widthPixels;
//		// For this sample we'll use half of the longest width to resize our
//		// images. As the
//		// image scaling ensures the image is larger than this, we should be
//		// left with a
//		// resolution that is appropriate for both portrait and landscape. For
//		// best image quality
//		// we shouldn't divide by 2, but this will use more memory and require a
//		// larger memory
//		// cache.
//		final int longest = (height > width ? height : width) / 2;
//		mImageFetcher = new ImageFetcher(a, longest);
//		;
//
//		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
//				a, IMAGE_CACHE_DIR);
//		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
//													// app memory
//
//		mImageFetcher.addImageCache(a.getFragmentManager(), cacheParams);
//		mImageFetcher.setImageFadeIn(false);
	}

	@Override
	public int getCount() {
		return picUrlList.size();
	}

	@Override
	public Object getItem(int position) {
		return picUrlList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Pokemon pm = pokemonList.get(position);

		if (convertView == null) {
			LayoutInflater inflater;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pic_list_row, null);

		}

		ImageView pic = (ImageView) convertView.findViewById(R.id.iv_pic);
		try {
			loadImage(picUrlList.get(position), pic);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// convertView.setTag();
		return convertView;
	}
	
	@SuppressLint("NewApi")
	public void loadImage(String url, ImageView iv) throws IOException{
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		URL aURL = new URL(url);
		URLConnection conn = aURL.openConnection();
		conn.connect();

		InputStream is = conn.getInputStream();
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeStream(is, null, options);
////		options.inSampleSize = calculateInSampleSize(options, 768, 1280);
//		options.inJustDecodeBounds = false;

		BufferedInputStream bis = new BufferedInputStream(is);
		Bitmap bm = BitmapFactory.decodeStream(bis);
		  // 获得图片的宽高
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    // 设置想要的大小
	    final DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        final int newHeight = displayMetrics.heightPixels;
        final int newWidth = displayMetrics.widthPixels;
        //	    int newHeight = 480;
	    // 计算缩放比例
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = scaleWidth;
	    // 取得想要缩放的matrix参数
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    // 得到新的图片
	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
	      true);
		iv.setImageBitmap(newbm);
		bis.close();
		is.close();
		
	}
	
	   /**
     * Download a bitmap from a URL and write the content to an output stream.
     *
     * @param urlString The URL to fetch
     * @return true if successful, false otherwise
     */
    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        disableConnectionReuseIfNecessary();
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            Log.e(TAG, "Error in downloadBitmap - " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {}
        }
        return false;
    }
    
    /**
     * Workaround for bug pre-Froyo, see here for more info:
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    public static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
    
    private Bitmap Bytes2Bimap(byte[] b){  
        if(b.length!=0){  
            return BitmapFactory.decodeByteArray(b, 0, b.length);  
        }  
        else {  
            return null;  
        }  
  }  
}
