package com.oneapp.oneappandroidapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import com.alipay.mobile.antui.basic.AUButton;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartCreator.AAChartView;
import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartEnum.AAChartType;
import com.oneapp.oneappandroidapp.R;
import com.oneapp.oneappandroidapp.view.ImgTxtButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;


/**
 * Notice: add one when getting `month`, but do not add one when setting `month`
 */
public class VolunteerDashboardActivity extends AppCompatActivity {

    private static final String TAG = "MTAG_VolunteerDbdAct";
    private AnyChartView anyChartView;
    private LineChart lineChart;
    private AAChartView aaLineChart;
    private AAChartView columnChart;
    private AUButton btnSelectDate;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImgTxtButton btn1;
    private ImgTxtButton btn2;
    private ImgTxtButton btn3;

    private boolean isAutoRefresh = true;

    private Calendar date;

    private static final int REFRESH = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
//                updateAALineChart();
                updateColumnChart();
            }
        }
    };


    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_volunteer_dashboard);
        lineChart = findViewById(R.id.mp_line_chart);
        anyChartView = findViewById(R.id.chart);
        aaLineChart = findViewById(R.id.line_chart);
        columnChart = findViewById(R.id.column_chart);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btn1 = findViewById(R.id.vd_btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);


        btnSelectDate.setOnClickListener(v -> showDialog());
        date = new GregorianCalendar();
        // 加一对齐
        btnSelectDate.setText(String.format("%d年%d月%d日", date.get(Calendar.YEAR),
                date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH)));

        showMPLineChart();
        showAnyChart();
        showAALineChart();
        showColumnChart();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showAALineChart();
            swipeRefreshLayout.setRefreshing(false);
        });
        btn1.setMCallback(v -> {
            Log.d(TAG, "onCreate: btn1 callback");
            final AlertDialog dialog = new AlertDialog.Builder(
                    VolunteerDashboardActivity.this).create();
            View dialogView = View.inflate(VolunteerDashboardActivity.this,
                    R.layout.view_volunteer_work_diary, null);
            dialog.setView(dialogView);
            dialog.show();
            final Button btnNext = findViewById(R.id.btn_next);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        });

        new Thread(() -> {
            while (isAutoRefresh) {
                try {
                    Thread.sleep(3000);
                    Message msg = Message.obtain();
                    msg.what = REFRESH;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }

    private void showMPLineChart() {
        LineData lineData = new LineData();
        lineData.addDataSet(new LineDataSet(getEntries(1),"场馆A"));
        lineData.addDataSet(new LineDataSet(getEntries(1),"场馆B"));
        lineData.addDataSet(new LineDataSet(getEntries(1),"场馆C"));
        lineChart.setData(lineData);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(24);
        xAxis.setAxisMinimum(0);
//        lineChart.setDragEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.enableGridDashedLine(5, 1, 0);
        lineChart.invalidate();
    }

    private List<Entry> getEntries(int len) {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0,7));
        entries.add(new Entry(1,10));
        entries.add(new Entry(2,12));
        entries.add(new Entry(3,6));
        entries.add(new Entry(4,3));
        return entries;
    }

    private void showAnyChart() {

        anyChartView.setProgressBar(findViewById(R.id.progress));
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("各场馆正在工作的志愿者数量分布");

        cartesian.yAxis(0).title("正在工作的志愿者数量");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("00:00", 0, 5, 0));
        seriesData.add(new CustomDataEntry("01:00", 0, 5, 0));
        seriesData.add(new CustomDataEntry("02:00", 0, 5, 0));
        seriesData.add(new CustomDataEntry("03:00", 0, 5, 0));
        seriesData.add(new CustomDataEntry("04:00", 0, 5, 0));
        seriesData.add(new CustomDataEntry("05:00", 0, 5, 18));
        seriesData.add(new CustomDataEntry("06:00", 16, 5, 21));
        seriesData.add(new CustomDataEntry("07:00", 18, 5, 20));
        seriesData.add(new CustomDataEntry("08:00", 13, 5, 19));
        seriesData.add(new CustomDataEntry("09:00", 12, 50, 14));
        seriesData.add(new CustomDataEntry("10:00", 3, 50, 9));
        seriesData.add(new CustomDataEntry("11:00", 4, 45, 5));
        seriesData.add(new CustomDataEntry("12:00", 6, 32, 5));
        seriesData.add(new CustomDataEntry("13:00", 9, 20, 4));
        seriesData.add(new CustomDataEntry("14:00", 11, 25, 4));
        seriesData.add(new CustomDataEntry("15:00", 13, 48, 1));
        seriesData.add(new CustomDataEntry("16:00", 14, 48, 5));
        seriesData.add(new CustomDataEntry("17:00", 16, 47, 6));
        seriesData.add(new CustomDataEntry("18:00", 18, 45, 8));
        seriesData.add(new CustomDataEntry("19:00", 17, 20, 10));
        seriesData.add(new CustomDataEntry("20:00", 16, 20, 11));
        seriesData.add(new CustomDataEntry("21:00", 14, 20, 12));
        seriesData.add(new CustomDataEntry("22:00", 15, 10, 10));
        seriesData.add(new CustomDataEntry("23:00", 12, 5, 8));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("场馆A");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("场馆B");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("场馆C");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        anyChartView.setChart(cartesian);
    }

    private AASeriesElement[] getAALineChartSeriesElements() {
        return new AASeriesElement[]{
                new AASeriesElement()
                        .name("场馆A")
                        .data(getRandom(date)),
                new AASeriesElement()
                        .name("场馆B")
                        .data(getRandom(date)),
                new AASeriesElement()
                        .name("场馆C")
                        .data(getRandom(date))};
    }

    private AAChartModel getLineChartModel() {
        return new AAChartModel()
                .chartType(AAChartType.Line)
                .title("各场馆正在工作的志愿者数量分布")
//                .subtitle("Virtual Data")
//                .backgroundColor("#4b2b7f")
                .categories(new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
                        "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
                        "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
                        "22:00", "23:00"})
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .yAxisTitle("")
                .series(getAALineChartSeriesElements());
    }

    private void showAALineChart() {
        aaLineChart.aa_drawChartWithChartModel(getLineChartModel());
    }

    private void updateAALineChart() {
        aaLineChart.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(
                getAALineChartSeriesElements(), true
        );
    }


    private AASeriesElement[] getColumnChartSeriesElements() {
        return new AASeriesElement[] {
                new AASeriesElement()
                        .name("场馆引导")
                        .data(getRandom(3)),
                new AASeriesElement()
                        .name("设备监测与维护")
                        .data(getRandom(3)),
                new AASeriesElement()
                        .name("治安与应急")
                        .data(getRandom(3))
        };
    }

    private AAChartModel getColumnChartAAChartModel() {
        return new AAChartModel()
                .chartType(AAChartType.Column)
                .title("各场馆正在工作的志愿者实时数量分布")
                .categories(new String[]{"场馆A", "场馆B", "场馆C"})
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .yAxisTitle("")
                .series(getColumnChartSeriesElements());
    }

    private void showColumnChart() {
        columnChart.aa_drawChartWithChartModel(getColumnChartAAChartModel());
    }

    private void updateColumnChart() {
        columnChart.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(
                getColumnChartSeriesElements(), true
        );
    }

    private Object[] getRandom(int length) {

        List<Object> ran = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            ran.add(random.nextInt(100));
        }
        return ran.toArray();

    }

    private Object[] getRandom(Calendar calendar) {

        Log.d(TAG, "getRandom: calendar" + calendar.get(Calendar.YEAR) + " "
                + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.DAY_OF_MONTH));
        Calendar today = new GregorianCalendar();
        Log.d(TAG, "getRandom: today" + today.get(Calendar.YEAR) + " "
                + today.get(Calendar.MONTH) + " " + today.get(Calendar.DAY_OF_MONTH));

        if (calendar == null) {
            // 针对0点的对齐
            return getRandom(today.get(Calendar.HOUR_OF_DAY) + 1);
        }

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == (today.get(Calendar.MONTH))
                && calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
            Log.e(TAG, "getRandom: calendar is today");
            return getRandom(today.get(Calendar.HOUR_OF_DAY) + 1);
        } else return getRandom(24);
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                VolunteerDashboardActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(VolunteerDashboardActivity.this,
                R.layout.view_date_picker, null);
        dialog.setView(dialogView);
        dialog.show();

        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker_dialog);
        datePicker.setMaxDate(new Date().getTime());
        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            Log.d(TAG, "showDialog: year: " + year + ", monthOfYear: "
                    + monthOfYear + ", dayOfMonth: " + dayOfMonth);
            date = new GregorianCalendar();
            // monthOfYear从0开始，加一对齐
            date.set(year, monthOfYear, dayOfMonth);
            btnSelectDate.setText(String.format(
                    "%d年%d月%d日", year, monthOfYear+1, dayOfMonth));
            showAALineChart(); // todo 增加日期参数，根据日期读取后台数据
            dialog.dismiss();
        });

    }
}