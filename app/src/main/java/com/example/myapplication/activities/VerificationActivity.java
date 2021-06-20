package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    private EditText otp;
    Button verify;
   private TextView resend;
   private MKLoader loader;
   private String number,id;
   private FirebaseAuth mAuth;

    private static final String TAG = "VerificationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        otp=findViewById(R.id.otp);
        verify=findViewById(R.id.submit);
        resend=findViewById(R.id.resend);

        mAuth=FirebaseAuth.getInstance();
        number=getIntent().getStringExtra("number");



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(VerificationActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                }else if(otp.getText().toString().replace("","").length()!=6){
                    Toast.makeText(VerificationActivity.this, "Enter the valid six digit OTP", Toast.LENGTH_SHORT).show();
                }else {

                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(id,otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);

                }

            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });
    }




    private void sendVerificationCode(){
        Log.d(TAG, "sendVerificationCode: starts");
        new CountDownTimer(60000,100){
            @Override
            public void onTick(long millisUntilFinished) {
                resend.setText("" + millisUntilFinished/1000);
                resend.setEnabled(false);

            }

            @Override
            public void onFinish() {
                resend.setText("resend");
                resend.setEnabled(true);

            }
        }.start();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks( new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@Nullable PhoneAuthCredential phoneAuthCredential) {
                                Log.d(TAG, "onVerificationCompleted: starts");
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                                Log.d(TAG, "onVerificationCompleted: ends");

                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(VerificationActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent( @Nullable String id,
                                                    @Nullable PhoneAuthProvider.ForceResendingToken token) {
                                VerificationActivity.this.id=id;

                               
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        Log.d(TAG, "signInWithPhoneAuthCredential: starts");

            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete( @Nullable Task<AuthResult> task) {
                            Log.d(TAG, "onComplete: starts");





                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(VerificationActivity.this, MainActivity.class));
                                    finish();

                                    FirebaseUser user = task.getResult().getUser();
                                    // Update UI
                                } else {
                                    // Sign in failed, display a message and update the UI
                                    Toast.makeText(VerificationActivity.this, "Verification Failed!Try again", Toast.LENGTH_SHORT).show();
                                }
                            Log.d(TAG, "onComplete: ends");

                        }


                    });
        }

    }

















