package com.example.meet.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework.view.TouchPictureV;
import com.example.meet.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TouchPictureV mTPV = findViewById(R.id.mTPV);
        mTPV.setViewResultListener(new TouchPictureV.OnViewResultListener() {
            @Override
            public void onResult() {
                Toast.makeText(TestActivity.this,"success",Toast.LENGTH_LONG).show();
            }
        });
    }
}
