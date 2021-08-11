package com.example.myapplication;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

public class HolidayManager {
    public static final String holidayFile="holiday";
    protected HashMap<Integer,holiday> holidays;
    public String dataFolder;
    public HolidayManager(String dataFolder){
        this.dataFolder=dataFolder;
        holidays=dataHelper.read(dataFolder,holidayFile);
        if(holidays==null){
            holidays=new HashMap<Integer,holiday>();
        }
        //ArrayList<holiday> a = dataHelper.readAll(HolidayManager.holidayFolder);
        //this.holidays=new HashMap<Integer, holiday>();
        //for(holiday h:a){
        //    this.holidays.put(h.getId(),h);
        //}
    }
    public HolidayManager(ArrayList<holiday> input){
        this.holidays=new HashMap<Integer, holiday>();
        for(holiday h:input){
            this.holidays.put(h.getId(),h);
        }
    }
    public void addNormalHoliday(Month m, String name, int date){
        int id = 0;
        for (int i=0; i<=holidays.size();i++){
            if(!holidays.containsKey(i)){
                id=i;
                break;
            }
        }
        this.holidays.put(id,new normalHoliday(id,name,m,date));
    }
    public void addSpecialHoliday(Month m, String name, int index ,DayOfWeek date){
        int id = 0;
        for (int i=0; i<=holidays.size();i++){
            if(!holidays.containsKey(i)){
                id=i;
                break;
            }
        }
        this.holidays.put(id,new SpecialHoliday(id,name,index,m,date));
    }
    public void removeHoliday(int id){
        holidays.remove(id);
    }
    public HashMap<LocalDate,ArrayList<Integer>> holidayInMonth(Month m, int year){
        HashMap<LocalDate,ArrayList<Integer>> result = new HashMap<LocalDate,ArrayList<Integer>>();
        for(int i :holidays.keySet()){
            if(holidays.get(i).getMonth()==m){
                LocalDate d = holidays.get(i).getDate(year);
                if(result.containsKey(d)){
                    result.get(d).add(i);
                }else {
                    ArrayList<Integer> b = new ArrayList<Integer>();
                    b.add(i);
                    result.put(d,b);
                }
            }
        }
        return result;
    }
    public HashMap<LocalDate,ArrayList<HashMap<String,Object>>> holidayInfoInMonth(Month m, int year){
        HashMap<LocalDate,ArrayList<HashMap<String,Object>>> result = new HashMap<LocalDate,ArrayList<HashMap<String,Object>>>();
        for(int i :holidays.keySet()){
            if(holidays.get(i).getMonth()==m){
                LocalDate d = holidays.get(i).getDate(year);
                if(!result.containsKey(d)){
                    ArrayList<HashMap<String,Object>> b = new ArrayList<HashMap<String,Object>>();
                    result.put(d,b);
                }
                result.get(d).add(holidays.get(i).getInfo(year));
            }
        }
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<HashMap<String,Object>> holidayInfoOnDate(LocalDate d){
        ArrayList<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
        for(int i :holidays.keySet()){
            if(holidays.get(i).getDate(d.getYear())==d){
                result.add(holidays.get(i).getInfo(d.getYear()));
            }
        }
        return result;
    }
    public void saveToFile(){
        dataHelper.writeToFile(dataFolder,holidayFile,holidays);
    }
}
