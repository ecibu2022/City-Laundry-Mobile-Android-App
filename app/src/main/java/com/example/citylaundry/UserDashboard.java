package com.example.citylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class UserDashboard extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title for the action bar
        getSupportActionBar().setTitle("Home");

        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UserHomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.home);
        }

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }


    //Going back to Login
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Check if the current fragment is the HomeFragment
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof UserHomeFragment) {
                // Navigate to the LoginActivity
                navigateToLogin();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.home) {
            fragment = new UserHomeFragment();
            getSupportActionBar().setTitle("Home");
        } else if (id == R.id.services) {
            fragment = new UserServicesFragment();
            getSupportActionBar().setTitle("Our Services");
        } else if (id == R.id.profile) {
            fragment = new UserProfileFragment();
            getSupportActionBar().setTitle("User Profile");
        } else if (id == R.id.logout) {
            startActivity(new Intent(UserDashboard.this, Login.class));
            finish();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}