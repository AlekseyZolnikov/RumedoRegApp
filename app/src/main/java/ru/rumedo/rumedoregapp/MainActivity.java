package ru.rumedo.rumedoregapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import ru.rumedo.rumedoregapp.database.UserDataSource;
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
        getMenuInflater().inflate(R.menu.database_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        FrameLayout layout = findViewById(R.id.main_activity_frame_layout);

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_database) {
            UserDataSource userDataSource = new UserDataSource(this);
            userDataSource.open();
            userDataSource.deleteAll();
            Snackbar.make(layout, "Очистка базы", Snackbar.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
