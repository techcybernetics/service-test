package com.services.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTimeUtil {
    public DateAndTimeUtil(){

    }
    public static String getTimeStamp(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String strDate=dateFormat.format(new Date());
        return strDate;
    }
    public static String getNewTimeStamp(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd-HH-mm.ss.SSSSSS");
        String strDate=dateFormat.format(new Date());
        return strDate;
    }
}
