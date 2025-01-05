package com.nextgen.eriksha;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalPreference {

    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    private Context context;
    public GlobalPreference(Context context) {
        sharedPreference = context.getSharedPreferences("sample",MODE_PRIVATE);
        editor = sharedPreference.edit();
    }
    public void saveIP(String ipaddress){
        editor.putString("ip",ipaddress);
        editor.apply();
    }
    public String getIP(){
        return  sharedPreference.getString("ip","");
    }

    public void saveID(String uid){
        editor.putString("user_id",uid);
        editor.apply();
    }
    public String getID(){
        return  sharedPreference.getString("user_id","");
    }

    public void saveName(String name){
        editor.putString("name",name);
        editor.apply();
    }
    public String getName(){
        return  sharedPreference.getString("name","");
    }

    public void saveLatitude(String latitude){
        editor.putString("latitude",latitude);
        editor.apply();
    }
    public String getLatitude(){
        return  sharedPreference.getString("latitude","");
    }

    public void saveLongitude(String longitude){
        editor.putString("longitude",longitude);
        editor.apply();
    }
    public String getLongitude(){
        return  sharedPreference.getString("longitude","");
    }

    public void saveAccountNo(String accountNo){
        editor.putString("accountNo",accountNo);
        editor.apply();
    }
    public String getAccountNo(){
        return  sharedPreference.getString("accountNo","");
    }

}
