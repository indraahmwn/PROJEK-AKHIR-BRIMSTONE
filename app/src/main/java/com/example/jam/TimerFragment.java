package com.example.jam;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment {

    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;
    private NumberPicker secondsPicker;
    private Button startTimerButton;
    private Button resetTimerButton;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private long totalTimeInMillis;
    private boolean timerRunning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        hoursPicker = view.findViewById(R.id.hours_picker);
        minutesPicker = view.findViewById(R.id.minutes_picker);
        secondsPicker = view.findViewById(R.id.seconds_picker);
        startTimerButton = view.findViewById(R.id.start_timer_button);
        timerTextView = view.findViewById(R.id.timer_text_view);
        resetTimerButton = view.findViewById(R.id.reset_timer_button);

        // Setup NumberPickers
        setupNumberPickers();

        // Handle Start Timer button click
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerRunning) {
                    startTimer();
                } else {
                    stopTimer();
                }
            }
        });
        resetTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        return view;
    }

    private void setupNumberPickers() {
        // Setup NumberPicker for hours (0-23)
        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);

        // Setup NumberPicker for minutes and seconds (0-59)
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59);
    }

    private void startTimer() {
        int hours = hoursPicker.getValue();
        int minutes = minutesPicker.getValue();
        int seconds = secondsPicker.getValue();

        // Calculate total time in milliseconds
        totalTimeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startTimerButton.setText("Start Timer");
            }
        }.start();

        timerRunning = true;
        startTimerButton.setText("Pause Timer");
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startTimerButton.setText("Start Timer");
    }

    private void updateTimerText(long milliseconds) {
        int hours = (int) (milliseconds / 1000) / 3600;
        int minutes = (int) ((milliseconds / 1000) % 3600) / 60;
        int seconds = (int) (milliseconds / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }
    private void resetTimer() {
        stopTimer();
        hoursPicker.setValue(0);
        minutesPicker.setValue(0);
        secondsPicker.setValue(0);
        timerTextView.setText("00:00:00");
    }
}

