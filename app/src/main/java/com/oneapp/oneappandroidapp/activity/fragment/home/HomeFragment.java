package com.oneapp.oneappandroidapp.activity.fragment.home;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alipay.android.phone.scancode.export.ScanRequest;
import com.alipay.android.phone.scancode.export.adapter.MPScan;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.mpaas.nebula.adapter.api.MPTinyHelper;
import com.oneapp.oneappandroidapp.R;
import com.oneapp.oneappandroidapp.activity.DashboardActivity;
import com.oneapp.oneappandroidapp.activity.LifeServiceActivity;
import com.oneapp.oneappandroidapp.activity.NotificationTestActivity;
import com.oneapp.oneappandroidapp.activity.QaaActivity;
import com.oneapp.oneappandroidapp.activity.UniWebActivity;
import com.oneapp.oneappandroidapp.activity.VolunteerDashboardActivity;
import com.oneapp.oneappandroidapp.view.ImgTxtButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ViewSwitcher.ViewFactory {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private ImageSwitcher is;
    private LinearLayout point_layout;
    private ImgTxtButton btn_AYBK;
    private ImgTxtButton btn_SSZL;
    private ImgTxtButton btn_AYWS;
    private ImgTxtButton btn_SHFW;
    private ImgTxtButton btn_AYHD;
    private ImgTxtButton btn_TZCXZ;
    private ImgTxtButton btn_MORE;
    private ImgTxtButton btn_ELEME;
    private ImgTxtButton btn_FEIZHU;
    private ImgTxtButton btn_GAODE;
    private ImgTxtButton btn_MOJI;
    private ImgTxtButton btn_YOUKU;


    int[] images = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3};
    //实例化存储导航圆点的集合
    ArrayList<ImageView> points = new ArrayList<>();
    int index;  // 声明index，记录图片id数组下标
    float startX;  // 手指接触屏幕时X的坐标（演示左右滑动）
    float endX;  // 手指离开屏幕时的坐标（演示左右滑动）



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();
        is =  getActivity().findViewById(R.id.home_images);
        try {
            is.setFactory(this);
        } catch (Exception ignored) {
        }
        initPnt();
        is.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //按下屏幕
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    startX=event.getX();//获取按下屏幕时X轴的坐标
                    //手指抬起
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    endX=event.getX();
                    //判断结束坐标大于起始坐标则为下一张（为避免误操作，设置30的判断区间）
                    if(startX-endX>30){
                        //三目运算判断当前图片已经为最后一张，则从头开始
                        index = index+1<images.length?++index:0;
                        //使用系统自带的切换出入动画效果（也可以向ViewFlipper中一样自定义动画效果）
                        is.setInAnimation(getContext(),android.R.anim.fade_in);
                        is.setOutAnimation(getContext(),android.R.anim.fade_out);

                        //判断结束坐标小于于起始坐标则为上一张（为避免误操作，设置30的判断区间）
                    }else if(endX-startX>30){
                        //三目运算判断当前图片已经为第一张，则上一张为数组内最后一张图片
                        index = index-1>=0?--index:images.length-1;
                        is.setInAnimation(getContext(),android.R.anim.fade_in);
                        is.setOutAnimation(getContext(),android.R.anim.fade_out);
                    }
                    //设置ImageSwitcher的图片资源
                    is.setImageResource(images[index]);
                    //调用方法设置圆点对应状态
                    setImageBackground(index);
                }
                return true;
            }
        });

        btn_AYBK = getActivity().findViewById(R.id.btn_AYBK);
        btn_SSZL = getActivity().findViewById(R.id.btn_SZZL);
        btn_AYWS = getActivity().findViewById(R.id.btn_AYWS);
        btn_SHFW = getActivity().findViewById(R.id.btn_SHFW);
        btn_AYHD = getActivity().findViewById(R.id.btn_AYHD);
        btn_TZCXZ = getActivity().findViewById(R.id.btn_TZCXZ);
        btn_ELEME = getActivity().findViewById(R.id.btn_ELEME);
        btn_FEIZHU = getActivity().findViewById(R.id.btn_FEIZHU);
        btn_GAODE = getActivity().findViewById(R.id.btn_GAODE);
        btn_MORE = getActivity().findViewById(R.id.btn_MORE);
        btn_MOJI = getActivity().findViewById(R.id.btn_MOJI);
        btn_YOUKU = getActivity().findViewById(R.id.btn_YOUKU);


        // 奥运百科
        btn_AYBK.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), QaaActivity.class);
            startActivity(intent);
        });
        // 数字之旅
        btn_SSZL.setMCallback(v -> {
        });
        // 生活服务
        btn_SHFW.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    LifeServiceActivity.class);
            startActivity(intent);
        });
        // 太子城小镇
        btn_TZCXZ.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    UniWebActivity.class);
            intent.putExtra("url",
                    "https://720yun.com/t/3e9judskOy4#scene_id=68800528");
            startActivity(intent);

        });
        // 奥运微视
        btn_AYWS.setMCallback(v -> {
            Log.i(TAG, "onStart: ayws");
//            MPNebula.startApp("0000000000000002");
            MPNebula.startApp("1234123412340001");
        });
        // 更多
        btn_MORE.setMCallback(v -> {
            Log.i(TAG, "onStart: more");
            ScanRequest request = new ScanRequest();
            request.setScanType(ScanRequest.ScanType.QRCODE);
            MPScan.startMPaasScanActivity(getActivity(), request, (b, intent) -> {
                if (null != intent && null != intent.getData()) {
                    MPTinyHelper.getInstance().launchIdeQRCode(intent.getData(), new Bundle());
                }
            });
        });
        // 饿了么
        btn_ELEME.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    UniWebActivity.class);
            intent.putExtra("url", "https://h5.ele.me");
            startActivity(intent);
        });
        // 飞猪
        btn_FEIZHU.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    UniWebActivity.class);
            intent.putExtra("url",
                    "https://market.m.taobao.com/app/trip/rx-home/pages/home?_projVer=1.0.2");
            startActivity(intent);
        });
        // 高德
        btn_GAODE.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    UniWebActivity.class);
            intent.putExtra("url", "https://m.amap.com");
            startActivity(intent);
        });
        // 墨迹天气
        btn_MOJI.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    UniWebActivity.class);
            intent.putExtra("url", "http://m.moji.com");
            startActivity(intent);
        });
        // 优酷
        btn_YOUKU.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    UniWebActivity.class);
            intent.putExtra("url", "https://www.youku.com");
            startActivity(intent);
        });
        // 数字之旅
        btn_SSZL.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    VolunteerDashboardActivity.class);
            Intent intent1 = new Intent(getActivity().getApplicationContext(),
                    DashboardActivity.class);
            startActivity(intent);
        });
        btn_AYHD.setMCallback(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                    NotificationTestActivity.class);
            startActivity(intent);
        });

    }

    private void initPnt() {
        point_layout = getActivity().findViewById(R.id.point_layout);
        int count = point_layout.getChildCount();//获取布局中圆点数量
        for(int i = 0; i < count; i++){
            //将布局中的圆点加入到圆点集合中
            points.add((ImageView)point_layout.getChildAt(i));
        }
        //设置第一张图片（也就是图片数组的0下标）的圆点状态为触摸实心状态
        points.get(0).setImageResource(R.drawable.grey_circle);
    }

    //设选中图片对应的导航原点的状态
    public void setImageBackground(int selectImage) {
        for (int i = 0; i < points.size(); i++){
            //如果选中图片的下标等于圆点集合中下标的id，则改变圆点状态
            if (i == selectImage) {
                points.get(i).setImageResource(R.drawable.grey_circle);
            } else {
                points.get(i).setImageResource(R.drawable.white_circle);
            }
        }
    }
    //实现ViewFactory的方法实例化imageView（这里未设置ImageView的属性）
    @Override
    public View makeView() {
        //实例化一个用于切换的ImageView视图
        ImageView iv = new ImageView(getActivity());
        //默认展示的第一个视图为images[0]
        iv.setImageResource(images[0]);
        return iv;
    }
}