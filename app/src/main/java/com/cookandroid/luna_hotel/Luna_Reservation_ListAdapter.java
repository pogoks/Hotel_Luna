package com.cookandroid.luna_hotel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


// 리스트 뷰에 사용되는 어댑터입니다.
public class Luna_Reservation_ListAdapter extends BaseAdapter {

    Context mContext = null;

    LayoutInflater mLayoutInflater = null;

    // 리스트 뷰 아이템을 저장하기 위한 배열리스트 sample
    ArrayList<Luna_Reservation_ItemData> sample;

    // 취소 버튼 누를 때 마다 다시 회원 예약정보 가져오기 위해 사용하는 String과 배열리스트
    public String jsonString;
    ArrayList<ReserveInfo> reserveArrayList;

    // 생성자
    public Luna_Reservation_ListAdapter(Context context, ArrayList<Luna_Reservation_ItemData> data) {
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
    public Luna_Reservation_ItemData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, final View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.luna_reservation_check_list, null);

        final TextView resCode = (TextView) view.findViewById(R.id.list_code);
        TextView resName = (TextView) view.findViewById(R.id.list_name);
        TextView resHotelName = (TextView) view.findViewById(R.id.list_hotel);
        TextView resRoomName = (TextView) view.findViewById(R.id.list_room);
        TextView resReserveDate = (TextView) view.findViewById(R.id.list_reservedate);
        TextView resPrice = (TextView) view.findViewById(R.id.list_price);

        Button btn_cancel = (Button) view.findViewById(R.id.list_cancel);

        resCode.setText(sample.get(position).getStrList_Code());
        resName.setText(sample.get(position).getStrList_Name());
        resHotelName.setText(sample.get(position).getStrList_Hotel());
        resRoomName.setText(sample.get(position).getStrList_Room());
        resReserveDate.setText(sample.get(position).getStrList_reserveDate());
        resPrice.setText(sample.get(position).getStrList_Price());

        // 해당 아이템의 예약번호를 code에 저장합니다.
        final String code = resCode.getText().toString();

        // 취소 버튼 누를 시 발생하는 이벤트
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                // 예약취소 재 확인 대화상자 출력
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                builder.setMessage("예약을 취소하시겠습니까?");

                // 확인을 누를 경우
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText et = new EditText(mContext);

                        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                        builder1.setTitle("비밀번호 확인");
                        builder1.setMessage("비밀번호를 입력해 주세요.");
                        InputFilter[] EditFilter = new InputFilter[1];
                        EditFilter[0] = new InputFilter.LengthFilter(16);
                        et.setFilters(EditFilter);
                        et.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                        et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        et.setFocusable(true);

