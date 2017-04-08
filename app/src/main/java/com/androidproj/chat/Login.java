package com.androidproj.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by NgocLong on 2/25/17.
 */

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin, btnReg;
    boolean isFirstLogin;
    String email, password;
    FirebaseAuth auth;
    Toolbar toolbar;
    CheckBox cbSavePassword;
    DatabaseReference data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng nhập");

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
                        if(task.isSuccessful()){
                            SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
                            SharedPreferences.Editor prefEditor = sharedPref.edit();
                            if (cbSavePassword.isChecked()){
                                prefEditor.putInt("isLogged", 1);
                                prefEditor.putString("curUID", auth.getCurrentUser().getUid());
                                prefEditor.commit();
                            }else{
                                prefEditor.putInt("isLogged", 0);
                                prefEditor.commit();
                            }
                            Toast.makeText(Login.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(Login.this, Main_Chat_Interface.class);
                            data.child("Users").child(auth.getCurrentUser().getUid()).child("active").setValue(true);
                            it.putExtra("myuid", auth.getCurrentUser().getUid());
                            startActivity(it);
                        }else{
                            Exception ex = task.getException();

                            Toast.makeText(Login.this, ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    protected void mappingView(){
        auth = FirebaseAuth.getInstance();
        etEmail = (EditText) findViewById(R.id.et_LoginEmail);
        etPassword = (EditText) findViewById(R.id.et_LoginPassword);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        btnReg = (Button) findViewById(R.id.btn_Reg);

        cbSavePassword = (CheckBox) findViewById(R.id.cbSavePassword);
data= FirebaseDatabase.getInstance().getReference();
        toolbar = (Toolbar) findViewById(R.id.toolbar_Login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
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

        // test chat group
        // this will delete if list user and list convertion complete
        if(resultCode == CreateGroupChat.CREATEGROUPCHAT){
            Bundle b = data.getBundleExtra("group");
            Intent it = new Intent(Login.this, Chat.class);
            it.putExtra("user", b);
            startActivity(it);
        }
    }
}
