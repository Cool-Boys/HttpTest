package com.example.yxl.httptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.yxl.httptest.service.BitmapCache;
import com.example.yxl.httptest.service.ImageService;
import com.example.yxl.httptest.service.VolleySingleton;

import java.util.Map;

public class HttpImageActivity extends AppCompatActivity {
    private EditText pathText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_image);
        pathText = (EditText) this.findViewById(R.id.path);
        imageView = (ImageView) this.findViewById(R.id.imageView);
    }

    public void showimage(View v) {
        String path = pathText.getText().toString();
        try {
            Bitmap bitmap = ImageService.getImage(path);
            imageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    public void volleyshowimage(View v) {
        String path = pathText.getText().toString();
        try {
            RequestQueue mRequestQueue = VolleySingleton.getVolleySingleton(getApplicationContext()).getRequestQueue();
            ImageRequest imgRequest = new ImageRequest(path,
                    new Response.Listener<Bitmap>() {
                        /**
                         * 加载成功
                         * @param arg0
                         */
                        @Override
                        public void onResponse(Bitmap arg0) {
                            imageView.setImageBitmap(arg0);
                        }
                    }, 300, 200, Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        //加载失败
                        @Override
                        public void onErrorResponse(VolleyError arg0) {
//                            imageView.setImageResource(R.drawable.ic_launcher);
                        }
                    });
            //将图片加载放入请求队列中去
            //将请求添加到队列
            mRequestQueue.add(imgRequest);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    public void volley2showimage(View v) {
        String path = pathText.getText().toString();
        try {
            RequestQueue mRequestQueue = VolleySingleton.getVolleySingleton(getApplicationContext()).getRequestQueue();
            ImageLoader imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
            imageLoader.get(path, listener);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
        }
    }
}
