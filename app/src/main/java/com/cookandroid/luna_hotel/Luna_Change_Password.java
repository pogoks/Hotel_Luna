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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class Luna_Change_Password extends AppCompatActivity {

    EditText  edit_password, edit_passwordcheck, edit_password_now;
    Button  btn_back,btn_next;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_change_password);

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_back = (Button) findViewById(R.id.btn_back);

        edit_password_now = (EditText) findViewById(R.id.edit_password_now);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_passwordcheck = (EditText) findViewById(R.id.edit_passwordcheck);

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

        edit_password_now.setFilters(new InputFilter[]{filter});
        edit_password_now.setFilters(filters);
        edit_password.setFilters(new InputFilter[]{filter});
        edit_password.setFilters(filters);
        edit_passwordcheck.setFilters(new InputFilter[]{filter});
        edit_passwordcheck.setFilters(filters);



        // 좌측 상단 뒤로가기버튼 클릭시 창이 꺼짐 + 애니메이션 이벤트
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // 변경하기 이벤트
        // 비밀번호랑 비밀번호확인 값 같은지 체크 등등 조건문 작성해서 넘기기
        // 대화상자로 변경묻는거 필요

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String CheckID = Login_gloval.login_id;
                final String CheckPW = edit_password_now.getText().toString();
                final String UpdatePW = edit_password.getText().toString();
                final String UpdatePW_Check = edit_passwordcheck.getText().toString();

                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                final SharedPreferences.Editor editor = info.edit();

                if(!CheckPW.equals(Login_gloval.login_password)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Change_Password.this);
                    dialog = builder.setMessage("현재 비밀번호를 확인해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    edit_password_now.requestFocus();
                    return;
                }

                if(!UpdatePW_Check.equals(UpdatePW)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Change_Password.this);
                    dialog = builder.setMessage("변경되는 비밀번호가 서로 일치하지 않습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    edit_passwordcheck.requestFocus();
                    return;
                }

                // 변경 전 확인 대화상자
                AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Change_Password.this);
                builder.setMessage("비밀번호를 변경하시겠습니까?");

                // 변경 버튼을 누를 경우 회원 정보 변경 진행
                builder.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        UpdatePW(UpdatePW, CheckID);

                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Change_Password.this);
                        builder.setMessage("성공적으로 변경되었습니다. 다시 로그인 해주세요.");

                        // 확인 버튼을 누르면 메인화면으로 이동합니다.
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();

                                Login_gloval.login_id =null;
                                Login_gloval.login_password=null;

                                editor.clear();
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), Luna_Login.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();
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
    }


    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    // UpdateData 메소드는 회원 변경 창에서 받은 데이터 값을 서버로 넘기는 메소드 입니다.
    // UpdateData 메소드를 통해 회원의 이름, 전화번호, 이메일 변경 값을 데이터베이스에 전송합니다.
    private void UpdatePW(String userPW, final String userID) {

        // AsyncTask 클래스 여기다가 구현.
        class Updatepw extends AsyncTask<String, Void, String> {
            String TAG = "Update Password";

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
                    String userPW = (String) strings[0];
                    String userID = (String) strings[1];

                    String url = "http://35.203.164.40/HL_UpdatePW.php";

                    String data = URLEncoder.encode("userPW", "UTF-8") + "=" + URLEncoder.encode(userPW, "UTF-8");
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
        Updatepw updatepw = new Updatepw();
        updatepw.execute(userPW, userID);
    }
}
