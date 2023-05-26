package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView cityNameT,temperatureT,typeT,shiduT,type1T,low1T,high1T,wuranzhishuT,type2T,low2T,high2T,type3T,low3T,high3T,week3T,timeT;
    private ImageView backgroundImg,typeIcon1,typeIcon2,typeIcon3;
    private String UpdateCityCode,cityname,todaywendu,zhishu,type;
    private String mdate0,mdate1,mdate2,mdate3,mdate4;
    private String mtype0_d,mtype0_n,mtype1_d,mtype1_n,mtype2_d,mtype2_n,mtype3_d,mtype3_n,mtype4_d,mtype4_n;
    private String mlow0,mhigh0,mlow1,mhigh1,mlow2,mhigh2,mlow3,mhigh3,mlow4,mhigh4;
    private String mfx0,mfx1,mfx2,mfx3,mfx4,mfl0,mfl1,mfl2,mfl3,mfl4;

    //实时更新主界面数据
    final Handler mhandler  = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    updateTodayWeather((TodayWeather)msg.obj);
                    break;
                default:
                    break;
            }
        }
    };


    TodayWeather todayWeather = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉标题栏
        getSupportActionBar().hide();


        Button weather = (Button) findViewById(R.id.viewweather);
        weather.getBackground().setAlpha(100);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("date0",mdate0);
                intent.putExtra("date1",mdate1);
                intent.putExtra("date2",mdate2);
                intent.putExtra("date3",mdate3);
                intent.putExtra("date4",mdate4);
                
                intent.putExtra("mtype0_d",mtype0_d);
                intent.putExtra("mtype0_n",mtype0_n);
                intent.putExtra("mtype1_d",mtype1_d);
                intent.putExtra("mtype1_n",mtype1_n);
                intent.putExtra("mtype2_d",mtype2_d);
                intent.putExtra("mtype2_n",mtype2_n);
                intent.putExtra("mtype3_d",mtype3_d);
                intent.putExtra("mtype3_n",mtype3_n);
                intent.putExtra("mtype4_d",mtype4_d);
                intent.putExtra("mtype4_n",mtype4_n);
                
                intent.putExtra("mlow0",mlow0);
                intent.putExtra("mhigh0",mhigh0);
                intent.putExtra("mlow1",mlow1);
                intent.putExtra("mhigh1",mhigh1);
                intent.putExtra("mlow2",mlow2);
                intent.putExtra("mhigh2",mhigh2);
                intent.putExtra("mlow3",mlow3);
                intent.putExtra("mhigh3",mhigh3);
                intent.putExtra("mlow4",mlow4);
                intent.putExtra("mhigh4",mhigh4);


                intent.putExtra("mfx0",mfx0);
                intent.putExtra("mfx1",mfx1);
                intent.putExtra("mfx2",mfx2);
                intent.putExtra("mfx3",mfx3);
                intent.putExtra("mfx4",mfx4);
                intent.putExtra("mfl0",mfl0);
                intent.putExtra("mfl1",mfl1);
                intent.putExtra("mfl2",mfl2);
                intent.putExtra("mfl3",mfl3);
                intent.putExtra("mfl4",mfl4);
                startActivity(intent);

                //设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });


        ImageButton btn2 = (ImageButton) findViewById(R.id.imageButton3);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityManagement.class);

                intent.putExtra("cityname",cityname);
                intent.putExtra("todaywendu",todaywendu);
                intent.putExtra("mlow1",mlow1);
                intent.putExtra("mhigh1",mhigh1);
                intent.putExtra("zhishu",zhishu);
                intent.putExtra("type",type);

                startActivity(intent);

                //设置切换动画，从右边进入，左边退出
                //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        if (new CheckNet().checkNetworkInfo(this) == CheckNet.NET_NONE) {
            System.out.println("connect failed!");
        } else {
            System.out.println("connect successed!");

            //从SharePreference文件中获取数据
            SharedPreferences mySharePre = getSharedPreferences("citycodePreferences", Activity.MODE_PRIVATE);
            String sharecode = mySharePre.getString("citycode", "");
            if (!sharecode.equals("")) {
                getWeatherDatafromNet(sharecode);
            } else {
                getWeatherDatafromNet("101040100");
            }
        }

        initView();

        //获取点击城市的citycode
        UpdateCityCode = getIntent().getStringExtra("citycode");
        if (UpdateCityCode!="-1"){
            getWeatherDatafromNet(UpdateCityCode);
        }


    }

    public void CheckWeather(View view) {
    }

    //初始化主页面数据
    public void initView(){
        cityNameT = (TextView)findViewById(R.id.detailaddress);
        cityNameT.setText("N/A");

        temperatureT = (TextView)findViewById(R.id.temperature);
        temperatureT.setText("N/A");

        type1T = (TextView)findViewById(R.id.weather_forcast1);
        type1T.setText("N/A");

        typeT = (TextView)findViewById(R.id.weathercondition);
        typeT.setText("N/A");

        shiduT = (TextView)findViewById(R.id.shidu);
        shiduT.setText("N/A");

        high1T = (TextView)findViewById(R.id.high1);
        high1T.setText("N/A");

        low1T = (TextView)findViewById(R.id.low1);
        low1T.setText("N/A");

        type2T = (TextView)findViewById(R.id.weather_forcast2);
        type2T.setText("N/A");

        high2T = (TextView)findViewById(R.id.high2);
        high2T.setText("N/A");

        low2T = (TextView)findViewById(R.id.low2);
        low2T.setText("N/A");

        type3T = (TextView)findViewById(R.id.weather_forcast3);
        type3T.setText("N/A");

        high3T = (TextView)findViewById(R.id.high3);
        high3T.setText("N/A");

        low3T = (TextView)findViewById(R.id.low3);
        low3T.setText("N/A");

        week3T = (TextView)findViewById(R.id.date3);
        week3T.setText("N/A");

        wuranzhishuT = (TextView)findViewById(R.id.pollutionlevel);
        wuranzhishuT.setText("N/A");

        timeT = (TextView)findViewById(R.id.blankshang);
        timeT.setText("N/A");

        backgroundImg = (ImageView)findViewById(R.id.main_background);

        typeIcon1 = (ImageView)findViewById(R.id.weather_icon1);
        typeIcon2 = (ImageView)findViewById(R.id.weather_icon2);
        typeIcon3 = (ImageView)findViewById(R.id.weather_icon3);

    }


    //通过城市码(citycode)连接网络，获取天气状况的xml数据
    private void getWeatherDatafromNet(String cityCode) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(address);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();

