package com.example.jam;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

public class StopwatchFragment extends Fragment {

    private Chronometer chronometer;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;

    private boolean isRunning = false;
    private long pauseOffset = 0;

    private final Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        chronometer = view.findViewById(R.id.chronometer);
        startButton = view.findViewById(R.id.startButton);
        stopButton = view.findViewById(R.id.stopButton);
        resetButton = view.findViewById(R.id.resetButton);

        chronometer.setFormat(getString(R.string.chronometer_format));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch();
            }
        });

        return view;
    }

    private void startStopwatch() {
        if (!isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            isRunning = true;
            handler.postDelayed(updateStopwatchRunnable, 10); // Update setiap 10 milidetik
        }
    }

    private void stopStopwatch() {
        if (isRunning) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            isRunning = false;
            handler.removeCallbacks(updateStopwatchRunnable);
        }
    }

    private void resetStopwatch() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        if (isRunning) {
            chronometer.stop();
            isRunning = false;
            handler.removeCallbacks(updateStopwatchRunnable);
        }
    }

    private final Runnable updateStopwatchRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                handler.postDelayed(this, 10); // Update setiap 10 milidetik
                // Perform any additional operations here
            }
        }
    };
}

