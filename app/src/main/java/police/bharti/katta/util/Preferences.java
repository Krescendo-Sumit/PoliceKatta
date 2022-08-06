package police.bharti.katta.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;


public class Preferences {


    public static final String SELECTEDSARAVID = "SelectedSaravId" ;
    public static final String SELECTEDCHALUGHADAMODIID = "SelectedChaluGhadamodiId";
    public static final String SELECTEDTESTMENU = "SelectedTestMenu";
    public static final String SELECTEDPAPERID = "SelectedPaperId";
    public static final String SELECTEDPAPERFILE = "SelectedFile";

    public static final String LIVESELECTEDPAPERID = "LivePaperId";
    public static final String LIVESELECTEDPAPERFILE = "LiveSelectedFile";


    public static final String SELECTEDIMPMENU = "SelectedImpNotesMenu";
    public static final String SELECTEDMAGILPRASHNPATRIKA = "SelectedMagilPrasnpatrika";
    public static final String SELECTEDVIDEOID = "SelectedVideoId";
    public static final String LIVESELECTEDPAPERDATE ="live_test_date" ;
    public static final String LIVESELECTEDPAPERTIME = "live_test_time";
    public static final String LIVESELECTEDPAPERDURATION = "live_test_duration";
    public static final String USER_ID = "user_code";
    public static final String USER_INSTALL_ID ="usaer_installed_id" ;
    public static final String SELECTEDSARAVMASTERNAME = "SELECTED_SARAV_MASTER";
    public static final String SELECTEDMAGILPRASHNPATRIKAMENU = "SLECTEDMAGOLPRASHNPATRIKAMENU";
    public static final String SELECTEDMAGILPRASHNPATRIKAHEADING = "SLECTEDMAGOLPRASHNPATRIKAHEADING";
    public static final String SELECTEDTESTSERIESHEADING ="SelectedTestSeriesHeading" ;


    public Preferences(Context context) {
    }


    public static final String APP_PREF = "MahycoRetailPreferences";
    public static SharedPreferences sp;
    public static final String USER_MOBILE="UserMobile";
    public static final String SELECTEDEXAMID="SelectedExamId";

    public static void saveIntegerArrayList(Context context, String key, ArrayList<Integer> value) {
        try {
            sp = context.getSharedPreferences(APP_PREF, 0);
            SharedPreferences.Editor edit = sp.edit();


            String s = "";
            for (Integer i : value) {
                s += i + ",";
            }

            edit.putString(key, s);
            edit.commit();
        } catch (Exception e) {

        }
    }

    public static ArrayList<Integer> getIntegerArrayList(Context context, String key) {

        sp = context.getSharedPreferences(APP_PREF, 0);
        String s = sp.getString(key, "");
        StringTokenizer st = new StringTokenizer(s, ",");
        ArrayList<Integer> result = new ArrayList<Integer>();
        while (st.hasMoreTokens()) {
            result.add(Integer.parseInt(st.nextToken()));
        }
        return result;
    }

    public static void saveStringArrayList(Context context, String key, ArrayList<String> value) {
        try {
            sp = context.getSharedPreferences(APP_PREF, 0);
            SharedPreferences.Editor edit = sp.edit();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();

            String jsonData = gson.toJson(value, type);
            edit.putString(key, jsonData);

            edit.commit();
        } catch (Exception e) {

        }
    }

    public static ArrayList<String> getStringArrayList(Context context, String key) {
        Gson gson = new Gson();
        sp = context.getSharedPreferences(APP_PREF, 0);
        String json = sp.getString(key, null);
        Log.d("cart", "getArrayList: " + json);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        // ArrayList<JSONObject> arrayList = gson.fromJson(json, type);
        return gson.fromJson(json, type);
    }


    public static void save(Context context, String key, String value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void saveBool(Context context, String key, boolean value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static Boolean getBool(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        boolean val = sp.getBoolean(key, false);
        return val;
    }

    public static float getFloat(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        float userId = sp.getFloat(String.valueOf(key), 0);
        return userId;
    }

    public static void saveFloat(Context context, String key, float value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public static long getLong(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        long value = sp.getLong(String.valueOf(key), 0);
        return value;
    }

    public static void saveLong(Context context, String key, long value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }


    public static int getInt(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        int userId = sp.getInt(String.valueOf(key), 0);
        return userId;
    }

    public static void saveInt(Context context, String key, int value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static String get(Context context, String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        String userId = sp.getString(key, "");
        return userId;
    }

    public static void clearPreference(Context context) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }


}
