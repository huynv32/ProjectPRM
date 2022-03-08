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
            CharSequence name = "báo thức";
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

    //Hàm này để hủy báo thức
//    Khi người dùng ấn Cancel thì sẽ hủy các báo thức
    private void cancelAlarm() {
        // sẽ gọi đến cái thông báo khi đến giờ báo thức
        Intent intent = new Intent(this, AlarmReceiver.class);
//        đưa vào 1 cái pendingintent
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        //Xóa báo thức
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Cancel Successfully", Toast.LENGTH_SHORT).show();
    }

    //Khi người dùng án vào button Set Time
    private void setAlarm() {

        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setTime(txtTime.getText().toString());
        alarmModel.setDescription(txtTime.getText().toString());
        alarmModel.setValue(true);
        //lưu giờ mới vào Database
        dao.insertDB(alarmModel);
        //Update lại list hiển thị list trên recycleView
        //Nhưng đang lỗi
        adapter.updateData(list);
        //set thời gian được thêm vào alarm để cài báo thức
//        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Gọi đến cái thông báo
        Intent intent = new Intent(this, AlarmReceiver.class);
        //đưa cái thông báo vào Pending iten
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //khởi tạo Alarm Manage
//        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        set thời gian cho Alarm. Cài đặt h báo thức
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();

    }

    private void ShowTimePicker() {
        //Show cái TimePicker
        //Có thể lấy được giwof hiện tại để đưa vào chỗ chọn thời gian
        //Đang để mặc định 12h AM
        picker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12).setMinute(0)
                .setTitleText("Select Alarm Time").build();
        //Show
        picker.show(getSupportFragmentManager(), "huynv");
        //Khi click vào nút ok trên time picker
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
                //Set thời gian vào calender để đưa vào Alarm báo thức
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
        //Khời tạo database
        dao = new Database(this, null, null, 1, null);
        //Khởi tạo các đối tượng trên view
        btnCancel = findViewById(R.id.btnCancel);
        txtTime = findViewById(R.id.selectTime);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnSetTime = findViewById(R.id.btnSetAlarm);
        rcvAlarm = findViewById(R.id.rcvArlarm);

        //Get dữ liệu từ DB
        list = new ArrayList<>();
        list = dao.selectAll();
        //Duyệt vòng for để tất cả các giờ báo thức có trong DB
        for (int i = 0; i < list.size(); i++) {
            AlarmModel a = list.get(i);
            String time = a.getTime();
            String[] timeSlip = time.split(":");
            //Khỏi tạo 1 lớp Calendar
            calendar = Calendar.getInstance();
            //SET Giờ cho calendar
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSlip[0].trim()));
            //SET Phút cho calendar
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeSlip[1].trim()));
//            Khởi tạo thư viện Alarm
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            //Set thông báo cho pending intent
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            //Set thời gian báo thức
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        //Gọi đến Adapter để set list cho recycleView
        adapter = new AlarmAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAlarm.setLayoutManager(linearLayoutManager);
        rcvAlarm.setAdapter(adapter);
        //Hiển thị giờ hiện tại
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
// Class để hiển thị giờ hiện tại 1 cách liên tục
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