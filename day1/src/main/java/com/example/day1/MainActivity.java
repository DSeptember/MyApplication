package com.example.day1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        int what = msg.what;
        if(what==1){
            String out= (String) msg.obj;
            Gson gson = new Gson();
        }

    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Tag","8888822ddd3");
        getData();
    }



    public void getData(){
     new Thread(){
         @Override
         public void run() {

             try {

                 URL url=new URL("http://www.meirixue.com/api.php?c=index&a=index");
                 HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                 connection.setRequestMethod("GET");
                 connection.setConnectTimeout(3000);
                 int responseCode = connection.getResponseCode();
                 if(responseCode == 200){
                     ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                     InputStream inputStream = connection.getInputStream();
                     byte[] bytes=new byte[1024];
                     int read=0;
                     while ((read=inputStream.read(bytes))!=-1){
                         outputStream.write(bytes,0,read);
                     }
                     Message msg = new Message();
                     msg.what=1;
                     msg.obj=outputStream.toString();
                     handler.sendMessage(msg);
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }.start();

    }
}
