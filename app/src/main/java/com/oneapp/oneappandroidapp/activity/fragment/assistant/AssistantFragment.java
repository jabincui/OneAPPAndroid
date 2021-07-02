package com.oneapp.oneappandroidapp.activity.fragment.assistant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.oneapp.oneappandroidapp.R;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AssistantFragment extends Fragment {

    private AssistantViewModel dashboardViewModel;

    private Button btnHttpTest;
    private TextView txtHttpTest;

    private final String TAG = "AssistantFragment";

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
        btnHttpTest = getActivity().findViewById(R.id.btn_http_test);
        txtHttpTest = getActivity().findViewById(R.id.txt_http_test);
        btnHttpTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(networkTask).start();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String resp = data.getString("resp");
            txtHttpTest.setText(resp);
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
            OkHttpClient cli = new OkHttpClient();
            Response resp;
            String url = "https://oneapp.businsight.net/oneapp/hello";
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
