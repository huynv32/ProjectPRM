package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class DestinationActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    //Class này là khi người dùng ấn vào thông báo ở trên màn hình
    //sẽ là màn hình thời tiết
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        mediaPlayer.stop();
    }
}