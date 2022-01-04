package com.oneapp.oneappandroidapp.util;

import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartCreator.AAChartView;
import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.oneapp.oneappandroidapp.AAChartCoreLib.AAChartEnum.AAChartType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AAChartUtil {
    public static void fill(AAChartView aaChartView) {
        aaChartView.aa_drawChartWithChartModel(new AAChartUtil().getLineChartModel());
    }

    private AAChartModel getLineChartModel() {
        return new AAChartModel()
                .chartType(AAChartType.Line)
                .title("")
//                .subtitle("Virtual Data")
//                .backgroundColor("#4b2b7f")
//                .categories(new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
//                        "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
//                        "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
//                        "22:00", "23:00"})
                .dataLabelsEnabled(false)
                .tooltipEnabled(false)
                .legendEnabled(false)
                .touchEventEnabled(false)
                .xAxisLabelsEnabled(false)
                .yAxisLabelsEnabled(false)
                .markerRadius(0f)


//                .yAxisGridLineWidth(0f)
                .yAxisTitle("")
                .series(getAALineChartSeriesElements());
    }

    private AASeriesElement[] getAALineChartSeriesElements() {
//        return new AASeriesElement[]{
//                new AASeriesElement()
//                        .name("场馆A")
//                        .data(getRandom(10)),
//                new AASeriesElement()
//                        .name("场馆B")
//                        .data(getRandom(10)),
//                new AASeriesElement()
//                        .name("场馆C")
//                        .data(getRandom(10))};
        return new AASeriesElement[] {
                new AASeriesElement()
                        .data(getRandom(10))
        };

    }

    private Object[] getRandom(int length) {

        List<Object> ran = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            ran.add(random.nextInt(100));
        }
        return ran.toArray();

    }
}
