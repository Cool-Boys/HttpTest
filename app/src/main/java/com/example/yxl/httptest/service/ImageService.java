package com.example.yxl.httptest.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yxl on 2016-09-15.
 */
public class ImageService {
    /**
     * 获取图片
     *
     * @param path 图片路径
     * @return
     */
    public static Bitmap getImage(String path) throws Exception {
        //httpGet连接对象
//        HttpGet httpRequest = new HttpGet(path);
//        //取得HttpClient 对象
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpResponse httpResponse = httpclient.execute(httpRequest);
//        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            //取得相关信息 取得HttpEntiy
//            HttpEntity httpEntity = httpResponse.getEntity();
//            //获得一个输入流
//            InputStream is = httpEntity.getContent();
//            System.out.println(is.available());
//            System.out.println("Get, Yes!");
//            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//            return bitmap;
//        }

//没成功
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        InputStream inStream = conn.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inStream);
        return bitmap;
            /*byte[] data = StreamTool.read(inStream);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			return bitmap;*/


  //  return null;
}
}
