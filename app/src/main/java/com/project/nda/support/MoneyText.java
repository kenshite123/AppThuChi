package com.project.nda.support;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by ndact on 31/08/2016.
 */
public class MoneyText implements TextWatcher{

    EditText edtNhapTienChi;

    public MoneyText(EditText edtNhapTienChi)
    {
        this.edtNhapTienChi = edtNhapTienChi;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() == 0){
            return;
        }

        //gioi han 1 ty
        String value = s.toString().replaceAll(",", "");
        if (value.length() > 10) {
            value = value.substring(0, 10);
        }
        String formattedPrice = getFormatedCurrency(value);
        if (!(formattedPrice.equalsIgnoreCase(s.toString()))) {
            /***
             * The below given line will call the function recursively
             * and will ends at this if block condition
             ***/
            edtNhapTienChi.setText(formattedPrice);
            edtNhapTienChi.setSelection(edtNhapTienChi.length());
        }
    }

    public String getFormatedCurrency(String value) {
        try {
            NumberFormat formatter = new DecimalFormat("#,###,###,###");
            return formatter.format(Double.parseDouble(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
