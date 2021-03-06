package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Calendar;

public class ManagerSearchCarScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String LOG_TAG = ManagerSearchCarScreen.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //private TextView startDateTextView;
    private Calendar startDate;
    private Spinner spinnerStartTime;
    private ArrayAdapter<CharSequence> arrayAdapterStartTime;
    private String startTime;
    private TextView dateView;
    private String carName;
    private Button mBtnStartDate;
    private String start_Date_Time;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_search_car_screen);
        startDate = Calendar.getInstance();
        startTime = "";
        dateView=(TextView)findViewById(R.id.setDate);
        spinnerStartTime = findViewById(R.id.startTime_spinner);
        arrayAdapterStartTime = getArrayAdapterByDayOfWeek(startDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(spinnerStartTime, arrayAdapterStartTime);
        setUpDate(dateView, startDate);

    }

    public void searchCar(View view) {

        this.carName = ((EditText)findViewById(R.id.car_name)).getText().toString();
        ManagerSearchCarsController searchCar = new ManagerSearchCarsController(this);
        searchCar.searchCars(carName,startDate,startTime);
       /* Log.d("ManagerSearchcar", "car Name:"+carName);
        start_Date_Time = AAUtil.formatDate(startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD) +" "+ startTime.split(" ")[0];
        Log.d("ManagerSearchcar", "startDate And Time:"+ start_Date_Time );
        Log.d("ManagerSearchcar", "startTime:"+startTime);
        Intent intent = new Intent(this, ManagerSearchCarSummaryScreen.class);
        intent.putExtra("start_Date_Time", start_Date_Time);
        intent.putExtra("carName", carName);
        startActivity(intent);*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vrc_menu_logout){
            AAUtil.logout(this);
        }
        return(super.onOptionsItemSelected(item));
    }

    public void showStartDatePicker(View view) {
        DialogFragment startDateFragment = new ManagerSearchCarDatepicker();
        startDateFragment.show(getSupportFragmentManager(), getString(R.string.rcStartDatePicker));
    }

    private void setUpDate(TextView targetDateTextView, Calendar calendar){
        String dateStr = formatDateAsMMDDYYYY(calendar);
        targetDateTextView.setText(dateStr);
    }

    public void processStartDatePickerResult(int year, int month, int day){
        startDate.set(year, month, day);
        setUpDate(dateView, startDate);
    }

    private void setUpSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(ManagerSearchCarScreen.this);
    }

    private ArrayAdapter<CharSequence> getArrayAdapterByDayOfWeek(int dayOfWeek){
        ArrayAdapter<CharSequence> adapter;
        if(dayOfWeek == Calendar.SATURDAY){
            adapter = ArrayAdapter.createFromResource(ManagerSearchCarScreen.this, R.array.saturday_hours, android.R.layout.simple_spinner_item);
        }else if(dayOfWeek == Calendar.SUNDAY){
            adapter = ArrayAdapter.createFromResource(ManagerSearchCarScreen.this, R.array.sunday_hours, android.R.layout.simple_spinner_item);
        }else{
            adapter = ArrayAdapter.createFromResource(ManagerSearchCarScreen.this, R.array.weekday_hours, android.R.layout.simple_spinner_item);
        }
        return adapter;
    }

    private String formatDateAsMMDDYYYY(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String yearStr = Integer.toString(year);
        String monthStr = Integer.toString(month + 1);
        String dayStr = Integer.toString(day);

        String dateStr = (monthStr + "/" + dayStr + "/" + yearStr);
        return dateStr;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.startTime_spinner){
            startTime = adapterView.getItemAtPosition(i).toString();
           // Log.d("ManagerSearchcarSummary", "startTime:"+startTime);
        }
//        }else if(adapterView.getId() == R.id.spinner_end_time){
//            endTime = adapterView.getItemAtPosition(i).toString();
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}