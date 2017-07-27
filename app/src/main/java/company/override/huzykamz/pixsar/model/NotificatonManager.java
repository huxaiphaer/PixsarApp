package company.override.huzykamz.pixsar.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pixsor.app.huzykamz.pixoradmin.R;

/**
 * Created by HUZY_KAMZ on 12/9/2016.
 */

public class NotificatonManager extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);



        Bundle extras = getIntent().getExtras();
        if(null!= extras && getIntent().getExtras().containsKey("message")){

            TextView message =(TextView) findViewById(R.id.notification_id);
            message.setText(extras.getString("message"));
        }
    }
}
