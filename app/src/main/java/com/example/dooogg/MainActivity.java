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

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    WebView video;
    //    Socket mSocket;
    View view;
    String url = "http://180.189.89.108:5000";
//    {
//        try {
//            mSocket = IO.socket(url);
//         } catch (URISyntaxException e) {
//            System.out.println(e);
//        }
//    }

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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), view.class);
                startActivity(intent);
            }
        });

//        Intent it = new Intent(this,ReceiverService.class);
//        startService(it);
        //startForegroundService(it);

        /*
        // 소켓 통신
        mSocket.connect();
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i("연결여부", "성공");
            }
        });
        mSocket.emit("alarm");
        mSocket.on("alarm", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i("들어온 값 : ", args[0].toString());
            }
        });
         */
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
            }
        });

    }
}