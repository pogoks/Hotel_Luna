package com.cookandroid.luna_hotel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest_RemoveNews extends StringRequest{
    // 서버에 위치한 DeleteNews.php 스트링 형식으로 저장
    final static private String URL = "http://35.203.164.40/HL_DeleteNews.php";
    private Map<String, String> parameters;

    // 이곳은 생성자입니다.
    // 생성자를 통해 비교 하게 될 newsTitle 값을 전송.
    public ValidateRequest_RemoveNews(String newsTitle, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("newsTitle", newsTitle);
    }

    // 추후에 사용하기 위한 부분.
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}