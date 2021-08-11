package com.example.myapplication;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    public Manager manager;
    //protected ImageView[][] a;
    public int timeTableDisplay;
    public static int screenWidth=900;
    public static int screenHight=900;
    public int yearDisplay;
    public int monthDisplay;
    public String[] monthes = new String[]{"Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sept","Oct","Nov","Dec"};
    public static HashMap<String, Integer> monthToInt=new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager=new Manager(this.getFilesDir()+"/");
        timeTableDisplay=-1;
        yearDisplay=LocalDate.now().getYear();
        monthDisplay=LocalDate.now().getMonthValue();
        for(int i=0; i<12;i++){
            monthToInt.put(monthes[i],i+1);
        }
        setUpDates(yearDisplay,monthDisplay,900,900);

    }

    protected void setUpDates(int year, int m, int width, int hight){
        setContentView(R.layout.dates);
        LinearLayout l =findViewById(R.id.datesMainLayout);
        ViewGroup.LayoutParams tableRowSize = new ViewGroup.LayoutParams(width, hight/6);
        //HashMap<Button, LocalDate> result = new HashMap<Button,LocalDate>();
        LocalDate a = LocalDate.of(year,m,1);
        LocalDate start = a.minusDays(a.getDayOfWeek().getValue()%7);
        //Date start = new Date(year-1900,1,1);
        for(int i=0; i<6;i++){
            //TableRow c = new TableRow(this);
            //c.setLayoutParams(tableRowSize);
            LinearLayout c = new LinearLayout(this);
            c.setLayoutParams(tableRowSize);
            c.setOrientation(LinearLayout.HORIZONTAL);
            for(int j=0;j<7;j++){
                Button b = new Button(this);
                b.setLayoutParams(new ViewGroup.LayoutParams(width/7,hight/6));
                int finalJ = j;
                int finalI = i;
                b.setText((""+start.plusDays(finalI *7+ finalJ).getDayOfMonth()));
                b.setOnClickListener(new View.OnClickListener() {
                   @Override
                    public void onClick(View view) {
                        setUpEventPage(start.plusDays(finalI *7+ finalJ));
                    }
                });
                c.addView(b);
            }
            l.addView(c);
        }
        EditText y=findViewById(R.id.datesYear);
        Spinner month = findViewById(R.id.datesMonth);
        Button manageHoliday = findViewById(R.id.datesManageholiday);
        Button manageTimetable = findViewById(R.id.datesManagetimetable);
        Button save = findViewById(R.id.datessave);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,monthes);
        month.setAdapter(monthAdapter);
        month.setSelection(m-1);
        y.setText(""+year);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=m-1) {
                    setUpDates(Integer.parseInt(y.getText().toString()), monthToInt.get(month.getSelectedItem().toString()), width, hight);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        y.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_ENTER&&keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    setUpDates(Integer.parseInt(y.getText().toString()), monthToInt.get(month.getSelectedItem().toString()), width, hight);
                }
                return false;
            }
        });
        manageTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpTimeTablePage();
            }
        });
        Spinner selectTimeTable = findViewById(R.id.datesSelectTimetable);
        HashMap<Integer,String> tnames = manager.getTimeTableNames();
        ArrayList<String> tn = new ArrayList<String>();
        ArrayList<Integer> ind = new ArrayList<Integer>();
        for (int i:tnames.keySet()){
            tn.add(tnames.get(i));
            ind.add(i);
        }
        String[] tNames= tn.toArray(new String[tn.size()]);
        Integer[] timetableIndex = ind.toArray(new Integer[ind.size()]);
        ArrayAdapter<String> timetableNames= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tNames);

        selectTimeTable.setAdapter(timetableNames);
        if(ind.contains(timeTableDisplay)){
            selectTimeTable.setSelection(ind.indexOf(timeTableDisplay));
        }
        selectTimeTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeTableDisplay=timetableIndex[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.save();
            }
        });
        //manageHoliday.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
         //       setUpCommingSoon();
           // }
        //});
        manageHoliday.setAlpha(0);
        manageHoliday.setActivated(false);
    }
    public void setUpCommingSoon(){
        setContentView(R.layout.commingsoon);
        Button comingsoonback = findViewById(R.id.comingsoonback);
        comingsoonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDates(yearDisplay,monthDisplay,900,900);
            }
        });
    }
    public void setUpEventPage(LocalDate d){
        setContentView(R.layout.eventendate);
        LinearLayout l = findViewById(R.id.eventondateMainLayout);
        ArrayList<HashMap<String,Object>> eventInfo = manager.eventsInfoOnDate(timeTableDisplay,d);
        if(eventInfo.size()>0) {
            for (HashMap<String, Object> info : eventInfo) {
                LinearLayout c = new LinearLayout(this);
                c.setLayoutParams(new ViewGroup.LayoutParams(screenWidth,200));
                c.setOrientation(LinearLayout.HORIZONTAL);
                Button b = new Button(this);
                int eventid = (int) info.get("id");
                b.setLayoutParams(new ViewGroup.LayoutParams(screenWidth*4/5,200));
                String eventName = (String) info.get("Name");
                String eventText = (String) info.get("Text");
                LocalDateTime eventStartTime = (LocalDateTime) info.get("Start time");
                LocalDateTime eventEndTime = (LocalDateTime) info.get("End time");
                b.setText(""+eventStartTime+"-"+eventEndTime+":"+eventName);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SetUPEventDetailPage(eventName, eventText, eventStartTime, eventEndTime, eventid);
                    }
                });
                Button delateb = new Button(this);
                delateb.setLayoutParams(new ViewGroup.LayoutParams(screenWidth/5,200));
                delateb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        manager.removeEvent(timeTableDisplay,eventid);
                        setUpEventPage(d);
                    }

                });
                delateb.setText("X");
                c.addView(b);
                c.addView(delateb);
                l.addView(c);
            }
        }
        Button back = findViewById(R.id.eventondateback);
        //back.setText(""+eventInfo.size());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDates(yearDisplay,monthDisplay,900,900);
            }
        });
        Button add = findViewById(R.id.eventondateadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetUPAddEventPage(d);
            }
        });
    }
    public void SetUPEventDetailPage(String name, String text, LocalDateTime start, LocalDateTime end, int id){
        setContentView(R.layout.eventdital);
        EditText ditalname=findViewById(R.id.eventditalname);
        ditalname.setText(name);
        EditText ditalnote = findViewById(R.id.eventditalnote);
        ditalnote.setText(text);
        EditText ditalstartyear = findViewById(R.id.eventditalstartyear);
        ditalstartyear.setText(""+start.getYear(), TextView.BufferType.EDITABLE);
        EditText ditalendyear = findViewById(R.id.eventditalendyear);
        ditalendyear.setText(""+end.getYear(), TextView.BufferType.EDITABLE);
        Spinner ditalstartmonth=findViewById(R.id.eventditalstartmonth);
        EditText ditalstartdate = findViewById(R.id.eventditalstartday);
        ditalstartdate.setText(""+start.toLocalDate().getDayOfMonth(), TextView.BufferType.EDITABLE);
        EditText ditalstarthour = findViewById(R.id.eventditalstarthour);
        ditalstarthour.setText(""+start.getHour(), TextView.BufferType.EDITABLE);
        EditText ditalstartminute = findViewById(R.id.eventditalstartminute);
        ditalstartminute.setText(""+start.getMinute(), TextView.BufferType.EDITABLE);
        Spinner ditalendmonth=findViewById(R.id.eventditalendmonth);
        EditText ditalenddate = findViewById(R.id.eventditalendday);
        ditalenddate.setText(""+end.getDayOfMonth(), TextView.BufferType.EDITABLE);
        EditText ditalendhour = findViewById(R.id.eventditalendhour);
        ditalendhour.setText(""+end.getHour(), TextView.BufferType.EDITABLE);
        EditText ditalendminute = findViewById(R.id.eventditalendminute);
        ditalendminute.setText(""+end.getMinute(), TextView.BufferType.EDITABLE);
        Button back = findViewById(R.id.eventditalback);
        Button modify =findViewById(R.id.eventditalmodify);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpEventPage(start.toLocalDate());
            }
        });
        ArrayAdapter<String> startMonthes = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,monthes);
        ditalstartmonth.setAdapter(startMonthes);
        ditalstartmonth.setSelection(start.getMonth().getValue()-1);
        ditalendmonth.setAdapter(startMonthes);
        ditalendmonth.setSelection(end.getMonth().getValue()-1);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int startYear = Integer.parseInt(ditalstartyear.getText().toString());
                    int endYear = Integer.parseInt(ditalendyear.getText().toString());
                    int startMonth = monthToInt.get(ditalstartmonth.getSelectedItem().toString());
                    int endMonth = monthToInt.get(ditalendmonth.getSelectedItem().toString());
                    int startDate = Integer.parseInt(ditalstartdate.getText().toString());
                    int endDate = Integer.parseInt(ditalenddate.getText().toString());
                    int startHour = Integer.parseInt(ditalstarthour.getText().toString());
                    int endHour = Integer.parseInt(ditalendhour.getText().toString());
                    int startMinute = Integer.parseInt(ditalstartminute.getText().toString());
                    int endMinute = Integer.parseInt(ditalendminute.getText().toString());

                    if (id == -1) {
                        manager.addEvent(timeTableDisplay, LocalDateTime.of(startYear, startMonth, startDate, startHour, startMinute),
                                LocalDateTime.of(endYear, endMonth, endDate, endHour, endMinute), ditalname.getText().toString(), ditalnote.getText().toString());
                    } else {
                        manager.fixEvent(timeTableDisplay, id, LocalDateTime.of(startYear, startMonth, startDate, startHour, startMinute),
                                LocalDateTime.of(endYear, endMonth, endDate, endHour, endMinute), ditalname.getText().toString(), ditalnote.getText().toString());
                    }
                    //modify.setText(""+LocalDateTime.of(endYear,endMonth,endDate,endHour,endMinute));
                    setUpEventPage(start.toLocalDate());
                }catch (DateTimeException e){

                }
            }
        });
    }
    public void SetUPAddEventPage(LocalDate date){
        SetUPEventDetailPage("","",date.atStartOfDay(), date.atStartOfDay(), -1);
    }
    public void setUpHolidayPage(LocalDate localDate){
        setContentView(R.layout.holidays);
        LinearLayout hl = findViewById(R.id.holidaysMainLayout);
        Button back = findViewById(R.id.holidaysBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDates(yearDisplay,monthDisplay,900,900);
            }
        });
        Button add = findViewById(R.id.holidaysAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetUpAddHolidayPage(localDate);
            }
        });
    }
    public void SetUpAddHolidayPage(LocalDate localDate){
        SetUpHolidayDetailPage();
    }
    public void SetUpHolidayDetailPage(){

    }
    public void setUpTimeTablePage(){
        setContentView(R.layout.timetables);
        HashMap<Integer,String> timeTables= manager.getTimeTableNames();
        LinearLayout mainlayout = findViewById(R.id.timetablesmainlayout);
        for(int i :timeTables.keySet()){
            EditText name = new EditText(this);
            name.setText(timeTables.get(i));
            int finali = i;
            name.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if(i==KeyEvent.KEYCODE_ENTER&&keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                        manager.setTimetableName(finali,name.getText().toString());
                    }
                    return false;
                }
            });
            name.setLayoutParams(new ViewGroup.LayoutParams(screenWidth*3/4,100));
            Button b =new Button(this);
            b.setLayoutParams(new ViewGroup.LayoutParams(screenWidth/4,100));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manager.removeTimetable(finali);
                    setUpTimeTablePage();
                }
            });
            b.setText("X");
            LinearLayout l =new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setLayoutParams(new ViewGroup.LayoutParams(screenWidth,100));
            l.addView(name);
            l.addView(b,1);
            mainlayout.addView(l);
        }
        EditText name = new EditText(this);
        name.setLayoutParams(new ViewGroup.LayoutParams(screenWidth*3/4,100));
        Button b =new Button(this);
        b.setLayoutParams(new ViewGroup.LayoutParams(screenWidth/4,100));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                if(!n.equals("")) {
                    manager.addTimeTable(n);
                    setUpTimeTablePage();
                }
            }
        });
        b.setText("ADD");
        LinearLayout l =new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setLayoutParams(new ViewGroup.LayoutParams(screenWidth,100));
        l.addView(name);
        l.addView(b,1);
        mainlayout.addView(l);
        Button back = findViewById(R.id.timetablesback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDates(yearDisplay,monthDisplay,screenWidth,screenHight);
            }
        });
    }

}