package com.xiaotian.post;

import com.xiaotian.post.*;
import android.app.*;
import android.os.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends Activity 
{

	Handler handler = new Handler(){

		public void handleMessage(android.os.Message msg)
		{
			String result = (String) msg.obj;
			tv1.setText(result);
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
					String url=ed1.getText().toString().trim();
					String par=ed2.getText().toString().trim();
					if (TextUtils.isEmpty(url))
					{
						Toast.makeText(MainActivity.this, "请输入链接", 0).show();
						return;
					}
					Obtain(url, par);
				}
			});
    }

	private void Obtain(final String url, final String par)
	{
		// TODO: Implement this method
		new Thread(){

			@Override
			public void run()
			{
				// TODO: Implement this method
				try
				{
					String result = HtmlService.getHtml(url);
					Message msg = new Message();
					msg.obj = result;
					handler.sendMessage(msg);
				}
				catch (Exception e)
				{
					Log.v("MainActivity", e.toString());
					Toast.makeText(MainActivity.this, "网络连接失败", 0).show();
				}
			}
		}.start();
    }

}
