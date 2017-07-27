package company.override.huzykamz.pixsar;

import android.os.Bundle;
import android.app.Activity;

import pixsor.app.huzykamz.pixoradmin.R;

public class ScrollingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
    }
}
