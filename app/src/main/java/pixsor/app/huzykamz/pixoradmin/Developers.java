package pixsor.app.huzykamz.pixoradmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Developers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        //setting an action bar..
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Developers");

    }
    public boolean onOptionsItemSelected(MenuItem item) {






        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;



        }
        return super.onOptionsItemSelected(item);




    }
}
