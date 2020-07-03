package com.example.sunshine.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sunshine.R;

public class CommonUiActivity extends AppCompatActivity {
    public Button mButton1,mButton2,mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_ui);
        mButton1=findViewById(R.id.bn_1);
        mButton1.setOnClickListener(new OnClick());
        mButton2=findViewById(R.id.bn_2);
        mButton2.setOnClickListener(new OnClick());
        mButton3=findViewById(R.id.bn_3);
        mButton3.setOnClickListener(new OnClick());
    }
    class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent ;
            switch (v.getId()){
                case R.id.bn_1:
                    intent=new Intent(CommonUiActivity.this,ListViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bn_2:
                    intent=new Intent(CommonUiActivity.this,FruitActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bn_3:
                    intent=new Intent(CommonUiActivity.this,MsgActivity.class);
                    startActivity(intent);
                    break;
                default:

            }

        }
    }
}