package com.communityuni.hocimplicitintent_phonesms;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtPhone, edtSms;
    Button btnDial, btnCall, btnSms1, btnSms2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDial();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyCall();
            }
        });
        btnSms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySms1();
            }
        });
        btnSms2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySms2();
            }
        });
    }

    private void xuLySms2() {
        SmsManager smsManager=SmsManager.getDefault();
        Intent msgSent=new Intent("ACTION_MSG_SENT");
        PendingIntent pendingMsgmSent=
                PendingIntent.getBroadcast(MainActivity.this,0,msgSent,0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int kq=getResultCode();
                if(kq== Activity.RESULT_OK)
                {
                    Toast.makeText(MainActivity.this,"đã gửi tới bạn thân ok",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"đã gửi thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        },new IntentFilter("ACTION_MSG_SENT"));

        smsManager.sendTextMessage(
                edtPhone.getText().toString(),
                null,
                edtSms.getText().toString(),pendingMsgmSent,null);
    }

    private void xuLySms1() {
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(
                edtPhone.getText().toString(),
                null,
                edtSms.getText().toString(),null,null);
    }

    private void xuLyCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + edtPhone.getText().toString());
        intent.setData(uri);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void xuLyDial() {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        Uri uri=Uri.parse("tel:"+edtPhone.getText().toString());
        intent.setData(uri);
        startActivity(intent);
    }

    private void addControls() {
        edtPhone= (EditText) findViewById(R.id.edtPhone);
        edtSms= (EditText) findViewById(R.id.edtSms);

        btnDial= (Button) findViewById(R.id.btnDial);
        btnCall= (Button) findViewById(R.id.btnCall);
        btnSms1= (Button) findViewById(R.id.btnSms1);
        btnSms2= (Button) findViewById(R.id.btnSms2);
    }
}
