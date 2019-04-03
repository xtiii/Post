package com.xiaotian.post;

import com.xiaotian.post.*;
import android.app.*;
import android.os.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import org.json.*;

public class MainActivity extends Activity 
{

	Handler handler = new Handler(){

		public void handleMessage(android.os.Message msg)
		{
			String result = (String) msg.obj;
			//tv1.setText(result);
			JsonAnalysis(result);
		}
	};

	private EditText ed1,ed2;
	private Button bt1;
	private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		ed1 = (EditText) findViewById(R.id.url);
		ed2 = (EditText) findViewById(R.id.par);
		bt1 = (Button) findViewById(R.id.Sub);
		tv1 = (TextView) findViewById(R.id.Rever);

		bt1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					String url="https://api.hibai.cn/api/index/index";//ed1.getText().toString().trim();
					String par="TransCode=020117&OpenId=Test";//ed2.getText().toString().trim();
					if (TextUtils.isEmpty(url))
					{
						Toast.makeText(MainActivity.this, "请输入链接", 0).show();
						return;
					}
					else if (TextUtils.isEmpty(par))
					{
						tv1.setText("正在获取请稍候...");
						getObtain(url);
					}
					else
					{
						tv1.setText("正在提交请稍候...");
						postObtain(url, par);
					}

				}
			});
    }

	private void getObtain(final String url)
	{
		// TODO: Implement this method
		new Thread(){

			@Override
			public void run()
			{
				// TODO: Implement this method
				try
				{
					String result = GetService.getHtml(url);
					Message msg = new Message();
					msg.obj = result;
					handler.sendMessage(msg);
				}
				catch (Exception e)
				{
					Log.v("getObtain", e.toString());
					Toast.makeText(MainActivity.this, "网络连接失败", 0).show();
				}
			}
		}.start();
    }

	private void postObtain(final String url, final String par)
	{
		// TODO: Implement this method
		new Thread(){

			@Override
			public void run()
			{
				// TODO: Implement this method
				try
				{
					String result = PostService.getHtml(url, par);
					Message msg = new Message();
					msg.obj = result;
					handler.sendMessage(msg);
				}
				catch (Exception e)
				{
					Log.e("postObtain", e.toString());
					Toast.makeText(MainActivity.this, "网络连接失败", 0).show();
				}
			}
		}.start();
	}

	private void JsonAnalysis(String result)
	{
		try
		{
			tv1.setText(null);
			JSONObject NetMusic = new JSONObject(result);
			String ErrCode = NetMusic.getString("ErrCode");
			if (ErrCode.equals("OK"))
			{
				JSONArray array = NetMusic.getJSONArray("Body");
				for (int i = 0; i < array.length(); i++)
				{
					JSONObject item = array.getJSONObject(i);
					tv1.append(item.getString("title"));
					tv1.append("\n");
				}
				tv1.setText(tv1.getText().toString().trim());
				return;
			}

		}
		catch (Exception e)
		{
			Log.e("JsonAnalysis", e.toString());
		}
	}
}
