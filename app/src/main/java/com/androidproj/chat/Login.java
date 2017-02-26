package com.androidproj.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by NgocLong on 2/25/17.
 */

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    boolean isFirstLogin;
    String email, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mappingView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true){
                    Intent intent = new Intent(Login.this, GetInfo.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Login.this, GroupList.class);
                    startActivity(intent);
                }
            }
        });
    }

    protected void mappingView(){
        etEmail = (EditText) findViewById(R.id.et_LoginEmail);
        etPassword = (EditText) findViewById(R.id.et_LoginPassword);
        btnLogin = (Button) findViewById(R.id.btn_Login);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Register.REGISTER_ID && requestCode == Activity.RESULT_OK){
            email = data.getStringExtra("email");
            password = data.getStringExtra("password");
            data.getBooleanExtra("is_first_login", isFirstLogin);
            etEmail.setText(email);
            etPassword.setText(password);
        }

    }
}
