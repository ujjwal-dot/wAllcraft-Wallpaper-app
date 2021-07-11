package com.ujjwal.wallcraft.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ujjwal.wallcraft.MainActivity;
import com.ujjwal.wallcraft.R;

public class Splashscreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


//                   if(GoogleSignIn.getLastSignedInAccount(getApplicationContext())==null) {
//                       startActivity(new Intent(Splashscreen.this, GoogleLoginActivity.class));
//                       finish();
//                   }else {
                       startActivity(new Intent(Splashscreen.this, MainActivity.class));
//                   }
            }
        },3500);
    }



}