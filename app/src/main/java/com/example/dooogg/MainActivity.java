package com.example.dooogg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    WebView video;
    View view;
    String url = "http://121.181.177.60:5000";
    Call<List<action>> call;
    Map<String, Integer> actions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video = (WebView) findViewById(R.id.wv);
        video.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
        video.getSettings().setUseWideViewPort(true);//wide viewport설정
        video.setWebViewClient(new WebViewClient());//웹뷰 요청 조작
        video.getSettings().setBuiltInZoomControls(true); // 확대버튼
        video.getSettings().setSupportZoom(true);
        video.setInitialScale(100);
        video.loadUrl(url);
        view = (View) findViewById(R.id.view);

        getData();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), view.class);
                intent.putExtra("walk", actions.get("walk"));
                intent.putExtra("bark", actions.get("bark"));
                intent.putExtra("sit", actions.get("sit"));
                intent.putExtra("eat", actions.get("eat"));
                intent.putExtra("lie", actions.get("lie"));
                intent.putExtra("stand", actions.get("stand"));
                startActivity(intent);
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
            }
        });

    }

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
}