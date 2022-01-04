package com.oneapp.oneappandroidapp.activity.fragment.assistant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.oneapp.oneappandroidapp.OneAPP;
import com.oneapp.oneappandroidapp.R;
import com.oneapp.oneappandroidapp.activity.DuoLunActivity;
import com.oneapp.oneappandroidapp.activity.fragment.home.HomeFragment;

public class AssistantFragment extends Fragment {

    private AssistantViewModel dashboardViewModel;
//
//    private HashMap<String, String> map;
//    private List<String> q;
//    private List<QaaMsg> msgList;
//    private AutoCompleteTextView edtInput;
//    private Button btnSend;
//    private RecyclerView msgView;
//    private QaaMsgAdapter adapter;
//
//    private String question;

    private final String TAG = "MTAG_AssistantFragment";

    private static int status = 0;

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    getActivity().getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.navigation_home, new HomeFragment(), null)
//                            .addToBackStack(null)
//                            .commit();
//                }
//        }
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(AssistantViewModel.class);
        View root = inflater.inflate(R.layout.fragment_assistant, container, false);
//        final TextView textView = root.findViewById(R.id.txt_http_test);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (status == 0) {
            Log.i(TAG, "onStart: status 0");
            status = 1;
            Intent intent = new Intent(getActivity(), DuoLunActivity.class);
            startActivityForResult(intent, 1);
        } else {
            Log.i(TAG, "onStart: status 1");
            status = 0;
            Intent intent = new Intent(getActivity(), OneAPP.class);
            startActivity(intent);
            this.getActivity().finish();
        }


        // 初始化
//        Log.i(TAG, "onStart: ...");
//        init();
//        Log.i(TAG, "onStart: " + msgView.getClass());
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        msgView.setLayoutManager(manager);
//        adapter = new QaaMsgAdapter(msgList);
//        msgView.setAdapter(adapter);
//        ArrayAdapter<String> questionAdapter = new ArrayAdapter<>(
//                getActivity(), R.layout.support_simple_spinner_dropdown_item, q);
//        edtInput.setAdapter(questionAdapter);
//
//        // 打招呼
//        // Random r = new Random();
//        // String hello = "你好，我是小奥，你可以问问我：\"" + q.get(r.nextInt(q.size())) + "\"";
//        // QaaMsg msg = new QaaMsg(hello, QaaMsg.RECEIVE);
//        // msgList.add(msg);
//        // adapter.notifyItemInserted(msgList.size() - 1);
//        // msgView.scrollToPosition(msgList.size() - 1);
//        // 点击事件
//        btnSend.setOnClickListener(v -> {
//            String content = edtInput.getText().toString();
//            if (!"".equals(content)) {
//                QaaMsg send = new QaaMsg(content, QaaMsg.SEND);
//                msgList.add(send);
//                adapter.notifyItemInserted(msgList.size() - 1);
//                msgView.scrollToPosition(msgList.size() - 1);
//                question = content;
//                edtInput.setText("");
//                DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
//                QaaMsg receive = new QaaMsg("[" + df.format(new Date()) + "]小助手正在思考您的问题...", QaaMsg.RECEIVE);
//                msgList.add(receive);
//                adapter.notifyItemInserted(msgList.size() - 1);
//                msgView.scrollToPosition(msgList.size() - 1);
//                new Thread(networkTask).start();
//            }
//        });
//    }
//
//    private void init() {
//        msgList = new ArrayList<>();
//        map = QAA.getQAAMap();
//        q = new ArrayList<>(map.keySet());
//        System.out.println(q.get(0));
////        System.out.println(getActivity());
//        edtInput = getActivity().findViewById(R.id.edt_question_ast);
//        btnSend = getActivity().findViewById(R.id.btn_send_ast);
//        msgView = getActivity().findViewById(R.id.recycler_qaa_msg_ast);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        btnHttpTest = getActivity().findViewById(R.id.btn_http_test);
//        txtHttpTest = getActivity().findViewById(R.id.txt_http_test);
//        btnHttpTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(networkTask).start();
//            }
//        });
//    }

