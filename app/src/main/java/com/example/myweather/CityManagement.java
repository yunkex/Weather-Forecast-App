package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CityManagement extends AppCompatActivity {

    private String cityType;

    private ImageView img;
    private String cityname,todaywendu,mlow1,mhigh1,zhishu,type;
    private TextView citynameT,todaywenduT,mlow1T,mhigh1T,zhishuT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city1);


        //去掉标题栏
        getSupportActionBar().hide();

        ImageButton btn2 = (ImageButton)findViewById(R.id.imageButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                //设置切换动画，从左边进入，右边退出
                //overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        //设置共享元素动画
        Button gv = (Button)findViewById(R.id.seach_view);
        gv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityManagement.this,SearchCity.class);
                //startActivity(intent);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(CityManagement.this,gv,"myimageview").toBundle());
            }
        });

        //设置共享元素动画
        ImageView gv2 = (ImageView)findViewById(R.id.img);
        gv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityManagement.this,MainActivity.class);
                //startActivity(intent);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(CityManagement.this,gv2,"testImg").toBundle());
            }
        });

        cityname = getIntent().getStringExtra("cityname");
        todaywendu = getIntent().getStringExtra("todaywendu");
        String []slow = getIntent().getStringExtra("mlow1").substring(3).split("℃");
        String []shigh = getIntent().getStringExtra("mhigh1").substring(3).split("℃");
        zhishu = getIntent().getStringExtra("zhishu");
        type = getIntent().getStringExtra("type");
        mlow1 = slow[0];
        mhigh1 = shigh[0];

        initCityMange();
        updateData();


    }

    public void  initCityMange(){

        citynameT = (TextView)findViewById(R.id.city);
        citynameT.setText("N/A");

        todaywenduT = (TextView)findViewById(R.id.wendu);
        todaywenduT.setText("N/A");

        zhishuT = (TextView)findViewById(R.id.wuran);
        zhishuT.setText("N/A");

        citynameT = (TextView)findViewById(R.id.city);
        citynameT.setText("N/A");

        mlow1T = (TextView)findViewById(R.id.high);
        mlow1T.setText("N/A");

        mhigh1T = (TextView)findViewById(R.id.low);
        mhigh1T.setText("N/A");

        img = (ImageView)findViewById(R.id.img);

    }
    public void updateData(){

        citynameT.setText(cityname);
        todaywenduT.setText(todaywendu+"°");
        mlow1T.setText(mlow1+"°");
        mhigh1T.setText(mhigh1+"°");
        zhishuT.setText(zhishu);

        switch (type){
            case "晴":
                img.setImageResource(R.drawable.qingtian);
                break;
            case "阴":
                img.setImageResource(R.drawable.yintian3);
                break;
            case "雾":
                img.setImageResource(R.drawable.wu);
                break;
            case "多云":
                img.setImageResource(R.drawable.duoyun3);
                break;
            case "小雨":
                img.setImageResource(R.drawable.xiaoyu);
                break;
            case "中雨":
                img.setImageResource(R.drawable.zhongyu);
                break;
            case "大雨":
                img.setImageResource(R.drawable.dayu);
                break;
            case "阵雨":
                img.setImageResource(R.drawable.dayu);
                break;
            case "雷电":
                img.setImageResource(R.drawable.leidian);
                break;
            default:
                break;
        }

    }



}