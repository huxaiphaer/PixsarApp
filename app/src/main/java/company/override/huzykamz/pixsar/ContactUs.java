package company.override.huzykamz.pixsar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pixsor.app.huzykamz.pixoradmin.R;

public class ContactUs extends AppCompatActivity {
  TextView  info , contact_one, contact_two, contact_three,theme_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");
        info = (TextView) findViewById(R.id.contact_us);
        contact_one =(TextView) findViewById(R.id.contact_one);
        contact_two =(TextView) findViewById(R.id.contact_two) ;
        contact_three =(TextView) findViewById(R.id.contact_three);
        theme_app =(TextView) findViewById(R.id.theme_two);
        String htmlAsString = getString(R.string.contact_us);
        String theme_appString = getString(R.string.theme_app);
        Spanned theme_appSpanned =Html.fromHtml(theme_appString);

        Spanned searchContac = Html.fromHtml(htmlAsString);

        theme_app.setSelected(true);

        info.setText(searchContac);
       // theme_app.setText(theme_appSpanned);

        contact_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_CALL);

                in.setData(Uri.parse("tel:+256 750 182 773"));

                startActivity(in);
            }
        });

        contact_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_CALL);

                in.setData(Uri.parse("tel:+256 700 304 850"));

                startActivity(in);
            }
        });
        contact_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_CALL);

                in.setData(Uri.parse("tel:+256 704 594 180"));

                startActivity(in);
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);


    }



    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended, need detect flag
                // from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }


    }

}
