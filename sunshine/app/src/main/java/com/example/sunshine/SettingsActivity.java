package com.example.sunshine;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar==null){
            //出现向左返回箭头
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.home){
            /**
             * To navigate up when the user presses the app icon,
             * you can use the NavUtils class's static method,navigateUpFromSameTask().
             * When you call this method, it finishes the current activity and starts (or resumes)
             * the appropriate parent activity.
             * If the target parent activity is in the task's back stack,
             * it is brought forward as defined by FLAG_ACTIVITY_CLEAR_TOP.
             */
            NavUtils.navigateUpFromSameTask(this);
//            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
}