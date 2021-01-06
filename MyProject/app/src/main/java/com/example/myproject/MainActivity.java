package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.ATask.LoginSelect;
import com.example.myproject.Dto.MemberDTO;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    // 로그인이 성공하면, 전역변수 static loginDTO 변수에 담아서
    // 어느곳에서나 접근할 수 있게 한다
    public static MemberDTO loginDTO = null;

    EditText etId, etPASSWD;
    Button btnLogin, btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        etId = findViewById(R.id.etId);
        etPASSWD = findViewById(R.id.etPASSWD);

        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);

        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginDTO = null;

                if (etId.getText().toString().length() !=0 && etPASSWD.getText().toString().length() != 0){
                    String id = etId.getText().toString();
                    String passwd = etPASSWD.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(id, passwd);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "아이디 / 암호를 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "btnLogin.onClick: 아이디 / 암호를 입력하세요");
                    return;
                }//if(etID.length() && etPASSWD.length())


                if (loginDTO != null) {
                    Toast.makeText(MainActivity.this, "로그인 성공!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: " + loginDTO.getId() + " 님이 로그인 되었습니다.!!");
                    //Log.d(TAG, "onClick1: " + etId.getText() + ", " + loginDTO.getId() + ", " + etPASSWD.getText() + ", " + loginDTO.getPasswd() );

                    // 로그인 정보에 값이 있으면 로그인이 되었으므로 메인화면으로 이동
                    if(loginDTO != null){
                        Intent intent = new Intent(getApplicationContext(), Sub1.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "아이디 또는, 비밀번호가 일치하지 않습니다!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: 아이디 또는, 비밀번호가 일치하지 않음!!");
                    //Log.d(TAG, "onClick3: " + etId.getText() + ", " + loginDTO.getId() + ", " + etPASSWD.getText() + ", " + loginDTO.getPasswd() );
                    etId.setText("");
                    etPASSWD.setText("");
                    etId.requestFocus();

                }//if(loginDTO)

            }//onClick()
        });//btnLogin.setOnClickListener()

        // 회원 가입 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }//onClick()
        });//btnJoin.setOnClickListener()

    }//onCreate()

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}//class MainActivity