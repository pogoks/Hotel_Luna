package com.cookandroid.luna_hotel;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// 회원가입 할 때 아이디 중복확인 버튼에 필요한 클래스입니다.
public class ValidateRequest extends StringRequest {

    // 서버에 위치한 UserValidate.php 스트링 형식으로 저장
    final static private String URL = "http://35.203.164.40/HL_UserValidate.php";
    private Map<String, String> parameters;

    // 이곳은 생성자입니다.
    // 생성자를 통해 비교 하게 될 userID 값을 전송.
    public ValidateRequest(String userID, Response.Listener<String> listener){
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