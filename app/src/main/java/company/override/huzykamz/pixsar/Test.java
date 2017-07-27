package company.override.huzykamz.pixsar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pixsor.app.huzykamz.pixoradmin.R;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
