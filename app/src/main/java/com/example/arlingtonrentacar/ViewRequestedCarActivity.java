/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arlingtonrentacar.renter.RequestCarSummaryItem;
import com.example.arlingtonrentacar.renter.ViewRequestedCarController;

import java.util.LinkedList;

public class ViewRequestedCarActivity extends AppCompatActivity {
    private static String LOG_TAG = ViewRequestedCarActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RequestedCarListAdapter adapter;
    private SharedPreferences sessionPref;
    private LinkedList<RequestCarSummaryItem> availableCarList;
    private ViewRequestedCarController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requested_car);
        final String METHOD_NAME = "onCreate()";
        sessionPref = AAUtil.getLogInSession(this);
        mController = new ViewRequestedCarController(this);
        availableCarList =
                mController.generateAvailableCarList(
                sessionPref.getString(getString(R.string.session_req_car_form_data_numOfRiders),""),
                sessionPref.getString(getString(R.string.session_req_car_form_date_startDateTime), ""),
                sessionPref.getString(getString(R.string.session_req_car_form_data_endDateTime),"")
                );
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView = findViewById(R.id.vrc_recyclerview);
        adapter = new RequestedCarListAdapter(this, availableCarList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.hasFixedSize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_requested_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vrc_menu_logout){
            AAUtil.logout(this);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

}

