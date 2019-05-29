package org.iii.chihlee;

import android.content.Context;
import android.content.SharedPreferences;

import org.iii.more.common.Logs;

/**
 * Created by Jugo on 2019/5/29
 */
public abstract class ConfigHandler
{
    public static String restoreIP(Context context)
    {
        // get ip that stored by share preference
        SharedPreferences settings = context.getSharedPreferences("chihlee", 0);
        //取出name屬性的字串
        return settings.getString("ip", "127.0.0.1");
    }
    
    public static void saveIP(String strIP, Context context)
    {
        //取得SharedPreference設定("Preference"為設定檔的名稱)
        SharedPreferences settings = context.getSharedPreferences("chihlee", 0);
        //置入name屬性的字串
        settings.edit().putString("ip", strIP).apply();
        Logs.showTrace("[ActivitySetting] saveIP IP: " + strIP);
    }
}
