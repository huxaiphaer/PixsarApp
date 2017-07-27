package company.override.huzykamz.pixsar.customization;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import company.override.huzykamz.pixsar.FontCache;

/**
 * Created by HUZY_KAMZ on 3/25/2016.
 */
public class FjallaOneRegular extends TextView {


    public FjallaOneRegular(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public FjallaOneRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public FjallaOneRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("FjallaOne-Regular.ttf", context);
        setTypeface(customFont);
    }
}
