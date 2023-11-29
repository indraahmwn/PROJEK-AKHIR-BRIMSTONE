package com.example.jam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ClockFragment extends Fragment {

    private TextView currentTimeTextView;
    private TextView currentDateTextView;
    private Spinner timeZoneSpinner;
    private ArrayAdapter<String> timeZoneAdapter;
    private String[] timeZoneIds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock, container, false);

        currentTimeTextView = view.findViewById(R.id.current_time_text_view);
        currentDateTextView = view.findViewById(R.id.current_date_text_view);
        timeZoneSpinner = view.findViewById(R.id.time_zone_spinner);

        setupTimeZoneSpinner();
        updateTimeAndDate();

        return view;
    }

    private void setupTimeZoneSpinner() {
        // Mendapatkan daftar ID zona waktu yang tersedia
        timeZoneIds = TimeZone.getAvailableIDs();
        timeZoneAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeZoneIds);
        timeZoneSpinner.setAdapter(timeZoneAdapter);

        timeZoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                updateTimeAndDate(); // Update time when a new time zone is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }

    private void updateTimeAndDate() {
        Calendar calendar = Calendar.getInstance();
        int selectedTimeZoneIndex = timeZoneSpinner.getSelectedItemPosition();

        // Mendapatkan TimeZone berdasarkan ID yang dipilih
        TimeZone selectedTimeZone = TimeZone.getTimeZone(timeZoneIds[selectedTimeZoneIndex]);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        // Menyesuaikan zona waktu
        timeFormat.setTimeZone(selectedTimeZone);
        String currentTime = timeFormat.format(calendar.getTime());
        currentTimeTextView.setText(currentTime);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        // Menyesuaikan zona waktu
        dateFormat.setTimeZone(selectedTimeZone);
        String currentDate = dateFormat.format(calendar.getTime());
        currentDateTextView.setText(currentDate);
    }
}



