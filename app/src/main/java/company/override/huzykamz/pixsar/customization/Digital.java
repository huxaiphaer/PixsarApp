package company.override.huzykamz.pixsar.customization;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by HUZY_KAMZ on 3/25/2016.
 */
public class Digital extends TextView {



    public Digital(Context context) {
        super(context);
        setFont();
    }
    public Digital(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public Digital(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/digital.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
