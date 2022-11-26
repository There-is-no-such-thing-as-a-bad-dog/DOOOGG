package com.example.dooogg;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class view extends AppCompatActivity {
    PieChart chart;
    Button btn;

    protected void onCreate(Bundle savedInstanceState) {
        // 데이터보다 chart가 먼저 렌더링 되는 상황 발생..
//        getData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        btn = (Button) findViewById(R.id.bt);
        btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
        });

        chart = (PieChart) findViewById(R.id.piechart);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleRadius(61f);

        Description description = new Description();
        description.setText("강아지 행동 유형 분석");
        description.setTextSize(15);
        chart.setDescription(description);


        ArrayList yValues = new ArrayList();

        Intent intent = getIntent();
        int walk = intent.getIntExtra("walk", 0);
        int bark = intent.getIntExtra("bark", 0);
        int sit = intent.getIntExtra("sit", 0);
        int eat = intent.getIntExtra("eat", 0);
        int lie = intent.getIntExtra("lie", 0);
        int stand = intent.getIntExtra("stand", 0);

        yValues.add(new PieEntry(walk, "walk"));
        yValues.add(new PieEntry(bark, "bark"));
        yValues.add(new PieEntry(sit, "sit"));
        yValues.add(new PieEntry(eat, "eat"));
        yValues.add(new PieEntry(lie, "lie"));
        yValues.add(new PieEntry(stand, "stand"));


        PieDataSet dataSet = new PieDataSet(yValues, "행동 유형");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        chart.setData(data);
    }

}
