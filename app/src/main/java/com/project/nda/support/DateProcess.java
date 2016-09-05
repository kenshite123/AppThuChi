package com.project.nda.support;

/**
 * Created by DELL on 9/5/2016.
 */
public class DateProcess {
    public static int getLastDayOfMonth(int month, int year){
        int ngay=1;
        switch (month){
            case 1: ngay=31; break;
            case 2:
                if(((year%4==0)&&(year%100!=0))||(year%400==0))
                    ngay=29;
                else
                    ngay=28;
                break;
            case 3: ngay=31; break;
            case 4: ngay=30; break;
            case 5: ngay=31; break;
            case 6: ngay=30; break;
            case 7: ngay=31; break;
            case 8: ngay=31; break;
            case 9: ngay=30; break;
            case 10: ngay=31; break;
            case 11: ngay=30; break;
            case 12: ngay=31; break;
        }

        return ngay;
    }
}
