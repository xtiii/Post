package com.xiaotian.post;

import com.xiaotian.post.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlService
{

	public static String getHtml(String path) throws Exception
	{
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(3000);
		int code = conn.getResponseCode();
		if (code == 200)
		{
			InputStream inStream = conn.getInputStream();//通过输入流获取html数据
			byte[] data = StreamTool.readInputStream(inStream);//得到html的二进制数据
			String html = new String(data, "utf-8");
			conn.disconnect();//关闭链接
			return html;
		}
		return null;
	}

}
