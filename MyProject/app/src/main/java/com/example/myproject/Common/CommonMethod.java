package com.example.myproject.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CommonMethod {

    /*public static String  ipConfig = "http://192.168.200.151:8989";*/
    public static String ipConfig = "http://192.168.0.116:8080";
    //public static String  ipConfig = "http://localhost:8080";
    //public static String ipConfig = "http://121.148.239.200:80";

    // 네트워크에 연결되어 있는가
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                Log.d("isconnected : ", "WIFI 로 설정됨");
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d("isconnected : ", "일반망으로 설정됨");
            }
            Log.d("isconnected : ", "OK => " + info.isConnected());
            return true;
        }else {
            Log.d("isconnected : ", "False => 데이터 통신 불가!!!" );
            return false;
        }

    }




}
