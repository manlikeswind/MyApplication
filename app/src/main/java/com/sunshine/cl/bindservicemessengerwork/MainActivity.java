package com.sunshine.cl.bindservicemessengerwork;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ServiceConnection conn;
    Messenger messenger_server;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null){
                int code = msg.what;
                if (code == 1){
                    int arg = msg.arg1;
                    Toast.makeText(MainActivity.this,""+arg,Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    Messenger messenger_client = new Messenger(handler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                messenger_server = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    public void sendMessage(View view){
        if (messenger_server != null){
            Message message = new Message();
            message.what = 0;
            message.arg1 = 111;
            message.replyTo = messenger_client;
            try {
                messenger_server.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(MainActivity.this,MyService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
