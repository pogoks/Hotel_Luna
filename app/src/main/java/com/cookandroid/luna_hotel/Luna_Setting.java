package com.cookandroid.luna_hotel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Luna_Setting extends AppCompatActivity {

    Button btn_back,btn_lunalogo,btn_cash_remove,btn_rnjsgks,btn_apply;
    Switch switch_SMS_adver, switch_SMS_reser,switch_autologin;

    String sms_copy = null;
    String sms_reser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_setting);

        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_cash_remove  = (Button) findViewById(R.id.btn_cash_remove);
        btn_lunalogo  = (Button) findViewById(R.id.btn_lunalogo);
        btn_back  = (Button) findViewById(R.id.btn_back);
        btn_rnjsgks  = (Button) findViewById(R.id.btn_rnjsgks);

        switch_SMS_adver  = (Switch) findViewById(R.id.switch_SMS_adver);
        switch_SMS_reser  = (Switch) findViewById(R.id.switch_SMS_reser);
        switch_autologin = (Switch)findViewById(R.id.switch_autologin);

        final SharedPreferences logininfo = getSharedPreferences("user",0); // user 라는 파일을 생성합니다.
        final SharedPreferences.Editor editor = logininfo.edit(); // 에디터 연결합니다.

        // SMS 알림 서비스 스위치 관련
        final SharedPreferences SMS_info1 = getSharedPreferences("SMS1",0);
        final SharedPreferences.Editor editor1 = SMS_info1.edit(); // 에디터 연결합니다.

        final SharedPreferences SMS_info2 = getSharedPreferences("SMS2",0);
        final SharedPreferences.Editor editor2 = SMS_info2.edit(); // 에디터 연결합니다.



        final String option_on = "ON";

        // SMS 옵션 설정관련 SharedPreferences 에 이미 값이 있으면 ?
        if(SMS_info1.contains("SMS_COPY"))
        {
             sms_copy = SMS_info1.getString("SMS_COPY",null);
        }
        if(SMS_info2.contains("SMS_RESER"))
        {
            sms_reser = SMS_info2.getString("SMS_RESER",null);
        }
        // 값이 있으면 true 없으면  false
        if(sms_copy != null) {
            switch_SMS_adver.setChecked(true);
        }
        if(sms_copy == null) {
            switch_SMS_adver.setChecked(false);
        }
        if(sms_reser != null) {
            switch_SMS_reser.setChecked(true);
        }
        if(sms_reser == null) {
            switch_SMS_reser.setChecked(false);
        }



        // 자동로그인 SharedPreferences 에 이미 값이 있으면 ?
        if(logininfo.contains("id")&& logininfo.contains("pw"))
        {
            switch_autologin.setChecked(true);
        }
       switch_autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               // 자동로그인을 체크했을때
               if(isChecked)
               {
                   if(Login_gloval.login_id == null) // 로그인이 안되어있다면?
                   {
                       // 토스트 메시지 출력후
                       Toast loginToast = Toast.makeText(Luna_Setting.this,"로그인이 되어있지 않습니다.",Toast.LENGTH_SHORT);
                       loginToast.show();
                       // 다시 스위치 비활성화
                       switch_autologin.setChecked(false);
                   }
                   else   // 위 if문에 이상이 없다면
                       {
                       // SharedPreferences 에 값 저장하기
                       editor.putString("id", Login_gloval.login_id); // 유저 파일에 로그인한 id 저장
                       editor.putString("pw", Login_gloval.login_password); // 유저 파일에 로그인한 id 저장
                       editor.commit(); // 저장하기
                           Toast loginToast = Toast.makeText(Luna_Setting.this,"자동로그인 기능을 켭니다.",Toast.LENGTH_SHORT);
                           loginToast.show();
                   }
               }
               else
               {
                   editor.clear();
                   editor.commit();
                   Toast loginToast = Toast.makeText(Luna_Setting.this,"자동로그인 기능을 끕니다.",Toast.LENGTH_SHORT);
                   loginToast.show();
               }
           }
       });



        //좌측상단 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });


        // 로고클릭 이벤트
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(),Luna_Main.class);
                startActivity(Home_Intent);
            }
        });


        //권한설정 버튼  -> os에서 막은거라서 앱의 정보까지는 들어갈수없습니다.
        btn_rnjsgks.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent rnjsgks_Intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                 startActivity(rnjsgks_Intent);
             }
        });


        // 케시삭제 버튼 동작 -> 케시진짜삭제하면 큰일나서 그냥 흉내만 냈어요
        btn_cash_remove.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast CashToast = Toast.makeText(Luna_Setting.this,"케시 삭제 완료",Toast.LENGTH_SHORT);
                 CashToast.show();
             }
        });


        // 광고수신 스위치 토스트메시지 출력
        switch_SMS_adver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    editor1.putString("SMS_COPY", option_on); // sms 광고수신 변수 1 선언
                    editor1.commit(); // 저장하기
                    Toast adverToast = Toast.makeText(Luna_Setting.this,"SMS 광고를 수신합니다.",Toast.LENGTH_SHORT);
                    adverToast.show();
                } else {
                    editor1.clear();
                    editor1.commit(); // 저장하기
                    Toast adverToast = Toast.makeText(Luna_Setting.this,"SMS 광고를 수신하지 않습니다.",Toast.LENGTH_SHORT);
                    adverToast.show();
                }
            }
        });


        // 예약알림 스위치 토스트메시지 출력
        switch_SMS_reser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    editor2.putString("SMS_RESER", option_on); // sms 예약수신 변수 1 선언
                    editor2.commit(); // 저장하기
                    Toast reserToast = Toast.makeText(Luna_Setting.this,"SMS 예약 알림을 받습니다.",Toast.LENGTH_SHORT);
                    reserToast.show();
                } else {
                    editor2.clear();
                    editor2.commit(); // 저장하기
                    Toast reserToast = Toast.makeText(Luna_Setting.this,"SMS 예약 알림을 받지 않습니다.",Toast.LENGTH_SHORT);
                    reserToast.show();
                }
            }
        });

        // 적용버튼 클릭 메소드
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast applyToast = Toast.makeText(Luna_Setting.this,"설정이 적용되었습니다.",Toast.LENGTH_SHORT);
                applyToast.show();
               finish(); // 창이 종료됩니다.
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });


    }


    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
