package com.example.auction;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;
    EditText edtEmailRegister;
    EditText edtPasswordRegister;
    EditText edtRePasswordRegister;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegister();
            }
        });

    }

    private void onClickLogin() {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }

    private void onClickRegister() {
//        Khởi tạo FireBase Auth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = edtEmailRegister.getText().toString().trim();
        String password = edtPasswordRegister.getText().toString().trim();

        progressDialog.show();
//Gọi đến phương thức register của firebase
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Đăng khí thành công sẽ trra về MainActivity
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });

    }

    private void bindingView() {
        btnLogin = findViewById(R.id.register_btnReturnLogin);
        btnRegister = findViewById(R.id.register_btnRegister);
        edtEmailRegister = findViewById(R.id.register_email);
        edtPasswordRegister = findViewById(R.id.register_password);
        edtRePasswordRegister = findViewById(R.id.register_repassword);
        progressDialog = new ProgressDialog(this);

    }
}