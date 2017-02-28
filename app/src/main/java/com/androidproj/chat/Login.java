package com.androidproj.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by NgocLong on 2/25/17.
 */

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    boolean isFirstLogin;
    String email, password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mappingView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                try{
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                }catch (Exception e){
                    Toast.makeText(Login.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Bundle b = new Bundle();
                        b.putString("key", "Ys2Nbtz0eKadn9YcnKweFYwuemu2" + auth.getCurrentUser().getUid().toString());
                        b.putString("uid", auth.getCurrentUser().getUid().toString());
                        b.putString("chatWithName", "ngoclong_1997");
                        Intent it = new Intent(Login.this, Chat.class);
                        it.putExtra("user", b);
                        startActivity(it);
                    }
                });
            }
        });
    }

    protected void mappingView(){
        auth = FirebaseAuth.getInstance();
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
