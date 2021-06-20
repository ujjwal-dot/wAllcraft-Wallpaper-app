package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText number;
      Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        countryCodePicker=findViewById(R.id.ccp);
        number=findViewById(R.id.number);
        next=findViewById(R.id.next);

        countryCodePicker.registerPhoneNumberTextView(number);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(number.getText().toString())){
                    Toast.makeText(LoginActivity.this,"Please enter your Phone no.",Toast.LENGTH_SHORT).show();
                }
                else if(number.getText().toString().replace("","").length()!=10) {
                    Toast.makeText(LoginActivity.this,"Please enter valid phone number",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent(LoginActivity.this,VerificationActivity.class);
                    intent.putExtra("number",countryCodePicker.getFullNumberWithPlus()
                    .replace("",""));
                    startActivity(intent);
                    finish();

                }


            }
        });
    }
}