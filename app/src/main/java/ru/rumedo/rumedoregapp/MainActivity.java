package ru.rumedo.rumedoregapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.rumedo.rumedoregapp.fragment.MainFragment;
import ru.rumedo.rumedoregapp.fragment.RegistrationFragment;
import ru.rumedo.rumedoregapp.fragment.UserListFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_activity_frame_layout);
        if (fragment == null) {
            fragment = new MainFragment();
            showFragment(fragment);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        View userFragment = findViewById(R.id.fragment_user);
        View userListFragment = findViewById(R.id.fragment_user_list);
        View userRegistrationFragment = findViewById(R.id.fragment_registration_user);

        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else if(userFragment != null) {
            Fragment fragment = new UserListFragment();
            showFragment(fragment);
        } else if(userListFragment != null) {
            Fragment fragment = new MainFragment();
            showFragment(fragment);
        } else if(userRegistrationFragment != null) {
            Fragment fragment = new MainFragment();
            showFragment(fragment);
        }else {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frame_layout, fragment)
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_list) {
            Fragment userListFragment = new UserListFragment();
            showFragment(userListFragment);
        }else if(id == R.id.nav_user_registration) {
            Fragment registrationFragment = new RegistrationFragment();
            showFragment(registrationFragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
