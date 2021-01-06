package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproject.ATask.JoinInsert;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "main:JoinActivity";

    String state;

    EditText etId, etPasswd, etName, etPhoneNum, etAddress;
    Button btnJoin, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etId = findViewById(R.id.etId);
        etPasswd = findViewById(R.id.etPasswd);
        etName = findViewById(R.id.etName);
        etPhoneNum = findViewById(R.id.etPhoneNum);
        etAddress = findViewById(R.id.etAddress);

        btnJoin = findViewById(R.id.btnJoin);
        btnCancel = findViewById(R.id.btnLogout);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString();
                String passwd = etPasswd.getText().toString();
                String name = etName.getText().toString();
                String phonenumber =  etPhoneNum.getText().toString();
                String address = etAddress.getText().toString();

                JoinInsert joinInsert = new JoinInsert(id, passwd, name, phonenumber, address);
                try {
                    state = joinInsert.execute().get().trim();
                    Log.d(TAG, "joinact0 : " + state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "DB 삽입 성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "joinact : " + "삽입성공 !!!");
                    finish();
                }else{
                    Toast.makeText(JoinActivity.this, "DB 삽입 실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "joinact : " + "삽입실패 !!!");
                    finish();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate()

}//class JoinActivity