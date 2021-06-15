package com.example.customcalendar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.customcalendar.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    LocalDate selectedDate;
    private ActivityMainBinding binding;

    int height;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        int orientation = getResources().getConfiguration().orientation;

        ViewGroup.LayoutParams parentLayoutContainer = binding.parentLayout.getLayoutParams();
        ViewGroup.LayoutParams params = binding.recyclerViewCalendar.getLayoutParams();
        ViewGroup.LayoutParams weeksLayoutParams = binding.linearLayoutWeekDays.getLayoutParams();


        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            parentLayoutContainer.width = (int) (width * 0.6);
        } else {

            parentLayoutContainer.height = (int) (height);
            params.height = (int) (width);
        }

        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void setMonthView() {
        binding.monthYearTextView.setText(monthYearFromData(selectedDate));
        ArrayList<DayModel> daysInMonth = daysInMonth(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        binding.recyclerViewCalendar.setLayoutManager(new GridLayoutManager(getApplicationContext(), 7));
        binding.recyclerViewCalendar.setAdapter(calendarAdapter);
    }

    private ArrayList<DayModel> daysInMonth(LocalDate selectedDate) {
        ArrayList<DayModel> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstofMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstofMonth.getDayOfWeek().getValue();
        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(new DayModel("", false));
            } else {
                daysInMonthArray.add(new DayModel(String.valueOf(i - dayOfWeek), false));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromData(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            Toast.makeText(this, "Selected Date " + dayText + " " + monthYearFromData(selectedDate), Toast.LENGTH_SHORT).show();
        }
    }
}