package com.cookandroid.luna_hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Luna_Login extends AppCompatActivity {

    Button btn_Login, btn_account_find, btn_Sign_up, btn_menu, btn_lunalogo;
    EditText user_id, user_pw;
    TextView text_guest_login;
    CheckBox check_auto_login;

    private String jsonString;
    ArrayList<UserInfo> infoArrayList;

    private String jsonString2;
    ArrayList<ReserveInfo> reserveArrayList;

    private String jsonString3;
    ArrayList<NewsInfo> newsArrayList;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_login);

        //자동로그인을 위한 SharedPreferences 선언
        final SharedPreferences logininfo = getSharedPreferences("user",0); // user 라는 파일을 생성합니다.
        final SharedPreferences.Editor editor = logininfo.edit(); // 에디터 연결합니다.


        // 자동로그인 SharedPreferences 에 이미 값이 있으면 ?
        if(logininfo.contains("id")&& logininfo.contains("pw"))
        {
            // 로그인 전역변수에 아이디랑 비밀번호 값을 넣는다
            Login_gloval.login_id = logininfo.getString("id",null);
            Login_gloval.login_password = logininfo.getString("pw",null);
            // 메인화면으로 바로 이동한다.
            Intent Intent = new Intent(getApplicationContext(),Luna_Main.class);
            startActivity(Intent);
            //액티비티 전환 애니메이션 설정하는 부분
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }

        btn_Login = (Button) findViewById(R.id.btn_login);
        btn_account_find = (Button) findViewById(R.id.btn_account_find);
        btn_Sign_up = (Button) findViewById(R.id.btn_Sign_Up);
        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_menu = (Button)findViewById(R.id.btn_menu);
        text_guest_login = (TextView) findViewById(R.id.text_guest_login);
        user_id = (EditText) findViewById(R.id.Id_info);
        user_pw = (EditText) findViewById(R.id.Password_info);
        check_auto_login = (CheckBox)findViewById(R.id.check_auto_login);

        //하얀색 밑줄
        btn_account_find.setText(Html.fromHtml("<font color=#f0f0f0><u>" + "계정찾기" + "</u></font>"));
        btn_Sign_up.setText(Html.fromHtml("<font color=#f0f0f0><u>" + "회원가입" + "</u></font>"));
        text_guest_login.setText(Html.fromHtml("<font color=#f0f0f0><u>" + "GUEST 로 이용하기" + "</u></font>"));

        //영문과 숫쟈 허용
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                if (!ps.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };

        //글자수 16개로 설정
        InputFilter[] filters = new InputFilter[]{
                new InputFilter.LengthFilter(16), filter

        };

        //아이디 영문과 숫자만 허용, 글자수 제한설정
        user_id.setFilters(new InputFilter[]{filter});
        user_pw.setFilters(filters);

        //패스워드 영문과 숫자만 허용, 글자수 제한설정
        user_pw.setFilters(new InputFilter[]{filter});
        user_pw.setFilters(filters);


        // 메뉴 클릭 이벤트
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_menu.class);
                startActivity(Intent);
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });


        // 로고클릭 이벤트
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

       //로그인 하기 위한 버튼
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = user_id.getText().toString();
                final String userPW = user_pw.getText().toString();

                // 서버의 PHP로 아이디와 비밀번호를 검색하는 과정
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                // 자동로그인 체크되어있으면 실행되는 코드
                                if(check_auto_login.isChecked())
                                {
                                    // SharedPreferences 에 값 저장하기
                                    editor.putString("id",userID); // 유저 파일에 로그인한 id 저장
                                    editor.putString("pw",userPW); // 유저 파일에 로그인한 id 저장
                                    editor.commit(); // 저장하기
                                }

                                final String userID = jsonResponse.getString("userID");
                                final String userPW = jsonResponse.getString("userPW");

                                // 유저 정보가 저장되어 있는 info 저장소를 불러옵니다.
                                final SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);

                                // 관리자 계정과 일반 계정을 구분하기 위한 if문
                                // id가 master일 경우 관리 화면으로 이동
                                if(userID.equals("master")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Login.this);
                                    builder.setMessage("관리자 계정입니다. 로그인 하시겠습니까?");
                                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast accountToast = Toast.makeText(Luna_Login.this,"로그인 되었습니다.",Toast.LENGTH_SHORT);
                                            accountToast.show();

                                            Intent loginintent = new Intent(Luna_Login.this, Luna_Main.class);

                                            Login_gloval.login_id = userID;
                                            Login_gloval.login_password = userPW;
                                            Login_gloval.Login_resName = info.getString("userName", "");

                                            // 여기까지.
                                            Luna_Login.this.startActivity(loginintent);
                                        }
                                    });
                                    builder.setNegativeButton("아니요",null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }

                                // id가 master가 아닌 다른 id일 경우 (기존 로그인 방법)
                                else {
                                    Toast.makeText(getApplicationContext(), "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                                    Intent loginintent = new Intent(Luna_Login.this, Luna_Main.class);
                                    //  전역변수에 로그인에 성공한 아이디와 비밀번호 값을 보낸다.
                                    Login_gloval.login_id = userID;
                                    Login_gloval.login_password = userPW;

                                    // 전역변수에 가져온 유저 정보 중 이름 값을 resName 전역변수에 넣습니다.
                                    // 이 저장된 이름은 예약할 때 예약자 명이 들어가는 부분에서 사용됩니다.
                                    Login_gloval.Login_resName = info.getString("userName", "");

                                    // 여기까지.
                                    Luna_Login.this.startActivity(loginintent);
                                }
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Login.this);
                                dialog = builder.setMessage("계정이 존재하지 않거나 비밀번호가 일치하지 않습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // Volley 라이브러리를 통해 서버와 통신합니다. 값들을 큐에 넣어 전송합니다.
                LoginRequest loginRequest = new LoginRequest(userID, userPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Luna_Login.this);
                queue.add(loginRequest);


                // 로그인 직후 DB에 저장되어 있는 회원의 데이터를 가져오기 위한 부분입니다.
                final JsonParse jsonParse = new JsonParse();            // AsyncTask 생성
                jsonParse.execute("http://35.203.164.40/HL_GetInfo.php");   // AsyncTask 실행

                // 로그인 직후 DB에 저장되어 있는 회원의 예약정보를 가져오기 위한 부분입니다.
                final GetReserve getReserve = new GetReserve();
                getReserve.execute("http://35.203.164.40/HL_GetReserve.php");

                // 로그인 직후 DB에 저장되어 있는 공지사항 목록을 가져옵니다.
                final GetNews getNews = new GetNews();
                getNews.execute("http://35.203.164.40/HL_GetNews.php");
            }
        });


        //계정찾기 하기 위한 버튼
        btn_account_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(), Luna_Account_Search.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }

        });


        //회원가입 하기 위한 버튼
        btn_Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Setting_Intent = new Intent(getApplicationContext(),Luna_NewAccount.class);
                startActivity(Setting_Intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }

        });


        //게스트로 로그인하기 위한 버튼
        text_guest_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_gloval.login_id = null;
                Login_gloval.login_password = null;

                // 게스트로 로그인해도 공지사항은 받아와야 하기 때문에 공지사항을 불러오는 구문 실행.
                final GetNews getNews = new GetNews();
                getNews.execute("http://35.203.164.40/HL_GetNews.php");

                Intent Main_intent = new Intent(getApplicationContext(),Luna_Main.class);
                startActivity(Main_intent);
            }

        });
    }

    private long backKeyPressedTime = 0;
    private Toast toast;


    @Override
    public void onBackPressed(){
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

        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }


    // 이 밑은 로그인 할 때 로그인 정보로 회원 정보를 가져오는 구문입니다.

    // 로그인 직후 회원 정보를 DB에서 가져오는 부분입니다!
    // AsyncTask 클래스 여기다가 구현.
    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG = "JsonParse";

        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용합니다.
            String url = strings[0];

            try {
                // 따옴표 안의 userID= 부분을 통해 DB에서 해당 아이디로 쿼리를 실행합니다.
                String selectData = "userID=" + user_id.getText().toString();

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
                infoArrayList = doParse();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                // SharedPreferences 를 제어하기 위한 Editor 선언
                SharedPreferences.Editor editor = info.edit();

                editor.clear();
                // Editor를 이용해서 userCODE, userID, userName... 등등의 이름으로 회원 정보 저장
                editor.putString("userCODE", infoArrayList.get(0).getCODE());
                editor.putString("userID", infoArrayList.get(0).getID());
                editor.putString("userName", infoArrayList.get(0).getName());
                editor.putString("userPW", infoArrayList.get(0).getPW());
                editor.putString("userSsn", infoArrayList.get(0).getSsn());
                editor.putString("userGender", infoArrayList.get(0).getGender());
                editor.putString("userHP", infoArrayList.get(0).getHP());
                editor.putString("userEmail", infoArrayList.get(0).getEmail());

                // Editor로 작업한 내용들을 커밋.
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

        private ArrayList<UserInfo> doParse() {
            // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
            ArrayList<UserInfo> tmpArray = new ArrayList<UserInfo>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                // 값들을 복사해서 넣는 과정.
                for(int i = 0; i < jsonArray.length(); i++) {
                    UserInfo tmpinfo = new UserInfo();
                    JSONObject item = jsonArray.getJSONObject(i);

                    tmpinfo.setCODE(item.getString("userCODE"));
                    tmpinfo.setID(item.getString("userID"));
                    tmpinfo.setName(item.getString("userName"));
                    tmpinfo.setPW(item.getString("userPW"));
                    tmpinfo.setSsn(item.getString("userSsn"));
                    tmpinfo.setGender(item.getString("userGender"));
                    tmpinfo.setHP(item.getString("userHP"));
                    tmpinfo.setEmail(item.getString("userEmail"));

                    tmpArray.add(tmpinfo);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
            // JSON을 가공하여 tmpArray에 넣고 반환.
            return tmpArray;
        }
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
                // 따옴표 안의 resID= 부분을 통해 DB에서 해당 아이디로 쿼리를 실행합니다.
                String selectData = "resID=" + user_id.getText().toString();

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
                jsonString2 = fromdoInBackgroundString;
                // ArrayList에 값을 넣기 위해 doParse() 메소드 실행.
                // doParse() 메소드는 밑에 onProgressUpdate 부분에 넣음.
                reserveArrayList = doParse2();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences reserve = getSharedPreferences("reserve", MODE_PRIVATE);
                // SharedPreferences 를 제어하기 위한 Editor 선언
                SharedPreferences.Editor editor = reserve.edit();

                // DB에서 예약정보를 가져오면서 reserveArrayList의 사이즈를 "reserve"라는 int형 SharedPrefereces로 저장합니다.
                // 쉽게 말해서 배열 크기를 다른 클래스에서도 쓸 수 있게 전역변수처럼 선언한겁니당
                editor.putInt("reserve", reserveArrayList.size());

                // SharedPreferences에 값을 넣기 전에 한 번 초기화 시켜줍니다.
                editor.clear();

                // for문을 돌려서 로그인 한 아이디에 해당되는 예약정보들을 모두 불러옵니다.
                // 한 아이디가 여러개의 예약정보를 가지고 있다면, 배열이 여러개 필요하므로
                // for 문을 통해 DB에 저장된 배열 갯수만큼 SharedPreferences에 넣습니다.
                for(int i = 0; i < reserveArrayList.size(); i++) {
                    editor.putString("resCODE" + i, reserveArrayList.get(i).getResCODE());
                    editor.putString("resName" + i, reserveArrayList.get(i).getResName());
                    editor.putString("resID" + i, reserveArrayList.get(i).getResID());
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

                // SharedPreferencs 저장.
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
                JSONObject jsonObject = new JSONObject(jsonString2);
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


    // 서버에서 공지사항 목록을 불러오기 위한 클래스입니다.
    public class GetNews extends AsyncTask<String, Void, String> {
        String TAG = "Get News";

        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용합니다.
            String url = strings[0];

            try {
                // 어플에서 데이터 전송 준비
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


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
                jsonString3 = fromdoInBackgroundString;
                // ArrayList에 값을 넣기 위해 doParse() 메소드 실행.
                // doParse() 메소드는 밑에 onProgressUpdate 부분에 넣음.
                newsArrayList = doParse3();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences news = getSharedPreferences("news", MODE_PRIVATE);
                // SharedPreferences 를 제어하기 위한 Editor 선언
                SharedPreferences.Editor editor = news.edit();

                // newsArrayList에 들어간 배열의 크기를 news변수에 넣습니다.
                // 이 news는 리스트뷰에서 배열의 크기를 불러올 때 사용됩니다.
                editor.putInt("news", newsArrayList.size());

                editor.clear();

                // for 문을 통해 DB에 저장된 배열 갯수만큼 SharedPreferences에 넣습니다.
                for(int i = 0; i < newsArrayList.size(); i++) {
                    editor.putString("newsCODE" + i, newsArrayList.get(i).getNewsCODE());
                    editor.putString("newsTitle" + i, newsArrayList.get(i).getNewsTitle());
                    editor.putString("newsMain" + i, newsArrayList.get(i).getNewsMain());
                    editor.putString("newsWriter" + i, newsArrayList.get(i).getNewsWriter());
                    editor.putString("newsDate" + i, newsArrayList.get(i).getNewsDate());
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

        private ArrayList<NewsInfo> doParse3() {

            // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
            ArrayList<NewsInfo> tmpArray3 = new ArrayList<NewsInfo>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString3);
                // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                // 값들을 복사해서 넣는 과정.
                for(int i = 0; i < jsonArray.length(); i++) {
                    NewsInfo tmpinfo = new NewsInfo();
                    JSONObject item = jsonArray.getJSONObject(i);

                    tmpinfo.setNewsCODE(item.getString("newsCODE"));
                    tmpinfo.setNewsTitle(item.getString("newsTitle"));
                    tmpinfo.setNewsMain(item.getString("newsMain"));
                    tmpinfo.setNewsWriter(item.getString("newsWriter"));
                    tmpinfo.setNewsDate(item.getString("newsDate"));

                    tmpArray3.add(tmpinfo);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
            // JSON을 가공하여 tmpArray에 넣고 반환.
            return tmpArray3;
        }
    }
}
