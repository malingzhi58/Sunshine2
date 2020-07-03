package com.example.sunshine.background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sunshine.R;

public class BackUiActivity extends AppCompatActivity {
    public Button mB1, mB2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ui);
        mB1 = findViewById(R.id.bt_1);
        mB1.setOnClickListener(new OnClick());
        mB1 = findViewById(R.id.bt_2);
        mB1.setOnClickListener(new OnClick());
    }

    class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.bt_1:
                     intent = new Intent(BackUiActivity.this,BackgroundActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_2:
                     intent = new Intent(BackUiActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}