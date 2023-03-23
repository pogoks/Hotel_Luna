package com.cookandroid.luna_hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.AlteredCharSequence;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class Luna_Info_Room extends AppCompatActivity {

    Button btn_back, btn_lunalogo;

    //서울
    TextView text_luxury_room_seoul, text_single_room_seoul, text_double_room_seoul, text_family_room_seoul;
    TextView text_info_luxury_seoul, text_info_single_seoul, text_info_double_seoul, text_info_family_seoul;

    //부산
    TextView text_luxury_room_busan, text_single_room_busan, text_double_room_busan, text_family_room_busan;
    TextView text_info_luxury_busan, text_info_single_busan, text_info_double_busan, text_info_family_busan;

    //제주
    TextView text_luxury_room_jeju, text_single_room_jeju, text_double_room_jeju, text_family_room_jeju;
    TextView text_info_luxury_jeju, text_info_single_jeju, text_info_double_jeju, text_info_family_jeju;

    //속초
    TextView text_luxury_room_sokcho, text_single_room_sokcho, text_double_room_sokcho, text_family_room_sokcho;
    TextView text_info_luxury_sokcho, text_info_single_sokcho, text_info_double_sokcho, text_info_family_sokcho;

    //슬라이드 변수 선언
    ViewFlipper v_fllipper_luxuryroom; //럭셔리룸
    ViewFlipper v_fllipper_familyroom; //패밀리룸
    ViewFlipper v_fllipper_doubleroom; //더블룸

    private int hotel_number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_info_room);

        //텍스트(서울)
        text_single_room_seoul = (TextView) findViewById(R.id.text_single_room_seoul);
        text_luxury_room_seoul = (TextView) findViewById(R.id.text_luxury_room_seoul);
        text_family_room_seoul = (TextView) findViewById(R.id.text_family_room_seoul);
        text_double_room_seoul = (TextView) findViewById(R.id.text_double_room_seoul);
        text_info_luxury_seoul = (TextView) findViewById(R.id.text_info_luxury_seoul);
        text_info_single_seoul = (TextView) findViewById(R.id.text_info_single_seoul);
        text_info_family_seoul = (TextView) findViewById(R.id.text_info_family_seoul);
        text_info_double_seoul = (TextView) findViewById(R.id.text_info_double_seoul);

        //텍스트(부산)
        text_single_room_busan = (TextView) findViewById(R.id.text_single_room_busan);
        text_luxury_room_busan = (TextView) findViewById(R.id.text_luxury_room_busan);
        text_family_room_busan = (TextView) findViewById(R.id.text_family_room_busan);
        text_double_room_busan = (TextView) findViewById(R.id.text_double_room_busan);
        text_info_luxury_busan = (TextView) findViewById(R.id.text_info_luxury_busan);
        text_info_single_busan = (TextView) findViewById(R.id.text_info_single_busan);
        text_info_family_busan = (TextView) findViewById(R.id.text_info_family_busan);
        text_info_double_busan = (TextView) findViewById(R.id.text_info_double_busan);

        //텍스트(제주)
        text_single_room_jeju = (TextView) findViewById(R.id.text_single_room_jeju);
        text_luxury_room_jeju = (TextView) findViewById(R.id.text_luxury_room_jeju);
        text_family_room_jeju = (TextView) findViewById(R.id.text_family_room_jeju);
        text_double_room_jeju = (TextView) findViewById(R.id.text_double_room_jeju);
        text_info_luxury_jeju = (TextView) findViewById(R.id.text_info_luxury_jeju);
        text_info_single_jeju = (TextView) findViewById(R.id.text_info_single_jeju);
        text_info_family_jeju = (TextView) findViewById(R.id.text_info_family_jeju);
        text_info_double_jeju = (TextView) findViewById(R.id.text_info_double_jeju);

        //텍스트(속초)
        text_single_room_sokcho = (TextView) findViewById(R.id.text_single_room_sokcho);
        text_luxury_room_sokcho = (TextView) findViewById(R.id.text_luxury_room_sokcho);
        text_family_room_sokcho = (TextView) findViewById(R.id.text_family_room_sokcho);
        text_double_room_sokcho = (TextView) findViewById(R.id.text_double_room_sokcho);
        text_info_luxury_sokcho = (TextView) findViewById(R.id.text_info_luxury_sokcho);
        text_info_single_sokcho = (TextView) findViewById(R.id.text_info_single_sokcho);
        text_info_family_sokcho = (TextView) findViewById(R.id.text_info_family_sokcho);
        text_info_double_sokcho = (TextView) findViewById(R.id.text_info_double_sokcho);


        btn_back = (Button) findViewById(R.id.btn_back);
        btn_lunalogo = (Button) findViewById(R.id.btn_lunalogo);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 네모칸에 텍스트뷰가 가장먼저 앞으로 표시되게 하는 메소드입니다.(서울)
        text_luxury_room_seoul.bringToFront();
        text_single_room_seoul.bringToFront();
        text_double_room_seoul.bringToFront();
        text_family_room_seoul.bringToFront();

        // 네모칸에 텍스트뷰가 가장먼저 앞으로 표시되게 하는 메소드입니다.(부산)
        text_luxury_room_busan.bringToFront();
        text_single_room_busan.bringToFront();
        text_double_room_busan.bringToFront();
        text_family_room_busan.bringToFront();

        // 네모칸에 텍스트뷰가 가장먼저 앞으로 표시되게 하는 메소드입니다.(제주)
        text_luxury_room_jeju.bringToFront();
        text_single_room_jeju.bringToFront();
        text_double_room_jeju.bringToFront();
        text_family_room_jeju.bringToFront();

        // 네모칸에 텍스트뷰가 가장먼저 앞으로 표시되게 하는 메소드입니다.(속초)
        text_luxury_room_sokcho.bringToFront();
        text_single_room_sokcho.bringToFront();
        text_double_room_sokcho.bringToFront();
        text_family_room_sokcho.bringToFront();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1") 서울
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("서울");
        tabHost1.addTab(ts1);

        //럭셔리룸 슬라이드 이미지 넣는 코드(서울)
        int luxuryroom_seoul[] = {
                R.drawable.r_seoul_1l_1,
                R.drawable.r_seoul_1l_2,
                R.drawable.r_seoul_1l_3
        };
        //럭셔리룸 슬라이드 이미지 연결 코드(서울)
        v_fllipper_luxuryroom = findViewById(R.id.luxuryroom_seoul);
        for (int image_luxuryroom : luxuryroom_seoul) {
            fllipperImages_luxuryroom(image_luxuryroom);
        }
        // 럭셔리룸 자세히 보기 이벤트
        // 페이지가 이동되면서 호텔넘어도 같이 넘긴다.
        // 이 변수는 지점의 방마다 자바클래스랑 xml을 만들지않고 1개의 페이지에 지점4개의 방을 모두 표현할수 있다.
        text_info_luxury_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 1;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Luxury.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        //패밀리룸 슬라이드 이미지 넣는 코드(서울)
        int familyroom_seoul[] = {
                R.drawable.r_seoul_2f_1,
                R.drawable.r_seoul_2f_2,
                R.drawable.r_seoul_2f_3
        };
        //패밀리룸 슬라이드 이미지 연결 코드(서울)
        v_fllipper_familyroom = findViewById(R.id.familyroom_seoul);
        for (int image : familyroom_seoul) {
            fllipperImages_familyroom(image);
        }
        // 패밀리룸 자세히 보기이벤트
        text_info_family_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel_number = 1;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Family.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        //더블룸 슬라이드 이미지 넣는 코드(서울)
        int doubleroom_seoul[] = {
                R.drawable.r_seoul_3d_1,
                R.drawable.r_seoul_3d_2,
                R.drawable.r_seoul_3d_3
        };
        //더블룸 슬라이드 이미지 연결 코드(서울)
        v_fllipper_doubleroom = findViewById(R.id.doubleroom_seoul);
        for (int image : doubleroom_seoul) {
            fllipperImages_doubleroom(image);
        }
        // 더블룸 자세히보기 이벤트
        text_info_double_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 1;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Double.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        // 싱글룸 자세히 보기 이벤트
        text_info_single_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel_number = 1;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Single.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });


        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2") 부산
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("부산");
        tabHost1.addTab(ts2);

        //럭셔리룸 슬라이드 이미지 넣는 코드(부산)
        int luxuryroom_busan[] = {
                R.drawable.r_busan_1l_1,
                R.drawable.r_busan_1l_2,
                R.drawable.r_busan_1l_3
        };
        //럭셔리룸 슬라이드 이미지 연결 코드(부산)
        v_fllipper_luxuryroom = findViewById(R.id.luxuryroom_busan);
        for (int image_luxuryroom : luxuryroom_busan) {
            fllipperImages_luxuryroom(image_luxuryroom);
        }
        // 럭셔리룸 자세히 보기 이벤트
        text_info_luxury_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 2;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Luxury.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        //패밀리룸 슬라이드 이미지 넣는 코드(부산)
        int familyroom_busan[] = {
                R.drawable.r_busan_2f_1,
                R.drawable.r_busan_2f_2,
                R.drawable.r_busan_2f_3
        };
        //패밀리룸 슬라이드 이미지 연결 코드(부산)
        v_fllipper_familyroom = findViewById(R.id.familyroom_busan);
        for (int image : familyroom_busan) {
            fllipperImages_familyroom(image);
        }
        // 패밀리룸 자세히 보기이벤트
        text_info_family_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel_number = 2;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Family.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        //더블룸 슬라이드 이미지 넣는 코드(부산)
        int doubleroom_busan[] = {
                R.drawable.r_busan_3d_1,
                R.drawable.r_busan_3d_2,
                R.drawable.r_busan_3d_3
        };
        //더블룸 슬라이드 이미지 연결 코드(부산)
        v_fllipper_doubleroom = findViewById(R.id.doubleroom_busan);
        for (int image : doubleroom_busan) {
            fllipperImages_doubleroom(image);
        }
        // 더블룸 자세히보기 이벤트
        text_info_double_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel_number = 2;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Double.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        // 싱글룸 자세히 보기 이벤트
        text_info_single_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               hotel_number = 2;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Single.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3") 제주
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("제주");
        tabHost1.addTab(ts3);

        //럭셔리룸 슬라이드 이미지 넣는 코드(제주)
        int luxuryroom_jeju[] = {
                R.drawable.r_jeju_1l_1,
                R.drawable.r_jeju_1l_2,
                R.drawable.r_jeju_1l_3
        };
        //럭셔리룸 슬라이드 이미지 연결 코드(제주)
        v_fllipper_luxuryroom = findViewById(R.id.luxuryroom_jeju);
        for (int image_luxuryroom : luxuryroom_jeju) {
            fllipperImages_luxuryroom(image_luxuryroom);
        }
        // 럭셔리룸 자세히 보기 이벤트
        text_info_luxury_jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 3;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Luxury.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        //패밀리룸 슬라이드 이미지 넣는 코드(제주)
        int familyroom_jeju[] = {
                R.drawable.r_jeju_2f_1,
                R.drawable.r_jeju_2f_2,
                R.drawable.r_jeju_2f_3
        };
        //패밀리룸 슬라이드 이미지 연결 코드(제주)
        v_fllipper_familyroom = findViewById(R.id.familyroom_jeju);
        for (int image : familyroom_jeju) {
            fllipperImages_familyroom(image);
        }
        // 패밀리룸 자세히 보기이벤트
        text_info_family_jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 3;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Family.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        //더블룸 슬라이드 이미지 넣는 코드(제주)
        int doubleroom_jeju[] = {
                R.drawable.r_jeju_3d_1,
                R.drawable.r_jeju_3d_2,
                R.drawable.r_jeju_3d_3
        };
        //더블룸 슬라이드 이미지 연결 코드(제주)
        v_fllipper_doubleroom = findViewById(R.id.doubleroom_jeju);
        for (int image : doubleroom_jeju) {
            fllipperImages_doubleroom(image);
        }
        // 더블룸 자세히보기 이벤트
        text_info_double_jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 3;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Double.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        // 싱글룸 자세히 보기 이벤트
        text_info_single_jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel_number = 3;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Single.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        // 네 번째 Tab. (탭 표시 텍스트:"TAB 4"), (페이지 뷰:"content4") 속초
        TabHost.TabSpec ts4 = tabHost1.newTabSpec("Tab Spec 4");
        ts4.setContent(R.id.content4);
        ts4.setIndicator("속초");
        tabHost1.addTab(ts4);

        //럭셔리룸 슬라이드 이미지 넣는 코드(속초)
        int luxuryroom_sokcho[] = {
                R.drawable.r_sokcho_1l_1,
                R.drawable.r_sokcho_1l_2,
                R.drawable.r_sokcho_1l_3
        };
        //럭셔리룸 슬라이드 이미지 연결 코드(속초)
        v_fllipper_luxuryroom = findViewById(R.id.luxuryroom_sokcho);
        for (int image_luxuryroom : luxuryroom_sokcho) {
            fllipperImages_luxuryroom(image_luxuryroom);
        }
        // 럭셔리룸 자세히 보기 이벤트
        text_info_luxury_sokcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 4;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Luxury.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        //패밀리룸 슬라이드 이미지 넣는 코드(속초)
        int familyroom_sokcho[] = {
                R.drawable.r_sokcho_2f_1,
                R.drawable.r_sokcho_2f_2,
                R.drawable.r_sokcho_2f_3
        };
        //패밀리룸 슬라이드 이미지 연결 코드(속초)
        v_fllipper_familyroom = findViewById(R.id.familyroom_sokcho);
        for (int image : familyroom_sokcho) {
            fllipperImages_familyroom(image);
        }
        // 패밀리룸 자세히 보기이벤트
        text_info_family_sokcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hotel_number = 4;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Single.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        //더블룸 슬라이드 이미지 넣는 코드(속초)
        int doubleroom_sokcho[] = {
                R.drawable.r_sokcho_3d_1,
                R.drawable.r_sokcho_3d_2,
                R.drawable.r_sokcho_3d_3
        };
        //더블룸 슬라이드 이미지 연결 코드(속초)
        v_fllipper_doubleroom = findViewById(R.id.doubleroom_sokcho);
        for (int image : doubleroom_sokcho) {
            fllipperImages_doubleroom(image);
        }
        // 더블룸 자세히보기 이벤트
        text_info_double_sokcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotel_number = 4;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Double.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        // 싱글룸 자세히 보기 이벤트
        text_info_single_sokcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               hotel_number = 4;
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Single.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
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
    // 이미지 슬라이더 구현 메서드(럭셔리룸)
    public void fllipperImages_luxuryroom(int image_luxuryroom) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image_luxuryroom);

        v_fllipper_luxuryroom.addView(imageView);      // 이미지 추가
        v_fllipper_luxuryroom.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_luxuryroom.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper_luxuryroom.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper_luxuryroom.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    // 이미지 슬라이더 구현 메서드(패밀리룸)
    public void fllipperImages_familyroom(int image_familyroom) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image_familyroom);

        v_fllipper_familyroom.addView(imageView);      // 이미지 추가
        v_fllipper_familyroom.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_familyroom.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper_familyroom.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper_familyroom.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    // 이미지 슬라이더 구현 메서드(더블룸)
    public void fllipperImages_doubleroom(int image_doubleroom) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image_doubleroom);

        v_fllipper_doubleroom.addView(imageView);      // 이미지 추가
        v_fllipper_doubleroom.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper_doubleroom.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper_doubleroom.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper_doubleroom.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    // 슬라이드 뷰 코딩 end

    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
