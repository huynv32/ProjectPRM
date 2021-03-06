package com.example.auction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.database.Database;
import com.example.auction.model.AlarmModel;
import com.example.auction.model.OnitemClick;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarm extends AppCompatActivity implements OnitemClick {
    TextView txtTime;
    private Button btnSelectTime;
    private Button btnCancel;
    private Button btnSetTime;
    com.example.auction.database.Database dao;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    RecyclerView rcvAlarm;
    private List<AlarmModel> list;
    private AlarmAdapter adapter;


    private MaterialTimePicker picker;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        try {
            bindingView();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bindingAction();
        createNotificationChange();
    }

    private void createNotificationChange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "b??o th???c";
            String description = "Chanel for alrm ";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("huynv", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void bindingAction() {
        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowTimePicker();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });
    }

    //H??m n??y ????? h???y b??o th???c
//    Khi ng?????i d??ng ???n Cancel th?? s??? h???y c??c b??o th???c
    private void cancelAlarm() {
        // s??? g???i ?????n c??i th??ng b??o khi ?????n gi??? b??o th???c
        Intent intent = new Intent(this, AlarmReceiver.class);
//        ????a v??o 1 c??i pendingintent
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        //X??a b??o th???c
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Cancel Successfully", Toast.LENGTH_SHORT).show();
    }

    //Khi ng?????i d??ng ??n v??o button Set Time
    private void setAlarm() {

        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setTime(txtTime.getText().toString());
        alarmModel.setDescription(txtTime.getText().toString());
        alarmModel.setValue(true);
        //l??u gi??? m???i v??o Database
        dao.insertDB(alarmModel);
        //Update l???i list hi???n th??? list tr??n recycleView
        //Nh??ng ??ang l???i
        adapter.updateData(list);
        //set th???i gian ???????c th??m v??o alarm ????? c??i b??o th???c
//        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //G???i ?????n c??i th??ng b??o
        Intent intent = new Intent(this, AlarmReceiver.class);
        //????a c??i th??ng b??o v??o Pending iten
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //kh???i t???o Alarm Manage
//        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        set th???i gian cho Alarm. C??i ?????t h b??o th???c
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();

    }

    private void ShowTimePicker() {
        //Show c??i TimePicker
        //C?? th??? l???y ???????c giwof hi???n t???i ????? ????a v??o ch??? ch???n th???i gian
        //??ang ????? m???c ?????nh 12h AM
        picker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12).setMinute(0)
                .setTitleText("Select Alarm Time").build();
        //Show
        picker.show(getSupportFragmentManager(), "huynv");
        //Khi click v??o n??t ok tr??n time picker
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picker.getMinute() < 10) {
                    txtTime.setText(picker.getHour() + " : 0" + picker.getMinute());
                }
                if (picker.getHour() < 10) {
                    txtTime.setText("0" + picker.getHour() + " : " + picker.getMinute());
                }
                if (picker.getHour() < 10 && picker.getMinute() < 10) {
                    txtTime.setText("0" + picker.getHour() + " : 0" + picker.getMinute());
                } else {
                    txtTime.setText(picker.getHour() + " : " + picker.getMinute());
                }
                //Set th???i gian v??o calender ????? ????a v??o Alarm b??o th???c
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void bindingView() throws InterruptedException {
        //Kh???i t???o database
        dao = new Database(this, null, null, 1, null);
        //Kh???i t???o c??c ?????i t?????ng tr??n view
        btnCancel = findViewById(R.id.btnCancel);
        txtTime = findViewById(R.id.selectTime);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnSetTime = findViewById(R.id.btnSetAlarm);
        rcvAlarm = findViewById(R.id.rcvArlarm);

        //Get d??? li???u t??? DB
        list = new ArrayList<>();
        list = dao.selectAll();
        //Duy???t v??ng for ????? t???t c??? c??c gi??? b??o th???c c?? trong DB
        for (int i = 0; i < list.size(); i++) {
            AlarmModel a = list.get(i);
            String time = a.getTime();
            String[] timeSlip = time.split(":");
            //Kh???i t???o 1 l???p Calendar
            calendar = Calendar.getInstance();
            //SET Gi??? cho calendar
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSlip[0].trim()));
            //SET Ph??t cho calendar
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeSlip[1].trim()));
//            Kh???i t???o th?? vi???n Alarm
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            //Set th??ng b??o cho pending intent
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            //Set th???i gian b??o th???c
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        //G???i ?????n Adapter ????? set list cho recycleView
        adapter = new AlarmAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAlarm.setLayoutManager(linearLayoutManager);
        rcvAlarm.setAdapter(adapter);
        //Hi???n th??? gi??? hi???n t???i
        HourCurrent hourCurrent = new HourCurrent();
        hourCurrent.start();
    }

    @Override
    public void onItemClick(String id, boolean check) {
        if (check == true) {
            dao.UpdateClock(Integer.parseInt(id), 0);
        } else {
            dao.UpdateClock(Integer.parseInt(id), 1);
        }
        Toast.makeText(this, "On click" + id, Toast.LENGTH_SHORT).show();
        list = dao.selectAll();
        adapter = new AlarmAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAlarm.setLayoutManager(linearLayoutManager);
        rcvAlarm.setAdapter(adapter);
    }
// Class ????? hi???n th??? gi??? hi???n t???i 1 c??ch li??n t???c
    @RequiresApi(api = Build.VERSION_CODES.O)
    class HourCurrent extends Thread {
        public void run() {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                txtTime.setText(dtf.format(now));
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}