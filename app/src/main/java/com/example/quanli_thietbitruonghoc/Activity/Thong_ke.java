package com.example.quanli_thietbitruonghoc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.quanli_thietbitruonghoc.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class Thong_ke extends AppCompatActivity implements OnChartValueSelectedListener {
    private CombinedChart thongke;
    List<String> ngay_muonthietbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        thongke = (CombinedChart) findViewById(R.id.combinedChart);
        thongke.getDescription().setEnabled(false);
        thongke.setBackgroundColor(Color.WHITE);
        thongke.setDrawGridBackground(false);
        thongke.setDrawBarShadow(false);
        thongke.setHighlightFullBarEnabled(false);
        thongke.setOnChartValueSelectedListener(this);

        YAxis rightAxis = thongke.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = thongke.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        ngay_muonthietbi = new ArrayList<>();
        ngay_muonthietbi.add("4/4/2022");
        ngay_muonthietbi.add("5/4/2022");
        ngay_muonthietbi.add("6/4/2022");
        ngay_muonthietbi.add("7/4/2022");
        ngay_muonthietbi.add("8/4/2022");

        XAxis xAxis = thongke.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ngay_muonthietbi.get((int) value % ngay_muonthietbi.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) data_thongke());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        thongke.setData(data);
        thongke.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Tổng số lượng thiết bị: "
                        + e.getY()
                        + ", Ngày mượn : "
                        +ngay_muonthietbi.get(h.getDataIndex())
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {
    }

    private static LineDataSet data_thongke() {

        // dấu chấm trên vạch xanh số lượng
        LineData d = new LineData();
        int[] data = new int[]{1, 2, 2, 1, 3};

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < data.length; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Số lượng sử dụng thiết bị");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }


}