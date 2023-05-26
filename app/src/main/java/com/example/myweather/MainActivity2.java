package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    private String  week3,week4,mdate0,mdate1,mdate2,mdate3,mdate4;
    private TextView weekT3,weekT4,mdateT1,mdateT2,mdateT3,mdateT4,mdateT5;
    private String icon1_d,icon2_d,icon3_d,icon4_d,icon5_d,icon1_n,icon2_n,icon3_n,icon4_n,icon5_n;
    private ImageButton icon1Btn_d,icon2Btn_d,icon3Btn_d,icon4Btn_d,icon5Btn_d;
    private ImageButton icon1Btn_n,icon2Btn_n,icon3Btn_n,icon4Btn_n,icon5Btn_n;
    private TextView typeT1_d,typeT2_d,typeT3_d,typeT4_d,typeT5_d;
    private TextView typeT1_n,typeT2_n,typeT3_n,typeT4_n,typeT5_n;
    private String  fx1,fx2,fx3,fx4,fx5;
    private String  fl1,fl2,fl3,fl4,fl5;
    private TextView fxT1,fxT2,fxT3,fxT4,fxT5;
    private TextView flT1,flT2,flT3,flT4,flT5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        //去掉标题栏
        getSupportActionBar().hide();

        ImageButton btn2 = (ImageButton)findViewById(R.id.imageButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                //设置切换动画，从左边进入，右边退出
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });


        String date0 = getIntent().getStringExtra("date0");
        String date1 = getIntent().getStringExtra("date1");
        String date2 = getIntent().getStringExtra("date2");
        String date3 = getIntent().getStringExtra("date3");
        String date4 = getIntent().getStringExtra("date4");

        int length;
        length = date3.length();
        week3 = date3.substring(length-3,length);
        week4 = date4.substring(length-3,length);

        String []sdate0 = date0.split("星");
        mdate0 = sdate0[0];
        String []sdate1 = date1.split("星");
        mdate1 = sdate1[0];
        String []sdate2 = date2.split("星");
        mdate2 = sdate2[0];
        String []sdate3 = date3.split("星");
        mdate3 = sdate3[0];
        String []sdate4 = date4.split("星");
        mdate4 = sdate4[0];

        String []slow0 = getIntent().getStringExtra("mlow0").substring(3).split("℃");
        String []slow1 = getIntent().getStringExtra("mlow1").substring(3).split("℃");
        String []slow2 = getIntent().getStringExtra("mlow2").substring(3).split("℃");
        String []slow3 = getIntent().getStringExtra("mlow3").substring(3).split("℃");
        String []slow4 = getIntent().getStringExtra("mlow4").substring(3).split("℃");

        String []shigh0 = getIntent().getStringExtra("mhigh0").substring(3).split("℃");
        String []shigh1 = getIntent().getStringExtra("mhigh1").substring(3).split("℃");
        String []shigh2 = getIntent().getStringExtra("mhigh2").substring(3).split("℃");
        String []shigh3 = getIntent().getStringExtra("mhigh3").substring(3).split("℃");
        String []shigh4 = getIntent().getStringExtra("mhigh4").substring(3).split("℃");

        int low0 = Integer.valueOf(slow0[0]).intValue();
        int low1 = Integer.valueOf(slow1[0]).intValue();
        int low2 = Integer.valueOf(slow2[0]).intValue();
        int low3 = Integer.valueOf(slow3[0]).intValue();
        int low4 = Integer.valueOf(slow4[0]).intValue();
        int []lowwendu ={low0,low1,low2,low3,low4};

        int high0 = Integer.valueOf(shigh0[0]).intValue();
        int high1 = Integer.valueOf(shigh1[0]).intValue();
        int high2 = Integer.valueOf(shigh2[0]).intValue();
        int high3 = Integer.valueOf(shigh3[0]).intValue();
        int high4 = Integer.valueOf(shigh4[0]).intValue();
        int []highwendu ={high0,high1,high2,high3,high4};


        icon1_d = getIntent().getStringExtra("mtype0_d");
        icon2_d = getIntent().getStringExtra("mtype1_d");
        icon3_d = getIntent().getStringExtra("mtype2_d");
        icon4_d = getIntent().getStringExtra("mtype3_d");
        icon5_d = getIntent().getStringExtra("mtype4_d");

        icon1_n = getIntent().getStringExtra("mtype0_n");
        icon2_n = getIntent().getStringExtra("mtype1_n");
        icon3_n = getIntent().getStringExtra("mtype2_n");
        icon4_n = getIntent().getStringExtra("mtype3_n");
        icon5_n = getIntent().getStringExtra("mtype4_n");


        fx1 = getIntent().getStringExtra("mfx0");
        fx2 = getIntent().getStringExtra("mfx1");
        fx3 = getIntent().getStringExtra("mfx2");
        fx4 = getIntent().getStringExtra("mfx3");
        fx5 = getIntent().getStringExtra("mfx4");

        fl1 = getIntent().getStringExtra("mfl0");
        fl2 = getIntent().getStringExtra("mfl1");
        fl3 = getIntent().getStringExtra("mfl2");
        fl4 = getIntent().getStringExtra("mfl3");
        fl5 = getIntent().getStringExtra("mfl4");

        WeatherChartView weatherChartView = (WeatherChartView)findViewById(R.id.line_char);

        weatherChartView.setlowData(lowwendu);
        weatherChartView.sethighwendu(highwendu);

        initData();
        updateWeatherData();

    }
    public void initData(){
        weekT3 = (TextView)findViewById(R.id.mfourd);
        weekT3.setText("N/A");

        weekT4 = (TextView)findViewById(R.id.mfived);
        weekT4.setText("N/A");

        mdateT1= (TextView)findViewById(R.id.mdate1);
        mdateT1.setText("N/A");

        mdateT2= (TextView)findViewById(R.id.mdate2);
        mdateT2.setText("N/A");

        mdateT3= (TextView)findViewById(R.id.mdate3);
        mdateT3.setText("N/A");

        mdateT4= (TextView)findViewById(R.id.mdate4);
        mdateT4.setText("N/A");

        mdateT5= (TextView)findViewById(R.id.mdate5);
        mdateT5.setText("N/A");

        icon1Btn_d=(ImageButton) findViewById(R.id.manyd_icon1);
        icon2Btn_d=(ImageButton) findViewById(R.id.manyd_icon2);
        icon3Btn_d=(ImageButton) findViewById(R.id.manyd_icon3);
        icon4Btn_d=(ImageButton) findViewById(R.id.manyd_icon4);
        icon5Btn_d=(ImageButton) findViewById(R.id.manyd_icon5);

        icon1Btn_n=(ImageButton) findViewById(R.id.manyn_icon1);
        icon2Btn_n=(ImageButton) findViewById(R.id.manyn_icon2);
        icon3Btn_n=(ImageButton) findViewById(R.id.manyn_icon3);
        icon4Btn_n=(ImageButton) findViewById(R.id.manyn_icon4);
        icon5Btn_n=(ImageButton) findViewById(R.id.manyn_icon5);

        typeT1_d= (TextView)findViewById(R.id.mtyped1);
        typeT1_d.setText("N/A");

        typeT2_d= (TextView)findViewById(R.id.mtyped2);
        typeT2_d.setText("N/A");

        typeT3_d= (TextView)findViewById(R.id.mtyped3);
        typeT3_d.setText("N/A");

        typeT4_d= (TextView)findViewById(R.id.mtyped4);
        typeT4_d.setText("N/A");

        typeT5_d= (TextView)findViewById(R.id.mtyped5);
        typeT5_d.setText("N/A");

        typeT1_n= (TextView)findViewById(R.id.mtypen1);
        typeT1_n.setText("N/A");

        typeT2_n= (TextView)findViewById(R.id.mtypen2);
        typeT2_n.setText("N/A");

        typeT3_n= (TextView)findViewById(R.id.mtypen3);
        typeT3_n.setText("N/A");

        typeT4_n= (TextView)findViewById(R.id.mtypen4);
        typeT4_n.setText("N/A");

        typeT5_n= (TextView)findViewById(R.id.mtypen5);
        typeT5_n.setText("N/A");

        fxT1= (TextView)findViewById(R.id.fx1);
        fxT1.setText("N/A");

        fxT2= (TextView)findViewById(R.id.fx2);
        fxT2.setText("N/A");

        fxT3= (TextView)findViewById(R.id.fx3);
        fxT3.setText("N/A");

        fxT4= (TextView)findViewById(R.id.fx4);
        fxT4.setText("N/A");

        fxT5= (TextView)findViewById(R.id.fx5);
        fxT5.setText("N/A");

        flT1= (TextView)findViewById(R.id.fl1);
        flT1.setText("N/A");

        flT2= (TextView)findViewById(R.id.fl2);
        flT2.setText("N/A");

        flT3= (TextView)findViewById(R.id.fl3);
        flT3.setText("N/A");

        flT4= (TextView)findViewById(R.id.fl4);
        flT4.setText("N/A");

        flT5= (TextView)findViewById(R.id.fl5);
        flT5.setText("N/A");


    }


    void updateWeatherData(){

        if (week3.equals("星期天")){
            weekT3.setText("周天");
        }else if (week3.equals("星期六")){
            weekT3.setText("周六");
        }else if (week3.equals("星期五")){
            weekT3.setText("周五");
        }else if (week3.equals("星期四")){
            weekT3.setText("周四");
        }else if (week3.equals("星期三")){
            weekT3.setText("周三");
        }else if (week3.equals("星期二")){
            weekT3.setText("周二");
        }else if (week3.equals("星期一")){
            weekT3.setText("周一");
        }
        if (week4.equals("星期天")){
            weekT4.setText("周天");
        }else if (week4.equals("星期六")){
            weekT4.setText("周六");
        }else if (week4.equals("星期五")){
            weekT4.setText("周五");
        }else if (week4.equals("星期四")){
            weekT4.setText("周四");
        }else if (week4.equals("星期三")){
            weekT4.setText("周三");
        }else if (week4.equals("星期二")){
            weekT4.setText("周二");
        }else if (week4.equals("星期一")){
            weekT4.setText("周一");
        }

        Calendar cal = Calendar.getInstance();
        String smonth = String.valueOf(cal.get(Calendar.MONTH) + 1)+"月";

        mdateT1.setText(smonth+mdate0);
        mdateT2.setText(smonth+mdate1);
        mdateT3.setText(smonth+mdate2);
        mdateT4.setText(smonth+mdate3);
        mdateT5.setText(smonth+mdate4);

        typeT1_d.setText(icon1_d);
        typeT2_d.setText(icon2_d);
        typeT3_d.setText(icon3_d);
        typeT4_d.setText(icon4_d);
        typeT5_d.setText(icon5_d);

        typeT1_n.setText(icon1_n);
        typeT2_n.setText(icon2_n);
        typeT3_n.setText(icon3_n);
        typeT4_n.setText(icon4_n);
        typeT5_n.setText(icon5_n);

        fxT1.setText(fx1);
        fxT2.setText(fx2);
        fxT3.setText(fx3);
        fxT4.setText(fx4);
        fxT5.setText(fx5);

        flT1.setText(fl1);
        flT2.setText(fl2);
        flT3.setText(fl3);
        flT4.setText(fl4);
        flT5.setText(fl5);



        switch (icon1_d){
            case "晴":
                icon1Btn_d.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon1Btn_d.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon1Btn_d.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon1Btn_d.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon1Btn_d.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon1Btn_d.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon1Btn_d.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon1Btn_d.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon1Btn_d.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon2_d){
            case "晴":
                icon2Btn_d.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon2Btn_d.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon2Btn_d.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon2Btn_d.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon2Btn_d.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon2Btn_d.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon2Btn_d.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon2Btn_d.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon2Btn_d.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon3_d){
            case "晴":
                icon3Btn_d.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon3Btn_d.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon3Btn_d.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon3Btn_d.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon3Btn_d.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon3Btn_d.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon3Btn_d.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon3Btn_d.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon3Btn_d.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon4_d){
            case "晴":
                icon4Btn_d.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon4Btn_d.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon4Btn_d.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon4Btn_d.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon4Btn_d.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon4Btn_d.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon4Btn_d.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon4Btn_d.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon4Btn_d.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon5_d){
            case "晴":
                icon5Btn_d.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon5Btn_d.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon5Btn_d.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon5Btn_d.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon5Btn_d.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon5Btn_d.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon5Btn_d.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon5Btn_d.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon5Btn_d.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }


        switch (icon1_n){
            case "晴":
                icon1Btn_n.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon1Btn_n.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon1Btn_n.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon1Btn_n.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon1Btn_n.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon1Btn_n.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon1Btn_n.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon1Btn_n.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon1Btn_n.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon2_n){
            case "晴":
                icon2Btn_n.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon2Btn_n.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon2Btn_n.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon2Btn_n.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon2Btn_n.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon2Btn_n.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon2Btn_n.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon2Btn_n.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon2Btn_n.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon3_n){
            case "晴":
                icon3Btn_n.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon3Btn_n.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon3Btn_n.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon3Btn_n.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon3Btn_n.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon3Btn_n.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon3Btn_n.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon3Btn_n.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon3Btn_n.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon4_n){
            case "晴":
                icon4Btn_n.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon4Btn_n.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon4Btn_n.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon4Btn_n.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon4Btn_n.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon4Btn_n.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon4Btn_n.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon4Btn_n.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon4Btn_n.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (icon5_n){
            case "晴":
                icon5Btn_n.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                icon5Btn_n.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                icon5Btn_n.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                icon5Btn_n.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                icon5Btn_n.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                icon5Btn_n.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                icon5Btn_n.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                icon5Btn_n.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                icon5Btn_n.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }
    }
}