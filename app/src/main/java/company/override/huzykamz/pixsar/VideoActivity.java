package company.override.huzykamz.pixsar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

import pixsor.app.huzykamz.pixoradmin.R;

public class VideoActivity extends AppCompatActivity {


    String Url ;
   private FullscreenVideoLayout videoLayout;
    private ProgressBar mbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        videoLayout = (FullscreenVideoLayout) findViewById(R.id.post_video);


        Intent inn = getIntent();

        Url = inn.getStringExtra("VideoUrl");

        Uri videoUri = Uri.parse(Url);

        try {
            videoLayout.setVideoURI(videoUri);





        } catch (IOException e) {
            e.printStackTrace();
        }



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
    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        //    mbar = (ProgressBar) findViewById(R.id.progressBar_video);
              mbar.setVisibility(View.VISIBLE);


        }

        @Override
        protected Void doInBackground(Void... params) {

            videoLayout = (FullscreenVideoLayout) findViewById(R.id.post_video);
            Intent inn = getIntent();


            Url = inn.getStringExtra("VideoUrl");

            Uri videoUri = Uri.parse(Url);


            try {


                videoLayout.setVideoURI(videoUri);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            mbar.setVisibility(View.INVISIBLE);

        }
    }

}
