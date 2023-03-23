package com.cookandroid.luna_hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class Luna_menu extends AppCompatActivity {

    TextView menu_id, menu_email, btn_login_logout,btn_new_acoout,text_hi;
    Button btn_back, btn_res_check, btn_setting, btn_room, btn_hotel_info,btn_myinfo,btn_call,
            btn_news,btn_promotion,btn_faq,btn_chat,btn_clause,btn_establishment,btn_hotel_local_info;
    ImageView image_gender;

    // 뷰페이저
    private ViewPager viewPager;
    private TextViewPagerAdapter pagerAdapter;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
    // 여기까지


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_menu);

        // 페이지 변수를 0으로 초기화합니다
        Login_gloval.pageNum = 0;

        // 회원 정보를 읽어오기 위한 SharedPreferences 선언
        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
        final SharedPreferences.Editor editor = info.edit();

        // 예약 정보를 읽어오기 위한 SharedPreferences 선언
        SharedPreferences reserve = getSharedPreferences("reserve", MODE_PRIVATE);
        final SharedPreferences.Editor editor2 = reserve.edit();

        // 공지사항 목록을 읽어오기 위한 SharedPreferences 선언
        SharedPreferences news = getSharedPreferences("news", MODE_PRIVATE);
        final SharedPreferences.Editor editor4 = news.edit();

        // 자동로그인 정보를 읽기 위한 SharedPreferences 선언
        final SharedPreferences logininfo = getSharedPreferences("user",0); // user 라는 파일을 생성합니다.
        final SharedPreferences.Editor editor3 = logininfo.edit(); // 에디터 연결합니다.


        image_gender = (ImageView)findViewById(R.id.image_gender);
        menu_id = (TextView) findViewById(R.id.menu_id);
        menu_email = (TextView) findViewById(R.id.menu_email);
        btn_login_logout = (TextView )findViewById(R.id.btn_login_logout);
        btn_new_acoout = (TextView)findViewById(R.id.btn_new_account);
        text_hi = (TextView)findViewById(R.id.text_hi);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_call = (Button)findViewById(R.id.btn_call);
        btn_myinfo = (Button) findViewById(R.id.btn_myinfo);
        btn_res_check = (Button)findViewById(R.id.btn_res_check);
        btn_hotel_info = (Button)findViewById(R.id.btn_hotel_info);
        btn_room = (Button)findViewById(R.id.btn_room);

        btn_news = (Button)findViewById(R.id.btn_news);
        btn_promotion = (Button)findViewById(R.id.btn_promotion);
        btn_faq = (Button)findViewById(R.id.btn_faq);
        btn_chat= (Button)findViewById(R.id.btn_chat);
        btn_clause = (Button)findViewById(R.id.btn_clause);
        btn_establishment = (Button)findViewById(R.id.btn_establishment);
        btn_hotel_local_info = (Button)findViewById(R.id.btn_hotel_local_info);


        // image_gender 사용설명
        // 젠더 값  -> 주민번호 뒷자리 1자리 값이 1 or 3이면
        // image_gender.setImageResource(R.drawable.man); 프로필사진 남자로

        // 젠더 값  -> 주민번호 뒷자리 1자리 값이 2 or 4 이면
        // image_gender.setImageResource(R.drawable.lady); // 프로필사진 여자로


        // 엑티비티가 호출됨과 동시에 조건문으로 전역변수의 값여부를 확인합니다
        if (Login_gloval.login_id == null) {    // 널값이면 로그인이 필요하다는 뜻입니다
            image_gender.setImageResource(R.drawable.guest);  // GUEST 계정이면 손님 사진을 사용합니다.
            btn_new_acoout.setVisibility(View.VISIBLE); // 회원가입 버튼이 보이게함
            text_hi.setVisibility(View.INVISIBLE); // 안녕하세요 문구가 안보이게함
            btn_login_logout.setText("로그인");
            btn_login_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Intent = new Intent(getApplicationContext(), Luna_Login.class);
                    startActivity(Intent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
            });
        }

        // 로그인 되어있는 상태라면 else 문을 실행
        else {
            // 현재 로그인 된 회원의 성별 정보를 가져와서 gender 변수에 넣습니다.
            String gender = info.getString("userGender", "");

            // 회원의 주민번호 뒷자리 시작이 1 또는 3이면 남자 사진,
            if(gender.equals("1") || gender.equals("3")) {
                image_gender.setImageResource(R.drawable.man);
            }
            // 1, 3이 아니라면 여자 사진이 적용됩니다.
            else {
                image_gender.setImageResource(R.drawable.lady);
            }

            menu_id.setText((info.getString("userName", "")) + "님");
            menu_email.setText(info.getString("userEmail", ""));
            btn_new_acoout.setVisibility(View.INVISIBLE); // 회원가입 버튼이 안보이게함
            text_hi.setVisibility(View.VISIBLE); // 안녕하세요 문구가 보이게함
            btn_login_logout.setText("로그아웃"); // 값이 있다면 로그인이 되어있는 상태입니다
            btn_login_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // 로그아웃 버튼을 누르면 전연변수의 모든 값이 null로 초기화됩니다.

                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_menu.this);
                    builder.setMessage("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast accountToast = Toast.makeText(Luna_menu.this,"로그아웃 되셨습니다.",Toast.LENGTH_SHORT);
                            accountToast.show();

                            Login_gloval.login_id = null;
                            Login_gloval.login_password = null;

                            Login_gloval.Login_resName = null;

                            editor.clear();
                            editor.commit();

                            editor2.clear();
                            editor2.commit();

                            // 자동로그인 파일 내용 전부삭제
                            editor3.clear();
                            editor3.commit();

                            editor4.clear();
                            editor4.commit();

                            Intent goMain = new Intent(getApplicationContext(), Luna_Login.class);
                            goMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(goMain);
                            finish();
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }
                    });
                    builder.setNegativeButton("아니요",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        // 뷰페이저 코드
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new TextViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);



        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 4) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        // 여기까지

        // 좌측 상단 뒤로가기버튼 클릭시 창이 꺼짐 + 애니메이션 이벤트
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });




        // 예약확인 버튼 이벤트
        // 로그인 여부를 항상 물어봐야함
        // 로그인이 안되어있다면 로그인을 하라는 대화상자가 출력되야함
        btn_res_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences reserve = getSharedPreferences("reserve", MODE_PRIVATE);

                // 로그인 여부를 확인허는 if ~ else 문
                if (Login_gloval.login_id == null) {    // 널값이면 로그인이 필요하다는 뜻입니다
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_menu.this);
                    builder.setMessage("로그인이 필요합니다.");
                    builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Luna_Login.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("취소",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                // 가져온 예약 정보의 resCODE, 즉 예약번호가 하나라도 없다면 (=공백이라면) 밑 else if문 실행
                else if (reserve.getString("resCODE0", "").equals("")) {
                    // 예약확인을 눌렀을 때 접수된 예약이 없다고 뜨는 TextView가 있는 액티비티로 넘어갑니다.
                    Intent intent = new Intent(getApplicationContext(), Luna_Reservation_Check_Nothing.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
                // 로그인도 되어있고 가져온 예약 정보가 하나라도 있다면 예약확인 액티비티로 이동
                else {
                    Intent intent = new Intent(getApplicationContext(), Luna_Reservation_Check.class);
                    startActivity(intent);
                    // 페이지 변수 선언 1은 메뉴화면에서 진입했다는 뜻입니다.
                    Login_gloval.pageNum = 1;
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
            }
        });
        // 내 정보 버튼 이벤트
        // 로그인 여부를 항상 물어봐야함
        // 로그인이 안되어있다면 로그인을 하라는 대화상자가 출력되야함
        btn_myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 여부를 확인허는 if ~ else 문
                if (Login_gloval.login_id == null) {    // 널값이면 로그인이 필요하다는 뜻입니다
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_menu.this);
                    builder.setMessage("로그인이 필요합니다.");
                    builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Luna_Login.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("취소",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Luna_Myinfo.class);
                    startActivity(intent);
                    // 페이지 변수 선언 1은 메뉴화면에서 진입했다는 뜻입니다.
                    Login_gloval.pageNum = 1;
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
            }
        });


        // 환경설정 버튼 이벤트
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_Setting.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 호텔 소개 버튼 이벤트
        btn_hotel_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_Info_Hotel.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });


        // 객실안내 버튼 이벤트
        btn_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_Info_Room.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 지점 안내
        btn_hotel_local_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Local_Hotel_Info.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });
        // 프로모션
        btn_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_Promotion.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        // FAQ
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_FAQ.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        // 전화하기
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(Luna_menu.this);
                dlg.setTitle("지점을 선택해 주세요"); //제목
                final String[] versionArray = new String[] {"호텔 루나 - 서울 점 010-3826-6515 ","호텔 루나 - 부산 점 010-4993-0654",
                        "호텔 루나 - 제주 점 010-5480-7429","호텔 루나 - 속초 점 010-4122-9142"};

                dlg.setItems(versionArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) // 서울점
                        {
                            Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01038266515"));
                            startActivity(tt);
                        }
                        else if (which == 1) // 부산점
                        {
                            Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01049930654"));
                            startActivity(tt);
                        }
                        else if (which == 2) // 제주점
                        {
                            Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 01054807429"));
                            startActivity(tt);
                        }
                        else if (which == 3) // 속초점
                        {
                            Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01041229142"));
                            startActivity(tt);
                        }
                        else{}
                    }
                });
//                버튼 클릭시 동작
                dlg.setPositiveButton("취소",null);
                dlg.show();
            }
        });
        // 시설안내
        btn_establishment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Establishment_Info.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        // 이용약관
        btn_clause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_Clause.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        // 1대1 상담
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://plus.kakao.com/home/@hotel_luna"));
                startActivity(i);
            }
        });
        // 공지사항
        btn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_News.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });
        // 회원가입 버튼 이벤트
        btn_new_acoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_NewAccount.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });




    }




    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
