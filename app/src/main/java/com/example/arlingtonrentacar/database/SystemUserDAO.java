/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.users.SystemUser;

import java.security.KeyStore;

public class SystemUserDAO {
    private static final String LOG_TAG = SystemUserDAO.class.getSimpleName();
    public static final String SYSTEM_USERS_TABLE = "system_users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_UTA_ID = "uta_id";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "street_address";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_ZIP = "zip_code";
    public static final String COLUMN_AAA_MEM_STAT = "aaa_member_status";
    public static final String COLUMN_USER_STATUS = "user_status";

    private static SystemUserDAO systemUserDAO;
    private DatabaseHelper dbHelper;

    private SystemUserDAO(@Nullable Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public static SystemUserDAO getInstance(@Nullable Context context){
        if(systemUserDAO == null){
            systemUserDAO = new SystemUserDAO(context);
        }
        return systemUserDAO;
    }

    public boolean isCorrectCredentials(String username, String password){
        final String METHOD_NAME = "isCorrectCredentials()";
        boolean result = false;
        Log.d(LOG_TAG, METHOD_NAME +": username, password: " + username + ", " + password);
        SQLiteDatabase databaseHandle = dbHelper.getWritableDatabase();
        String sql = "SELECT username FROM " + SYSTEM_USERS_TABLE + " WHERE username=? AND password=?;";
        String[] selectionArgs = {username, password};
        Cursor cursor = databaseHandle.rawQuery(sql, selectionArgs);
        if(cursor.getCount() == 1)
            result = true;
        return result;
    }

    public String getSystemUserRole(String username){
        final String METHOD_NAME = "getSystemUserRole()";
        String role = "";
        Log.d(LOG_TAG, METHOD_NAME + ": username: "+username);
        SQLiteDatabase databaseHandle = dbHelper.getWritableDatabase();
        String sql = "SELECT role FROM " + SYSTEM_USERS_TABLE + " WHERE username=?;";
        String[] selectionArgs = {username};
        Cursor cursor = databaseHandle.rawQuery(sql, selectionArgs);
        if(cursor.moveToFirst()){
            role = cursor.getString(cursor.getColumnIndex("role"));
            Log.d(LOG_TAG, METHOD_NAME + ": returned role: "+ role);
        }else{
            Log.d(LOG_TAG, METHOD_NAME + "moveToFirst() returned false. returned role is empty string. user won't be able to login.");
        }
        return role;
    }

    public boolean isRegistered(String username){
        final String METHOD_NAME = "isRegistered()";
        Log.d(LOG_TAG, METHOD_NAME + ": username: "+username);
        boolean result = false;
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        String sql = "SELECT username FROM " + SYSTEM_USERS_TABLE + " WHERE username=?;";
        String[] selectionArgs = {username};
        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        if(cursor.getCount() == 0){
            result = false;
        }else{
            result = true;
        }
        return result;
    }

    public String store(SystemUser user){
        final String METHOD_NAME = "store()";
        String msg = "";
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_LAST_NAME, user.getLastName());
        cv.put(COLUMN_FIRST_NAME, user.getFirstName());
        cv.put(COLUMN_ROLE, user.getRole());
        cv.put(COLUMN_UTA_ID, Integer.toString(user.getUtaID()));
        cv.put(COLUMN_PHONE,  user.getPhone());
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_ADDRESS, user.getStreetAddress());
        cv.put(COLUMN_CITY, user.getCity());
        cv.put(COLUMN_STATE, user.getState());
        cv.put(COLUMN_ZIP, user.getZip());
        cv.put(COLUMN_AAA_MEM_STAT, Integer.toString(user.getAaaMemberStatus()));
        cv.put(COLUMN_USER_STATUS, Integer.toString(user.getUserStatus()));
        long newRowID = dbHandle.insert(SYSTEM_USERS_TABLE, null, cv);
        if(newRowID == -1){
            msg = "Registration failed.\nPlease try again.";
            Log.e(LOG_TAG, METHOD_NAME + "This should never happen.\nFailed to register user.\n Error: Conflict with pre-existing data in the database");
        }
        return msg;
    }

    public String getFullNameByUsername(String username){
        final int FIRST_NAME_COL = 0;
        final int LAST_NAME_COL = 1;
        String fullName = "";
        String sql = "SELECT first_name, last_name FROM " + SYSTEM_USERS_TABLE + " WHERE username = " + AAUtil.quoteStr(username);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            fullName = cursor.getString(FIRST_NAME_COL) + " " + cursor.getString(LAST_NAME_COL);
        }
        Log.d(LOG_TAG, "getFullNameByUsername: fullName = " + fullName);
        return fullName;
    }

    public SystemUser getSystemUserByUsername(String username){
        SystemUser user = null;
        String sql = "SELECT * FROM " + SYSTEM_USERS_TABLE + " WHERE " + COLUMN_USERNAME + " = ?;";
        String[] selectionArgs = {username};
        SQLiteDatabase dbHandle = dbHelper.getReadableDatabase();
        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        String dbusername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
        String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
        String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
        String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
        String role = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
        int utaID = cursor.getInt(cursor.getColumnIndex(COLUMN_UTA_ID));
        String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
        String email  = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
        String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
        String city  = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
        String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));
        String zip  = cursor.getString(cursor.getColumnIndex(COLUMN_ZIP));
        int aaaMemStat = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_AAA_MEM_STAT)));
        int userStat = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_STATUS)));
        user = new SystemUser(dbusername, password, lastName, firstName, role, utaID, phone, email, address, city, state, zip, aaaMemStat, userStat);
        return user;
    }

    public boolean updateRenterProfile(SystemUser renter){
        boolean result = false;
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, renter.getPassword());
        values.put(COLUMN_LAST_NAME, renter.getLastName());
        values.put(COLUMN_FIRST_NAME, renter.getFirstName());
        values.put(COLUMN_UTA_ID, renter.getUtaID());
        values.put(COLUMN_PHONE, renter.getPhone());
        values.put(COLUMN_EMAIL, renter.getEmail());
        values.put(COLUMN_ADDRESS, renter.getStreetAddress());
        values.put(COLUMN_CITY, renter.getCity());
        values.put(COLUMN_STATE, renter.getState());
        values.put(COLUMN_ZIP, renter.getZip());
        values.put(COLUMN_AAA_MEM_STAT, renter.getAaaMemberStatus());
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {renter.getUsername()};
        int count = dbHandle.update(SYSTEM_USERS_TABLE, values, selection, selectionArgs);
        if(count == 1){ // num of rows affected should be 1, as username is pk
            result = true;
        }
        return result;
    }

    public int getSystemUserStatus(String username){
        int status = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT " + COLUMN_USER_STATUS + " FROM " + SYSTEM_USERS_TABLE + " WHERE " + COLUMN_USERNAME + " = ?;";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        status = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_STATUS));
        return status;
    }
    public boolean updateSelectedUserProfile(SystemUser user){
        boolean result = false;
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_UTA_ID, user.getUtaID());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_ADDRESS, user.getStreetAddress());
        values.put(COLUMN_CITY, user.getCity());
        values.put(COLUMN_STATE, user.getState());
        values.put(COLUMN_ZIP, user.getZip());
        values.put(COLUMN_AAA_MEM_STAT, user.getAaaMemberStatus());
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {user.getUsername()};
        int count = dbHandle.update(SYSTEM_USERS_TABLE, values, selection, selectionArgs);
        if(count == 1){ // num of rows affected should be 1, as username is pk
            result = true;
        }
        return result;
    }
    public boolean updateUserRole(SystemUser user){
        boolean result = false;
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ROLE, user.getRole());
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {user.getUsername()};
        int count = dbHandle.update(SYSTEM_USERS_TABLE, values, selection, selectionArgs);
        if(count == 1){ // num of rows affected should be 1, as username is pk
            result = true;
        }
        return result;
    }

    public boolean revokeUser(SystemUser user){
        boolean result = false;
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_STATUS, user.getUserStatus());
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {user.getUsername()};
        int count = dbHandle.update(SYSTEM_USERS_TABLE, values, selection, selectionArgs);
        if(count == 1){ // num of rows affected should be 1, as username is pk
            result = true;
        }
        return result;
    }
}
