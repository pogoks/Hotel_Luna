package com.cookandroid.luna_hotel;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// 회원탈퇴 할 때 전역변수에 선언되어 있는 아이디를 받아서 탈퇴할 때 사용하게 해주는 클래스입니다.
public class ValidateRequest_RemoveUser_DeleteID extends StringRequest {

    // 서버에 위치한 UserValidate.php 스트링 형식으로 저장
    final static private String URL = "http://35.203.164.40/HL_DeleteID.php";
    private Map<String, String> parameters;

    // 이곳은 생성자입니다.
    // 생성자를 통해 비교 하게 될 userID 값을 전송.
    public ValidateRequest_RemoveUser_DeleteID(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    // 추후에 사용하기 위한 부분.
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}