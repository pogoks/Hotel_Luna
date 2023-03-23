package com.cookandroid.luna_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Luna_Reservation_Room extends AppCompatActivity {

    Button btn_next, btn_lunalogo, btn_back;
    RadioButton Rbtn_single, Rbtn_luxury,Rbtn_double,Rbtn_family;
    TextView text_info_luxury,text_info_double,text_info_single,text_info_family;
    TextView  text_soldout1,text_soldout2,text_soldout3,text_soldout4;
    TextView text_checkin_mmdd,text_checkin_yy,text_checkout_mmdd,text_checkout_yy,text_tnrqkr,text_hotel_name;
    ImageView image_single, image_double, image_family, image_luxury;


    // 예약하기 중요 변수들
    // 메인화면에서 가져올 변수들
    private int tnrqkr = 0; // 숙박 몇박 변수
    private  int hotel_number = 0; // 호텔 지점 번호입니다
    private String put_checkin =null; // 예약한 날짜를 보낼 변수
    private String put_checkout=null; // 예약한 날짜를 보낼 변수

    // 스트링형 변수를 선언
    private  String date_s_in,month_s_in,year_s_in; //  체크인 날짜 변환하는 변수
    private  String date_s_out,month_s_out,year_s_out; //  체크인 날짜 변환하는 변수
    // 인트형 변수를 선언  변환하는 변수
    private  int date_int = 0;
    // 여기까지

    private String hotel_name = null; // 선택한 호텔 지점 이름
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_reservation_room);

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_lunalogo = (Button) findViewById(R.id.btn_lunalogo);
        btn_back = (Button) findViewById(R.id.btn_back);
        text_soldout1 = (TextView) findViewById(R.id.text_soldout1); // 싱글
        text_soldout2 = (TextView) findViewById(R.id.text_soldout2); // 더블
        text_soldout3 = (TextView) findViewById(R.id.text_soldout3); // 패밀리
        text_soldout4 = (TextView) findViewById(R.id.text_soldout4); // 럭셔리
        text_info_luxury = (TextView) findViewById(R.id.text_info_luxury);
        text_info_single = (TextView) findViewById(R.id.text_info_single);
        text_info_family = (TextView) findViewById(R.id.text_info_family);
        text_info_double = (TextView) findViewById(R.id.text_info_double);
        Rbtn_single = (RadioButton) findViewById(R.id.Rbtn_single);
        Rbtn_luxury = (RadioButton) findViewById(R.id.Rbtn_luxury);
        Rbtn_double = (RadioButton) findViewById(R.id.Rbtn_double);
        Rbtn_family = (RadioButton)findViewById(R.id.Rbtn_family);
        image_single = (ImageView)findViewById(R.id.image_single);
        image_double = (ImageView)findViewById(R.id.image_double);
        image_luxury = (ImageView)findViewById(R.id.image_luxury);
        image_family = (ImageView)findViewById(R.id.image_family);

        // 예약했던 데이터를 불러옵니다.
        Intent intent = getIntent();
        tnrqkr = intent.getIntExtra("tnrqkr",0);
        date_int = intent.getIntExtra("date_int",0);
        //체크인 변수
        date_s_in = intent.getStringExtra("date_s_in");
        month_s_in = intent.getStringExtra("month_s_in");
        year_s_in = intent.getStringExtra("year_s_in");
        put_checkin = intent.getStringExtra("put_checkin");
        // 체크아웃 변수
        date_s_out = intent.getStringExtra("date_s_out");
        month_s_out = intent.getStringExtra("month_s_out");
        year_s_out = intent.getStringExtra("year_s_out");
        put_checkout = intent.getStringExtra("put_checkout");
        // 지점 호텔 번호를 불러온다.
        hotel_number = intent.getIntExtra("hotel_number",0);

        text_checkin_mmdd = (TextView) findViewById(R.id.text_checkin_mmdd);
        text_checkout_mmdd = (TextView) findViewById(R.id.text_checkout_mmdd);
        text_checkout_yy = (TextView) findViewById(R.id.text_checkout_yy);
        text_checkin_yy = (TextView) findViewById(R.id.text_checkin_yy);
        text_tnrqkr = (TextView) findViewById(R.id.text_tnrqkr);
        text_hotel_name = (TextView)findViewById(R.id.text_hotel_name);
        // 날짜 표시하는곳에 텍스트 담기.
        text_checkin_mmdd.setText(month_s_in + "월" + date_s_in + "일");
        text_checkin_yy.setText(year_s_in + "년");
        text_checkout_mmdd.setText(month_s_out + "월" + date_s_out + "일");
        text_checkout_yy.setText(year_s_out + "년");
        text_tnrqkr.setText(tnrqkr + "박");

        // 호텔번호를 체크해서 호텔이름을 부여한다
        // 지점마다 다른 사진이 나오게한다
        if (hotel_number == 1) // 서울
        {
            hotel_name = "호텔 루나 서울";

            image_single.setImageResource(R.drawable.r_seoul_4s_1);
            image_double.setImageResource(R.drawable.r_seoul_3d_1);
            image_family.setImageResource(R.drawable.r_seoul_2f_1);
            image_luxury.setImageResource(R.drawable.r_seoul_1l_1);

            text_hotel_name.setText("- " +  hotel_name + " -");
        }
        else if (hotel_number == 2) // 부산
        {
            hotel_name = "호텔 루나 부산";
            image_single.setImageResource(R.drawable.r_busan_4s_1);
            image_double.setImageResource(R.drawable.r_busan_3d_1);
            image_family.setImageResource(R.drawable.r_busan_2f_1);
            image_luxury.setImageResource(R.drawable.r_busan_1l_1);

            text_hotel_name.setText("- " +  hotel_name + " -");
        }
        else if (hotel_number == 3) // 제주
        {
            hotel_name = "호텔 루나 제주";
            image_single.setImageResource(R.drawable.r_jeju_4s_1);
            image_double.setImageResource(R.drawable.r_jeju_3d_1);
            image_family.setImageResource(R.drawable.r_jeju_2f_1);
            image_luxury.setImageResource(R.drawable.r_jeju_1l_1);
            text_hotel_name.setText("- " +  hotel_name + " -");
        }
        else  // 속초
        {
            hotel_name = "호텔 루나 속초";
            image_single.setImageResource(R.drawable.r_sokcho_4s_1);
            image_double.setImageResource(R.drawable.r_sokcho_3d_1);
            image_family.setImageResource(R.drawable.r_sokcho_2f_1);
            image_luxury.setImageResource(R.drawable.r_sokcho_1l_1);

            text_hotel_name.setText("- " +  hotel_name + " -");
        }

    // 랜덤으로 방의 예약을 방는 코드입니다

        if(hotel_number == 1) // 서울 점 선택
        {
            if(date_int <= 10){ // 일이 10보다 작으면

                // 더블룸 예약 불가능하게 만듬
                Rbtn_double.setVisibility(View.INVISIBLE);
                text_soldout2.setText("예약 불가");
                text_soldout2.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 10 && date_int <= 20)
            {
                Rbtn_family.setVisibility(View.INVISIBLE);
                text_soldout3.setText("예약 불가");
                text_soldout3.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 20 && date_int <= 31)
            {
                Rbtn_luxury.setVisibility(View.INVISIBLE);
                text_soldout4.setText("예약 불가");
                text_soldout4.setTextColor(Color.parseColor("#ff7e7e"));
                // 클릭시 아무 동작 못하게 막음

            }
        }
        else if (hotel_number == 2) // 부산 점 선택
        {
            if(date_int <= 10){ // 일이 10보다 작으면
                // 더블룸 예약 불가능하게 만듬
                Rbtn_double.setVisibility(View.INVISIBLE);
                text_soldout2.setText("예약 불가");
                text_soldout2.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 10 && date_int <= 20)
            {
                Rbtn_family.setVisibility(View.INVISIBLE);
                text_soldout3.setText("예약 불가");
                text_soldout3.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 20 && date_int <= 31)
            {
                Rbtn_single.setVisibility(View.INVISIBLE);
                text_soldout1.setText("예약 불가");
                text_soldout1.setTextColor(Color.parseColor("#ff7e7e"));
                // 클릭시 아무 동작 못하게 막음

            }
        }

        else if (hotel_number == 3) // 제주
        {
            if(date_int <= 10){ // 일이 10보다 작으면
                // 더블룸 예약 불가능하게 만듬
                Rbtn_double.setVisibility(View.INVISIBLE);
                text_soldout2.setText("예약 불가");
                text_soldout2.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 10 && date_int <= 20)
            {
                Rbtn_luxury.setVisibility(View.INVISIBLE);
                text_soldout4.setText("예약 불가");
                text_soldout4.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 20 && date_int <= 31)
            {
                Rbtn_family.setVisibility(View.INVISIBLE);
                text_soldout3.setText("예약 불가");
                text_soldout3.setTextColor(Color.parseColor("#ff7e7e"));
                // 클릭시 아무 동작 못하게 막음

            }
        }
        else // 속초
        {
            if(date_int <= 10){ // 일이 10보다 작으면

                Rbtn_family.setVisibility(View.INVISIBLE);
                text_soldout3.setText("예약 불가");
                text_soldout3.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 10 && date_int <= 20)
            {
                Rbtn_single.setVisibility(View.INVISIBLE);
                text_soldout1.setText("예약 불가");
                text_soldout1.setTextColor(Color.parseColor("#ff7e7e"));

            }
            else if (date_int > 20 && date_int <= 31)
            {
                Rbtn_luxury.setVisibility(View.INVISIBLE);
                text_soldout4.setText("예약 불가");
                text_soldout4.setTextColor(Color.parseColor("#ff7e7e"));
                // 클릭시 아무 동작 못하게 막음

            }
        }

        // 여기까지 랜덤방 예약 코드였습니다.

        // 버튼 모두 비활성화
        Rbtn_single.setChecked(false);
        Rbtn_luxury.setChecked(false);
        Rbtn_double.setChecked(false);
        Rbtn_family.setChecked(false);

        // 럭셔리룸 자세히 보기 이벤트
        text_info_luxury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Luxury.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });
        // 패밀리룸 자세히 보기이벤트
        text_info_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Family.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });



        // 싱글룸 자세히 보기 이벤트
        text_info_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Single.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });
        // 더블룸 자세히보기 이벤트
        text_info_double.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Double.class);
                intent.putExtra("hotel_number",hotel_number);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });


        // 아무것도 선택안되어 있는 상태의 다음으로 이벤트
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(Luna_Reservation_Room.this);
                dlg.setMessage("객실을 선택해 주세요.");
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });


        // 싱글룸 버튼 선택
        Rbtn_single.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean isCheckd) {
                if(isCheckd){
                    Rbtn_luxury.setChecked(false);
                    Rbtn_double.setChecked(false);
                    Rbtn_family.setChecked(false);
                     // 럭셔리 버튼은 비활성화
                    // 싱글룸 변수는 1이다
                    final int roomNum = 1;
                    btn_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent Intent = new Intent(getApplicationContext(), Luna_Reservation_pay.class);
                            Intent.putExtra("roomNum",roomNum);  // 방번호다
                            Intent.putExtra("hotel_number",hotel_number); // 호텔번호다 지점이 선택된다
                            Intent.putExtra("year_s_out",year_s_out);    // 체크아웃의 년도다 ex 2020
                            Intent.putExtra("month_s_out",month_s_out); // 체크아웃의 월이다 ex 08
                            Intent.putExtra("date_s_out",date_s_out); // 체크아웃의 일이다 ex 20
                            Intent.putExtra("year_s_in",year_s_in); // 체크인의 년도다 ex 2020
                            Intent.putExtra("month_s_in", month_s_in); // 체크인의 월이다 ex 09
                            Intent.putExtra("date_s_in",date_s_in); // 체크아웃의 일이다 ex 01
                            Intent.putExtra("put_checkout",put_checkout); // 체크아웃 8자리 ex 20200820
                            Intent.putExtra("put_checkin", put_checkin); // 체크인 8자리 20200901
                            Intent.putExtra("tnrqkr",tnrqkr); // 숙박일수 정수형 변수
                            Intent.putExtra("date_int",date_int); // 일자 정수형 변수다 이변수는 나중에 랜덤한 방 예약을 위한 변수.
                            startActivity(Intent);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }
                    });
                } else {
                }
            }
        });


        // 럭셔리룸 버튼 선택
        Rbtn_double.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckd) {
                if(isCheckd){
                    Rbtn_single.setChecked(false);
                    Rbtn_luxury.setChecked(false);
                    Rbtn_family.setChecked(false);
                    // 싱글룸 비활성화
                    //더블룸 변수는 2이다
                    final int roomNum = 2;
                    btn_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent Intent = new Intent(getApplicationContext(), Luna_Reservation_pay.class);
                            Intent.putExtra("roomNum",roomNum);  // 방번호다
                            Intent.putExtra("hotel_number",hotel_number); // 호텔번호다 지점이 선택된다
                            Intent.putExtra("year_s_out",year_s_out);    // 체크아웃의 년도다 ex 2020
                            Intent.putExtra("month_s_out",month_s_out); // 체크아웃의 월이다 ex 08
                            Intent.putExtra("date_s_out",date_s_out); // 체크아웃의 일이다 ex 20
                            Intent.putExtra("year_s_in",year_s_in); // 체크인의 년도다 ex 2020
                            Intent.putExtra("month_s_in", month_s_in); // 체크인의 월이다 ex 09
                            Intent.putExtra("date_s_in",date_s_in); // 체크아웃의 일이다 ex 01
                            Intent.putExtra("put_checkout",put_checkout); // 체크아웃 8자리 ex 20200820
                            Intent.putExtra("put_checkin", put_checkin); // 체크인 8자리 20200901
                            Intent.putExtra("tnrqkr",tnrqkr); // 숙박일수 정수형 변수
                            Intent.putExtra("date_int",date_int); // 일자 정수형 변수다 이변수는 나중에 랜덤한 방 예약을 위한 변수.
                            startActivity(Intent);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }
                    });
                } else {
                }
            }
        });

        // 패밀리룸 버튼 선택
        Rbtn_family.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckd) {
                if(isCheckd){
                    Rbtn_single.setChecked(false);
                    Rbtn_double.setChecked(false);
                    Rbtn_luxury.setChecked(false);
                    // 싱글룸 비활성화
                    //럭셔리룸 변수는 3이다
                    final int roomNum = 3;
                    btn_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent Intent = new Intent(getApplicationContext(), Luna_Reservation_pay.class);
                            Intent.putExtra("roomNum",roomNum);  // 방번호다
                            Intent.putExtra("hotel_number",hotel_number); // 호텔번호다 지점이 선택된다
                            Intent.putExtra("year_s_out",year_s_out);    // 체크아웃의 년도다 ex 2020
                            Intent.putExtra("month_s_out",month_s_out); // 체크아웃의 월이다 ex 08
                            Intent.putExtra("date_s_out",date_s_out); // 체크아웃의 일이다 ex 20
                            Intent.putExtra("year_s_in",year_s_in); // 체크인의 년도다 ex 2020
                            Intent.putExtra("month_s_in", month_s_in); // 체크인의 월이다 ex 09
                            Intent.putExtra("date_s_in",date_s_in); // 체크아웃의 일이다 ex 01
                            Intent.putExtra("put_checkout",put_checkout); // 체크아웃 8자리 ex 20200820
                            Intent.putExtra("put_checkin", put_checkin); // 체크인 8자리 20200901
                            Intent.putExtra("tnrqkr",tnrqkr); // 숙박일수 정수형 변수
                            Intent.putExtra("date_int",date_int); // 일자 정수형 변수다 이변수는 나중에 랜덤한 방 예약을 위한 변수.
                            startActivity(Intent);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }
                    });
                } else {
                }
            }
        });

        // 럭셔리룸 버튼 선택
        Rbtn_luxury.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckd) {
                if(isCheckd){
                    Rbtn_single.setChecked(false);
                    Rbtn_double.setChecked(false);
                    Rbtn_family.setChecked(false);
                    // 싱글룸 비활성화
                    //럭셔리룸 변수는 4이다
                    final int roomNum = 4;
                    btn_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent Intent = new Intent(getApplicationContext(), Luna_Reservation_pay.class);
                            Intent.putExtra("roomNum",roomNum);  // 방번호다
                            Intent.putExtra("hotel_number",hotel_number); // 호텔번호다 지점이 선택된다
                            Intent.putExtra("year_s_out",year_s_out);    // 체크아웃의 년도다 ex 2020
                            Intent.putExtra("month_s_out",month_s_out); // 체크아웃의 월이다 ex 08
                            Intent.putExtra("date_s_out",date_s_out); // 체크아웃의 일이다 ex 20
                            Intent.putExtra("year_s_in",year_s_in); // 체크인의 년도다 ex 2020
                            Intent.putExtra("month_s_in", month_s_in); // 체크인의 월이다 ex 09
                            Intent.putExtra("date_s_in",date_s_in); // 체크아웃의 일이다 ex 01
                            Intent.putExtra("put_checkout",put_checkout); // 체크아웃 8자리 ex 20200820
                            Intent.putExtra("put_checkin", put_checkin); // 체크인 8자리 20200901
                            Intent.putExtra("tnrqkr",tnrqkr); // 숙박일수 정수형 변수
                            Intent.putExtra("date_int",date_int); // 일자 정수형 변수다 이변수는 나중에 랜덤한 방 예약을 위한 변수.
                            startActivity(Intent);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }
                    });
                } else {
                }
            }
        });


// 좌측 상단 뒤로가기버튼 클릭시 창이 꺼짐 + 애니메이션 이벤트
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
