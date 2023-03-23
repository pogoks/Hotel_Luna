package com.cookandroid.luna_hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Pattern;

    public class Luna_Edit_Profile extends AppCompatActivity {

        Button btn_next,btn_lunalogo,btn_back;
        EditText edit_name,edit_hp,edit_email;

        AlertDialog dialog;

        private String jsonString;
        ArrayList<ChangeInfo> changeArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_edit_profile);

        // DB에 저장된 회원의 값을 가져오기 위한 SharedPreferences 선언
        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
        final SharedPreferences.Editor editor = info.edit();

        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_next= (Button)findViewById(R.id.btn_next);

        edit_hp = (EditText) findViewById(R.id.edit_hp);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_name = (EditText) findViewById(R.id.edit_name);

        //필터 선언
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return null;
            }
        };

        //글자수 30개로 설정
        InputFilter[] filters = new InputFilter[]{
                new InputFilter.LengthFilter(30), filter

        };

        //글자수 13개로 설정
        InputFilter[] filters2 = new InputFilter[]{
                new InputFilter.LengthFilter(13), filter

        };

        // 각 EditText에 글자 수 제한 및 허용되는 문자 설정
        // 이름 적는 칸 빼곤 한글 안되게, 즉 전부 영어랑 숫자만 가능

        edit_hp.setFilters(new InputFilter[]{filter});
        edit_hp.setFilters(filters2);

        edit_email.setFilters(new InputFilter[]{filter});
        edit_email.setFilters(filters);


        // 여기까지 기존 코드 가져온건데 나머지는 내가 잘몰라서 안넣었음
        // 여기에 해야하는 사항들 적어둠
        // 1. 변경전의 기존 유저의 정보가 edittext 에 들어가 있어야함
        // 내 핸드폰번호가 010 3826 6515 라면
        // 회원정보변경 페이지에 들어왔을때 이미 핸드폰번호 에디트 텍스트에 내 번호가 기입되야함
        // 그러고 나서 자유롭게 수정가능하게 한다.
        // 2. 핸드폰 번호 길이 이메일 형식이 맞는지 확인하는 코드 넣어줄것.

        edit_name.setText(info.getString("userName", ""));
        edit_hp.setText(info.getString("userHP", ""));
        edit_email.setText(info.getString("userEmail", ""));


        //  변경하기 버튼 이벤트
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //여기에 변경되는 회원정보드 코드들어가야함
                // 추가로 핸드폰 번호 길이 랑 이메일 형식이 맞는 조건문 넣어야함
                // 대화상자로 변경할거냐는 물음이 표시되어야함
                // 성공적으로 적용되면 적용됬다고 대화상자 나오고 다시 내정보로 이동되어야함

                final String UpdateName = edit_name.getText().toString();
                final String UpdateHP = edit_hp.getText().toString();
                final String UpdateEmail = edit_email.getText().toString();
                final String CheckID = Login_gloval.login_id;

                // 여기서 SharedPreferences를 선언해 준 이유는 탈퇴 할 때 단말기에 저장 되어있는 회원 정보를 삭제하는 작업을 하기 위해서 선언했습니다.
                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                final SharedPreferences.Editor editor = info.edit();

                // 변경할 전화번호의 형식이 000-0000-0000 형식이 아닐 경우에 밑 대화상자 출력.
                if(!UpdateHP.matches("...(-)....(-)....")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Edit_Profile.this);
                    dialog = builder.setMessage("전화번호 형식에 맞게 작성해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    edit_hp.requestFocus();
                    return;
                }

                // 변경할 이메일의 형식이 aaaaaa@aaaa.aaa 형식이 아닐 경우에 밑 대화상자 출력.
                if(!UpdateEmail.matches(".+(@).+.[.].+")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Edit_Profile.this);
                    dialog = builder.setMessage("이메일 형식에 맞게 작성해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    edit_email.requestFocus();
                    return;
                }


                // 변경 전 확인 대화상자
                AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Edit_Profile.this);
                builder.setMessage("회원정보를 변경하시겠습니까?");

                // 변경 버튼을 누를 경우 회원 정보 변경 진행
                builder.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // UpdateData 메소드를 실행해서 현재 입력된 수정 값들을 서버로 보내서 덮어쓰기 합니다.
                        // UpdateData 메소드는 밑에 구현해 두었습니다.
                        UpdateData(UpdateName, UpdateHP, UpdateEmail, CheckID);

                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Edit_Profile.this);
                        builder.setMessage("성공적으로 변경되었습니다.");

                        // 확인 버튼을 누르면 메인화면으로 이동합니다.
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), Luna_Myinfo.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();

                        // GetData 클래스를 통해 변경한 값들을 서버에서 가져오고 이 값들을 메인화면 및 어플에 적용시킵니다.
                        // GetData 클래스는 밑에 구현해놨어용
                        final GetData getdata = new GetData();
                        getdata.execute("http://35.203.164.40/HL_GetChangeData.php");
                    }
                });
                // 취소 눌렀을 경우
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        // 로고클릭
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
                startActivity(intent);
            }
        });


        // 뒤로가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });


    }


    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    // UpdateData 메소드는 회원 변경 창에서 받은 데이터 값을 서버로 넘기는 메소드 입니다.
    // UpdateData 메소드를 통해 회원의 이름, 전화번호, 이메일 변경 값을 데이터베이스에 전송합니다.
    private void UpdateData(String userName, String userHP, String userEmail, final String userID) {

        // AsyncTask 클래스 여기다가 구현.
        class Update extends AsyncTask<String, Void, String> {
            String TAG = "Update";

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

            @Override
            protected String doInBackground(String... strings) {
                // execute의 매개변수를 받아와서 사용합니다.
                try {
                    String userName = (String) strings[0];
                    String userHP = (String) strings[1];
                    String userEmail = (String) strings[2];
                    String userID = (String) strings[3];

                    String url = "http://35.203.164.40/HL_UpdateData.php";

                    String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                    data += "&" + URLEncoder.encode("userHP", "UTF-8") + "=" + URLEncoder.encode(userHP, "UTF-8");
                    data += "&" + URLEncoder.encode("userEmail", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8");
                    data += "&" + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");

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
                    return sb.toString();
                } catch (Exception e) {
                    Log.d(TAG, "Error", e);
                    String errorString = e.toString();
                    return null;
                }
            }
        }

        // 백그라운드에서 실행할 수 있게 Update 클래스의 인스턴스를 생성하여 execute로 실행합니다.
        Update update = new Update();
        update.execute(userName, userHP, userEmail, userID);
    }


    // GetData 클래스는 정보 변경 직후에 변경된 값 들을 서버에서 가져오는 클래스입니다.
    // GetData 클래스로 변경한 값들을 다시 가져와서 메인 화면 및 어플에 적용시킵니다.
    public class GetData extends AsyncTask<String, Void, String> {
        String TAG = "Get Change Data";

        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용합니다.
            String url = strings[0];

            try {
                // 따옴표 안의 userID= 부분을 통해 DB에서 해당 아이디로 쿼리를 실행합니다.
                String selectData = "userID=" + Login_gloval.login_id;

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
                changeArrayList = doParse();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                // SharedPreferences 를 제어하기 위한 Editor 선언
                SharedPreferences.Editor editor = info.edit();

                // Editor를 이용해서 변경된 Name, 전화번호, 이메일을 저장
                editor.putString("userName", changeArrayList.get(0).getName());
                editor.putString("userHP", changeArrayList.get(0).getHP());
                editor.putString("userEmail", changeArrayList.get(0).getEmail());

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

        private ArrayList<ChangeInfo> doParse() {
            // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
            ArrayList<ChangeInfo> tmpArray = new ArrayList<ChangeInfo>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                // 값들을 복사해서 넣는 과정.
                for(int i = 0; i < jsonArray.length(); i++) {
                    ChangeInfo tmpinfo = new ChangeInfo();
                    JSONObject item = jsonArray.getJSONObject(i);

                    tmpinfo.setName(item.getString("userName"));
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
}
