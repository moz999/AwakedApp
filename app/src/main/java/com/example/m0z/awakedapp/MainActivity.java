package com.example.m0z.awakedapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //アラームマネージャ
    private AlarmManager alarmManager;
    //時間設定クラス
    private TimePicker timePicker;
    //時間設定
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button alarm_on = findViewById(R.id.alert_on);
        final Button alarm_off = findViewById(R.id.alert_off);

        //ONボタンクリックイベント
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm_on.setEnabled(false);
                alarm_off.setEnabled(true);
                //アラート設定
                startAlarm();
            }
        });

        //OFFボタンクリックイベント
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm_off.setEnabled(false);
                alarm_on.setEnabled(true);
                //アラート解除
                stopAlarm();
            }
        });
    }

    private void startAlarm(){
        Log.d("startAlarm", "startAlarm()");

        //アラームマネージャを使用するには必要な作業です
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //時間を設定
        calendar = Calendar.getInstance();

        //現在日付の設定
        calendar.setTimeInMillis(System.currentTimeMillis());

        //指定した日付の設定
        timePicker = findViewById(R.id.timePicker);
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour()); //API24以降はgetHour()を使用
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);

        //アラームマネージャの設定
        alarmManager.set(AlarmManager.RTC_WAKEUP, //どのタイミングでPendingIntentを実行するか
                         calendar.getTimeInMillis(), //どれほど経過したタイミングで呼ぶか
                         getPendingIntent());   //何を呼ぶか

    }

    private void stopAlarm(){
        Log.d("stopAlarm", "stopAlarm()");

        alarmManager.cancel(getPendingIntent());
    }

    private PendingIntent getPendingIntent(){
        //サービスの設定
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0);
        return pendingIntent;
    }
}
