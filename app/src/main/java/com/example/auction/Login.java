package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.auction.model.User;

public class Login extends AppCompatActivity {
    private LinearLayout layoutRegister;

    Button btnLogin;
    EditText edtEmail;
    EditText edtPassword;
    TextView txtRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingView();
        bindingAction();

    }

    private void bindingAction() {
//        btnLogin.setOnClickListener(view -> Login(view));
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);

            }
        });
    }

    private void Login(View view) {
        String valueEmail = edtEmail.getText().toString();
        String valuePassword = edtPassword.getText().toString();
        User user = new User();
        user.setPassword(valuePassword);
        user.setEmail(valueEmail);

    }

    private void bindingView() {
        txtRegister= (TextView) findViewById(R.id.txtRegister);
        layoutRegister = findViewById(R.id.layout_register);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

    }
}