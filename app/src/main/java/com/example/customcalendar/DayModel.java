package com.example.customcalendar;

public class DayModel {
    public String dayOfMonth;
    public boolean isSelected;

    public DayModel(String dayOfMonth, boolean isSelected) {
        this.dayOfMonth = dayOfMonth;
        this.isSelected = isSelected;
    }
}
