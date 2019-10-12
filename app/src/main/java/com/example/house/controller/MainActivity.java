package com.example.house.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.house.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_add:

                break;
            case R.id.btn_show_standard:
                Intent intent=new Intent(MainActivity.this,StandardActivity.class);
                startActivity(intent);
                break;
        }
    }
}
