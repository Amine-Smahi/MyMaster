package com.smahi.amine.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class SelectMasterActivity extends AppCompatActivity {


    Button chouseMaster;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_master);
        auth = FirebaseAuth.getInstance();
        chouseMaster = findViewById(R.id.master);
        //Glide.with(this).load(auth.getCurrentUser().getPhotoUrl()).into(displayavatar);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(SelectMasterActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "khrej", Toast.LENGTH_SHORT).show();
                }
            }
        };
        chouseMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), QuestionsActivity.class);
                startActivity(i);
            }
        });
    }


}