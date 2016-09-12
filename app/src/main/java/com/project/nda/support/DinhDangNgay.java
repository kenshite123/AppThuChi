package com.project.nda.Support;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by ndact on 06/09/2016.
 */
public class DinhDangNgay {

    public void DinhDangDatePicker(Context context, TextView texiView, int dayOfMonth, int monthOfYear, int year) {
        if(dayOfMonth < 10) {
            if(monthOfYear + 1<10)
            {
                texiView.setText("0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" +year);
            }
            else
            {
                texiView.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }

        }
        else if(monthOfYear + 1 < 10 )
        {
            texiView.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
        }
        else
        {
            texiView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

        }
    }
    public String DinhDangNgay(Context context, TextView textView)
    {
        String ngaydadinhdang = null;
        String[] arrDate=textView.getText().toString().split("/");
        String ngay = arrDate[0];
        String thang = arrDate[1];
        String nam = arrDate[2];
        return ngaydadinhdang = nam + "-" + thang + "-" + ngay;
    }

}
