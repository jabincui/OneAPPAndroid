package com.oneapp.oneappandroidapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.oneapp.oneappandroidapp.R;
import com.oneapp.oneappandroidapp.adapter.QaaMsgAdapter;
import com.oneapp.oneappandroidapp.model.QaaMsg;
import com.oneapp.oneappandroidapp.util.QAA;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DuoLunActivity extends AppCompatActivity {

    private HashMap<String, String> map;
    private List<String> q;
    private List<QaaMsg> msgList;
    private AutoCompleteTextView edtInput;
    private Button btnSend;
    private RecyclerView msgView;
    private QaaMsgAdapter adapter;

    private final String TAG = "MTAG_DuoLunActivity";

    private String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duo_lun);
        Intent intent = new Intent();
        intent.putExtra("data_return", "2");
        setResult(RESULT_OK, intent);

        init();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        msgView.setLayoutManager(manager);
        adapter = new QaaMsgAdapter(msgList);
        msgView.setAdapter(adapter);
        ArrayAdapter<String> questionAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, q);
        edtInput.setAdapter(questionAdapter);

        // 打招呼
        // Random r = new Random();
        // String hello = "你好，我是小奥，你可以问问我：\"" + q.get(r.nextInt(q.size())) + "\"";
        // QaaMsg msg = new QaaMsg(hello, QaaMsg.RECEIVE);
        // msgList.add(msg);
        // adapter.notifyItemInserted(msgList.size() - 1);
        // msgView.scrollToPosition(msgList.size() - 1);
        // 点击事件
        btnSend.setOnClickListener(v -> {
            String content = edtInput.getText().toString();
            if (!"".equals(content)) {
                QaaMsg send = new QaaMsg(content, QaaMsg.SEND);
                msgList.add(send);
                adapter.notifyItemInserted(msgList.size() - 1);
                msgView.scrollToPosition(msgList.size() - 1);
                question = content;
                edtInput.setText("");
                DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
                QaaMsg receive = new QaaMsg("[" + df.format(new Date()) + "]小助手正在思考您的问题...", QaaMsg.RECEIVE);
                msgList.add(receive);
                adapter.notifyItemInserted(msgList.size() - 1);
                msgView.scrollToPosition(msgList.size() - 1);
                new Thread(networkTask).start();
            }

//            // 查答案
//            String value = map.get(content);
//            String answer;
//            if (value == null) {
//                answer = "我听不懂你在说什么，小奥还要继续学习";
//            } else {
//                answer = value;
//            }
//            // 更新ui
//            QaaMsg receive = new QaaMsg(answer, QaaMsg.RECEIVE);
//            msgList.add(receive);
//            adapter.notifyItemInserted(msgList.size() - 1);
//            msgView.scrollToPosition(msgList.size() - 1);
        });


    }



    private void init() {
        msgList = new ArrayList<>();
        map = QAA.getQAAMap();
        q = new ArrayList<>(map.keySet());
        System.out.println(q.get(0));
        edtInput = findViewById(R.id.edt_question_dl);
        btnSend = findViewById(R.id.btn_send_dl);
        msgView = findViewById(R.id.recycler_qaa_msg_dl);
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String resp = data.getString("resp");
            Log.d(TAG, "handleMessage: " + resp);
            String ans = null;
//            try {
//                JSONObject rspJ = new JSONObject(resp);
//                ans = (String) rspJ.get("answer");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            ans = resp;
            DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
            QaaMsg receive = new QaaMsg("[" + df.format(new Date()) + "]" + ans, QaaMsg.RECEIVE);
            msgList.add(receive);
            adapter.notifyItemInserted(msgList.size() - 1);
            msgView.scrollToPosition(msgList.size() - 1);
        }
    };


    private final Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle data = new Bundle();
//            data.putString("resp", "网络连接中...");
//            msg.setData(data);
//            handler.sendMessage(msg);

            // TASK networks
            OkHttpClient cli = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(180, TimeUnit.SECONDS).build();
            Response resp;
            Log.d(TAG, "run: " + question);
            String url = "https://oneapp.businsight.net/duolun/q=" + question;
            Request req = new Request.Builder().url(url).build();
//            req = new Request.Builder().url("https://www.baidu.com/").build();
            Call call = cli.newCall(req);
            Log.d(TAG, "run: " + call.toString());
            try {
                resp = call.execute();
            } catch (IOException e) {
                Log.e(TAG, "network: Calling server error.");
                data.putString("resp", "network: Calling server error.");
                msg.setData(data);
                handler.sendMessage(msg);
                e.printStackTrace();
                return;
            }
            Log.d(TAG, "network: Having got response successfully.");
            try {
                data.putString("resp", resp.body().string());
            } catch (IOException e) {
                Log.e(TAG, "network: Setting response text error.");
                data.putString("resp", "network: Setting response text error.");
                msg.setData(data);
                handler.sendMessage(msg);
                e.printStackTrace();
                return;
            }
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
}