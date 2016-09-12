package com.project.nda.Support;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by ndact on 04/09/2016.
 */
public class DinhDangTienTe {

    public String DinhDangTextView(Context context, String tiente)
    {
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            tiente = decimalFormat.format(Integer.parseInt(tiente));
        }
        catch (Exception e)
        {

        }
        return  tiente;
    }
}
