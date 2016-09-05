package com.project.nda.support;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by ndact on 06/09/2016.
 */
public class FormatDateTime {

    public void FormatDatePicker(Context context, TextView texiView, int dayOfMonth, int monthOfYear, int year) {
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

}
