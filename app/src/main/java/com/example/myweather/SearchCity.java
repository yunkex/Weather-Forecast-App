package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.FontRequest;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import java.util.ArrayList;
import java.util.List;

public class SearchCity extends AppCompatActivity {

    private MyApplication myApplication;
    private List<City> mCityList;
    private ArrayList<String> mArrayList;
    private List<String> allCitys;
    private String UpdateCityCode = "-1";
    private String loccitycode,locacitycode;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    //Button chcityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        //去掉标题栏
        getSupportActionBar().hide();

        TextView textView = (TextView)findViewById(R.id.cancel);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                //设置切换动画，从左边进入，右边退出
                //overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

            }
        });


        initLocation();

        //获取mCityList数据
        myApplication = (MyApplication)getApplication();
        mCityList = myApplication.getCityList();

        mArrayList = new ArrayList<String>();
        allCitys = new ArrayList<String>();

        //将数据存储到allCitys
        for (int i = 0; i<mCityList.size();i++){
            String cityName = mCityList.get(i).getCity();
            String provinceName = mCityList.get(i).getProvince();
            allCitys.add(cityName+"-"+provinceName+",中国");
        }


        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);

        // 配置Adaptor，将allCitys的数据显示到item中
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allCitys);
        autoCompleteTextView.setAdapter(adapter);
        //通过点击城市item项获取citycode
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String itemData = arg0.getItemAtPosition(arg2).toString();
                String cityStr = itemData.substring(0,itemData.indexOf("-"));

                for (int i = 0; i<mCityList.size();i++) {
                    String number = mCityList.get(i).getNumber();
                    String provinceName = mCityList.get(i).getProvince();
                    String cityName = mCityList.get(i).getCity();


                    if (cityStr.equals(cityName)) {
                        UpdateCityCode = number;
                    }
                }
                //存储数据到Preference,为了得到最近一次查询的城市
                SharedPreferences sharedPreferences = getSharedPreferences("citycodePreferences", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode",UpdateCityCode);
                editor.commit();

                //跳转点击城市的天气页面
                Intent intent = new Intent(SearchCity.this,MainActivity.class);
                intent.putExtra("citycode",UpdateCityCode);
                startActivity(intent);

                }
        });

        clickPopularCities();


    }
    //热门城市
    public void clickPopularCities(){


//        btn1 = (Button)findViewById(R.id.bjcity);
//        btn2 = (Button)findViewById(R.id.shcity);
//        btn3 = (Button)findViewById(R.id.gzcity);
//        btn4 = (Button)findViewById(R.id.szcity);
//        btn5 = (Button)findViewById(R.id.zhcity);
//        btn6 = (Button)findViewById(R.id.chcity);
//        btn7 = (Button)findViewById(R.id.cdcity);
//        btn7 = (Button)findViewById(R.id.xacity);




        Button chcityBtn = (Button)findViewById(R.id.chcity);

        chcityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (City city:mCityList){
                    String cityName = chcityBtn.getText().toString();
                    if (cityName.equals(city.getCity())) {
                        loccitycode = city.getNumber();
                    }
                }
                //存储数据到Preference,为了得到最近一次查询的城市
                SharedPreferences sharedPreferences = getSharedPreferences("citycodePreferences", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode",loccitycode);
                editor.commit();

                //跳转点击城市的天气页面
                Intent intent = new Intent(SearchCity.this,MainActivity.class);
                intent.putExtra("citycode",loccitycode);
                startActivity(intent);
            }
        });
    }


    //定位
    private void initLocation(){

        Button locacityBtn = (Button)findViewById(R.id.locatecity);

        LocationClient mLocationClient = new LocationClient(this);
        MyLocationListener myLocationListener = new MyLocationListener(locacityBtn);
        mLocationClient.registerLocationListener(myLocationListener);

        //获取option对象，用来初始化定位信息
        LocationClientOption option = new LocationClientOption();
        // 设置定位模式,一共三种模式，高精度（使用GPS、网络定位，精度最高），低功耗（仅使用网络定位），仅设备（仅使用GPS定位）
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置坐标系，gcj02表示国测局加密经纬度坐标，bd0911百度加密经纬度坐标，bd09百度加密墨卡托坐标
        option.setCoorType("bd09ll");
        // 设置发起定位请求的间隔时间为1000ms
        option.setScanSpan(1000);
        //设置是否反地理编码，TRUE表示会显示位置的文字信息
        option.setIsNeedAddress(true);
        //设置是否使用gps
        option.setOpenGps(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);

        //自动开始定位
        mLocationClient.start();

        //跳转到定位城市天气界面
        Button locateBtn = (Button)findViewById(R.id.locatecity);
        locateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (City city:mCityList){
                    String cityName = locateBtn.getText().toString();
                    if (cityName.equals(city.getCity())) {
                        locacitycode = city.getNumber();
                    }
                }
                //存储数据到Preference,为了得到最近一次查询的城市
                SharedPreferences sharedPreferences = getSharedPreferences("citycodePreferences", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode",locacitycode);
                editor.commit();

                //跳转点击城市的天气页面
                Intent intent = new Intent(SearchCity.this,MainActivity.class);
                intent.putExtra("citycode",locacitycode);
                startActivity(intent);
                mLocationClient.stop();
            }
        });

    }

}


class MyLocationListener implements BDLocationListener  {
    Button locBtn;

    MyLocationListener(Button locBtn)
    {
        this.locBtn = locBtn;
    }

    String cityName;

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        cityName = bdLocation.getCity();
        Log.d("Locate",cityName);
        locBtn.setText(cityName);
    }
}

