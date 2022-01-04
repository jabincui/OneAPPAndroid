package com.oneapp.oneappandroidapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartCreator.AAChartView;
import com.oneapp.oneappandroidapp.R;
import com.oneapp.oneappandroidapp.model.DashboardItem;
import com.oneapp.oneappandroidapp.util.AAChartUtil;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private List<DashboardItem> dashboardItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        RecyclerView recyclerView = findViewById(R.id.dashboard_recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        initDashboardItems();
        DashboardAdapter adapter = new DashboardAdapter(dashboardItems);
        recyclerView.setAdapter(adapter);
    }

    private void initDashboardItems() {
        dashboardItems = new ArrayList<>();
        dashboardItems.add(new DashboardItem("总注册用户数", "123,000,000", ""));
    }

    static class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<DashboardItem> dashboardItems;

        static class VH extends RecyclerView.ViewHolder {
            TextView titleView;
            TextView dataView;
            TextView remarkView;
            AAChartView chartView;
            public VH(@NonNull View itemView) {
                super(itemView);
                titleView = itemView.findViewById(R.id.card_title);
                dataView = itemView.findViewById(R.id.card_data);
                remarkView = itemView.findViewById(R.id.card_remark);
                chartView = itemView.findViewById(R.id.card_chart);
            }
        }

        public DashboardAdapter(List<DashboardItem> dashboardItems) {
            this.dashboardItems = dashboardItems;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dashboard_card, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DashboardItem item = dashboardItems.get(position);
            inject(((VH) holder).titleView, item.getTitle());
            inject(((VH) holder).dataView, item.getData());
            inject(((VH) holder).remarkView, item.getRemark());
            AAChartUtil.fill(((VH) holder).chartView);
        }

        private void inject(TextView tv, String content) {
            if (content == null || content.equals("")) {
                tv.setVisibility(View.GONE);
            } else {
                tv.setText(content);
            }
        }

        @Override
        public int getItemCount() {
            return dashboardItems.size();
        }

    }
}