package com.example.jam;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class AlarmFragment extends Fragment {
    private TextView tvAlarmPrompt;
    private int ALARM_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        tvAlarmPrompt = view.findViewById(R.id.tv_alarm_prompt);

        Button buttonSetAlarm = view.findViewById(R.id.btn_set_alarm);
        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSetAlarm(v);
            }
        });

        return view;
    }


    private void init(View view) {
        tvAlarmPrompt = view.findViewById(R.id.tv_alarm_prompt);
    }

    public void clickSetAlarm(View view) {
        tvAlarmPrompt.setText("");
        openTimePickerDialog();
    }

    private void openTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();
                calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calSet.set(Calendar.MINUTE, minute);
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                if (calSet.compareTo(calNow) <= 0) {
                    // Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                    Log.i("hasil", " =<0");
                } else if (calSet.compareTo(calNow) > 0) {
                    Log.i("hasil", " > 0");
                } else {
                    Log.i("hasil", " else ");
                }
                setAlarm(calSet);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    private void setAlarm(Calendar targetCal) {
        tvAlarmPrompt.setText("\n\nAlarm set on " + targetCal.getTime() + "\n");

        Intent intent = new Intent(requireContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        }
    }
}

