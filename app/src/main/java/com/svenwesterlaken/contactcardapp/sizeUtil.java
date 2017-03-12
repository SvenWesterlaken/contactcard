package com.svenwesterlaken.contactcardapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Sven Westerlaken on 11-3-2017.
 */

public class sizeUtil {

    public static int convertDPtoPX(Context c, int pd) {
        Resources r = c.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                pd,
                r.getDisplayMetrics()
        );

        return px;
    }



}