//    @SuppressLint("HandlerLeak")
//    private final Handler handler = new Handler(){
////        @Override
////        public void handleMessage(Message msg) {
////            super.handleMessage(msg);
////            Bundle data = msg.getData();
////            String resp = data.getString("resp");
////            txtHttpTest.setText(resp);
////        }
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Bundle data = msg.getData();
//            String resp = data.getString("resp");
//            Log.d(TAG, "handleMessage: " + resp);
//            String ans = null;
//            try {
//                JSONObject rspJ = new JSONObject(resp);
//                ans = (String) rspJ.get("answer");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
//            QaaMsg receive = new QaaMsg("[" + df.format(new Date()) + "]" + ans, QaaMsg.RECEIVE);
//            msgList.add(receive);
//            adapter.notifyItemInserted(msgList.size() - 1);
//            msgView.scrollToPosition(msgList.size() - 1);
//        }
//    };


//    private final Runnable networkTask = new Runnable() {
//        @Override
//        public void run() {
//            Message msg = new Message();
//            Bundle data = new Bundle();
////            data.putString("resp", "网络连接中...");
////            msg.setData(data);
////            handler.sendMessage(msg);
//
//            // TASK networks
//            OkHttpClient cli = new OkHttpClient();
//            Response resp;
//            String url = "https://oneapp.businsight.net/oneapp/hello";
//            Request req = new Request.Builder().url(url).build();
////            req = new Request.Builder().url("https://www.baidu.com/").build();
//            Call call = cli.newCall(req);
//            Log.d(TAG, "run: " + call.toString());
//            try {
//                resp = call.execute();
//            } catch (IOException e) {
//                Log.e(TAG, "network: Calling server error.");
//                data.putString("resp", "network: Calling server error.");
//                msg.setData(data);
//                handler.sendMessage(msg);
//                e.printStackTrace();
//                return;
//            }
//            Log.d(TAG, "network: Having got response successfully.");
//            try {
//                data.putString("resp", resp.body().string());
//            } catch (IOException e) {
//                Log.e(TAG, "network: Setting response text error.");
//                data.putString("resp", "network: Setting response text error.");
//                msg.setData(data);
//                handler.sendMessage(msg);
//                e.printStackTrace();
//                return;
//            }
//            msg.setData(data);
//            handler.sendMessage(msg);
//        }
//    };
//    private final Runnable networkTask = new Runnable() {
//        @Override
//        public void run() {
//            Message msg = new Message();
//            Bundle data = new Bundle();
//    //            data.putString("resp", "网络连接中...");
//    //            msg.setData(data);
//    //            handler.sendMessage(msg);
//
//            // TASK networks
//            OkHttpClient cli = new OkHttpClient.Builder()
//                    .connectTimeout(10, TimeUnit.SECONDS)
//                    .readTimeout(180, TimeUnit.SECONDS).build();
//            Response resp;
//            Log.d(TAG, "run: " + question);
//            String url = "https://oneapp.businsight.net/faq?q=" + question + '/';
//            Request req = new Request.Builder().url(url).build();
//    //            req = new Request.Builder().url("https://www.baidu.com/").build();
//            Call call = cli.newCall(req);
//            Log.d(TAG, "run: " + call.toString());
//            try {
//                resp = call.execute();
//            } catch (IOException e) {
//                Log.e(TAG, "network: Calling server error.");
//                data.putString("resp", "network: Calling server error.");
//                msg.setData(data);
//                handler.sendMessage(msg);
//                e.printStackTrace();
//                return;
//            }
//            Log.d(TAG, "network: Having got response successfully.");
//            try {
//                data.putString("resp", resp.body().string());
//            } catch (IOException e) {
//                Log.e(TAG, "network: Setting response text error.");
//                data.putString("resp", "network: Setting response text error.");
//                msg.setData(data);
//                handler.sendMessage(msg);
//                e.printStackTrace();
//                return;
//            }
//            msg.setData(data);
//            handler.sendMessage(msg);
//        }
//    };
}
