package com.cookandroid.luna_hotel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


// 공지사항 리스트 뷰에 사용되는 어댑터입니다.
public class Luna_News_ListAdapter extends BaseAdapter {

    Context mContext = null;

    LayoutInflater mLayoutInflater = null;

    // 리스트 뷰 아이템을 저장하기 위한 배열리스트 sample
    ArrayList<Luna_News_ItemData> sample;

    // 취소 버튼 누를 때 마다 다시 회원 예약정보 가져오기 위해 사용하는 String과 배열리스트
    public String jsonString;
    ArrayList<NewsInfo> newsArrayList;

    // 생성자
    public Luna_News_ListAdapter(Context context, ArrayList<Luna_News_ItemData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Luna_News_ItemData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, final View converView, ViewGroup parent) {

        // luna_news_list 레이아웃을 inflate
        View view = mLayoutInflater.inflate(R.layout.luna_news_list, null);

        TextView newsTitle = (TextView) view.findViewById(R.id.news_title2);
        TextView newsMain = (TextView) view.findViewById(R.id.news_main);
        TextView newsWriter = (TextView) view.findViewById(R.id.news_writer);
        TextView newsDate = (TextView) view.findViewById(R.id.news_date);

        Button newsDelete = (Button) view.findViewById(R.id.news_delete);

        newsTitle.setText(sample.get(position).getStrList_Title());
        newsMain.setText(sample.get(position).getStrList_Main());
        newsWriter.setText(sample.get(position).getStrList_Writer());
        newsDate.setText(sample.get(position).getStrList_Date());

        final String Title = newsTitle.getText().toString();

        // 로그인 된 계정이 관리자 계정이면
        if("master".equals(Login_gloval.login_id)) {
            newsDelete.setVisibility(View.VISIBLE);
        } else {
            newsDelete.setVisibility(View.INVISIBLE);
        }

        newsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                // 예약취소 재 확인 대화상자 출력
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                builder.setMessage("공지사항을 삭제하시겠습니까?");

                // 확인을 누를 경우
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    // 서버와 연결이 제대로 되지 않았을 경우
                                    if(success) {
                                        androidx.appcompat.app.AlertDialog.Builder builder2 = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                                        builder2.setMessage("취소 과정에서 오류가 발생했습니다.");
                                        builder2.setPositiveButton("확인", null);
                                        androidx.appcompat.app.AlertDialog dialog1 = builder2.create();
                                        dialog1.show();
                                    }

                                    // 서버와 연결이 되어 쿼리가 실행 될 경우
                                    // 여기서는 delete from reserve where resCODE= **** 쿼리가 실행됩니다.
                                    // 즉, 예약 취소를 누른 예약정보는 DB에서 삭제됩니다.
                                    else {
                                        androidx.appcompat.app.AlertDialog.Builder builder2 = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                                        builder2.setMessage("삭제되었습니다.");

                                        // 확인 버튼을 누를 경우 예약 취소 진행
                                        builder2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // 삭제 된 DB가 있기 때문에 다시 예약정보를 가져오기 위한 GetReserve 클래스 실행.
                                                final GetNews getNews = new GetNews();
                                                getNews.execute("http://35.203.164.40/HL_GetNews.php");

                                                // 대화상자에서 확인 누르면 메인 화면으로 이동됩니다.
                                                // 갱신 찾아보면서 했는데 너무 어려워서 일단은 메인으로 이동해서 다시 확인하면 갱신되는 방향으로 했습니다...
                                                Intent intent = new Intent(mContext, Luna_Main.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                mContext.startActivity(intent);
                                            }
                                        });
                                        AlertDialog dialog1 = builder2.create();
                                        dialog1.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        // 해당 resCODE를 넘겨서 Delete 쿼리 실행하는 queue 생성.
                        ValidateRequest_RemoveNews validateRequest_removeNews = new ValidateRequest_RemoveNews(Title, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(v.getContext());
                        queue.add(validateRequest_removeNews);
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

        return view;
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
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
            else {
                // null이 아니라면, 밑 문장들 실행
                jsonString = fromdoInBackgroundString;
                // ArrayList에 값을 넣기 위해 doParse() 메소드 실행.
                // doParse() 메소드는 밑에 onProgressUpdate 부분에 넣음.
                newsArrayList = doParse3();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences news = mContext.getApplicationContext().getSharedPreferences("news", MODE_PRIVATE);
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
                JSONObject jsonObject = new JSONObject(jsonString);
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
}
