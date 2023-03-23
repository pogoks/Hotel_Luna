package com.cookandroid.luna_hotel;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Luna_Promotion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_promotion);


        Button btn_back,btn_lunalogo;
        ImageView btn_copy1,btn_copy2,btn_copy3,btn_copy4;

        btn_copy1 = (ImageView) findViewById(R.id.btn_copy1);
        btn_copy2 = (ImageView) findViewById(R.id.btn_copy2);
        btn_copy3 = (ImageView) findViewById(R.id.btn_copy3);
        btn_copy4 = (ImageView) findViewById(R.id.btn_copy4);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);

        // 1번 광고클릭
        btn_copy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(), Luna_Copy1.class);
                startActivity(Home_Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 2번 광고클릭
        btn_copy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(), Luna_Copy2.class);
                startActivity(Home_Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 3번 광고클릭
        btn_copy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(), Luna_Copy3.class);
                startActivity(Home_Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 4번 광고클릭
        btn_copy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(), Luna_Copy4.class);
                startActivity(Home_Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        //뒤로버튼 클릭 메소드
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // 로고를 누르면 홈화면으로 이동하는 코드
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(), Luna_Main.class);
                startActivity(Home_Intent);
            }
        });
    }

    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
