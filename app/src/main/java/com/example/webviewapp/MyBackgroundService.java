package com.example.webviewapp;


import android.app.Service;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.webkit.URLUtil;

import androidx.annotation.Nullable;

public class MyBackgroundService extends Service {

    private boolean flag=true;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        if(flag){
            final ClipboardManager clipboardManager=(ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    String str=clipboardManager.getText().toString();
                    if(URLUtil.isValidUrl(str)){
                        //Toast.makeText(getBaseContext(),"copied",Toast.LENGTH_LONG).show();
                        //Intent intent1=new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                        //startActivity(intent1);

                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_MAIN);
                        i.setAction("com.example.webviewapp.URL_COPIED");
                        i.addCategory(Intent.CATEGORY_LAUNCHER);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                        i.setComponent(new ComponentName(getApplicationContext().getPackageName(), MainActivity.class.getName()));
                        i.putExtra("url",str);
                        //sendBroadcast(i);
                        startActivity(i);
                    }
                }
            });
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag=false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
