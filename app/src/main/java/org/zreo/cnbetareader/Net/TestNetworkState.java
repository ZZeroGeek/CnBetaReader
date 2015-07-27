package org.zreo.cnbetareader.Net;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import org.zreo.cnbetareader.R;

public class TestNetworkState extends  Activity {
       private IntentFilter intentFilter;
       private NetworkChangeReceiver networkChangeReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }
    @Override
            protected  void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        }
          class  NetworkChangeReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context,Intent intent){
                Toast.makeText(context,"network change",Toast.LENGTH_SHORT).show();


            }}

        }




