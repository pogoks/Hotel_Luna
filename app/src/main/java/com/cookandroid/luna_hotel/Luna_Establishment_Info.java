package com.cookandroid.luna_hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class Luna_Establishment_Info extends AppCompatActivity {

    Button btn_back, btn_lunalogo;

    //서울(내용)
    LinearLayout layout_info_fitness_seoul, layout_info_sauna_seoul, layout_info_swimmimg_seoul, layout_info_golf_seoul, layout_info_business_seoul, layout_info_parking_seoul;

    //서울(상세보기)
    Button btn_info_fitness_seoul, btn_info_sauna_seoul, btn_info_swimming_seoul, btn_info_golf_seoul, btn_info_business_seoul, btn_info_parking_seoul;

    //서울(화살표)
    Button btn_info_fitness_seoul_arrow, btn_info_sauna_seoul_arrow, btn_info_swimming_seoul_arrow, btn_info_golf_seoul_arrow, btn_info_business_seoul_arrow;


    //슬라이드 변수 선언
    ViewFlipper v_fllipper_1; //첫 번째 이미지 슬라이더
    ViewFlipper v_fllipper_2; //두 번째 이미지 슬라이더
    ViewFlipper v_fllipper_3; //세 번째 이미지 슬라이더
    ViewFlipper v_fllipper_4; //네 번째 이미지 슬라이더


    private int hotel_number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_establishment_info);

        //연결(서울)
        btn_info_fitness_seoul = (Button) findViewById(R.id.btn_info_fitness_seoul); //상세보기(버튼)
        layout_info_fitness_seoul = (LinearLayout) findViewById(R.id.layout_info_fitness_seoul); //상세보기(내용)
        btn_info_fitness_seoul_arrow = (Button) findViewById(R.id.btn_info_fitness_seoul_arrow); //화살표(내용)

        btn_info_sauna_seoul = (Button) findViewById(R.id.btn_info_sauna_seoul); //상세보기(버튼)
        layout_info_sauna_seoul = (LinearLayout) findViewById(R.id.layout_info_sauna_seoul); //상세보기(내용)
        btn_info_sauna_seoul_arrow = (Button) findViewById(R.id.btn_info_sauna_seoul_arrow); //화살표(내용)

        btn_info_swimming_seoul = (Button) findViewById(R.id.btn_info_swimming_seoul); //상세보기(버튼)
        layout_info_swimmimg_seoul = (LinearLayout) findViewById(R.id.layout_info_swimming_seoul); //상세보기(내용)
        btn_info_swimming_seoul_arrow = (Button) findViewById(R.id.btn_info_swimming_seoul_arrow); //화살표(내용)

        btn_info_golf_seoul = (Button) findViewById(R.id.btn_info_golf_seoul); //상세보기(버튼)
        layout_info_golf_seoul = (LinearLayout) findViewById(R.id.layout_info_golf_seoul); //상세보기(내용)
        btn_info_golf_seoul_arrow = (Button) findViewById(R.id.btn_info_golf_seoul_arrow); //화살표(내용)

        btn_info_business_seoul = (Button) findViewById(R.id.btn_info_business_seoul); //상세보기(버튼)
        layout_info_business_seoul = (LinearLayout) findViewById(R.id.layout_info_business_seoul); //상세보기(내용)
        btn_info_business_seoul_arrow = (Button) findViewById(R.id.btn_info_business_seoul_arrow); //화살표(내용)

        // 주차장 상세보기 (보류)
        //btn_info_parking_seoul = (Button) findViewById(R.id.btn_info_parking_seoul); //상세보기(버튼)
        //layout_info_parking_seoul = (LinearLayout) findViewById(R.id.layout_info_parking_seoul); //상세보기(내용)

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_lunalogo = (Button) findViewById(R.id.btn_lunalogo);


        //피트니스 상세 보기 이벤트(서울)(상세보기 버튼)
        btn_info_fitness_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_fitness_seoul.getVisibility() == View.GONE) {//GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_fitness_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_fitness_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_fitness_seoul.setVisibility(View.GONE);
                    btn_info_fitness_seoul_arrow.setSelected(false);// 화살표 아래 방향으로 다시 설정
                }
            }
        });

        //피트니스 상세 보기 이벤트(서울)(화살표 버튼)
        btn_info_fitness_seoul_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_fitness_seoul.getVisibility() == View.GONE) { //GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_fitness_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_fitness_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_fitness_seoul.setVisibility(View.GONE);
                    btn_info_fitness_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }

            }
        });

        //사우나 슬라이드 이미지 넣는 코드(서울)
        int sauna_seoul[] = {
                R.drawable.e_seoul_2_sauna_1,
                R.drawable.e_seoul_2_sauna_2,
                R.drawable.e_seoul_2_sauna_3
        };
        //사우나 슬라이드 이미지 연결 코드(서울)
        v_fllipper_1 = findViewById(R.id.img_sauna_seoul);
        for (int image_1 : sauna_seoul) {
            fllipperImages_1(image_1);
        }

        //사우나 상세 보기 이벤트(서울)(상세보기 버튼)
        btn_info_sauna_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_sauna_seoul.getVisibility() == View.GONE) { //GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_sauna_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_sauna_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_sauna_seoul.setVisibility(View.GONE);
                    btn_info_sauna_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }
            }
        });

        //사우나 상세 보기 이벤트(서울)(화살표 버튼)
        btn_info_sauna_seoul_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_sauna_seoul.getVisibility() == View.GONE) { //GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_sauna_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_sauna_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_sauna_seoul.setVisibility(View.GONE);
                    btn_info_sauna_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }

            }
        });

        //수영장 슬라이드 이미지 넣는 코드(서울)
        int swimming_seoul[] = {
                R.drawable.e_seoul_3_swimming_1,
                R.drawable.e_seoul_3_swimming_2
        };
        //수영장 슬라이드 이미지 연결 코드(서울)
        v_fllipper_2 = findViewById(R.id.img_swimming_seoul);
        for (int image_2 : swimming_seoul) {
            fllipperImages_2(image_2);
        }

        //수영장 상세 보기 이벤트(서울)(상세보기 버튼)
        btn_info_swimming_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_swimmimg_seoul.getVisibility() == View.GONE) { //GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_swimmimg_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_swimming_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_swimmimg_seoul.setVisibility(View.GONE);
                    btn_info_swimming_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }
            }
        });

        //수영장 상세 보기 이벤트(서울)(화살표 버튼)
        btn_info_swimming_seoul_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_swimmimg_seoul.getVisibility() == View.GONE) { //GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_swimmimg_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_swimming_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_swimmimg_seoul.setVisibility(View.GONE);
                    btn_info_swimming_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }

            }
        });

        //골프 상세 보기 이벤트(서울)(상세보기 버튼)
        btn_info_golf_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_golf_seoul.getVisibility() == View.GONE) { //GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_golf_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_golf_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_golf_seoul.setVisibility(View.GONE);
                    btn_info_golf_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }
            }
        });

        //골프 상세 보기 이벤트(서울)(화살표 버튼)
        btn_info_golf_seoul_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_golf_seoul.getVisibility() == View.GONE) { //GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_golf_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_golf_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_golf_seoul.setVisibility(View.GONE);
                    btn_info_golf_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }
            }
        });


        //비즈니스 슬라이드 이미지 넣는 코드(서울)
        int business_seoul[] = {
                R.drawable.e_seoul_5_business_1,
                R.drawable.e_seoul_5_business_2,
                R.drawable.e_seoul_5_business_3
        };
        //비즈니스 슬라이드 이미지 연결 코드(서울)
        v_fllipper_3 = findViewById(R.id.img_business_seoul);
        for (int image_3 : business_seoul) {
            fllipperImages_3(image_3);
        }

        //비즈니스 상세 보기 이벤트(서울)(상세보기 버튼)
        btn_info_business_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_business_seoul.getVisibility() == View.GONE) {//GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_business_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_business_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_business_seoul.setVisibility(View.GONE);
                    btn_info_business_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }
            }
        });

        //비즈니스 상세 보기 이벤트(서울)(화살표 버튼)
        btn_info_business_seoul_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_info_business_seoul.getVisibility() == View.GONE) {//GONE : 해당 뷰를 안 보여줌(공간마저 감춤)
                    layout_info_business_seoul.setVisibility(View.VISIBLE); // VISIBLE : 해당 뷰를 보여줌, {참고: INVISIBLE :해당 뷰를 안 보여줌(공간은 존재)}
                    btn_info_business_seoul_arrow.setSelected(true); // 화살표 위 방향으로 설정
                } else {
                    layout_info_business_seoul.setVisibility(View.GONE);
                    btn_info_business_seoul_arrow.setSelected(false); // 화살표 아래 방향으로 다시 설정
                }
            }
        });

        //주차장 슬라이드 이미지 넣는 코드(서울)
        int parking_seoul[] = {
                R.drawable.e_seoul_6_parking_1,
                R.drawable.e_seoul_6_parking_2
        };
        //주차장 슬라이드 이미지 연결 코드(서울)
        v_fllipper_4 = findViewById(R.id.img_parking_seoul);
        for (int image_4 : parking_seoul) {
            fllipperImages_4(image_4);
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

    // 슬라이드 뷰 코딩 start
    // 이미지 슬라이더 구현 메서드(1번째 슬라이드 화면)
    public void fllipperImages_1(int image_1) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image_1);

        v_fllipper_1.addView(imageView);      // 이미지 추가
        v_fllipper_1.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_1.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper_1.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper_1.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    // 이미지 슬라이더 구현 메서드(2번째 슬라이드 화면)
    public void fllipperImages_2(int image_2) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image_2);

        v_fllipper_2.addView(imageView);      // 이미지 추가
        v_fllipper_2.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_2.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper_2.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper_2.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    // 슬라이드 뷰 코딩 end

    // 이미지 슬라이더 구현 메서드(3번째 슬라이드 화면)
    public void fllipperImages_3(int image_3) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image_3);

        v_fllipper_3.addView(imageView);      // 이미지 추가
        v_fllipper_3.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_3.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper_3.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper_3.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    // 슬라이드 뷰 코딩 end

    // 이미지 슬라이더 구현 메서드(4째 슬라이드 화면)
    public void fllipperImages_4(int image_4) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image_4);

        v_fllipper_4.addView(imageView);      // 이미지 추가
        v_fllipper_4.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_4.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper_4.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper_4.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    // 슬라이드 뷰 코딩 end


    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
