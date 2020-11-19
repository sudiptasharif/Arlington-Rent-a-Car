package com.example.arlingtonrentacar;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AAUtil {
    public static final String DATABASE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String USER_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm a";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String USD_CURRENCY_FORMAT = "#,###.##";
    public static final double TAX = 1.0825; // 8.25%
    public static final double DISCOUNT = 0.9; // 10%
    public static final String EMPTYSTR = "";
    public static final String EIGHT_AM = "8:00 AM";
    public static final String EIGHT_THIRTY_AM = "8:30 AM";
    public static final String NINE_AM = "9:00 AM";
    public static final String NINE_THIRTY_AM = "9:30 AM";
    public static final String TEN_AM  = "10:00 AM";
    public static final String TEN_THIRTY_AM = "10:30 AM";
    public static final String ELEVEN_AM = "11:00 AM";
    public static final String ELEVEN_THIRTY_AM = "11:30 AM";
    public static final String TWELVE_PM = "12:00 PM";
    public static final String TWELVE_THIRTY_PM = "12:30 PM";
    public static final String ONE_PM = "1:00 PM";
    public static final String ONE_THIRTY_PM = "1:30 PM";
    public static final String TWO_PM = "2:00 PM";
    public static final String TWO_THIRTY_PM = "2:30 PM";
    public static final String THREE_PM = "3:00 PM";
    public static final String THREE_THIRTY_PM = "3:30 PM";
    public static final String FOUR_PM = "4:00 PM";
    public static final String FOUR_THIRTY_PM = "4:30 PM";
    public static final String FIVE_PM = "5:00 PM";
    public static final String FIVE_THIRTY_PM = "5:30 PM";
    public static final String SIX_PM  = "6:00 PM";
    public static final String SIX_THIRTY_PM = "6:30 PM";
    public static final String SEVEN_PM = "7:00 PM";
    public static final String SEVEN_THIRTY_PM = "7:30 PM";
    public static final String EIGHT_PM = "8:00 PM";

    public static Role roleStrToEnum(String role){
        role = role.toLowerCase();
        if(role.equals("renter")){
            return Role.RENTER;
        }else if(role.equals("manager")){
            return Role.MANAGER;
        }else if(role.equals("admin")){
            return Role.ADMIN;
        }else{
            return Role.NONE;
        }
    }

    public static String roleEnumToStr(Role role){
        if(role == Role.RENTER){
            return "renter";
        }else if(role == Role.MANAGER){
            return  "manager";
        }else if(role == Role.ADMIN){
            return "admin";
        }else {
            return "none";
        }
    }

    public static AAAMemberStatus aaaMemberStatusStrToEnum(String status){
        status = status.toLowerCase();
        if(status.equals("yes"))
            return AAAMemberStatus.YES;
        else
            return AAAMemberStatus.NO;
    }

    public static int aaaMemberStatusEnumToInt(AAAMemberStatus status){
        if (status == AAAMemberStatus.YES)
            return 1;
        else
            return 0;
    }

    public static AAAMemberStatus aaaMemberStatusIntToEnum(int status){
        if(status == 1){
            return AAAMemberStatus.YES;
        }else{
            return AAAMemberStatus.NO;
        }
    }

    public static String getGreetingByHour(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        if(hour >= 0 && hour < 12){
            return "Good Morning";
        }else if(hour >= 12 && hour < 16){
            return "Good Afternoon";
        }else if(hour >= 16 && hour < 21){
            return "Good Evening";
        }else{
            return "Hello";
        }
    }

    public static String quoteStr(String strToQuote){
        return "\'" + strToQuote + "\'";
    }

    public static boolean isNumeric(String numStr){
        final String EMPTY = "";
        boolean result;
        if(numStr.equals(EMPTY)){
            result = false;
        }else if(numStr == null){
            result = false;
        }else{
            try{
                double d = Double.parseDouble(numStr);
                result = true;
            }catch (Exception e){
                result = false;
            }
        }
        return result;
    }

    public static String carNameEnumToStr(CarName carName){
        String carNameStr;
        if(carName == CarName.SMART){
            carNameStr = "Smart";
        }else if(carName == CarName.ECONOMY){
            carNameStr = "Economy";
        }else if(carName == CarName.COMPACT){
            carNameStr = "Compact";
        }else if(carName == CarName.INTERMEDIATE){
            carNameStr = "Intermediate";
        }else if(carName == CarName.STANDARD){
            carNameStr= "Standard";
        }else if(carName == CarName.FULLSIZE){
            carNameStr = "Full Size";
        }else if(carName == CarName.SUV){
            carNameStr = "SUV";
        }else if(carName == CarName.MINIVAN){
            carNameStr = "MiniVan";
        }else if(carName == CarName.ULTRASPORTS){
            carNameStr = "Ultra Sports";
        }else{
            carNameStr = "None";
        }
        return carNameStr;
    }

    public static CarName carNameStrToEnum(String strCarName){
        CarName enumCarName;
        strCarName = strCarName.toLowerCase();
        if(strCarName.equals("smart")){
            enumCarName = CarName.SMART;
        }else if(strCarName.equals("economy")){
            enumCarName = CarName.ECONOMY;
        }else if(strCarName.equals("compact")){
            enumCarName = CarName.COMPACT;
        }else if(strCarName.equals("intermediate")){
            enumCarName = CarName.INTERMEDIATE;
        }else if(strCarName.equals("standard")){
            enumCarName = CarName.STANDARD;
        }else if(strCarName.equals("full size")){
            enumCarName = CarName.FULLSIZE;
        }else if(strCarName.equals("suv")){
            enumCarName = CarName.SUV;
        }else if(strCarName.equals("minivan")){
            enumCarName = CarName.MINIVAN;
        }else if(strCarName.equals("ultra sports")){
            enumCarName = CarName.ULTRASPORTS;
        }else{
            enumCarName = CarName.NONE;
        }
        return enumCarName;
    }

    public static String formatDate(Calendar calendar, String dateFormatPattern){
        String formattedDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatPattern);
        Date date = calendar.getTime();
        formattedDate = formatter.format(date);
        return formattedDate;
    }

    public static int getHourOfDayFromTimeStr(String timeWithAMPM){
        int result = 0;
        if(timeWithAMPM.equals(EIGHT_AM) || timeWithAMPM.equals(EIGHT_THIRTY_AM)){
            result = 8;
        }else if(timeWithAMPM.equals(NINE_AM) || timeWithAMPM.equals(NINE_THIRTY_AM)){
            result = 9;
        }else if(timeWithAMPM.equals(TEN_AM) || timeWithAMPM.equals(TEN_THIRTY_AM)){
            result = 10;
        }else if(timeWithAMPM.equals(ELEVEN_AM) || timeWithAMPM.equals(ELEVEN_THIRTY_AM)){
            result = 11;
        }else if(timeWithAMPM.equals(TWELVE_PM) || timeWithAMPM.equals(TWELVE_THIRTY_PM)){
            result = 12;
        }else if(timeWithAMPM.equals(ONE_PM) || timeWithAMPM.equals(ONE_THIRTY_PM)){
            result = 13;
        }else if(timeWithAMPM.equals(TWO_PM) || timeWithAMPM.equals(TWO_THIRTY_PM)){
            result = 14;
        }else if(timeWithAMPM.equals(THREE_PM) || timeWithAMPM.equals(THREE_THIRTY_PM)){
            result = 15;
        }else if(timeWithAMPM.equals(FOUR_PM) || timeWithAMPM.equals(FOUR_THIRTY_PM)){
            result = 16;
        }else if(timeWithAMPM.equals(FIVE_PM) || timeWithAMPM.equals(FIVE_THIRTY_PM)){
            result = 17;
        }else if(timeWithAMPM.equals(SIX_PM) || timeWithAMPM.equals(SIX_THIRTY_PM)){
            result = 18;
        }else if(timeWithAMPM.equals(SEVEN_PM) || timeWithAMPM.equals(SEVEN_THIRTY_PM)){
            result = 19;
        }else if(timeWithAMPM.equals(EIGHT_PM)){
            result = 20;
        }
        return result;
    }

    public static int getMinFromTimeStr(String time){
        int result = 0;
        if(time.contains(":00")){
            return 0;
        }else if(time.contains(":30")){
            return 30;
        }
        return result;
    }

    public static Calendar getCalendarDateWithTime(Calendar calendar, String time){
        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar.clear();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = getHourOfDayFromTimeStr(time);
        int min = getMinFromTimeStr(time);

        resultCalendar.set(year, month, date, hourOfDay, min);
        return resultCalendar;
    }

    public static boolean equalDateAndTime(Calendar date1, Calendar date2){
        boolean result = false;
        if((date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                (date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)) &&
                (date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)) &&
                (date1.get(Calendar.HOUR_OF_DAY) == date2.get(Calendar.HOUR_OF_DAY)) &&
                (date1.get(Calendar.MINUTE) == date2.get(Calendar.MINUTE)))){
            result = true;
        }
        return  result;
    }

    public static boolean equalDate(Calendar date1, Calendar date2){
        boolean result = false;
        if((date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                (date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)) &&
                (date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)))){
            result = true;
        }
        return  result;
    }

    public static boolean durationIsAWeek(Calendar date, Calendar otherDate){
        boolean result = false;
        Calendar date1 = Calendar.getInstance();
        date1.clear();

        Calendar date2 = Calendar.getInstance();
        date2.clear();

        date1.set(date.YEAR, date.MONTH, date.DAY_OF_MONTH);
        date2.set(otherDate.YEAR, otherDate.MONTH, otherDate.DAY_OF_MONTH);

        date1.add(Calendar.DAY_OF_MONTH, 6);
        if(equalDate(date1, date2)){
            result = true;
        }
        return result;
    }

    public static Calendar databaseDateTimeToCalendar(String dateTime){
        // Note: dateTime stored in db: yyyy-MM-dd HH:mm
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        String[] arrDatTime = dateTime.trim().split(" ");
        String date = arrDatTime[0].trim();
        String time = arrDatTime[1].trim();

        String[] arrDateParts = date.split("-");
        String[] arrTimeParts = time.split(":");

        int year = Integer.parseInt(arrDateParts[0].trim());
        int month = Integer.parseInt(arrDateParts[1].trim()) - 1; // month starts from 0
        int dayOfMonth = Integer.parseInt(arrDateParts[2].trim());

        int hourOfDay = Integer.parseInt(arrTimeParts[0].trim());
        int min = Integer.parseInt(arrTimeParts[1].trim());

        calendar.set(year, month, dayOfMonth, hourOfDay, min);
        return calendar;
    }

    public static String generateGUID(){
        // UUID: Globally Unique ID
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static String convertDBDateToTargetFormat(String dateInDatabaseFormat, String targetDateFormat){
        // Note: dateInDatabaseFormat must be in the format: yyyy-MM-dd HH:mm
        String result;
        Calendar calendar = databaseDateTimeToCalendar(dateInDatabaseFormat);
        result = formatDate(calendar, targetDateFormat);
        return result;
    }

    public static SharedPreferences getLogInSession(Context context){
        SharedPreferences session = context.getSharedPreferences(context.getString(R.string.sessions_preference_file_key), Context.MODE_PRIVATE);
        return session;
    }

    public static ArrayList<Integer> getdaysOfWeekBetweenDates(Calendar date, Calendar other) {
        final int DAY_ADDEND = 1;
        ArrayList<Integer> days = new ArrayList<Integer>();

        Calendar date1 = Calendar.getInstance();
        date1.clear();
        date1.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH)-1, date.get(Calendar.DAY_OF_MONTH));

        Calendar date2 = Calendar.getInstance();
        date2.clear();
        date2.set(other.get(Calendar.YEAR), other.get(Calendar.MONTH)-1, other.get(Calendar.DAY_OF_MONTH));

        while(date1.get(Calendar.DAY_OF_MONTH) <= date2.get(Calendar.DAY_OF_MONTH)) {
            days.add(date1.get(Calendar.DAY_OF_WEEK));
            date1.add(Calendar.DAY_OF_MONTH, DAY_ADDEND);
        }
        return days;
    }

    public static int getNumOfWeekdays(ArrayList<Integer> days) {
        int result = 0;
        for(int i = 0; i < days.size(); i++) {
            if(!(days.get(i) == Calendar.SUNDAY || days.get(i) == Calendar.SATURDAY)) {
                result++;
            }
        }
        return result;
    }

    public static int getNumOfWeekends(ArrayList<Integer> days) {
        int result = 0;
        for(int i = 0; i < days.size(); i++) {
            if(days.get(i) == Calendar.SUNDAY || days.get(i) == Calendar.SATURDAY) {
                result++;
            }
        }
        return result;
    }

    public static String getAmountInCurrency(double amount, String currencyFormat){
        DecimalFormat df = new DecimalFormat(currencyFormat);
        String currency = df.format(amount);
        currency = padZeroAsNeeded(currency);
        currency = "$" + currency;
        return currency;
    }

    private static String padZeroAsNeeded(String amount){
        String[] parts = amount.split("\\.");

        if(parts.length == 1){
            amount = amount + ".00";
        }else if(parts[1].trim().length() == 1){
            amount = amount + "0";
        }
        return amount;
    }

    public static boolean intToBool(int val){
        boolean result = false;
        if(val == 1){
            result = true;
        }
        return result;
    }

    public static int boolToInt(boolean bool){
        int result = 0;
        if(bool){
            result = 1;
        }
        return result;
    }
}
