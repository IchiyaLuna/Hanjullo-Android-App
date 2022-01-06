package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hanjullo.hanjulloapp.databinding.ActivityCalendarBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    private long backPressedTime;

    private LocalDate currentDate;
    private ActivityCalendarBinding binding;
    private TextView todayTextView;
    private ImageButton profileImageButton;
    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;
    private ArrayList<CalendarItem> calendarItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setListener();

        currentDate = LocalDate.now();

        todayTextView.setText(currentDate.format(DateTimeFormatter.ofPattern("yyyy / MM / dd")));

        setCalenderItems();
    }

    private void setBinding() {
        profileImageButton = binding.profileBtn;
        todayTextView = binding.todayDateText;
    }

    private void setListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.profileBtn) {
                    intent = new Intent(CalendarActivity.this, ProfileActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_open, R.anim.slide_left_close);
            }
        };

        profileImageButton.setOnClickListener(listener);
    }

    private void setCalenderItems() {

        calendarItems = new ArrayList<>();

        LocalDate newDate;

        for (int i = 0; i < 200; i++) {
            newDate = currentDate.plusDays(200 - i);
            calendarItems.add(new CalendarItem(newDate));
        }

        for (int i = 0; i < 201; i++) {
            newDate = currentDate.minusDays(i);
            calendarItems.add(new CalendarItem(newDate));
        }

        calendarRecyclerView = binding.calendarRecyclerView;
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        calendarAdapter = new CalendarAdapter(calendarItems, getApplicationContext());
        calendarRecyclerView.setAdapter(calendarAdapter);

        calendarRecyclerView.scrollToPosition(200);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finishAffinity();
        }
    }
}