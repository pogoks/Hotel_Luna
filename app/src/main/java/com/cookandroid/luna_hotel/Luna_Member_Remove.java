package com.cookandroid.luna_hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Luna_Member_Remove extends AppCompatActivity {

    Button btn_back, btn_lunalogo, btn_remove;
    EditText edit_MR_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_member_remove);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_remove = (Button)findViewById(R.id.btn_remove);

        edit_MR_password = (EditText) findViewById(R.id.edit_MR_password);

        // 계정 탈퇴할 때 현재 로그인 된 전역변수 ID값을 가져옵니다.
        final String RemoveID = Login_gloval.login_id;


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


        // 조건문을 통하여 edit_MR_password 값이랑 디비의 회원비빌번호 값이랑 값이 일치하면
        // 해당 회원 칼럼을 삭제하는 코드가 들어가야함
        // 값이 다르면 토스트 메시지로 비밀번호가 일치하지 않는다는 문구가 떠야함
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String CheckPW = edit_MR_password.getText().toString();

                // 여기서 SharedPreferences를 선언해 준 이유는 탈퇴 할 때 단말기에 저장 되어있는 회원 정보를 삭제하는 작업을 하기 위해서 선언했습니다.
                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                final SharedPreferences.Editor editor = info.edit();

                SharedPreferences reserve = getSharedPreferences("reserve", MODE_PRIVATE);
                final SharedPreferences.Editor editor2 = reserve.edit();

                // 회원탈퇴 창에서 로그인 된 계정의 비밀번호와 일치하면 밑 구문 실행
                if(CheckPW.equals(Login_gloval.login_password)) {

                    // 탈퇴 재 확인 대화상자 출력
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Member_Remove.this);
                    builder.setMessage("정말 탈퇴하시겠습니까? 모든 정보가 삭제됩니다.");

                    // 탈퇴 버튼을 누를 경우 탈퇴 진행
                    builder.setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // 서버와 통신하는 부분
                            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse2 = new JSONObject(response);
                                        boolean success = jsonResponse2.getBoolean("success");

                                        // 서버와 연결이 제대로 되지 않았을 경우
                                        if(success) {
                                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Luna_Member_Remove.this);
                                            builder2.setMessage("탈퇴 과정에서 오류가 발생했습니다.");
                                            builder2.setPositiveButton("확인", null);
                                            AlertDialog dialog1 = builder2.create();
                                            dialog1.show();
                                        }

                                        // 서버와 연결이 되어 쿼리가 실행 될 경우
                                        else {
                                            Toast.makeText(getApplicationContext(), "성공적으로 탈퇴되었습니다.", Toast.LENGTH_SHORT).show();

                                            Login_gloval.login_id = null;
                                            Login_gloval.login_password = null;

                                            Login_gloval.Login_resName = null;

                                            editor.clear();
                                            editor.commit();

                                            editor2.clear();
                                            editor2.clear();

                                            Intent goLogin = new Intent(getApplicationContext(), Luna_Login.class);
                                            startActivity(goLogin);
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            ValidateRequest_RemoveUser_DeleteID validateRequest_deleteID = new ValidateRequest_RemoveUser_DeleteID(RemoveID, responseListener2);
                            RequestQueue queue = Volley.newRequestQueue(Luna_Member_Remove.this);
                            queue.add(validateRequest_deleteID);
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

                // 비밀번호가 일치하지 않거나, 빈 칸일 경우
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_Member_Remove.this);
                    builder.setMessage("현재 비밀번호와 일치하지 않습니다.");
                    builder.setPositiveButton("확인", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
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
