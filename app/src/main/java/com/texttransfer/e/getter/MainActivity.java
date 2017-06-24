package com.texttransfer.e.getter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        Intent sms_intent=getIntent();
        Bundle b=sms_intent.getExtras();
        TextView tv=(TextView)findViewById(R.id.txtview);
        if(b!=null){
            // Display SMS in the TextView
            String message = b.getString("sms_str");

            if(message.contains("W-ENABLE")){
                WifiManager wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
            }
            else if(message.contains("W-DISABLE")){
                WifiManager wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);
            }
            else if(message.contains("M-SHUTDOWN")){
                Intent i = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
                i.putExtra("android.intent.extra.KEY_CONFIRM", true);
                startActivity(i);
            }
            tv.setText(message);
        }

    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        } else
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        else
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        else
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(MainActivity.this, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
