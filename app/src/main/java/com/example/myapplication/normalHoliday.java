package com.example.myapplication;

import android.os.Build;
import androidx.annotation.RequiresApi;

public class normalHoliday extends holiday{
    // normal holiday happens at a specific date every year
    protected int date;
    public normalHoliday(int id,String name, java.time.Month month, int date){
        this.id=id;
        this.name=name;
        this.month=month;
        this.date=date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public java.time.LocalDate getDate(int year) {
        return java.time.LocalDate.of(year,month,date);
    }
}
