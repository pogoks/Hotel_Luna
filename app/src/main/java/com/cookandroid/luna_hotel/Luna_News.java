package com.cookandroid.luna_hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Luna_News extends AppCompatActivity {

    ArrayList<Luna_News_ItemData> dataList;

    TextView tv_news;
    Button btn_back, btn_lunalogo, btn_newsCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_news);

        tv_news = (TextView)findViewById(R.id.tv_news);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_newsCreate = (Button)findViewById(R.id.news_create);

        // 관리자 계정으로 로그인 되어있다면 공지사항 삭제버튼 표시
        if("master".equals(Login_gloval.login_id)) {
            btn_newsCreate.setVisibility(VISIBLE);
        }
        // 일반 계정으로 로그인 되어있다면 공지사항 삭제버튼 숨김
        else {
            btn_newsCreate.setVisibility(INVISIBLE);
        }

        // getListNews 메소드 호출인데, 이거 밑에 구현 해 두었습니다.
        this.getListNews();

        // 리스트 뷰 해당 액티비티랑 연동
        ListView listView = (ListView) findViewById(R.id.lv_news);

        // 리스트 뷰를 쓰기 위한 어댑터 선언, 어댑터는 Luna_News_ListAdapter에 구현했습니다.
        final Luna_News_ListAdapter adapter = new Luna_News_ListAdapter(this, dataList);

        // 리스트 뷰의 어댑터를 Luna_News_ListAdapter로 설정.
        listView.setAdapter(adapter);

        // 관리자 모드에서 글쓰기 버튼 클릭 메소드
        btn_newsCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Luna_News_Create.class);
                startActivity(intent);
            }
        });

        //뒤로버튼 클릭 메소드
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        // 로고를 누르면 홈화면으로 이동하는 코드
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
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


    // getListNews 메소드는 SharedPreferences에 저장된 공지사항 내용을 가져와 리스트 뷰로 보여주는 메소드입니다.
    public void getListNews() {
        // 리스트 뷰 자료형에 맞는 배열리스트를 선언합니다.
        dataList = new ArrayList<Luna_News_ItemData>();

        // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
        SharedPreferences news = getSharedPreferences("news", MODE_PRIVATE);
        // SharedPreferences 를 제어하기 위한 Editor 선언
        SharedPreferences.Editor editor = news.edit();

        // 공지사항 목록을 가져오면서 저장했던 배열리스트의 크기를 가져옵니다.
        int size = news.getInt("news", 0);

        int count = 0;

        // 해당되는 배열리스트의 사이즈 만큼 반복을 통해 리스트뷰에 각 데이터들을 넣습니다.
        // 예를 들어 resID = 1234는 예약을 총 3개 했으니 size는 3이 되고, 3번 반복해서 리스트뷰에 데이터를 넣음으로서 총 3개의 리스트가 생성됩니다.
        for(int i = 0; i < size; i++) {
            dataList.add(new Luna_News_ItemData(news.getString("newsCODE" + i, ""), news.getString("newsTitle" + i, ""),
                    news.getString("newsMain" + i, ""), "작성자 : " + news.getString("newsWriter" + i, ""),
                    "작성일 : " + news.getString("newsDate" + i, "")));

            // 작성된 공지사항의 갯수를 세기 위한 count 변수
            count++;
        }

        // 공지사항의 갯수를 표시합니다.
        tv_news.setText("" + count);
    }
}


