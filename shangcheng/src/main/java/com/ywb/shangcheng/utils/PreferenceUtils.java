package com.ywb.shangcheng.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/2/24.
 */

public class PreferenceUtils {
    private static final String PREFERENCE_NAME="shopcommon";

    public static boolean putString(Context context, String key, String value){
        SharedPreferences settings=context.getSharedPreferences(PREFERENCE_NAME,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putString(key,value);
        return editor.commit();
    }

    public static String getString(Context context,String key){
        return getString(context,key,null);
    }

    public static String getString(Context context,String key,String defaultValue){
        SharedPreferences settings=context.getSharedPreferences(PREFERENCE_NAME,context.MODE_PRIVATE);
    return settings.getString(key,defaultValue);
    }
}
