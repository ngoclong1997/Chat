package com.androidproj.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnRegister, btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mappingView();
        // push();

        SharedPreferences sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        int number = sharedPref.getInt("isLogged", 0);
        if(number == 1) {
            String curUID = sharedPref.getString("curUID","");
            Intent it = new Intent(MainActivity.this, Main_Chat_Interface.class);
            it.putExtra("myuid", curUID);
            Log.i("current", curUID);
            startActivity(it);
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });



//        Intent intent = new Intent(MainActivity.this, Chat.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("key", "wYB4rL8s18fLjb1LTsHBwBOVLGh27H4I4CkDnBWWK4nRrJJzeh5wDWD3");
//        bundle.putString("myuid", "7H4I4CkDnBWWK4nRrJJzeh5wDWD3");
//        bundle.putString("name", "thai");
//        intent.putExtra("con", bundle);
//        startActivityForResult(intent, 1);

    }

    private void push() {
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();
        User x = new User("UCIX0A4Py6XCyF9lnicNAUkKITp1", "Hung", "111111", "", "", "", "VN", "", true, true, "");
        User y = new User("i1xz26Gm31VUoBcBT0Wg0GW6O3A3", "Long", "", "", "", "", "VN", "", true, true, "");
        User z = new User("kKlSpeaiGdMu94vXoFFTfksdRlV2", "Thai", "", "", "", "", "VN", "", true, true, "");
        data.child("Users").child("UCIX0A4Py6XCyF9lnicNAUkKITp1").setValue(x);
        data.child("Users").child("i1xz26Gm31VUoBcBT0Wg0GW6O3A3").setValue(y);
        data.child("Users").child("kKlSpeaiGdMu94vXoFFTfksdRlV2").setValue(z);
    }

    protected void mappingView(){
        btnLogin = (Button) findViewById(R.id.btnGotoLogin);
        btnRegister = (Button) findViewById(R.id.btnGotoRegister);
    }

}
