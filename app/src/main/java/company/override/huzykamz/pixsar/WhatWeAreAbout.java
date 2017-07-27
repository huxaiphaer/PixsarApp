package company.override.huzykamz.pixsar;

import android.graphics.YuvImage;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.TextView;

import pixsor.app.huzykamz.pixoradmin.R;

public class WhatWeAreAbout extends AppCompatActivity {



    TextView whatweAreabout,search,reach_us,theme_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_we_are_about);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("What we are About");
        // get our html content
        String htmlAsString = getString(R.string.what_we_are_about);
        String reach_usString = getString(R.string.reach_us);
        String searchString = getString(R.string.how_to_search);
        String theme_appString = getString(R.string.theme_app);

        //Html span
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView
        Spanned searchSpanned =Html.fromHtml(searchString);
        Spanned reach_usSpanned =Html.fromHtml(reach_usString);
        Spanned theme_appSpanned =Html.fromHtml(theme_appString);

// set the html content on the TextView

        whatweAreabout = (TextView) findViewById(R.id.whatweAreabout) ;
        reach_us =(TextView) findViewById(R.id.reach_us);
        search   =(TextView) findViewById(R.id.search);
        theme_app =(TextView) findViewById(R.id.theme_app);

        whatweAreabout.setText(htmlAsSpanned);
        search.setText(searchSpanned);
        theme_app.setText(theme_appSpanned);
        reach_us.setText(reach_usSpanned);
       // whatweAreabout.setText(Html.fromHtml(R.string.wh));

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
}
