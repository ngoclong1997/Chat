package com.androidproj.chat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by NgocLong on 4/8/17.
 *
 */

public class ChangePassword extends Activity{
    public static int CHANGE_PASSWORD_SUCCESSFUL = 1;
    Button btnAccept, btnCancel;
    EditText etOldPass, etNewPass, etConfirmPass;
    String curUID, curPass, curEmail;
    TextView tvError;
    private DatabaseReference dbRef;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        curUID = getIntent().getStringExtra("myUID");
        mappingView();


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typedOldPassword = etOldPass.getText().toString();
                final String typedNewPassword = etNewPass.getText().toString();
                String typedConfirmPassword = etConfirmPass.getText().toString();

                if (!typedOldPassword.equals(curPass)){
                    tvError.setText("Mật khẩu cũ sai");
                }else{
                    if(!typedNewPassword.equals(typedConfirmPassword)){
                        tvError.setText("Mật khẩu mới không trùng khớp");
                    }else{
                        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(curEmail, curPass);
                        firebaseUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            firebaseUser.updatePassword(typedNewPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        dbRef.child("Users").child(curUID).child("email").setValue(curEmail);
                                                        dbRef.child("Users").child(curUID).child("password").setValue(typedNewPassword);
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                                                        builder.setMessage("Mật khẩu đã được đổi")
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                        LogOut();
                                                                        finish();
                                                                    }
                                                                }).show();
                                                    } else {
                                                        Exception e = task.getException();
                                                        Toast.makeText(ChangePassword.this,e.toString(),Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Exception e = task.getException();
                                            Toast.makeText(ChangePassword.this,e.toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void mappingView() {
        btnAccept = (Button) findViewById(R.id.btnCompleteChangePassword);
        btnCancel = (Button) findViewById(R.id.btnCancelChangePassword);
        etOldPass = (EditText) findViewById(R.id.et_OldPassword);
        etNewPass = (EditText) findViewById(R.id.et_NewPassword);
        etConfirmPass = (EditText) findViewById(R.id.et_ConfirmNewPassword);
        tvError = (TextView) findViewById(R.id.tvError);

        dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = dataSnapshot.getValue(User.class);
                if (curUID.equals(user.getUid())){
                    curPass = user.getPassword();
                    curEmail = user.getEmail();
                    //me = new User(user);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void signOut() {
        // Firebase sign out
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    private void LogOut(){
        SharedPreferences sharedPref = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("isLogged", 0);
        prefEditor.commit();
        //signOut();
    }
}
