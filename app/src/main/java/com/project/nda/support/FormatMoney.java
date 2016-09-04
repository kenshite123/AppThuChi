package com.project.nda.support;

import android.content.Context;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by ndact on 04/09/2016.
 */
public class FormatMoney {
    public String FormatEditText(Context context, EditText editText)
    {
        String moneyFomat = "";
        String edt = editText.getText().toString();
        String[] arrMoney = edt.split("[,]");
        for(int i = 0; i < arrMoney.length; i++)
        {
            moneyFomat += arrMoney[i].replace(",","");
        }
        return moneyFomat;
    }

    public String FormatTexView(Context context, String money)
    {
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            money = decimalFormat.format(Integer.parseInt(money));
        }
        catch (Exception e)
        {

        }
        return  money;
    }
}
