package company.override.huzykamz.pixsar.customization;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import company.override.huzykamz.pixsar.FontCache;

/**
 * Created by HUZY_KAMZ on 12/12/2016.
 */

public class LobsterTwo  extends TextView{


    public LobsterTwo(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public LobsterTwo(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public LobsterTwo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("LobsterTwo-Regular.ttf", context);
        setTypeface(customFont);
    }
}
