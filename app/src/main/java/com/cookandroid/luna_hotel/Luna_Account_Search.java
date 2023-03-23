package com.cookandroid.luna_hotel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;

public class Luna_Account_Search extends AppCompatActivity {

    Button btn_bottom_login, btn_id_search, btn_back, btn_password_search;
    EditText edit_id_name, edit_id_email, edit_password_email, edit_password_id, edit_password_name;

    // 서버에서 받은 JSON 스트링 문자를 저장하는 변수
    private String jsonString1;
    private String jsonString2;

    // 검색된 ID와 PW를 저장하는 배열 선언
    ArrayList<UserIDSearch> IDArrayList;
    ArrayList<UserPWSearch> PWArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_account_search);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_bottom_login = (Button) findViewById(R.id.btn_bottom_login);
        btn_id_search = (Button) findViewById(R.id.btn_id_search);
        btn_password_search = (Button) findViewById(R.id.btn_password_search);
        edit_id_email = (EditText) findViewById(R.id.edit_id_email);
        edit_id_name = (EditText) findViewById(R.id.edit_id_name);
        edit_password_id = (EditText) findViewById(R.id.edit_password_id);
        edit_password_name = (EditText) findViewById(R.id.edit_password_name);
        edit_password_email = (EditText) findViewById(R.id.edit_password_email);



        // 좌측 상단 뒤로가기버튼 클릭시 창이 꺼짐 + 애니메이션 이벤트
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // 하단버튼 로그인 버튼 이벤트
        btn_bottom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(), Luna_Login.class);
                startActivity(Intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });


        // 아이디 찾기 버튼이며  이름과 이메일 값을 디비로 조회하여 아이디를 찾는다
        // 조회된 값이 없다면 대화상자로   "조회된 아이디가 없습니다." 라고 출력한다
        // 값이 있다면 대화상자로 "조회된 아이디는 **** 입니다." 라고 출력한다.
        btn_id_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 직후 DB에 저장되어 있는 회원의 데이터를 가져오기 위한 부분입니다.
                String IDsearch_Name = edit_id_name.getText().toString();
                String IDsearch_Email = edit_id_email.getText().toString();

                // Find_ID 메소드로 받은 Name과 Email을 통해 DB 검색.
                Find_ID(IDsearch_Name, IDsearch_Email);
            }
        });

        // 비밀번호 찾기 버튼이며  이름과 이메일 아이디 값을 디비로 조회하여 아이디를 찾는다
        // 조회된 값이 없다면 대화상자로   "조회된 값이 없습니다." 라고 출력한다
        // 값이 있다면 대화상자로 "조회된 비밀번호는 **** 입니다." 라고 출력한다.
        btn_password_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PWsearch_ID = edit_password_id.getText().toString();
                String PWsearch_Name = edit_password_name.getText().toString();
                String PWsearch_Email = edit_password_email.getText().toString();

                // Find_PW 메소드로 받은 ID, Name, Email을 통해 DB 검색.
                Find_PW(PWsearch_ID, PWsearch_Name, PWsearch_Email);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    // Find_ID 메소드는 계정찾기 화면에서 아이디를 찾을 때 사용하는 메소드입니다.
    // Find_ID 메소드를 통해 서버에 userName과 userEmail을 보냅니다.
    // 서버에서는 받은 userName과 userEmail로 PHP에 적혀있는 select userID from user where userName = "$userName" and userEmail = "$userEmail" 구문을 통해서
    // 조건에 해당되는 userID 값을 가져오게 됩니다.
    private void Find_ID(String userName, String userEmail) {

        // AsyncTask 클래스 여기다가 구현.
        class IDSearch extends AsyncTask<String, Void, String> {
            String TAG = "IDSearch";

            @Override
            protected String doInBackground(String... strings) {
                // execute의 매개변수를 받아와서 사용합니다.
                try {
                    String userName = (String) strings[0];
                    String userEmail = (String) strings[1];

                    String url = "http://35.203.164.40/HL_IDSearch.php";

                    String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                    data += "&" + URLEncoder.encode("userEmail", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8");

                    // 해당 url로 접속 시도
                    URL serverURL = new URL(url);
                    URLConnection conn = serverURL.openConnection();

                    conn.setDoOutput(true);

                    OutputStreamWriter outputStream = new OutputStreamWriter(conn.getOutputStream());
                    outputStream.write(data);
                    outputStream.flush();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    // 받아온 JSON의 공백을 trim()으로 제거합니다.
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d(TAG, "Error", e);
                    String errorString = e.toString();
                    return null;
                }
            }

            @Override
            // doInBackgroundString에서 return한 값을 받습니다.
            protected void onPostExecute(String fromdoInBackgroundString) {
                super.onPostExecute(fromdoInBackgroundString);

                if (fromdoInBackgroundString == null) {
                    // doInBackgroundString에서 return받은 값이 null값일 경우 에러로 토스트 출력.
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    // null이 아니라면, 밑 문장들 실행
                    jsonString1 = fromdoInBackgroundString;
                    // ArrayList에 값을 넣기 위해 doParse() 메소드 실행.
                    // doParse() 메소드는 밑에 onProgressUpdate 부분에 넣음.
                    IDArrayList = doParse1();

                    String Find_ID = IDArrayList.get(0).getID();

                    // 조회된 아이디가 없을 경우, 즉 찾은 아이디가 null값일 경우
                    if(Find_ID.equals("null")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Account_Search.this);
                        builder.setMessage("조회된 아이디가 없습니다.");
                        builder.setPositiveButton("확인", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    // null값이 아니라면 찾은 아이디를 보여줍니다.
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Account_Search.this);
                        builder.setMessage("조회된 아이디는 " + Find_ID + " 입니다.");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
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

            private ArrayList<UserIDSearch> doParse1() {
                // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
                ArrayList<UserIDSearch> tmpArray1 = new ArrayList<UserIDSearch>();

                try {
                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                    // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("result");

                    // 값들을 복사해서 넣는 과정.
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        UserIDSearch tmpID = new UserIDSearch();
                        JSONObject item1 = jsonArray1.getJSONObject(i);

                        tmpID.setID(item1.getString("userID"));

                        tmpArray1.add(tmpID);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // JSON을 가공하여 tmpArray에 넣고 반환.
                return tmpArray1;
            }
        }

        IDSearch task = new IDSearch();
        task.execute(userName, userEmail);
    }


    // Find_PW 메소드는 계정찾기 화면에서 비밀번호를 찾을 때 사용하는 메소드입니다.
    // Find_PW 메소드를 통해 서버에 userID와 userName, 그리고 userEmail 총 3개를 보냅니다.
    // 서버에서는 받은 userID와 userName과 userEmail로 PHP에 적혀있는 select userPW from user where userID = "$userID" and userName = "$userName" and userEmail = "$userEmail" 구문을 통해서
    // 조건에 해당되는 userPW 값을 가져오게 됩니다.
    private void Find_PW(String userID, String userName, String userEmail) {

        // AsyncTask 클래스 여기다가 구현.
        class PWSearch extends AsyncTask<String, Void, String> {
            String TAG = "PWSearch";

            @Override
            protected String doInBackground(String... strings) {
                // execute의 매개변수를 받아와서 사용합니다.
                try {
                    String userID = (String) strings[0];
                    String userName = (String) strings[1];
                    String userEmail = (String) strings[2];

                    String url = "http://35.203.164.40/HL_PWSearch.php";

                    String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");
                    data += "&" + URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                    data += "&" + URLEncoder.encode("userEmail", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8");

                    // 해당 url로 접속 시도
                    URL serverURL = new URL(url);
                    URLConnection conn = serverURL.openConnection();

                    conn.setDoOutput(true);

                    OutputStreamWriter outputStream = new OutputStreamWriter(conn.getOutputStream());
                    outputStream.write(data);
                    outputStream.flush();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    // 받아온 JSON의 공백을 trim()으로 제거합니다.
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d(TAG, "Error", e);
                    String errorString = e.toString();
                    return null;
                }
            }

            @Override
            // doInBackgroundString에서 return한 값을 받습니다.
            protected void onPostExecute(String fromdoInBackgroundString) {
                super.onPostExecute(fromdoInBackgroundString);

                if (fromdoInBackgroundString == null) {
                    // doInBackgroundString에서 return받은 값이 null값일 경우 에러로 토스트 출력.
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    // null이 아니라면, 밑 문장들 실행
                    jsonString2 = fromdoInBackgroundString;
                    // ArrayList에 값을 넣기 위해 doParse() 메소드 실행.
                    // doParse() 메소드는 밑에 onProgressUpdate 부분에 넣음.
                    PWArrayList = doParse2();

                    String Find_PW = PWArrayList.get(0).getPW();

                    // 조회된 값이 없을 경우, 즉 찾은 비밀번호가 null값일 경우
                    if(Find_PW.equals("null")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Account_Search.this);
                        builder.setMessage("조회된 값이 없습니다.");
                        builder.setPositiveButton("확인", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    // null값이 아니라면 찾은 비밀번호를 보여줍니다.
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Account_Search.this);
                        builder.setMessage("조회된 비밀번호는 " + Find_PW + " 입니다.");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
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

            private ArrayList<UserPWSearch> doParse2() {
                // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
                ArrayList<UserPWSearch> tmpArray2 = new ArrayList<UserPWSearch>();

                try {
                    JSONObject jsonObject2 = new JSONObject(jsonString2);
                    // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                    JSONArray jsonArray2 = jsonObject2.getJSONArray("result");

                    // 값들을 복사해서 넣는 과정.
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        UserPWSearch tmpPW = new UserPWSearch();
                        JSONObject item2 = jsonArray2.getJSONObject(i);

                        tmpPW.setPW(item2.getString("userPW"));

                        tmpArray2.add(tmpPW);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // JSON을 가공하여 tmpArray에 넣고 반환.
                return tmpArray2;
            }
        }

        PWSearch task2 = new PWSearch();
        task2.execute(userID, userName, userEmail);
    }
}
