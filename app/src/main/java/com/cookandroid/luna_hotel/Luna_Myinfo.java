package com.cookandroid.luna_hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Luna_Myinfo extends AppCompatActivity {

    ImageView image_gender;
    TextView text_uesr_name,text_uesr_id,text_uesr_email,text_uesr_phone;
    Button btn_back,btn_lunalogo;
    LinearLayout lay_reser_check,lay_edit_profile,lay_change_password,lay_remove_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_myinfo);

        // 페이지 변수 선언 1로 선언해야 메인 -> 내정보 로 넘어가 지고  뒤로or 취소 를 눌렀을때 메뉴화면으로 이동가능함
        Login_gloval.pageNum = 1;

        image_gender = (ImageView) findViewById(R.id.image_gender);

        text_uesr_email = (TextView)findViewById(R.id.text_uesr_email); // 유저의 이메일 담는곳
        text_uesr_id = (TextView)findViewById(R.id.text_uesr_id);     // 유저의 아이디
        text_uesr_phone = (TextView)findViewById(R.id.text_uesr_phone); //유저의 폰번호
        text_uesr_name= (TextView)findViewById(R.id.text_uesr_name);   // 유저의 이름

        lay_reser_check = (LinearLayout)findViewById(R.id.lay_reser_check);
        lay_edit_profile = (LinearLayout)findViewById(R.id.lay_edit_profile);
        lay_change_password = (LinearLayout)findViewById(R.id.lay_change_password);
        lay_remove_member = (LinearLayout)findViewById(R.id.lay_remove_member);

        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_back = (Button)findViewById(R.id.btn_back);


        // 회원 정보를 읽어오기 위한 SharedPreferences 선언
        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
        final SharedPreferences.Editor editor = info.edit();

        /*
        image_gender 사용설명
        젠더 값  -> 주민번호 뒷자리 1자리 값이 1 or 3이면
        image_gender.setImageResource(R.drawable.man); 프로필사진 남자로

        젠더 값  -> 주민번호 뒷자리 1자리 값이 2 or 4 이면
        image_gender.setImageResource(R.drawable.lady); // 프로필사진 여자로
        */

        // 현재 로그인 된 회원의 성별 정보를 가져와서 gender 변수에 넣습니다.
        String gender = info.getString("userGender", "");
        String Name = info.getString("userName", "");
        String ID = info.getString("userID", "");
        String Email = info.getString("userEmail", "");
        String HP = info.getString("userHP", "");

        // 회원의 주민번호 뒷자리 시작이 1 또는 3이면 남자 사진,
        if(gender.equals("1") || gender.equals("3")) {
            image_gender.setImageResource(R.drawable.man);
        }
        // 1, 3이 아니라면 여자 사진이 적용됩니다.
        else {
            image_gender.setImageResource(R.drawable.lady);
        }

        text_uesr_name.setText(Name + "님");   // 회원의 이름을 가져옵니다.
        text_uesr_id.setText(ID);       // 회원의 아이디 정보를 가져옵니다.
        text_uesr_email.setText(Email); // 회원의 이메일 정보를 가져옵니다.
        text_uesr_phone.setText(HP);    // 회원의 전화번호 정보를 가져옵니다.


        // 비밀번호 변경 이벤트
        lay_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Change_Password.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 정보수정 이벤트
        lay_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Edit_Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });

        // 회원탈퇴 이벤트
        lay_remove_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Member_Remove.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        // 예약확인
        lay_reser_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Reservation_Check.class);
                startActivity(intent);
                Login_gloval.pageNum = 2; // 내정보에서 예약확인으로 들어가면 변수가 2가됩니다.
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        //로고클릭
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
                //홈 버튼을 눌렀을때는 페이지 변수를 0으로 초기화합니다.
                Login_gloval.pageNum = 0;
                startActivity(intent);
            }
        });

        // 뒤로가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기 하면 수정하는 창이 한 번 더 떠서
                // 뒤로가기 하면 메인으로 이동되게 했습니다.
                Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
                startActivity(intent);
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
    }


    // 단말기 자체 뒤로가기를 누르면 메인 화면으로 이동되게 했습니다.
    // 이유는?
    // 이유 : 뒤로가기를 하면 갱신 된 정보가 아닌 이전 정보가 자꾸 출력되서 그렇게 했어유
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
        startActivity(intent);
    }
}
