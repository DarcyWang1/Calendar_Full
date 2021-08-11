package com.example.myapplication;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
@RequiresApi(api = Build.VERSION_CODES.O)
public class Manager {
    private HolidayManager holidayManager;
    private TimeTableManager timeTableManager;
    //private int timetableDisplay =-1;

    public Manager(String dataFile){
        //ArrayList<holiday> a = dataHelper.readAll(HolidayManager.holidayFolder);
        //ArrayList<TimeTable> b = dataHelper.readAll(TimeTableManager.timeTableFolder);
        holidayManager = new HolidayManager(dataFile);
        timeTableManager = new TimeTableManager(dataFile);
    }
    public HashMap<java.time.LocalDate,ArrayList<HashMap<String,Object>>> eventsInfoOnMonth(int timetableDisplay,int year, java.time.Month m){
        if(timetableDisplay !=-1) {
            return timeTableManager.eventsInfoOnMonth(timetableDisplay, year, m);
        }
        return null;
    }
    public ArrayList<event> getEventOnDate(int timetableDisplay,java.time.LocalDate date){
        return timeTableManager.getEventOnDate(timetableDisplay, date);
    }
    public void addEvent(int timetableDisplay,java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,String name,String text){
        timeTableManager.addEvent(timetableDisplay, startTime, endTime, name, text);
    }
    public void removeEvent(int timetableDisplay,int eventId){
        timeTableManager.removeEvent(timetableDisplay, eventId);
    }
    public void fixEvent(int timetableDisplay,int eventid, java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,String name,String text){
        timeTableManager.fixEvent(timetableDisplay, eventid, startTime, endTime, name, text);
    }
    public void addTimeTable(String name){
        timeTableManager.addTimeTable(name);
    }
    public void addNormalHoliday(Month m, String name, int date){
        holidayManager.addNormalHoliday(m,name,date);
    }
    public void addSpecialHoliday(Month m, String name, int index , DayOfWeek date){
        holidayManager.addSpecialHoliday(m,name,index,date);
    }
    public void removeHoliday(int id){
        holidayManager.removeHoliday(id);
    }
    public HashMap<LocalDate,ArrayList<Integer>> holidayInMonth(Month m, int year){
        return holidayManager.holidayInMonth(m,year);
    }
    public HashMap<LocalDate,ArrayList<HashMap<String,Object>>> holidayInfoInMonth(Month m, int year){
        return holidayManager.holidayInfoInMonth(m,year);
    }
    public ArrayList<HashMap<String,Object>> eventsInfoOnDate(int timTableId, LocalDate d){
        return timeTableManager.eventsInfoOnDate(timTableId,d);
    }
    public void save(){
        holidayManager.saveToFile();
        timeTableManager.saveToFile();;
    }
    public HashMap<Integer,String> getTimeTableNames(){
        return timeTableManager.getTimeTableNames();
    }
    public void setTimetableName(int id,String name){
        timeTableManager.setTimetableName(id,name);
    }
    public void removeTimetable(int id){
        timeTableManager.removeTimetable(id);
    }
}
