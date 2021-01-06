package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myproject.Dto.MyItem;

public class Sub1 extends AppCompatActivity {
    public static MyItem selItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);
    }
}