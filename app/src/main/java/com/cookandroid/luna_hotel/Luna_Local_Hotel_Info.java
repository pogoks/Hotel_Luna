package com.cookandroid.luna_hotel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class Luna_Local_Hotel_Info extends AppCompatActivity {

    ViewFlipper v_fllipper; //슬라이드 전역변수 선언

    TabHost tabHost1;
    Button btn_back, btn_lunalogo;
    Button btn_seoulview1, btn_seoulview2, btn_seoulmap,btn_busanmap,btn_jejumap,btn_sokchomap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_local_hotel_info);

        tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_lunalogo = (Button) findViewById(R.id.btn_lunalogo);
        btn_seoulview1 = (Button) findViewById(R.id.btn_seoulview1);
        btn_seoulview2 = (Button) findViewById(R.id.btn_seoulview2);
        btn_seoulmap = (Button) findViewById(R.id.btn_seoulmap);
        btn_busanmap = (Button) findViewById(R.id.btn_busanmap);
        btn_jejumap = (Button) findViewById(R.id.btn_jejumap);
        btn_sokchomap = (Button) findViewById(R.id.btn_sokchomap);
        tabHost1.setup();


        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1") 서울
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("서울");
        tabHost1.addTab(ts1);

        // 슬라이드 뷰 코딩 start 서울
        int images_seoul[] = {
                R.drawable.h_seoul1,
                R.drawable.h_seoul2,
                R.drawable.h_seoul3
        };
        v_fllipper = findViewById(R.id.image_seoul);
        for (int image : images_seoul) {
            fllipperImages(image);
        }
        //슬라이드 뷰 코딩 end 서울

        /*
        //서울 지도 버튼
        btn_seoulmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Map_Seoul.class);
                startActivity(intent);
            }
        });

         */


        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2") 부산
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("부산");
        tabHost1.addTab(ts2);

        // 슬라이드 뷰 코딩 start 부산
        int images_busan[] = {
                R.drawable.h_busan1,
                R.drawable.h_busan2,
                R.drawable.h_busan3
        };
        v_fllipper = findViewById(R.id.image_busan);
        for (int image : images_busan) {
            fllipperImages(image);
        }
        //슬라이드 뷰 코딩 end 부산

        //부산 지도 버튼
        /*
        btn_busanmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Map_Busan.class);
                startActivity(intent);
            }
        });

         */

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3") 제주
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("제주");
        tabHost1.addTab(ts3);
        // 슬라이드 뷰 코딩 start 제주
        int images_jeju[] = {
                R.drawable.h_jeju01,
                R.drawable.h_jeju02,
                R.drawable.h_jeju03,
                R.drawable.h_jeju04,
                R.drawable.h_jeju05_1
        };
        v_fllipper = findViewById(R.id.image_jeju);
        for (int image : images_jeju) {
            fllipperImages(image);
        }
        //슬라이드 뷰 코딩 end 제주

        /*
        //제주 지도 버튼
        btn_jejumap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Map_Jeju.class);
                startActivity(intent);
            }
        });

         */

        // 네 번째 Tab. (탭 표시 텍스트:"TAB 4"), (페이지 뷰:"content4") 속초
        TabHost.TabSpec ts4 = tabHost1.newTabSpec("Tab Spec 4");
        ts4.setContent(R.id.content4);
        ts4.setIndicator("속초");
        tabHost1.addTab(ts4);

        // 슬라이드 뷰 코딩 start 속초
        int images_sokcho[] = {
                R.drawable.h_sokcho1,
                R.drawable.h_sokcho2,
                R.drawable.h_sokcho3,
                R.drawable.h_sokcho4
        };
        v_fllipper = findViewById(R.id.image_sokcho);
        for (int image : images_sokcho) {
            fllipperImages(image);
        }
        //슬라이드 뷰 코딩 end 속초

        /*
        //속초 지도 버튼
        btn_sokchomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Map_Sokcho.class);
                startActivity(intent);
            }
        });
        
         */

        //메뉴버튼 클릭 메소드
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // 로고를 누르면 홈화면으로 이동하면 코드
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(), Luna_Main.class);
                startActivity(Home_Intent);
            }
        });

    }


    // 슬라이드 뷰 코딩 start
    // 이미지 슬라이더 구현 메서드
    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    // 슬라이드 뷰 코딩 end

    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}
