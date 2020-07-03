package com.example.sunshine;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

/**
 * 设置可接受的范围
 * 要将可接受的值限制在 0（不包括）和 3（包括）之间，我们选择使用 PreferenceChangeListener - 它与 SharedPreferenceChangeListener 的不同之处为：
 *
 * SharedPreferenceChangeListener 在任何值保存到 SharedPreferences 文件后被触发。
 * PreferenceChangeListener 在值保存到 SharedPreferences 文件前被触发。因此，可以防止对偏好设置做出无效的更新。PreferenceChangeListeners 也附加到了单个偏好设置上。
 * 流程通常如下所示：
 *
 * 用户更新偏好设置。
 * 该偏好设置触发了 PreferenceChangeListener。
 * 新的值保存到了 SharedPreference 文件。
 * onSharedPreferenceChanged 监听器被触发。
 * 除此之外，它们的行为很相似。你将在你的 Activity 中实现 Preference.OnPreferenceChangeListener，
 * 覆盖 onPreferenceChange(Preference preference, Object newValue)。
 * onPreferenceChange 方法将返回 true 或 false，取决于偏好设置实际上是否要被保存。
 */
public class SettingsFragment2 extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        // COMPLETED (3) Get the preference screen, get the number of preferences and iterate through
        // all of the preferences if it is not a checkbox preference, call the setSummary method
        // passing in a preference and the value of the preference

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        Log.d("try1",sharedPreferences.toString());
        PreferenceScreen prefScreen = getPreferenceScreen();
        Log.d("try1",prefScreen.toString());
        int count = prefScreen.getPreferenceCount();

        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            Log.d("Preference",p.toString());
            // You don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if (!(p instanceof CheckBoxPreference)) {
                //p is a Preference instance, checkedBoxPreference or ListPreference, the Preference key is listed in the xml
                // the preference has a key-value pair
                // return the preference value
                String value = sharedPreferences.getString(p.getKey(), "");
                Log.d("PreferenceKey",p.getKey());//which is color
                Log.d("Preferencevalue",value);// this is the color: red, green
                setPreferenceSummary(p, value);
            }
        }

        // Add the OnPreferenceChangeListener specifically to the EditTextPreference
        // Add the preference listener which checks that the size is correct to the size preference
        Preference preference = findPreference(getString(R.string.pref_size_key));
        preference.setOnPreferenceChangeListener(this);
    }

    // COMPLETED (4) Override onSharedPreferenceChanged and, if it is not a checkbox preference,
    // call setPreferenceSummary on the changed preference
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Figure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                Log.d("Preferencevalue",value);// this is the color: red, green
                setPreferenceSummary(preference, value);
            }
        }
    }

    //  Create a setPreferenceSummary which takes a Preference and String value as parameters.
    // This method should check if the preference is a ListPreference and, if so, find the label
    // associated with the value. You can do this by using the findIndexOfValue and getEntries methods
    // of Preference.
    /**
     * Updates the summary for the preference
     *
     * @param preference The preference to be updated
     * @param value      The value that the preference was updated to which is green, blue
     */
    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            Log.d("CharSequence",String.valueOf(prefIndex) );
            if (prefIndex >= 0) {
                // Set the summary to that label
                CharSequence[] res =listPreference.getEntries();
//                listPreference.get
                for(CharSequence num:res){
                    Log.d("CharSequence",num.toString());
                    // which is the label, because we want to show the lable in the summary instead of the value
                    // and entry is the label
                }
                listPreference.setSummary(res[prefIndex]);
            }
        }else if (preference instanceof EditTextPreference) {
            // For EditTextPreferences, set the summary to the value's simple string representation.
            preference.setSummary(value);
        }
    }

    // COMPLETED (5) Register and unregister the OnSharedPreferenceChange listener (this class) in
    // onCreate and onDestroy respectively.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // there are two ways to get the current sharedPreference:
        // 1.PreferenceManager.getDefaultSharedPreferences(this);
        //2.getPreferenceScreen.getSharedPreferences
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Toast e2 = Toast.makeText(getContext(),"wrong",Toast.LENGTH_SHORT);
        String sizeKey = getString(R.string.pref_size_key);
        if(preference.getKey().equals(sizeKey)){
            String stringSize = (String) newValue;
            try{
                float size = Float.parseFloat(stringSize);
                if(size>3||size<=0){
                    e2.show();
                    return false;
                }
            }catch (NumberFormatException nfe){
                e2.show();
                return false;
            }
        }
        return true;
    }
}