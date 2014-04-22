package me.ninjachen.liulishuodemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TranslateActivity extends Activity {

	EditText et;
	Button bt;
	final String youdaoAPI = "http://fanyi.youdao.com/openapi.do?keyfrom=Ninjatest&key=1032949244&type=data&doctype=json&version=1.1&q={keyword}";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translate);
		
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		
		et = (EditText) findViewById(R.id.et);
		bt = (Button) findViewById(R.id.btn_translate);
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String originText = et.getText().toString();
				String apiurl = youdaoAPI.replace("{keyword}", originText);
				JSONObject json = get(apiurl);
				try {
					String translatedText = json.get("translation").toString();
					Toast.makeText(TranslateActivity.this, translatedText, Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static JSONObject get(String url) {  
		JSONObject resultJsonObject = null;
        HttpClient client = new DefaultHttpClient();  
        HttpGet get = new HttpGet(url);  
        try {  
            HttpResponse res = client.execute(get);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity httpEntity = res.getEntity();  
                BufferedReader bufferedReader=new BufferedReader  
                        (new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8*1024);  
                        String line=null;                          
                        StringBuilder entityStringBuilder = new StringBuilder();  

                        while ((line=bufferedReader.readLine())!=null) {  
                            entityStringBuilder.append(line+"/n");  
                        }  
                        //利用从HttpEntity中得到的String生成JsonObject  
                        resultJsonObject=new JSONObject(entityStringBuilder.toString()); 
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
              
        } finally{  
            //关闭连接 ,释放资源  
            client.getConnectionManager().shutdown();  
        }  
        return resultJsonObject;  
    }  
	
}
