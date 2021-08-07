package com.example.myapplication;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class SpecialHoliday extends holiday{
    //Special holidays happens at the ?th some date of a week in a specific month
    protected int index;
    protected DayOfWeek date;
    public SpecialHoliday(int id,String name, int index, java.time.Month month, DayOfWeek date){
        this.id=id;
        this.index=index;
        this.name=name;
        this.month=month;
        this.date=date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public java.time.LocalDate getDate(int year){
        int l = this.month.length(java.time.Year.isLeap(year));
        java.time.LocalDate d=java.time.LocalDate.of(year,this.month,1);
        DayOfWeek a = d.getDayOfWeek();
        int b = (a.getValue()-date.getValue()+7)%7;
        LocalDate result = java.time.LocalDate.of(year,this.month,(1+b+7*index+l)%l);
        if(result.getMonth()== this.month){
            return result;
        }else{
            return null;
        }
    }
}
