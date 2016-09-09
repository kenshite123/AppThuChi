package com.project.nda.support;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by ndact on 04/09/2016.
 */
public class FormatMoney {

    public String FormatTextView(Context context, String money)
    {
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            money = decimalFormat.format(Integer.parseInt(money));
        }
        catch (Exception e)
        {

        }
        return  money;
    }
}
