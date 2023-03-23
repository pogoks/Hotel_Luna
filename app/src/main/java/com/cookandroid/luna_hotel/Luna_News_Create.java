package com.cookandroid.luna_hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Luna_News_Create extends AppCompatActivity {

    private String jsonString3;
    ArrayList<NewsInfo> newsArrayList;

    EditText newsMain, newsTitle;
    Button btn_back, btn_lunalogo, btn_create;

    AlertDialog dialog;

    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luna_news_create);

        newsTitle = (EditText) findViewById(R.id.newsTitle);
        newsMain = (EditText) findViewById(R.id.newsMain);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_lunalogo = (Button)findViewById(R.id.btn_lunalogo);
        btn_create = (Button)findViewById(R.id.btn_create);

        // 현재 날짜를 구하기 위한 구문입니다.
        // 현재 시간을 구해서 now 변수에 넣습니다.
        long now = System.currentTimeMillis();
        // 시간이 들어있는 now 변수를 Date 형식의 mDate 변수로 변환합니다.
        Date mDate = new Date(now);
        // SimpleDateFormat 클래스를 통해 날짜 표시 포맷(0000-00-00) 형태로 변경합니다.
        SimpleDateFormat simpleDate = new SimpleDateFormat("YYYY-MM-dd");
        final String getTime = simpleDate.format(mDate);

        // 로그인 된 계정이 관리자 계정이라면 temp 변수에 "관리자" 문자열 값을 저장합니다.
        if(Login_gloval.login_id.equals("master")) {
            temp = "관리자";
        }
        // 일반 계정일 경우 temp 변수에 현재 ID 문자열 값을 저장합니다.
        else {
            temp = Login_gloval.login_id;
        }

        //뒤로버튼 클릭 메소드
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                startActivity(Intent);
            }
        });


        // 작성하기 눌렀을 때 이벤트
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String title = newsTitle.getText().toString();
                final String main = newsMain.getText().toString();
                final String date = getTime;
                final String writer = temp;

                // 공지사항을 작성할 때 제목이 비어있을 경우를 방지하기 위한 구문입니다.
                if(title.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_News_Create.this);
                    dialog = builder.setMessage("제목을 입력해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    newsTitle.requestFocus();
                    return;
                }

                // 공지사항을 작성할 때 내용이 비어있을 경우를 방지하기 위한 구문입니다.
                if(main.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Luna_News_Create.this);
                    dialog = builder.setMessage("내용을 입력해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    newsMain.requestFocus();
                    return;
                }

                // 작성 전 확인 대화상자
                AlertDialog.Builder builder = new AlertDialog.Builder(Luna_News_Create.this);
                builder.setMessage("작성하시겠습니까?");

                // 작성 버튼을 누를 경우 공지사항 작성.
                builder.setPositiveButton("작성", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateNews(title, main, writer, date);

                        AlertDialog.Builder builder = new AlertDialog.Builder(Luna_News_Create.this);
                        builder.setMessage("작성되었습니다.");

                        // 확인 버튼을 누르면 관리 화면으로 이동합니다.
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();

                        // 작성이 끝난 후 다시 공지사항 목록을 불러와 목록을 최신화 시킵니다.
                        final GetNews getNews = new GetNews();
                        getNews.execute("http://35.203.164.40/HL_GetNews.php");
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
        Intent intent = new Intent(getApplicationContext(), Luna_Main.class);
        startActivity(intent);
    }


    // 서버에서 공지사항 목록을 불러오기 위한 클래스입니다.
    // AsyncTask 클래스 여기다가 구현.
    public class GetNews extends AsyncTask<String, Void, String> {
        String TAG = "Get News";

        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용합니다.
            String url = strings[0];

            try {
                // 어플에서 데이터 전송 준비
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


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
                jsonString3 = fromdoInBackgroundString;
                // ArrayList에 값을 넣기 위해 doParse() 메소드 실행.
                // doParse() 메소드는 밑에 onProgressUpdate 부분에 넣음.
                newsArrayList = doParse3();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences news = getSharedPreferences("news", MODE_PRIVATE);
                // SharedPreferences 를 제어하기 위한 Editor 선언
                SharedPreferences.Editor editor = news.edit();

                // newsArrayList에 들어간 배열의 크기를 news변수에 넣습니다.
                // 이 news는 리스트뷰에서 배열의 크기를 불러올 때 사용됩니다.
                editor.putInt("news", newsArrayList.size());

                editor.clear();

                // for 문을 통해 DB에 저장된 배열 갯수만큼 SharedPreferences에 넣습니다.
                for(int i = 0; i < newsArrayList.size(); i++) {
                    editor.putString("newsCODE" + i, newsArrayList.get(i).getNewsCODE());
                    editor.putString("newsTitle" + i, newsArrayList.get(i).getNewsTitle());
                    editor.putString("newsMain" + i, newsArrayList.get(i).getNewsMain());
                    editor.putString("newsWriter" + i, newsArrayList.get(i).getNewsWriter());
                    editor.putString("newsDate" + i, newsArrayList.get(i).getNewsDate());
                }

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

        private ArrayList<NewsInfo> doParse3() {

            // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
            ArrayList<NewsInfo> tmpArray3 = new ArrayList<NewsInfo>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString3);
                // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                // 값들을 복사해서 넣는 과정.
                for(int i = 0; i < jsonArray.length(); i++) {
                    NewsInfo tmpinfo = new NewsInfo();
                    JSONObject item = jsonArray.getJSONObject(i);

                    tmpinfo.setNewsCODE(item.getString("newsCODE"));
                    tmpinfo.setNewsTitle(item.getString("newsTitle"));
                    tmpinfo.setNewsMain(item.getString("newsMain"));
                    tmpinfo.setNewsWriter(item.getString("newsWriter"));
                    tmpinfo.setNewsDate(item.getString("newsDate"));

                    tmpArray3.add(tmpinfo);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
            // JSON을 가공하여 tmpArray에 넣고 반환.
            return tmpArray3;
        }
    }


    // 작성 화면에서 입력한 제목과 내용, 작성자 정보, 날짜 정보를 서버 DB로 전송하는 메소드입니다.
    private void CreateNews(String newsTitle, String newsMain, String newsWriter, String newsDate) {

        // InsertData 클래스 선언, AsyncTask를 이용해 백그라운드에서 인터넷 통신으로 값을 넘김
        class InsertData extends AsyncTask<String, Void, String> {
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

            // Background에서 해당 코드 실행
            // String link에 서버 주소 값을 저장한 후, 서버 html 폴더 안에 있는 CreateNews.php를 읽어옴
            // CreateNews.php 안에 있는 SQL 쿼리를 통해 값을 저장하게 됨.
            @Override
            protected String doInBackground(String... params) {
                try {
                    // String 값들을 배열에 넣습니다.
                    String newsTitle = (String) params[0];
                    String newsMain = (String) params[1];
                    String newsWriter = (String) params[2];
                    String newsDate = (String) params[3];

                    // 서버 안에 있는 CreateNews.php의 주소를 link 안에 저장합니다.
                    String link = "http://35.203.164.40/HL_CreateNews.php";

                    // data라는 String 형태 변수 안에 배열의 내용들을 쭉 나열하여 넣습니다.
                    String data = URLEncoder.encode("newsTitle", "UTF-8") + "=" + URLEncoder.encode(newsTitle, "UTF-8");
                    data += "&" + URLEncoder.encode("newsMain", "UTF-8") + "=" + URLEncoder.encode(newsMain, "UTF-8");
                    data += "&" + URLEncoder.encode("newsWriter", "UTF-8") + "=" + URLEncoder.encode(newsWriter, "UTF-8");
                    data += "&" + URLEncoder.encode("newsDate", "UTF-8") + "=" + URLEncoder.encode(newsDate, "UTF-8");

                    // URLConnection 으로 서버에 접속하고 데이터들을 전송합니다.
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch(Exception e) {
                    return new String("Exception : " + e.getMessage());
                }
            }
        }

        // 백그라운드에서 실행할 수 있게 InsertData 클래스의 인스턴스를 생성하여 execute로 실행합니다.
        InsertData task = new InsertData();
        task.execute(newsTitle, newsMain, newsWriter, newsDate);
    }
}
