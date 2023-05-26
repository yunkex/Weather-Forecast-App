package com.example.myweather;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MyApplication extends Application {

    List<City> cityList;
    CityDb cityDb;


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("zhqMyApplication","OnCreate");
        cityDb = copyFileFromAssets("city.db");
        initCityList();


    }

    private boolean prepareCityList()
    {

        cityList = cityDb.getCityList();
        setCityList(cityList);

        for(City city:cityList)
        {
            String cityName = city.getCity();
            Log.d("CityDB",cityName);
        }
        return true;
    }


    public void initCityList()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }
        }).start();
    }

    public List<City> getCityList()
    {
        return cityList;
    }

    public void setCityList(List<City> cityLists){

        cityList = cityLists;

    }



    /**
     * 将assets目录下的文件拷贝到sd上
     *
     * @return 存储数据库的地址
     */
    public CityDb copyFileFromAssets(String SqliteFileName) {

        // 第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/<db_name>
        String path = "data/data/" + getPackageName() + "/databases";
        File dir = new File(path);

        //判断如果文件夹不存在，或者不是一个目录，那么就创建一个文件夹
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }

        //获取file对象
        File file = new File(dir, SqliteFileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。
        if (!file.exists()) {
            try {
                file.createNewFile();

                inputStream = getClass().getClassLoader().getResourceAsStream("assets/" + SqliteFileName);
                outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len;

                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return new CityDb(this,path);
    }

}
