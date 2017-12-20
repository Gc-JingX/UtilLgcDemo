package com.lgc.garylianglib.util.data;



import com.lgc.garylianglib.R;

import java.text.DecimalFormat;

public class PriceUtils {

    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static String formatPriceUnit(String calcTotalPrice) {
        return ResUtil.getFormatString(R.string.format_unit_price, calcTotalPrice);
    }

    public static String formatPriceAndUnit(String calcTotalPrice) {
        return ResUtil.getFormatString(R.string.format_unit_price, formatPriceText(calcTotalPrice));
    }

    public static String formatPrice(double calcTotalPrice) {
        return decimalFormat.format(calcTotalPrice);
    }

    public static String formatPriceText(String calcTotalPrice) {
        return decimalFormat.format(Double.parseDouble(calcTotalPrice));
    }

}
