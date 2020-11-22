/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.ViewRequestedCarActivity;
import com.example.arlingtonrentacar.database.SystemUserDAO;

import java.util.Calendar;


public class RequestCarController {
    private static String LOG_TAG = RequestCarController.class.getSimpleName();
    private Context requestCarActivityContext;
    private SharedPreferences sessionPrefs;

    public RequestCarController(Context context){
        this.requestCarActivityContext = context;
        sessionPrefs = AAUtil.getLogInSession(requestCarActivityContext);
    }

    public void geAvailableCarList(String numOfRiders, Calendar startDate, String startTime, Calendar endDate, String endTime){
        String msg = "";
        int userSatus = getRenterSatus();
        startDate = AAUtil.getCalendarDateWithTime(startDate, startTime);
        endDate = AAUtil.getCalendarDateWithTime(endDate, endTime);
        RequestCar requestCar = new RequestCar(numOfRiders, startDate, endDate);

        if(userSatus == 0){
            msg = "You can't rent any car. Your rental status is 'Revoked'";
            Toast.makeText(requestCarActivityContext, msg, Toast.LENGTH_LONG).show();
        }else if(!requestCar.validRequestCarFormData()){
            msg = "Invalid Data";
            Toast.makeText(requestCarActivityContext, msg, Toast.LENGTH_LONG).show();
        }else{
            addDataToSession(numOfRiders, AAUtil.formatDate(startDate, AAUtil.DATABASE_DATE_TIME_FORMAT), AAUtil.formatDate(endDate, AAUtil.DATABASE_DATE_TIME_FORMAT));
            Intent intent = new Intent(requestCarActivityContext, ViewRequestedCarActivity.class);
            requestCarActivityContext.startActivity(intent);
        }
    }

    private void addDataToSession(String numOfRiders, String startDateTime, String endDateTime){
        SharedPreferences.Editor sessions = sessionPrefs.edit();
        sessions.putString(requestCarActivityContext.getString(R.string.session_req_car_form_data_numOfRiders), numOfRiders);
        sessions.putString(requestCarActivityContext.getString(R.string.session_req_car_form_date_startDateTime), startDateTime);
        sessions.putString(requestCarActivityContext.getString(R.string.session_req_car_form_data_endDateTime), endDateTime);
        sessions.commit();
    }

    private int getRenterSatus(){
        String username = sessionPrefs.getString(requestCarActivityContext.getString(R.string.session_loggedin_username), "");
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(requestCarActivityContext);
        return systemUserDAO.getSystemUserStatus(username);
    }
}
