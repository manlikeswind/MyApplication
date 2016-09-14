package com.sunshine.cl.bindservicemessengerwork;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null){
                int code = msg.what;
                if (code == 0){
                    int arg = msg.arg1;
                    Log.e("===","==="+arg);
                    Messenger messenger_client = msg.replyTo;
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = 222;
                    try {
                        messenger_client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    Messenger messenger = new Messenger(handler);

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
