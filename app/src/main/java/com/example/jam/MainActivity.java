package com.example.jam;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    AlarmFragment firstFragment = new AlarmFragment();
    ClockFragment secondFragment = new ClockFragment();
    StopwatchFragment thirdFragment = new StopwatchFragment();
    TimerFragment fourthFragment = new TimerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_alarm);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_alarm) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, firstFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.navigation_clock) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, secondFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.navigation_stopwatch) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, thirdFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.navigation_timer) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fourthFragment)
                    .commit();
            return true;
        }
        return false;
    }
}
