package com.example.calorius;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity {

    private Button mLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        mLogout = (Button) findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(LogoutActivity.this,LoginActivity.class));
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(60);
                Toast.makeText(LogoutActivity.this,"Logout realizado",
                        Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getApplicationContext()
                        .getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
            }
        });    }
}
