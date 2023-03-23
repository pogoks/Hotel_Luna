package com.cookandroid.luna_hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Luna_Reservation_Check extends AppCompatActivity {

    ArrayList<Luna_Reservation_ItemData> dataList;

    Button btn_back, btn_lunalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_reservation_check);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);

        // getDataList 메소드 호출인데, 이거 밑에 구현 해 두었습니다.
        this.getDataList();

        // 리스트 뷰 해당 액티비티랑 연동
        ListView listView = (ListView) findViewById(R.id.listview);

        // 리스트 뷰를 쓰기 위한 어댑터 선언, 어댑터는 Luna_Reservation_ListAdapter에 구현했습니다.
        final Luna_Reservation_ListAdapter adapter = new Luna_Reservation_ListAdapter(this, dataList);

        // 리스트 뷰의 어댑터를 Luna_Reservation_ListAdapter로 설정.
        listView.setAdapter(adapter);

        //뒤로버튼 클릭 메소드
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                // 메뉴에서 진입한 경우
                Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
                startActivity(intent);
                //액티비티 전환 애니메이션 설정하는 부분
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });


        // 로고버튼 이벤트
        btn_lunalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(),Luna_Main.class);
                //홈 버튼을 눌렀을때는 페이지 변수를 0으로 초기화합니다.
                Login_gloval.pageNum = 0;
                startActivity(Intent);
            }
        });
    }


    // 취소버튼 누를때 생기는 애니메이션
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
        startActivity(intent);
    }


    // getDataList 메소드는 SharedPreferences에 저장된 예약 정보들을 가져와 리스트 뷰에 넣는 메소드입니다.
    public void getDataList() {

        // 리스트 뷰 자료형에 맞는 배열리스트를 선언합니다.
        dataList = new ArrayList<Luna_Reservation_ItemData>();

        // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
        SharedPreferences reserve = getSharedPreferences("reserve", MODE_PRIVATE);
        // SharedPreferences 를 제어하기 위한 Editor 선언
        SharedPreferences.Editor editor = reserve.edit();

        // 예약정보를 가져오면서 저장했던 배열리스트의 크기를 가져옵니다.
        int size = reserve.getInt("reserve", 0);

        // 해당되는 배열리스트의 사이즈 만큼 반복을 통해 리스트뷰에 각 데이터들을 넣습니다.
        // 예를 들어 resID = 1234는 예약을 총 3개 했으니 size는 3이 되고, 3번 반복해서 리스트뷰에 데이터를 넣음으로서 총 3개의 리스트가 생성됩니다.
        for(int i = 0; i < size; i++) {
            dataList.add(new Luna_Reservation_ItemData(reserve.getString("resCODE" + i, ""), "예약자명 : " + reserve.getString("resName" + i, ""),
                    "지점 : " + reserve.getString("resHotelName" + i, ""), "룸 : " + reserve.getString("resRoomName" + i, ""),
                    reserve.getString("resIN_year" + i, "") + "년 " + reserve.getString("resIN_month" + i, "") + "월 " + reserve.getString("resIN_date" + i, "") + "일  ~  "
                            + reserve.getString("resOUT_year" + i, "") + "년 " + reserve.getString("resOUT_month" + i, "") + "월 " + reserve.getString("resOUT_date" + i, "") + "일 총 " + reserve.getString("resTnrqkr" + i, "") +"박",
                    "결제 금액 : " + reserve.getString("resPrice" + i, "")));
        }
    }
}
