package pixsor.app.huzykamz.pixoradmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //setting an action bar..
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About");

    }


    public void onDevelopers(View v){

        Intent i = new Intent(About.this , Developers.class);
        startActivity(i);


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
