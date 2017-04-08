package com.androidproj.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserInfo extends AppCompatActivity {
    public static int LOG_OUT_CODE = 4;
    Toolbar toolbar;
    User user;
    String myUid;
    private DatabaseReference dbRef;
    EditText et_Username, et_Birthday, et_Firstname, et_LastName, et_Country;
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        mappingView();
        btnDone.setVisibility(View.INVISIBLE);
        et_Birthday.setEnabled(false);
        et_Country.setEnabled(false);
        et_Username.setEnabled(false);
        et_Firstname.setEnabled(false);
        et_LastName.setEnabled(false);
    }

    protected void mappingView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_Edit_User);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        et_Username = (EditText) findViewById(R.id.et_EditUsername);
        et_Birthday = (EditText) findViewById(R.id.et_EditBirthday);
        et_Country = (EditText)  findViewById(R.id.et_EditCountry);
        et_Firstname = (EditText) findViewById(R.id.et_EditFirstName);
        et_LastName = (EditText) findViewById(R.id.et_EditLastName);
        btnDone = (Button) findViewById(R.id.btnEditCompleted);
        //btnLogOut = (Button) findViewById(R.id.btnLogOut);

        dbRef = FirebaseDatabase.getInstance().getReference();

        myUid = getIntent().getStringExtra("myuid");

        //Log.i("Uid to be edited: ", myUid);

        dbRef.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = dataSnapshot.getValue(User.class);
                if (myUid.equals(user.getUid())){
                    fillInfo();
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

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = et_Firstname.getText().toString();
                String lastName = et_LastName.getText().toString();
                String userName = et_Username.getText().toString();
                String birthday = et_Birthday.getText().toString();
                String country = et_Country.getText().toString();
                DatabaseReference ref = dbRef.child("Users").child(myUid);
                ref.child("firstnane").setValue(firstname);
                ref.child("lastname").setValue(lastName);
                ref.child("username").setValue(userName);
                ref.child("birthday").setValue(birthday);
                ref.child("country").setValue(country);
                et_Birthday.setEnabled(false);
                et_Country.setEnabled(false);
                et_Username.setEnabled(false);
                et_Firstname.setEnabled(false);
                et_LastName.setEnabled(false);
                btnDone.setVisibility(View.INVISIBLE);
                //btnLogOut.setVisibility(View.VISIBLE);
            }
        });

    }

    private void signOut() {
        // Firebase sign out
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    private void fillInfo() {
        et_Firstname.setText(user.getFirstname());
        et_LastName.setText(user.getLastname());
        et_Username.setText(user.getUsername());
        et_Birthday.setText(user.getBirthday());
        et_Country.setText(user.getCountry());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_edit_user_info, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnEditUserInfo:
                EditUserInfo();
//                btnLogOut.setVisibility(View.INVISIBLE);
                return true;
            case R.id.btnLogout:
                LogOut();
                return true;
            case R.id.btnChangePassword:
                ChangePassword();
                return true;
            case android.R.id.home:
                dbRef.goOffline();
                setResult(ListMesseger.LOAD_CONVERSATION_LIST, getIntent());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ChangePassword() {
        Intent intent = new Intent(EditUserInfo.this, ChangePassword.class);
        intent.putExtra("myUID", myUid);
        startActivity(intent);
    }

    private void EditUserInfo(){
        et_Firstname.setEnabled(true);
        et_LastName.setEnabled(true);
        et_Country.setEnabled(true);
        et_Username.setEnabled(true);
        et_Birthday.setEnabled(true);
        btnDone.setVisibility(View.VISIBLE);
    }

    private void LogOut(){
        SharedPreferences sharedPref = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("isLogged", 0);
        prefEditor.commit();
        signOut();
        Intent intent = new Intent(EditUserInfo.this, Login.class);
        startActivity(intent);
    }
}
