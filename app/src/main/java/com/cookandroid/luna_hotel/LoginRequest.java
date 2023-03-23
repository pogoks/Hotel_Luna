package com.cookandroid.luna_hotel;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    // 서버에 위치한 Login.php 스트링 형식으로 저장
    final static private String URL = "http://35.203.164.40/HL_Login.php";
    private Map<String, String> parameters;

    // 이곳은 생성자입니다.
    // 생성자를 통해 비교 하게 될 userID와 userPW 값을 전송.
    public LoginRequest(String userID, String userPW, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPW", userPW);
    }

    //추후 사용을 위한 부분
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}