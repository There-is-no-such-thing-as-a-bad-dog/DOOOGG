package com.example.dooogg;

import static java.lang.Thread.sleep;

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

public class view  extends AppCompatActivity {
    Call<List<action>> call;
    TextView tv_outPut;
    PieChart chart;
    Map<String, Integer> actions = new HashMap<>();

    void getData() {
        call = retrofit_client.getApiService().test_api_get();
        call.enqueue(new Callback<List<action>>() {
            @Override
            public void onResponse(Call<List<action>> call, Response<List<action>> response) {
                List<action> result = response.body();
                for(int i = 0; i < result.size(); i++) {
                    actions.put(result.get(i).getAction(), result.get(i).getTime());
                }
            }

            @Override
            public void onFailure(Call<List<action>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        // 데이터보다 chart가 먼저 렌더링 되는 상황 발생..
        getData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                chart = (PieChart)findViewById(R.id.piechart);
                chart.setUsePercentValues(true);
                chart.getDescription().setEnabled(false);
                chart.setExtraOffsets(5, 10, 5,5);
                chart.setDragDecelerationFrictionCoef(0.95f);

                chart.setDrawHoleEnabled(false);
                chart.setHoleColor(Color.WHITE);
                chart.setTransparentCircleRadius(61f);

                Description description = new Description();
                description.setText("강아지 행동 유형 분석");
                description.setTextSize(15);
                chart.setDescription(description);


                ArrayList yValues = new ArrayList();

                yValues.add(new PieEntry(actions.get("walk"),"walk"));
                yValues.add(new PieEntry(1f,"bark"));
                yValues.add(new PieEntry(actions.get("sit"),"sit"));
                yValues.add(new PieEntry(1f,"eat"));
                yValues.add(new PieEntry(1f,"lie"));
                yValues.add(new PieEntry(0f,"stand"));



                PieDataSet dataSet = new PieDataSet(yValues,"행동 유형");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.YELLOW);

                chart.setData(data);
            }
        });
        th.start();
    }
}
