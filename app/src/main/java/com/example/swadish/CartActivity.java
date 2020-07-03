package com.example.swadish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initViews();
        initBottomNavView();
        setSupportActionBar(toolbar);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FragmentFirstCart());
        transaction.commit();
    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent homeintent = new Intent(CartActivity.this, MainActivity.class);
                        homeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeintent);
                        break;
                    case R.id.search:
                        Intent searchintent = new Intent(CartActivity.this, SettingActivity.class);
                        searchintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(searchintent);
                        break;
                    case R.id.cart:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initViews() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigatonview);
        toolbar = findViewById(R.id.toolbar);
    }

}
