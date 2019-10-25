package com.reny.git.rart.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.reny.git.mvvmlib.utils.AppUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.StringRes;

/**
 * Created by admin on 2017/7/4.
 */

public class SPUtils {

    //private static final String TAG = "SPUtils";

    private static SPUtils sSPUtils;

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;

    private static final String DEFAULT_SP_NAME = "SharedData";
    private static final int DEFAULT_INT = 0;
    private static final float DEFAULT_FLOAT = 0.0f;
    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final Set<String> DEFAULT_STRING_SET = new HashSet<>(0);

    private static String mCurSPName = DEFAULT_SP_NAME;

    private SPUtils() {
        this(DEFAULT_SP_NAME);
    }

    private SPUtils(String spName) {
        sSharedPreferences = AppUtils.self().getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        sEditor = sSharedPreferences.edit();
        mCurSPName = spName;
        //Log.i(TAG, "SPUtils: " + mCurSPName);
    }

    public static SPUtils init() {
        if (sSPUtils == null || !mCurSPName.equals(DEFAULT_SP_NAME)) {
            sSPUtils = new SPUtils();
        }
        return sSPUtils;
    }

    public static SPUtils init(String spName) {
        if (sSPUtils == null) {
            sSPUtils = new SPUtils(spName);
        } else if (!spName.equals(mCurSPName)) {
            sSPUtils = new SPUtils(spName);
        }
        return sSPUtils;
    }

    public static SPUtils init(@StringRes int spNameKey) {
        return init(getStr(spNameKey));
    }

    public SPUtils put(@StringRes int key, Object value) {
        return put(getStr(key), value);
    }

    public SPUtils put(String key, Object value) {
        if (value instanceof String) {
            sEditor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            sEditor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            sEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            sEditor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            sEditor.putLong(key, (Long) value);
        } else {
            sEditor.putString(key, value.toString());
        }
        sEditor.apply();
        return sSPUtils;
    }

    public Object get(@StringRes int key, Object defaultObject) {
        return get(getStr(key), defaultObject);
    }

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sSharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sSharedPreferences.getInt(key, (int) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sSharedPreferences.getBoolean(key, (boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sSharedPreferences.getFloat(key, (float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sSharedPreferences.getLong(key, (long) defaultObject);
        }
        return null;
    }

    public SPUtils putInt(String key, int value) {
        sEditor.putInt(key, value);
        sEditor.apply();
        return this;
    }

    public SPUtils putInt(@StringRes int key, int value) {
        return putInt(getStr(key), value);
    }

    public int getInt(@StringRes int key) {
        return getInt(getStr(key));
    }

    public int getInt(@StringRes int key, int defValue) {
        return getInt(getStr(key), defValue);
    }

    public int getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }


    public int getInt(String key, int defValue) {
        return sSharedPreferences.getInt(key, defValue);
    }

    public SPUtils putFloat(@StringRes int key, float value) {
        return putFloat(getStr(key), value);
    }

    public SPUtils putFloat(String key, float value) {
        sEditor.putFloat(key, value);
        sEditor.apply();
        return sSPUtils;
    }

    public float getFloat(String key) {
        return getFloat(key, DEFAULT_FLOAT);
    }

    public float getFloat(String key, float defValue) {
        return sSharedPreferences.getFloat(key, defValue);
    }

    public float getFloat(@StringRes int key) {
        return getFloat(getStr(key));
    }

    public float getFloat(@StringRes int key, float defValue) {
        return getFloat(getStr(key), defValue);
    }

    public SPUtils putLong(@StringRes int key, long value) {
        return putLong(getStr(key), value);
    }

    public SPUtils putLong(String key, long value) {
        sEditor.putLong(key, value);
        sEditor.apply();
        return sSPUtils;
    }

    public long getLong(String key) {
        return getLong(key, DEFAULT_INT);
    }

    public long getLong(String key, long defValue) {
        return sSharedPreferences.getLong(key, defValue);
    }

    public long getLong(@StringRes int key) {
        return getLong(getStr(key));
    }

    public long getLong(@StringRes int key, long defValue) {
        return getLong(getStr(key), defValue);
    }

    public SPUtils putString(@StringRes int key, String value) {
        return putString(getStr(key), value);
    }

    public SPUtils putString(String key, String value) {
        sEditor.putString(key, value);
        sEditor.apply();
        return sSPUtils;
    }

    public String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    public String getString(String key, String defValue) {
        return sSharedPreferences.getString(key, defValue);
    }

    public String getString(@StringRes int key) {
        return getString(getStr(key), DEFAULT_STRING);
    }

    public String getString(@StringRes int key, String defValue) {
        return getString(getStr(key), defValue);
    }

    public SPUtils putBoolean(@StringRes int key, boolean value) {
        return putBoolean(getStr(key), value);
    }

    public SPUtils putBoolean(String key, boolean value) {
        sEditor.putBoolean(key, value);
        sEditor.apply();
        return sSPUtils;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sSharedPreferences.getBoolean(key, defValue);
    }

    public boolean getBoolean(@StringRes int key) {
        return getBoolean(getStr(key));
    }

    public boolean getBoolean(@StringRes int key, boolean defValue) {
        return getBoolean(getStr(key), defValue);
    }

    public SPUtils putStringSet(@StringRes int key, Set<String> value) {
        return putStringSet(getStr(key), value);
    }

    public SPUtils putStringSet(String key, Set<String> value) {
        sEditor.putStringSet(key, value);
        sEditor.apply();
        return sSPUtils;
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, DEFAULT_STRING_SET);
    }


    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sSharedPreferences.getStringSet(key, defValue);
    }

    public Set<String> getStringSet(@StringRes int key) {
        return getStringSet(getStr(key));
    }

    public Set<String> getStringSet(@StringRes int key, Set<String> defValue) {
        return getStringSet(getStr(key), defValue);
    }


    public boolean contains(String key) {
        return sSharedPreferences.contains(key);
    }

    public boolean contains(@StringRes int key) {
        return contains(getStr(key));
    }

    public Map<String, ?> getAll() {
        return sSharedPreferences.getAll();
    }

    public SPUtils remove(@StringRes int key) {
        return remove(getStr(key));
    }

    public SPUtils remove(String key) {
        sEditor.remove(key);
        sEditor.apply();
        return sSPUtils;
    }

    public SPUtils clear() {
        sEditor.clear();
        sEditor.apply();
        return sSPUtils;
    }

    public SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }
    
    
    private static String getStr(@StringRes int key){
        return AppUtils.self().getContext().getString(key);
    }

}
