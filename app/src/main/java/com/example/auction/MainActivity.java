package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv_name, tv_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingView();
        showUserInformation();

    }

    private void bindingView() {
        imageView = findViewById(R.id.img_avatar);
        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_name);

    }

    private void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photo = user.getPhotoUrl();
        if (name == null) {
            tv_name.setVisibility(View.GONE);
        } else {
            tv_name.setVisibility(View.VISIBLE);
            tv_name.setText(name);
        }
        tv_name.setText(name);
        tv_email.setText(email);

        Glide.with(this).load(photo).error(R.drawable.ic_avatar_default).into(imageView);

    }

}