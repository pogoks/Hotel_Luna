package com.cookandroid.luna_hotel;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Luna_Main extends AppCompatActivity {

    Button  btn_lunalogo,btn_menu,btn_setting,btn_reser;
    TextView text_checkin_mmdd,text_checkin_yy,text_checkout_mmdd,text_checkout_yy,text_tnrqkr;
    TextView t_date,t_month,t_year,t_checkin,t_checkout;
    LinearLayout lay_checkin,lay_checkout,lay_reser_check,lay_hotel_info,lay_room_info,lay_chat,lay_call,lay_hotel_local_info;
    Spinner spn_hotel_name;

    private int tnrqkr = 0; // 숙박 몇박 변수
    private  int hotel_number = 0; // 호텔 지점 번호입니다
    private String put_checkin =null; // 예약한 날짜를 보낼 변수
    private String put_checkout=null; // 예약한 날짜를 보낼 변수

    // 스트링형 변수를 선언
    private  String date_s_in,month_s_in,year_s_in; //  체크인 날짜 변환하는 변수
    private  String date_s_out,month_s_out,year_s_out; //  체크인 날짜 변환하는 변수
    // 인트형 변수를 선언  변환하는 변수
    private  int date_int = 0;
    private  int month_int = 0;
    private  int year_int = 0;

    private static final String TAG = "Luna_SelectDate";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_main);

        // 다음은 내정보,예약확인 화면이동 오류를 해결하는 코드입니다.
        // 메뉴에서 진입한 경우 다시 홈으로 와서 메뉴화면으로 이동합니다
        if(Login_gloval.pageNum == 1)
        {
            Intent intent = new Intent(getApplicationContext(), Luna_menu.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }
        // 내정보 -> 예약확인에서 홈으로 온경우는 다시 내정보로 이동합니다
        if(Login_gloval.pageNum == 2)
        {
            Intent intent = new Intent(getApplicationContext(), Luna_Myinfo.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }


        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_menu = (Button)findViewById(R.id.btn_menu);
        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_reser = (Button)findViewById(R.id.btn_reser);

        t_checkin = (TextView) findViewById(R.id.t_checkin);
        t_checkout = (TextView) findViewById(R.id.t_checkout);
        t_date = (TextView) findViewById(R.id.t_date);
        t_month = (TextView) findViewById(R.id.t_month);
        t_year = (TextView) findViewById(R.id.t_year);
        text_checkin_mmdd = (TextView) findViewById(R.id.text_checkin_mmdd);
        text_checkout_mmdd = (TextView) findViewById(R.id.text_checkout_mmdd);
        text_checkout_yy = (TextView) findViewById(R.id.text_checkout_yy);
        text_checkin_yy = (TextView) findViewById(R.id.text_checkin_yy);
        text_tnrqkr = (TextView) findViewById(R.id.text_tnrqkr);

        lay_call = (LinearLayout)findViewById(R.id.lay_call);
        lay_chat = (LinearLayout)findViewById(R.id.lay_chat);
        lay_checkin = (LinearLayout)findViewById(R.id.lay_checkin);
        lay_checkout = (LinearLayout)findViewById(R.id.lay_checkout);
        lay_reser_check = (LinearLayout)findViewById(R.id.lay_reser_check);
        lay_hotel_info = (LinearLayout)findViewById(R.id.lay_hotel_info);
        lay_hotel_local_info= (LinearLayout)findViewById(R.id.lay_hotel_local_info);
        lay_room_info = (LinearLayout)findViewById(R.id.lay_room_info);

        spn_hotel_name = (Spinner) findViewById(R.id.spn_hotel_name);

        // 대화상자 선택 이벤트트
       spn_hotel_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int number, long l) {

                if(number == 1) { // 서울지점
                    hotel_number = 1;
                } else if (number == 2){ // 부산지점
                    hotel_number = 2;
                }else if (number == 3) { // 제주 지점
                    hotel_number = 3;
                }else if (number == 4){ // 속초 지점
                    hotel_number = 4;
                }else { // 아무것도 선택하지 않음
                    hotel_number = 0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        // 전화걸기 이제 대화상자로 지점을 선택합니다.
       lay_call.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               AlertDialog.Builder dlg = new AlertDialog.Builder(Luna_Main.this);
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



        // 1대1 상담
        lay_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://plus.kakao.com/home/@hotel_luna"));
                startActivity(i);

            }
        });
        // 호텔 소개
        lay_hotel_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Hotel.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        // 지점안내
        lay_hotel_local_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(getApplicationContext(), Luna_Local_Hotel_Info.class);
                 startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 예약확인
        lay_reser_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences reserve = getSharedPreferences("reserve", MODE_PRIVATE);

                // 로그인이 안되어있다면
                if(Login_gloval.login_id == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Main.this);
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
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
            }
        });
        // 객실소개
        lay_room_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Info_Room.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });




        //예약버튼 1. 로그인이 되어있을 것  2. tnrqkr 변수가 0이 아닐것
        // 3. 보내려는 변수들이 null 값이 아닐것 4. 지점번호가 0이 아닐것
        btn_reser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 안되어있을때
                if(Login_gloval.login_id == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Main.this);
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
                else{// 로그인 되어있을때
                    if(tnrqkr == 0) { // 숙박 변수가 0일 때 (날짜 체크 안함)
                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Main.this);
                        builder.setMessage("날짜를 선택해주세요.");
                        builder.setPositiveButton("확인",null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else if (hotel_number == 0){ // 호텔 번호가 0일때 (지점 선택 안함)
                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Main.this);
                        builder.setMessage("예약하실 지점을 선택해주세요.");
                        builder.setPositiveButton("확인",null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else
                    {
                        // 객실 선택으로 이동한다.
                        Intent intent = new Intent(getApplicationContext(), Luna_Reservation_Room.class);
                        intent.putExtra("hotel_number",hotel_number); // 호텔번호다 지점이 선택된다
                        intent.putExtra("year_s_out",year_s_out);    // 체크아웃의 년도다 ex 2020
                        intent.putExtra("month_s_out",month_s_out); // 체크아웃의 월이다 ex 08
                        intent.putExtra("date_s_out",date_s_out); // 체크아웃의 일이다 ex 20
                        intent.putExtra("year_s_in",year_s_in); // 체크인의 년도다 ex 2020
                        intent.putExtra("month_s_in", month_s_in); // 체크인의 월이다 ex 09
                        intent.putExtra("date_s_in",date_s_in); // 체크아웃의 일이다 ex 01
                        intent.putExtra("put_checkout",put_checkout); // 체크아웃 8자리 ex 20200820
                        intent.putExtra("put_checkin", put_checkin); // 체크인 8자리 20200901
                        intent.putExtra("tnrqkr",tnrqkr); // 숙박일수 정수형 변수
                        intent.putExtra("date_int",date_int); // 일자 정수형 변수다 이변수는 나중에 랜덤한 방 예약을 위한 변수.

                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }

                }

                }


        });





        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        final Calendar cal = Calendar.getInstance();

        Log.e(TAG, cal.get(Calendar.YEAR)+"");
        Log.e(TAG, cal.get(Calendar.MONTH)+1+"");
        Log.e(TAG, cal.get(Calendar.DATE)+"");
        Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY)+"");
        Log.e(TAG, cal.get(Calendar.MINUTE)+"");

        // 체크인 클릭
        lay_checkin.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {

                                               DatePickerDialog dialog = new DatePickerDialog(Luna_Main.this, new DatePickerDialog.OnDateSetListener() {

                                                   @Override
                                                   public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                                                       String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
                                                       Toast.makeText(Luna_Main.this, msg, Toast.LENGTH_SHORT).show();

                                                       // 텍스트뷰에 각각 일 월 년 의 데이터를 담는다
                                                       t_date.setText(String.format("%d",date));
                                                       t_month.setText(String.format("%d",month+1));
                                                       t_year.setText(String.format("%d",year));

                                                       // 정수형 변수에 그 데이터들을 각각 담는다
                                                        date_int = Integer.parseInt(t_date.getText().toString());
                                                        month_int = Integer.parseInt(t_month.getText().toString());
                                                        year_int = Integer.parseInt(t_year.getText().toString());

                                                       year_s_in = "" + year_int;
                                                       // 10일 미만 10월 미만 데이터들 앞에 0을 붙여서 스트링으로 변환한다.
                                                       if(date_int < 10) {
                                                           date_s_in = "0"+date_int;
                                                       } else {
                                                           date_s_in = "" + date_int;
                                                       }

                                                       if(month_int < 10) {
                                                           month_s_in = "0"+ month_int;
                                                       } else {
                                                           month_s_in = "" + month_int;
                                                       }
                                                       // 변수3개를 연달아서 텍스트에 담는다
                                                        put_checkin = year_s_in + month_s_in + date_s_in; // 다음화면에 보내줄 변수다
                                                       text_checkin_mmdd.setText(month_s_in + "월" + date_s_in + "일");
                                                       text_checkin_yy.setText(year_s_in + "년");
                                                       // 안보이는 텍스트에 담아준다
                                                       t_checkin.setText(put_checkin);
                                                       t_checkout.setText(put_checkout);

                                                       // 두날짜의 차이를 구해서 몇박몇일인지 구함
                                                       String strFormat = "yyyyMMdd";
                                                       String strStartDate = t_checkin.getText().toString();
                                                       String strEndDate = t_checkout.getText().toString();

                                                       //SimpleDateFormat 을 이용하여 startDate와 endDate의 Date 객체를 생성한다.
                                                       SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
                                                       try{
                                                           Date startDate = sdf.parse(strStartDate);
                                                           Date endDate = sdf.parse(strEndDate);

                                                           //두날짜 사이의 시간 차이(ms)를 하루 동안의 ms(24시*60분*60초*1000밀리초) 로 나눈다.
                                                           long diffDay = (endDate.getTime() - startDate.getTime()) / (24*60*60*1000);
                                                           long diffDay2 = diffDay+1;

                                                           // 잘못된 날짜가 체크되면 실행되는 코드
                                                           if(diffDay > 0) {
                                                               if(diffDay2 > 10) {
                                                                   AlertDialog.Builder dlg = new AlertDialog.Builder(Luna_Main.this);
                                                                   dlg.setMessage("10일 이상은 불가능합니다.");
                                                                   dlg.setPositiveButton("확인",null);
                                                                   dlg.show();
                                                                   text_checkin_mmdd.setText("");
                                                                   text_checkout_mmdd.setText("");
                                                                   text_checkin_yy.setText("");
                                                                   text_checkout_yy.setText("");
                                                                   t_checkin.setText("");
                                                                   t_checkout.setText("");
                                                                   year_s_out = null;
                                                                   month_s_out = null;
                                                                   date_s_out = null;
                                                                   year_s_in = null;
                                                                   month_s_in = null;
                                                                   date_s_in = null;
                                                                   put_checkin = null;
                                                                   put_checkout = null;
                                                                   tnrqkr = 0;
                                                                   year_int = 0;
                                                                   month_int = 0;
                                                                   date_int = 0;
                                                               }
                                                               tnrqkr = (int)diffDay;
                                                               text_tnrqkr.setText(tnrqkr + "박");
                                                           } else {
                                                               AlertDialog.Builder dlg = new AlertDialog.Builder(Luna_Main.this);
                                                               dlg.setMessage("체크아웃 날짜가 체크인 날짜 보다 앞에 있습니다.");
                                                               dlg.setPositiveButton("확인",null);
                                                               dlg.show();
                                                               text_checkin_mmdd.setText("");
                                                               text_checkout_mmdd.setText("");
                                                               text_checkin_yy.setText("");
                                                               text_checkout_yy.setText("");
                                                               t_checkin.setText("");
                                                               t_checkout.setText("");
                                                               year_s_out = null;
                                                               month_s_out = null;
                                                               date_s_out = null;
                                                               year_s_in = null;
                                                               month_s_in = null;
                                                               date_s_in = null;
                                                               put_checkin = null;
                                                               put_checkout = null;
                                                               tnrqkr = 0;
                                                               year_int = 0;
                                                               month_int = 0;
                                                               date_int = 0;
                                                           }
                                                       }catch(ParseException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                                               dialog.getDatePicker().setMinDate(new Date().getTime() + 86400000);    //입력한 날짜 이후로 클릭 안되게 옵션
                                               dialog.show();
                                           }
                                       }
        );

    // 체크아웃 클릭
        lay_checkout.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {

                                               DatePickerDialog dialog = new DatePickerDialog(Luna_Main.this, new DatePickerDialog.OnDateSetListener() {

                                                   @RequiresApi(api = Build.VERSION_CODES.N)
                                                   @Override
                                                   public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                                                       String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
                                                       Toast.makeText(Luna_Main.this, msg, Toast.LENGTH_SHORT).show();

                                                       // 텍스트뷰에 각각 일 월 년 의 데이터를 담는다
                                                       t_date.setText(String.format("%d",date));
                                                       t_month.setText(String.format("%d",month+1));
                                                       t_year.setText(String.format("%d",year));

                                                       // 정수형 변수에 그 데이터들을 각각 담는다
                                                        date_int = Integer.parseInt(t_date.getText().toString());
                                                        month_int = Integer.parseInt(t_month.getText().toString());
                                                        year_int = Integer.parseInt(t_year.getText().toString());

                                                        year_s_out = "" + year_int;
                                                       // 10일 미만 10월 미만 데이터들 앞에 0을 붙여서 스트링으로 변환한다.
                                                       if(date_int < 10) {
                                                           date_s_out = "0"+date_int;
                                                       } else {
                                                           date_s_out = "" + date_int;
                                                       }

                                                       if(month_int < 10) {
                                                           month_s_out = "0"+ month_int;
                                                       } else {
                                                           month_s_out = "" + month_int;
                                                       }
                                                       // 변수3개를 연달아서 텍스트에 담는다
                                                       put_checkout = year_s_out + month_s_out + date_s_out; // 다음화면에 보내줄 변수다
                                                       text_checkout_mmdd.setText(month_s_out + "월" + date_s_out + "일");
                                                       text_checkout_yy.setText(year_s_out + "년");
                                                       // 안보이는 텍스트에 담아준다
                                                       t_checkin.setText(put_checkin);
                                                       t_checkout.setText(put_checkout);

                                                       // 두날짜의 차이를 구해서 몇박몇일인지 구함
                                                       String strFormat = "yyyyMMdd";
                                                       String strStartDate = t_checkin.getText().toString();
                                                       String strEndDate = t_checkout.getText().toString();

                                                       //SimpleDateFormat 을 이용하여 startDate와 endDate의 Date 객체를 생성한다.
                                                       SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
                                                       try{
                                                           Date startDate = sdf.parse(strStartDate);
                                                           Date endDate = sdf.parse(strEndDate);

                                                           //두날짜 사이의 시간 차이(ms)를 하루 동안의 ms(24시*60분*60초*1000밀리초) 로 나눈다.
                                                           long diffDay = (endDate.getTime() - startDate.getTime()) / (24*60*60*1000);
                                                           long diffDay2 = diffDay+1;

                                                           // 잘못된 날짜가 체크되면 실행되는 코드
                                                           if(diffDay > 0) {
                                                               if(diffDay2 > 10) {
                                                                   AlertDialog.Builder dlg = new AlertDialog.Builder(Luna_Main.this);
                                                                   dlg.setMessage("10일 이상은 불가능합니다.");
                                                                   dlg.setPositiveButton("확인",null);
                                                                   dlg.show();
                                                                   text_checkin_mmdd.setText("");
                                                                   text_checkout_mmdd.setText("");
                                                                   text_checkin_yy.setText("");
                                                                   text_checkout_yy.setText("");
                                                                   t_checkin.setText("");
                                                                   t_checkout.setText("");
                                                                   year_s_out = null;
                                                                   month_s_out = null;
                                                                   date_s_out = null;
                                                                   year_s_in = null;
                                                                   month_s_in = null;
                                                                   date_s_in = null;
                                                                   put_checkin = null;
                                                                   put_checkout = null;
                                                                   tnrqkr = 0;
                                                                   year_int = 0;
                                                                   month_int = 0;
                                                                   date_int = 0;
                                                               }
                                                               tnrqkr = (int)diffDay;
                                                               text_tnrqkr.setText(tnrqkr + "박");
                                                           } else {
                                                               AlertDialog.Builder dlg = new AlertDialog.Builder(Luna_Main.this);
                                                               dlg.setMessage("체크아웃 날짜가 체크인 날짜 보다 앞에 있습니다.");
                                                               dlg.setPositiveButton("확인",null);
                                                               dlg.show();
                                                               text_checkin_mmdd.setText("");
                                                               text_checkout_mmdd.setText("");
                                                               text_checkin_yy.setText("");
                                                               text_checkout_yy.setText("");
                                                               t_checkin.setText("");
                                                               t_checkout.setText("");
                                                               year_s_out = null;
                                                               month_s_out = null;
                                                               date_s_out = null;
                                                               year_s_in = null;
                                                               month_s_in = null;
                                                               date_s_in = null;
                                                               put_checkin = null;
                                                               put_checkout = null;
                                                               tnrqkr = 0;
                                                               year_int = 0;
                                                               month_int = 0;
                                                               date_int = 0;
                                                           }
                                                       }catch(ParseException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                                               dialog.getDatePicker().setMinDate(new Date().getTime() + 86400000);    //입력한 날짜 이후로 클릭 안되게 옵션
                                               dialog.show();
                                           }
                                       }
        );




    }

    private long backKeyPressedTime = 0;
    private Toast toast;

    // 취소버튼 동작 코드입니다
    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

}
