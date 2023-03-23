package com.cookandroid.luna_hotel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Luna_Reservation_pay extends AppCompatActivity {

    Button  btn_lunalogo,btn_back,btn_setting, btn_next;
    TextView text_checkin_mmdd,text_checkin_yy,text_checkout_mmdd,text_checkout_yy,text_tnrqkr,text_total,text_room_name,text_hotel_name;
    LinearLayout lay_card;
    Spinner spn_card_name,spn_pay_name;

    private String jsonString;
    ArrayList<ReserveInfo> reserveArrayList;

    private  int hotel_number = 0; // 호텔 지점 번호입니다

    // 너가 디비로 보내야 할 변수들 목록임 너 입맞에 맞게 가져가셈

    private int tnrqkr = 0; // 숙박 몇박 변수
    private String put_checkin =null; // 예약한 날짜를 보낼 변수 ex 20200417
    private String put_checkout=null; // 예약한 날짜를 보낼 변수  ex 20200418
    private int roomNum = 0; // 방번호

    private  String date_s_in,month_s_in,year_s_in; //  체크인 날짜 변환하는 변수  date 2자리 month 2자리 year 4자리임
    private  String date_s_out,month_s_out,year_s_out; //  체크인 날짜 변환하는 변수  date 2자리 month 2자리 year 4자리임

    private  int date_int = 0;  // 이건 필요업는듯이제

    private int total_price = 0;   // 총가격
    private int room_price = 0;   // 방가격
    private String room_name = null; // 선택한 방이름
    private String hotel_name = null; // 선택한 호텔 지점 이름
    private  String total_price_string = null; // 스트링형 돈 변수 ex) 250.000 (. 표시 되게 담은거)

    private String payment = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_reservation_pay);

        // 이전화면에서 보낸 데이터 불러오기
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
        // 객실 번호 불러온다
        roomNum = intent.getIntExtra("roomNum",0);
        // 유저 아이디 가져옵니다.
        final String resID = Login_gloval.login_id;

        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_next = (Button) findViewById(R.id. btn_next);

        text_checkin_mmdd = (TextView) findViewById(R.id.text_checkin_mmdd);
        text_checkout_mmdd = (TextView) findViewById(R.id.text_checkout_mmdd);
        text_checkout_yy = (TextView) findViewById(R.id.text_checkout_yy);
        text_checkin_yy = (TextView) findViewById(R.id.text_checkin_yy);
        text_tnrqkr = (TextView) findViewById(R.id.text_tnrqkr);
        text_total = (TextView)findViewById(R.id.text_total) ;
        text_room_name = (TextView)findViewById(R.id.text_room_name) ;
        text_hotel_name = (TextView)findViewById(R.id.text_hotel_name) ;


        lay_card = (LinearLayout) findViewById(R.id.lay_card);

        spn_card_name = (Spinner)findViewById(R.id.spn_card_name);
        spn_pay_name = (Spinner)findViewById(R.id.spn_pay_name);

        //뒤로버튼 클릭 메소드
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // 메인화면으로 가는 로고버튼튼
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home_Intent = new Intent(getApplicationContext(),Luna_Main.class);
                startActivity(Home_Intent);
            }
        });



        // 날짜 표시하는곳에 텍스트 담기.
        text_checkin_mmdd.setText(month_s_in + "월" + date_s_in + "일");
        text_checkin_yy.setText(year_s_in + "년");
        text_checkout_mmdd.setText(month_s_out + "월" + date_s_out + "일");
        text_checkout_yy.setText(year_s_out + "년");
        text_tnrqkr.setText(tnrqkr + "박");

        // 방번호를 체크해서 방이름을 부여한다

        if (roomNum == 1) // 싱글룸
        {
            room_name = "Single Room";
            room_price = 150000;
            text_room_name.setText("- " + room_name + " -");
        }
        else if (roomNum == 2) // 더블룸
        {
            room_name = "Double Room";
            room_price = 200000;
            text_room_name.setText("- " + room_name + " -");
        }
        else if (roomNum == 3) // 패밀리
        {
            room_name = "Family Room";
            room_price = 250000;
            text_room_name.setText("- " + room_name + " -");
        }
        else  // 럭셔리
        {
            room_name = "Luxury Room";
            room_price = 300000;
            text_room_name.setText("- " + room_name + " -");
        }


        // 호텔번호를 체크해서 호텔이름을 부여한다
        if (hotel_number == 1) // 서울
        {
            hotel_name = "호텔 루나 서울";

            text_hotel_name.setText("- " +  hotel_name + " -");
        }
        else if (hotel_number == 2) // 부산
        {
            hotel_name = "호텔 루나 부산";

            text_hotel_name.setText("- " +  hotel_name + " -");
        }
        else if (hotel_number == 3) // 제주
        {
            hotel_name = "호텔 루나 제주";

            text_hotel_name.setText("- " +  hotel_name + " -");
        }
        else  // 속초
        {
            hotel_name = "호텔 루나 속초";

            text_hotel_name.setText("- " +  hotel_name + " -");
        }


        // 결제 방식 선택
        spn_pay_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int number, long l) {
                DecimalFormat formatter = new DecimalFormat("###,###"); //회계표시 실질적으로 적용 시는 문장
                //  String formattedStringPrice = formatter.format(result); //회계표시 적용
                // Money.setText(formattedStringPrice); // 결제금액 변경
                if (number == 1) // 삼성페이
                {
                    payment = "삼성페이";
                    total_price = (room_price * tnrqkr) / 10 * 9;
                    // 회계 표시 를한다
                    String formattedStringPrice = formatter.format(total_price);
                    // 총 가격 텍스트뷰에 담는다.
                    text_total.setText(formattedStringPrice);
                    lay_card.setVisibility(View.INVISIBLE);
                    // 스트링 변수에 총액 담기
                    total_price_string = text_total.getText().toString();


                }
                else if (number == 3) //신용카드 선택
                {

                    total_price = (room_price * tnrqkr);
                    // 회계 표시 를한다
                    String formattedStringPrice = formatter.format(total_price);
                    // 총 가격 텍스트뷰에 담는다.
                    text_total.setText(formattedStringPrice);
                    lay_card.setVisibility(View.VISIBLE);
                    total_price_string = text_total.getText().toString();
                    // 카드선택 이벤트
                    spn_card_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                 else
                {

                    total_price = (room_price * tnrqkr);
                    // 회계 표시 를한다
                    String formattedStringPrice = formatter.format(total_price);
                    // 총 가격 텍스트뷰에 담는다.
                    text_total.setText(formattedStringPrice);
                    lay_card.setVisibility(View.INVISIBLE);
                    total_price_string = text_total.getText().toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //다음으로 클릭 이벤트 1. 예약확인 물어봄. 2. 예약됬다고 뜸 -> 메인으로 이동
    btn_next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Reservation_pay.this);
            builder.setMessage("예약 하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String roomN;
                    String hotelN;
                    String Tnrqkr;

                    String resName = Login_gloval.Login_resName;

                    // int형 자료들 String으로 변환해서 DB에 넣으려구 변환시켰습니다!
                    roomN = Integer.toString(roomNum);
                    hotelN = Integer.toString(hotel_number);
                    Tnrqkr = Integer.toString(tnrqkr);

                    // 범준아 여기에 예약 DB 넣으면된다. 변수는 위에서 봐라 주석달았다.
                    Reserve(resID, resName, hotelN, hotel_name, roomN, room_name, year_s_in, month_s_in, date_s_in, year_s_out, month_s_out, date_s_out, total_price_string, Tnrqkr);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Reservation_pay.this);
                    builder.setMessage("예약되셨습니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
                            startActivity(intent);

                            final GetReserve getReserve = new GetReserve();
                            getReserve.execute("http://35.203.164.40/HL_GetReserve.php");
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            builder.setNegativeButton("취소",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    });



    }


    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    // 로그인 직후 예약 정보를 가져오는 구문입니다!
    // AsyncTask 클래스 여기다가 구현.
    public class GetReserve extends AsyncTask<String, Void, String> {
        String TAG = "Get Reserve Data";

        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용합니다.
            String url = strings[0];

            try {
                // 따옴표 안의 userID= 부분을 통해 DB에서 해당 아이디로 쿼리를 실행합니다.
                String selectData = "resID=" + Login_gloval.login_id;

                // 어플에서 데이터 전송 준비
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(selectData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                // 어플에서 데이터 전송 시작
                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, sb.toString().trim());

                // 받아온 JSON의 공백을 trim()으로 제거합니다.
                return sb.toString().trim();
            } catch(Exception e) {
                Log.d(TAG, "InsertData : Error", e);
                String errorString = e.toString();
                return null;
            }
        }

        @Override
        // doInBackgroundString에서 return한 값을 받습니다.
        protected void onPostExecute(String fromdoInBackgroundString) {
            super.onPostExecute(fromdoInBackgroundString);

            if(fromdoInBackgroundString == null) {
                // doInBackgroundString에서 return받은 값이 null값일 경우 에러로 토스트 출력.
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            else {
                // null이 아니라면, 밑 문장들 실행
                jsonString = fromdoInBackgroundString;
                // ArrayList에 값을 넣기 위해 doParse() 메소드 실행.
                // doParse() 메소드는 밑에 onProgressUpdate 부분에 넣음.
                reserveArrayList = doParse2();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences reserve = getSharedPreferences("reserve", MODE_PRIVATE);
                // SharedPreferences 를 제어하기 위한 Editor 선언
                SharedPreferences.Editor editor = reserve.edit();

                editor.putInt("reserve", reserveArrayList.size());

                editor.clear();

                for(int i = 0; i < reserveArrayList.size(); i++) {
                    editor.putString("resCODE" + i, reserveArrayList.get(i).getResCODE());
                    editor.putString("resID" + i, reserveArrayList.get(i).getResID());
                    editor.putString("resName" + i, reserveArrayList.get(i).getResName());
                    editor.putString("resHotelNum" + i, reserveArrayList.get(i).getHotelNum());
                    editor.putString("resHotelName" + i, reserveArrayList.get(i).getHotelName());
                    editor.putString("resRoomNum" + i, reserveArrayList.get(i).getRoomNum());
                    editor.putString("resRoomName" + i, reserveArrayList.get(i).getRoomName());
                    editor.putString("resIN_year" + i, reserveArrayList.get(i).getIn_year());
                    editor.putString("resIN_month" + i, reserveArrayList.get(i).getIn_month());
                    editor.putString("resIN_date" + i, reserveArrayList.get(i).getIn_date());
                    editor.putString("resOUT_year" + i, reserveArrayList.get(i).getOut_year());
                    editor.putString("resOUT_month" + i, reserveArrayList.get(i).getOut_month());
                    editor.putString("resOUT_date" + i, reserveArrayList.get(i).getOut_date());
                    editor.putString("resTnrqkr" + i, reserveArrayList.get(i).getTnrqkr());
                    editor.putString("resPrice" + i, reserveArrayList.get(i).getPrice());
                }

                editor.commit();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        private ArrayList<ReserveInfo> doParse2() {

            // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
            ArrayList<ReserveInfo> tmpArray2 = new ArrayList<ReserveInfo>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                // 값들을 복사해서 넣는 과정.
                for(int i = 0; i < jsonArray.length(); i++) {
                    ReserveInfo tmpinfo = new ReserveInfo();
                    JSONObject item = jsonArray.getJSONObject(i);

                    tmpinfo.setResCODE(item.getString("resCODE"));
                    tmpinfo.setResID(item.getString("resID"));
                    tmpinfo.setResName(item.getString("resName"));
                    tmpinfo.setHotelNum(item.getString("resHotelNum"));
                    tmpinfo.setHotelName(item.getString("resHotelName"));
                    tmpinfo.setRoomNum(item.getString("resRoomNum"));
                    tmpinfo.setRoomName(item.getString("resRoomName"));
                    tmpinfo.setIn_year(item.getString("resIN_year"));
                    tmpinfo.setIn_month(item.getString("resIN_month"));
                    tmpinfo.setIn_date(item.getString("resIN_date"));
                    tmpinfo.setOut_year(item.getString("resOUT_year"));
                    tmpinfo.setOut_month(item.getString("resOUT_month"));
                    tmpinfo.setOut_date(item.getString("resOUT_date"));
                    tmpinfo.setTnrqkr(item.getString("resTnrqkr"));
                    tmpinfo.setPrice(item.getString("resPrice"));

                    tmpArray2.add(tmpinfo);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
            // JSON을 가공하여 tmpArray에 넣고 반환.
            return tmpArray2;
        }
    }


    // 예약하기를 누르면 입력한 데이터들이 DB에 들어가는 매소드입니다.
    // Reserve 함수 구현
    private void Reserve(String resID, String resName, String resHotelNum, String resHotelName, String resRoomNum, String resRoomName, String resIN_year, String resIN_month, String resIN_date, String resOUT_year, String resOUT_month, String resOUT_date, String resPrice, String resTnrqkr) {

        // InsertData 클래스 선언, AsyncTask를 이용해 백그라운드에서 인터넷 통신으로 값을 넘김
        class ReserveHotel extends AsyncTask<String, Void, String> {
            // pre 형식 실행 시
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            // POST 형식 실행 시
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            // Background에서 해당 코드 실행
            // String link에 서버 주소 값을 저장한 후, 서버 html 폴더 안에 있는 Register.php를 읽어옴
            // Register.php 안에 있는 SQL 쿼리를 통해 값을 저장하게 됨.
            @Override
            protected String doInBackground(String... params) {
                try {
                    // String 값들을 배열에 넣습니다.
                    String resID = (String) params[0];
                    String resName = (String) params[1];
                    String resHotelNum = (String) params[2];
                    String resHotelName = (String) params[3];
                    String resRoomNum = (String) params[4];
                    String resRoomName = (String) params[5];
                    String resIN_year = (String) params[6];
                    String resIN_month = (String) params[7];
                    String resIN_date = (String) params[8];
                    String resOUT_year = (String) params[9];
                    String resOUT_month = (String) params[10];
                    String resOUT_date = (String) params[11];
                    String resPrice = (String) params[12];
                    String resTnrqkr = (String) params[13];

                    // 서버 안에 있는 Register.php의 주소를 link 안에 저장합니다.
                    String link = "http://35.203.164.40/HL_Reserve.php";

                    // data라는 String 형태 변수 안에 배열의 내용들을 쭉 나열하여 넣습니다.
                    String data = URLEncoder.encode("resID", "UTF-8") + "=" + URLEncoder.encode(resID, "UTF-8");
                    data += "&" + URLEncoder.encode("resName", "UTF-8") + "=" + URLEncoder.encode(resName, "UTF-8");
                    data += "&" + URLEncoder.encode("resHotelNum", "UTF-8") + "=" + URLEncoder.encode(resHotelNum, "UTF-8");
                    data += "&" + URLEncoder.encode("resHotelName", "UTF-8") + "=" + URLEncoder.encode(resHotelName, "UTF-8");
                    data += "&" + URLEncoder.encode("resRoomNum", "UTF-8") + "=" + URLEncoder.encode(resRoomNum, "UTF-8");
                    data += "&" + URLEncoder.encode("resRoomName", "UTF-8") + "=" + URLEncoder.encode(resRoomName, "UTF-8");
                    data += "&" + URLEncoder.encode("resIN_year", "UTF-8") + "=" + URLEncoder.encode(resIN_year, "UTF-8");
                    data += "&" + URLEncoder.encode("resIN_month", "UTF-8") + "=" + URLEncoder.encode(resIN_month, "UTF-8");
                    data += "&" + URLEncoder.encode("resIN_date", "UTF-8") + "=" + URLEncoder.encode(resIN_date, "UTF-8");
                    data += "&" + URLEncoder.encode("resOUT_year", "UTF-8") + "=" + URLEncoder.encode(resOUT_year, "UTF-8");
                    data += "&" + URLEncoder.encode("resOUT_month", "UTF-8") + "=" + URLEncoder.encode(resOUT_month, "UTF-8");
                    data += "&" + URLEncoder.encode("resOUT_date", "UTF-8") + "=" + URLEncoder.encode(resOUT_date, "UTF-8");
                    data += "&" + URLEncoder.encode("resPrice", "UTF-8") + "=" + URLEncoder.encode(resPrice, "UTF-8");
                    data += "&" + URLEncoder.encode("resTnrqkr", "UTF-8") + "=" + URLEncoder.encode(resTnrqkr, "UTF-8");

                    System.out.println(data);

                    // URLConnection 으로 서버에 접속하고 데이터들을 전송합니다.
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch(Exception e) {
                    return new String("Exception : " + e.getMessage());
                }
            }
        }

        // 백그라운드에서 실행할 수 있게 InsertData 클래스의 인스턴스를 생성하여 execute로 실행합니다.
        ReserveHotel reserve = new ReserveHotel();
        reserve.execute(resID, resName, resHotelNum, resHotelName, resRoomNum, resRoomName, resIN_year, resIN_month, resIN_date, resOUT_year, resOUT_month, resOUT_date, resPrice, resTnrqkr);
    }
}
