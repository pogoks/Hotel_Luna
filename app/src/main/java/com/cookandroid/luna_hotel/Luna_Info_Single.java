package com.cookandroid.luna_hotel;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Luna_Info_Single extends AppCompatActivity {
private int hotel_number = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_info_single);

        TextView text_info;
        Button btn_back,btn_lunalogo;

        text_info = (TextView) findViewById(R.id.text_info);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_lunalogo = (Button) findViewById(R.id.btn_lunalogo);
        text_info.bringToFront();

        ImageView image_room_01,image_room_02,image_room_03,image_nightview, image_spa;
        image_room_01 = (ImageView) findViewById(R.id.image_room_01);
        image_room_02 = (ImageView) findViewById(R.id.image_room_02);
        image_room_03 = (ImageView) findViewById(R.id.image_room_03);
        image_spa= (ImageView) findViewById(R.id.image_spa);
        image_nightview = (ImageView) findViewById(R.id.image_nightview);





        // 이전 페이지에서 받아온 호텔변수를 받는다
        Intent intent = getIntent();
        hotel_number = intent.getIntExtra("hotel_number",0);

        if (hotel_number == 1 ) // 서울 지점이면 이미지 변경
        {
            image_room_01.setImageResource(R.drawable.r_seoul_4s_1);


        }
        else if (hotel_number == 2) // 부산
        {
            image_room_01.setImageResource(R.drawable.r_busan_4s_1);

            image_nightview.setImageResource(R.drawable.busan_loop);
            image_spa.setImageResource(R.drawable.busan_spa);
        }
        else if (hotel_number == 3) // 제주
        {
            image_room_01.setImageResource(R.drawable.r_jeju_4s_1);

            image_nightview.setImageResource(R.drawable.jeju_loop);
            image_spa.setImageResource(R.drawable.jeju_spa);
        }
        else  // 속초
        {
            image_room_01.setImageResource(R.drawable.r_sokcho_4s_1);
            image_nightview.setImageResource(R.drawable.sokcho_loop);
            image_spa.setImageResource(R.drawable.sokcho_spa);
        }

        //뒤로버튼 클릭 메소드
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
    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
