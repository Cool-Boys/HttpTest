package com.example.yxl.httptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtHttp;
    private TextView imageHttp;
    private TextView MP3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtHttp= (TextView) findViewById(R.id.textView);
        imageHttp= (TextView) findViewById(R.id.txtPic);
        MP3= (TextView) findViewById(R.id.txtMp3);
        imageHttp.setOnClickListener(this);
        txtHttp.setOnClickListener(this);
        MP3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.textView:
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.txtPic:
                Intent intent1=new Intent(Main2Activity.this,HttpImageActivity.class);
                startActivity(intent1);
                break;
            case R.id.txtMp3:
                Intent intent2=new Intent(Main2Activity.this,mp3Activity.class);
                startActivity(intent2);
                break;
        }
    }
}