//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuffer sb = new StringBuffer();
//                    String str;
//                    while ((str = reader.readLine()) != null) {
//                        sb.append(str);
//                    }
//                    String response = sb.toString();
//                    InputStream inputStream = new ByteArrayInputStream(response.getBytes());
//
//                    if(response != null && response.startsWith("\ufeff"))
//                    {
//                        response =  response.substring(1);
//                    }

//                    todayWeather = parseXML(response);
//                    if (todayWeather!=null){
//                        Message message = new Message( );
//                        message.what = 1;
//                        message.obj=todayWeather;
//                        mhandler.sendMessage(message);
//                    }
                    todayWeather = requestXML2(in);


                    if (todayWeather!=null){
                        Message message = new Message( );
                        message.what = 1;
                        message.obj=todayWeather;
                        mhandler.sendMessage(message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    //解析天气数据
    public TodayWeather requestXML2(InputStream  inputStream) throws DocumentException {

        TodayWeather todayWeather = new TodayWeather();


        SAXReader reader = new SAXReader();
        Document document =reader.read(inputStream);
        //3.获取根节点
        Element root = document.getRootElement();
        //root.elementText("city");
        todayWeather.setCity(root.elementText("city"));

        //root.elementText("updatetime");
        todayWeather.setTime(root.elementText("updatetime"));
        //root.elementText("wendu");
        todayWeather.setTemperature(root.elementText("wendu"));
        root.elementText("fengli");
        //root.elementText("shidu");
        todayWeather.setShidu(root.elementText("shidu"));
        root.elementText("fengxiang");
        root.elementText("yesterday");

        Element yesterday = root.element("yesterday");
        //yesterday.elementText("date_1");
        todayWeather.setDate0(yesterday.elementText("date_1"));

        //mainActivity2.setMdate(yesterday.elementText("date_1"));
        yesterday.element("date_1").getText();
        //yesterday.elementText("high_1");
        todayWeather.setHigh0(yesterday.elementText("high_1"));
        //yesterday.elementText("low_1");
        todayWeather.setLow0(yesterday.elementText("low_1"));
        if (yesterday.element("day_1") != null) {
            Element day_1_ele = yesterday.element("day_1");
            //day_1_ele.elementText("type_1");
            todayWeather.setType0_d(day_1_ele.elementText("type_1"));
            //day_1_ele.elementText("fx_1");
            todayWeather.setFx0(day_1_ele.elementText("fx_1"));
            //day_1_ele.elementText("fl_1");
            todayWeather.setFl0(day_1_ele.elementText("fl_1"));
        }
        if (yesterday.element("night_1") != null) {
            Element day_1_elen = yesterday.element("night_1");
            //day_1_ele.elementText("type_1");
            todayWeather.setType0_n(day_1_elen.elementText("type_1"));
            day_1_elen.elementText("fx_1");
            day_1_elen.elementText("fl_1");
        }

        Element zhishus = root.element("zhishus");
        List<Element> list2 = zhishus.elements();
        for (int i = 0;i<list2.size();i++) {
            Element zhishu = list2.get(i);
            if (i == 7){//污染指数
                //zhishu.elementText("name");
                todayWeather.setWuran(zhishu.elementText("value"));

            }

        }


        Element forecast = root.element("forecast");
        List<Element> list = forecast.elements();
        //for (Element ele : list) {
            for (int i = 0;i<list.size();i++) {
                Element ele = list.get(i);

                if (i==0){//今天
                    // 获取每一天的天气
                    //ele.elementText("date");
                    todayWeather.setDate1(ele.elementText("date"));
                    // 获取当天的最高温度
                    //ele.elementText("high");
                    todayWeather.setHigh1(ele.elementText("high"));
                    // 获取当天的最低温度
                    //ele.elementText("low");
                    todayWeather.setLow1(ele.elementText("low"));

                    Log.d("30日",ele.elementText("date")+ele.elementText("high")+
                            ele.elementText("low"));
                    // 获取day
                    if (ele.element("day") != null) {
                        Element day_ele = ele.element("day");
                        //day_ele.elementText("type");

                        todayWeather.setType1(day_ele.elementText("type"));
                        //day_ele.elementText("fengli");
                        todayWeather.setFl1(day_ele.elementText("fengli"));
                        todayWeather.setFx1(day_ele.elementText("fengxiang"));
                        //day_ele.elementText("fengxiang");

                        Log.d("30日",day_ele.elementText("type")+day_ele.elementText("fengli")+
                                day_ele.elementText("fengxiang"));
                    }
                    if (ele.element("night") != null) {
                        Element day_elen = ele.element("night");
                        //day_elen.elementText("type");
                        todayWeather.setType1_n(day_elen.elementText("type"));
                        day_elen.elementText("fengli");
                        day_elen.elementText("fengxiang");

                        Log.d("30日",day_elen.elementText("type")+day_elen.elementText("fengli")+
                                day_elen.elementText("fengxiang"));
                    }
                }else if (i==1){//明天
                    // 获取每一天的天气
                    //ele.elementText("date");
                    todayWeather.setDate2(ele.elementText("date"));
                    // 获取当天的最高温度
                    //ele.elementText("high");
                    todayWeather.setHigh2(ele.elementText("high"));
                    // 获取当天的最低温度
                    //ele.elementText("low");
                    todayWeather.setLow2(ele.elementText("low"));

                    Log.d("31日",ele.elementText("date")+ele.elementText("high")+
                            ele.elementText("low"));
                    // 获取day
                    if (ele.element("day") != null) {
                        Element day_ele = ele.element("day");
                        //day_ele.elementText("type");
                        todayWeather.setType2(day_ele.elementText("type"));
                        //day_ele.elementText("fengli");
                        //day_ele.elementText("fengxiang");
                        todayWeather.setFl2(day_ele.elementText("fengli"));
                        todayWeather.setFx2(day_ele.elementText("fengxiang"));

                        Log.d("31日",day_ele.elementText("type")+day_ele.elementText("fengli")+
                                day_ele.elementText("fengxiang"));
                    }
                    if (ele.element("night") != null) {
                        Element day_elen = ele.element("night");
                        //day_elen.elementText("type");
                        todayWeather.setType2_n(day_elen.elementText("type"));
                        day_elen.elementText("fengli");
                        day_elen.elementText("fengxiang");

                        Log.d("30日",day_elen.elementText("type")+day_elen.elementText("fengli")+
                                day_elen.elementText("fengxiang"));
                    }
                }else if (i==2){//后天
                    // 获取每一天的天气
                    //ele.elementText("date");
                    todayWeather.setWeek3(ele.elementText("date"));
                    todayWeather.setDate3(ele.elementText("date"));
                    // 获取当天的最高温度
                    //ele.elementText("high");
                    todayWeather.setHigh3(ele.elementText("high"));
                    // 获取当天的最低温度
                    //ele.elementText("low");
                    todayWeather.setLow3(ele.elementText("low"));

                    Log.d("1日",ele.elementText("date")+ele.elementText("high")+
                            ele.elementText("low"));
                    // 获取day
                    if (ele.element("day") != null) {
                        Element day_ele = ele.element("day");
                        //day_ele.elementText("type");
                        todayWeather.setType3(day_ele.elementText("type"));
                        //day_ele.elementText("fengli");
                        //day_ele.elementText("fengxiang");
                        todayWeather.setFl3(day_ele.elementText("fengli"));
                        todayWeather.setFx3(day_ele.elementText("fengxiang"));

                        Log.d("1日",day_ele.elementText("type")+day_ele.elementText("fengli")+
                                day_ele.elementText("fengxiang"));
                    }
                    if (ele.element("night") != null) {
                        Element day_elen = ele.element("night");
                        //day_elen.elementText("type");
                        todayWeather.setType3_n(day_elen.elementText("type"));
                        day_elen.elementText("fengli");
                        day_elen.elementText("fengxiang");

                        Log.d("1日",day_elen.elementText("type")+day_elen.elementText("fengli")+
                                day_elen.elementText("fengxiang"));
                    }
                }else if (i == 3){

                    // 获取每一天的天气
                    //ele.elementText("date");
                    todayWeather.setDate4(ele.elementText("date"));
                    //todayWeather.setWeek3(ele.elementText("date"));
                    // 获取当天的最高温度
                    //ele.elementText("high");
                    todayWeather.setHigh4(ele.elementText("high"));
                    // 获取当天的最低温度
                    //ele.elementText("low");
                    todayWeather.setLow4(ele.elementText("low"));

                    Log.d("1日",ele.elementText("date")+ele.elementText("high")+
                            ele.elementText("low"));
                    // 获取day
                    if (ele.element("day") != null) {
                        Element day_ele = ele.element("day");
                        //day_ele.elementText("type");
                        todayWeather.setType4_d(day_ele.elementText("type"));
                        //todayWeather.setType3(day_ele.elementText("type"));
                        //day_ele.elementText("fengli");
                        //day_ele.elementText("fengxiang");
                        todayWeather.setFl4(day_ele.elementText("fengli"));
                        todayWeather.setFx4(day_ele.elementText("fengxiang"));

                        Log.d("1日",day_ele.elementText("type")+day_ele.elementText("fengli")+
                                day_ele.elementText("fengxiang"));
                    }
                    if (ele.element("night") != null) {
                        Element day_elen = ele.element("night");
                        //day_elen.elementText("type");
                        todayWeather.setType4_n(day_elen.elementText("type"));
                        day_elen.elementText("fengli");
                        day_elen.elementText("fengxiang");

                        Log.d("1日",day_elen.elementText("type")+day_elen.elementText("fengli")+
                                day_elen.elementText("fengxiang"));
                    }

                }
        }
        return todayWeather;
    }


    public  void getWeatherData(TodayWeather todayWeather) {

        cityname = todayWeather.getCity();
        todaywendu = todayWeather.getTemperature();
        zhishu = todayWeather.getWuran();
        type = todayWeather.getType1();


        mdate0 = todayWeather.getDate0();
        mdate1 = todayWeather.getDate1();
        mdate2 = todayWeather.getDate2();
        mdate3 = todayWeather.getDate3();
        mdate4 = todayWeather.getDate4();

        mtype0_d = todayWeather.getType0_d();
        mtype0_n = todayWeather.getType0_n();
        mtype1_d = todayWeather.getType1();
        mtype1_n = todayWeather.getType1_n();
        mtype2_d = todayWeather.getType2();
        mtype2_n = todayWeather.getType2_n();
        mtype3_d = todayWeather.getType3();
        mtype3_n = todayWeather.getType3_n();
        mtype4_d = todayWeather.getType4_d();
        mtype4_n = todayWeather.getType4_n();

        mlow0 = todayWeather.getLow0();
        mhigh0 = todayWeather.getHigh0();
        mlow1 = todayWeather.getLow1();
        mhigh1 = todayWeather.getHigh1();
        mlow2 = todayWeather.getLow2();
        mhigh2 = todayWeather.getHigh2();
        mlow3 = todayWeather.getLow3();
        mhigh3 = todayWeather.getHigh3();
        mlow4 = todayWeather.getLow4();
        mhigh4 = todayWeather.getHigh4();

        mfx0 = todayWeather.getFx0();
        mfx1 = todayWeather.getFx1();
        mfx2 = todayWeather.getFx2();
        mfx3 = todayWeather.getFx3();
        mfx4 = todayWeather.getFx4();

        mfl0 = todayWeather.getFl0();
        mfl1 = todayWeather.getFl1();
        mfl2 = todayWeather.getFl2();
        mfl3 = todayWeather.getFl3();
        mfl4 = todayWeather.getFl4();

    }

    //更新界面的天气数据
    void updateTodayWeather(TodayWeather todayWeather)
    {
        getWeatherData(todayWeather);

        cityNameT.setText(todayWeather.getCity());
        timeT.setText(todayWeather.getTime());
        temperatureT.setText(todayWeather.getTemperature()+"℃");

        type1T.setText(todayWeather.getType1());
        typeT.setText(todayWeather.getType1());
        shiduT.setText("湿度"+todayWeather.getShidu());
        high1T.setText(todayWeather.getHigh1().substring(3));
        low1T.setText(todayWeather.getLow1().substring(3));
        wuranzhishuT.setText(todayWeather.getWuran());

        type2T.setText(todayWeather.getType2());
        high2T.setText(todayWeather.getHigh2().substring(3));
        low2T.setText(todayWeather.getLow2().substring(3));

        type3T.setText(todayWeather.getType3());
        high3T.setText(todayWeather.getHigh3().substring(3));
        low3T.setText(todayWeather.getLow3().substring(3));
        int length;
        length = todayWeather.getWeek3().length();
        String week = todayWeather.getWeek3().substring(length-3,length);

        if (week.equals("星期天")){
            week3T.setText("周天");
        }else if (week.equals("星期六")){
            week3T.setText("周六");
        }else if (week.equals("星期五")){
            week3T.setText("周五");
        }else if (week.equals("星期四")){
            week3T.setText("周四");
        }else if (week.equals("星期三")){
            week3T.setText("周三");
        }else if (week.equals("星期二")){
            week3T.setText("周二");
        }else if (week.equals("星期一")){
            week3T.setText("周一");
        }


        switch (todayWeather.getType1()){
            case "晴":
                backgroundImg.setImageResource(R.drawable.qingtian);
                typeIcon1.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                backgroundImg.setImageResource(R.drawable.yintian3);
                typeIcon1.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                backgroundImg.setImageResource(R.drawable.wu);
                typeIcon1.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                backgroundImg.setImageResource(R.drawable.duoyun3);
                typeIcon1.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                backgroundImg.setImageResource(R.drawable.xiaoyu);
                typeIcon1.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                backgroundImg.setImageResource(R.drawable.zhongyu);
                typeIcon1.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                backgroundImg.setImageResource(R.drawable.dayu);
                typeIcon1.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                backgroundImg.setImageResource(R.drawable.dayu);
                typeIcon1.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                backgroundImg.setImageResource(R.drawable.leidian);
                typeIcon1.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (todayWeather.getType2()){
            case "晴":
                typeIcon2.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                typeIcon2.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                typeIcon2.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                typeIcon2.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                typeIcon2.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                typeIcon2.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                typeIcon2.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                typeIcon2.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                typeIcon2.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

        switch (todayWeather.getType3()){
            case "晴":
                typeIcon3.setImageResource(R.drawable.qingicon);
                break;
            case "阴":
                typeIcon3.setImageResource(R.drawable.yinicon);
                break;
            case "雾":
                typeIcon3.setImageResource(R.drawable.wuicon);
                break;
            case "多云":
                typeIcon3.setImageResource(R.drawable.duoyunicon);
                break;
            case "小雨":
                typeIcon3.setImageResource(R.drawable.xiaoyuicon);
                break;
            case "中雨":
                typeIcon3.setImageResource(R.drawable.zhongyuicon);
                break;
            case "大雨":
                typeIcon3.setImageResource(R.drawable.dayuicon);
                break;
            case "阵雨":
                typeIcon3.setImageResource(R.drawable.zhenyuicon);
                break;
            case "雷电":
                typeIcon3.setImageResource(R.drawable.leidianicon);
                break;
            default:
                break;
        }

    }





}