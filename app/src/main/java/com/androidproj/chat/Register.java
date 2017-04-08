package com.androidproj.chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by NgocLong on 2/25/17.
 */


public class Register extends AppCompatActivity {

    public static int REGISTER_ID = 1;
    EditText etUsername, etEmail, etPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    User user;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Đăng ký");

        mappingView();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Exception exception = task.getException();
                                    Toast.makeText(Register.this, exception.getMessage().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    creatUser(task.getResult().getUser());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                    builder.setMessage("Đăng ký thành công")
                                            .setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent myIntent = new Intent(Register.this, Login.class);
                                                    myIntent.putExtra("email", etEmail.getText().toString());
                                                    myIntent.putExtra("password", etPassword.getText().toString());
                                                    myIntent.putExtra("is_first_login", true);
                                                    setResult(RESULT_OK, myIntent);
                                                    startActivityForResult(myIntent, REGISTER_ID);
                                                }
                                            }).show();
                                }
                            }
                        });
            }
        });
    }

    protected void mappingView(){
        etUsername = (EditText) findViewById(R.id.et_username);
        etEmail = (EditText) findViewById(R.id.et_Email);
        etPassword = (EditText) findViewById(R.id.et_Password);
        btnRegister = (Button) findViewById(R.id.btn_Register);

        toolbar = (Toolbar) findViewById(R.id.toolbar_Register);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void creatUser(UserInfo userInfo){
        user = new User(userInfo.getUid(), etUsername.getText().toString(), etPassword.getText().toString(), null, null, null, null, etEmail.getText().toString(), true, true, null);
        mDatabase.child("Users").child(user.getUid()).setValue(user);
    }
}
