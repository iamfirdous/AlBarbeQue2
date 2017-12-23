package com.dev.firdous.al_barbeque.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.firdous.al_barbeque.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager mManager;
    static HomeActivity homeActivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_restaurants:
                    mManager.beginTransaction().replace(R.id.content, new SelectRestaurantFragment()).commit();
                    return true;
                case R.id.navigation_explore:
                    mManager.beginTransaction().replace(R.id.content, new ExploreFragment()).commit();
                    return true;
                case R.id.navigation_cart:
                    mManager.beginTransaction().replace(R.id.content, new CartFragment()).commit();
                    return true;
                case R.id.navigation_account:
                    mManager.beginTransaction().replace(R.id.content, new AccountFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivity = this;
        mManager = getSupportFragmentManager();
        BottomNavigationView navigation = findViewById(R.id.navigation);

        int frgToLd = getIntent().getExtras().getInt("fragmentToLoad");

        switch (frgToLd) {
            case 2:
                mManager.beginTransaction().replace(R.id.content, new SelectRestaurantFragment()).commit();
                navigation.setSelectedItemId(R.id.navigation_restaurants);
                break;
            case 1:
                mManager.beginTransaction().replace(R.id.content, new ExploreFragment()).commit();
                navigation.setSelectedItemId(R.id.navigation_explore);
                break;
            case 3:
                mManager.beginTransaction().replace(R.id.content, new CartFragment()).commit();
                navigation.setSelectedItemId(R.id.navigation_cart);
                break;
            case 4:
                mManager.beginTransaction().replace(R.id.content, new AccountFragment()).commit();
                navigation.setSelectedItemId(R.id.navigation_account);
                break;
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public static HomeActivity getInstance(){
        return homeActivity;
    }
}
