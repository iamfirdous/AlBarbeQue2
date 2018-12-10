package com.dev.firdous.al_barbeque;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.dev.firdous.al_barbeque.ui.HomeActivity;
import com.dev.firdous.al_barbeque.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkAvailable()) {

            mFirebaseAuth = FirebaseAuth.getInstance();

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    mUser = mFirebaseAuth.getCurrentUser();

                    if(mUser != null){
                        Intent restaurantIntent = new Intent(MainActivity.this, HomeActivity.class);
                        restaurantIntent.putExtra("fragmentToLoad", 2);
                        startActivity(restaurantIntent);
                        finish();
                    }
                    else{
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }
            };
        }
        else {
            Toast.makeText(this, "No active Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isNetworkAvailable())
            mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        Log.e("Hello", "Hi");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mAuthStateListener != null){
            if(isNetworkAvailable())
                mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isNetworkAvailable())
            mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}