                        builder1.setView(et);
                        builder1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(et.getText().toString().equals(Login_gloval.login_password)) {
                                    // 서버와 통신하는 부분
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse2 = new JSONObject(response);
                                                boolean success = jsonResponse2.getBoolean("success");

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
                                                    builder2.setMessage("예약이 취소되었습니다.");

                                                    // 확인 버튼을 누를 경우 예약 취소 진행
                                                    builder2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // 삭제 된 DB가 있기 때문에 다시 예약정보를 가져오기 위한 GetReserve 클래스 실행.
                                                            final GetReserve getReserve = new GetReserve();
                                                            getReserve.execute("http://35.203.164.40/HL_GetReserve.php");

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
                                    ValidateRequest_RemoveReserve validateRequest_removeReserve = new ValidateRequest_RemoveReserve(code, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(v.getContext());
                                    queue.add(validateRequest_removeReserve);
                                } else {
                                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                                    builder.setMessage("비밀번호가 일치하지 않습니다.");
                                    builder.setPositiveButton("확인", null);
                                    androidx.appcompat.app.AlertDialog dialog2 = builder.create();
                                    dialog2.show();
                                }
                            }
                        });

                        builder1.setNegativeButton("취소", null);
                        AlertDialog dialog2 = builder1.create();
                        dialog2.show();
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


    // 예약 정보를 가져오는 구문입니다.
    // AsyncTask 클래스 여기다가 구현.
    public class GetReserve extends AsyncTask<String, Void, String> {
        String TAG = "Get Reserve Data";

        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용합니다.
            String url = strings[0];

            try {
                // 따옴표 안의 resID= 부분을 통해 DB에서 해당 아이디로 쿼리를 실행합니다.
                String selectData = "resID=" + Login_gloval.login_id;

                // 어플에서 데이터 전송 준비
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(selectData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

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
                reserveArrayList = doParse2();

                // 단말기에 간단한 정보를 저장하는 SharedPreferences 객체를 PRIVATE모드로 선언
                SharedPreferences reserve = mContext.getApplicationContext().getSharedPreferences("reserve", MODE_PRIVATE);
                // SharedPreferences 를 제어하기 위한 Editor 선언
                SharedPreferences.Editor editor = reserve.edit();

                // DB에서 예약정보를 가져오면서 reserveArrayList의 사이즈를 "reserve"라는 int형 SharedPrefereces로 저장합니다.
                // 쉽게 말해서 배열 크기를 다른 클래스에서도 쓸 수 있게 전역변수처럼 선언한겁니당
                editor.putInt("reserve", reserveArrayList.size());

                // SharedPreferences에 값을 넣기 전에 한 번 초기화 시켜줍니다.
                editor.clear();

                // for문을 돌려서 로그인 한 아이디에 해당되는 예약정보들을 모두 불러옵니다.
                // 한 아이디가 여러개의 예약정보를 가지고 있다면, 배열이 여러개 필요하므로
                // for 문을 통해 DB에 저장된 배열 갯수만큼 SharedPreferences에 넣습니다.
                for(int i = 0; i < reserveArrayList.size(); i++) {
                    editor.putString("resCODE" + i, reserveArrayList.get(i).getResCODE());
                    editor.putString("resID" + i, reserveArrayList.get(i).getResID());
                    editor.putString("resName" + i, reserveArrayList.get(i).getResName());
                    editor.putString("resHotelNum" + i, reserveArrayList.get(i).getHotelNum());
                    editor.putString("resHotelName" + i, reserveArrayList.get(i).getHotelName());
                    editor.putString("resRoomNum" + i, reserveArrayList.get(i).getRoomNum());
                    editor.putString("resRoomName" + i, reserveArrayList.get(i).getRoomName());
                    editor.putString("resIN_year" + i, reserveArrayList.get(i).getIn_year());
                    editor.putString("resIN_month" + i, reserveArrayList.get(i).getIn_month());
                    editor.putString("resIN_date" + i, reserveArrayList.get(i).getIn_date());
                    editor.putString("resOUT_year" + i, reserveArrayList.get(i).getOut_year());
                    editor.putString("resOUT_month" + i, reserveArrayList.get(i).getOut_month());
                    editor.putString("resOUT_date" + i, reserveArrayList.get(i).getOut_date());
                    editor.putString("resTnrqkr" + i, reserveArrayList.get(i).getTnrqkr());
                    editor.putString("resPrice" + i, reserveArrayList.get(i).getPrice());
                }

                // SharedPreferencs 저장.
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

        private ArrayList<ReserveInfo> doParse2() {

            // ArrayList에 값을 넣기 위해 임시 ArrayList를 만듬
            ArrayList<ReserveInfo> tmpArray2 = new ArrayList<ReserveInfo>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                // PHP 구문을 통해 받은 결과인 result라는 이름의 JSONArray를 받아옴.
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                // 값들을 복사해서 넣는 과정.
                for(int i = 0; i < jsonArray.length(); i++) {
                    ReserveInfo tmpinfo = new ReserveInfo();
                    JSONObject item = jsonArray.getJSONObject(i);

                    tmpinfo.setResCODE(item.getString("resCODE"));
                    tmpinfo.setResID(item.getString("resID"));
                    tmpinfo.setResName(item.getString("resName"));
                    tmpinfo.setHotelNum(item.getString("resHotelNum"));
                    tmpinfo.setHotelName(item.getString("resHotelName"));
                    tmpinfo.setRoomNum(item.getString("resRoomNum"));
                    tmpinfo.setRoomName(item.getString("resRoomName"));
                    tmpinfo.setIn_year(item.getString("resIN_year"));
                    tmpinfo.setIn_month(item.getString("resIN_month"));
                    tmpinfo.setIn_date(item.getString("resIN_date"));
                    tmpinfo.setOut_year(item.getString("resOUT_year"));
                    tmpinfo.setOut_month(item.getString("resOUT_month"));
                    tmpinfo.setOut_date(item.getString("resOUT_date"));
                    tmpinfo.setTnrqkr(item.getString("resTnrqkr"));
                    tmpinfo.setPrice(item.getString("resPrice"));

                    tmpArray2.add(tmpinfo);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
            // JSON을 가공하여 tmpArray에 넣고 반환.
            return tmpArray2;
        }
    }

}