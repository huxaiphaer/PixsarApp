package company.override.huzykamz.pixsar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import company.override.huzykamz.pixsar.model.Posts;
import pixsor.app.huzykamz.pixoradmin.R;

public class BlogActivity extends AppCompatActivity {


    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabseUsers;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;
    private Context c;
    private   static  String eventname="";
    private FirebaseAuth mAuth;
    private String EName="Huzy";
    private FirebaseAuth.AuthStateListener mAuthLitsener;
    private static String EventName= "";
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    private String title_eve;
    private ImageView imagePost;
   //private long currentVisiblePosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog);
        Intent in_ = getIntent();

            if (null != in_) {
                title_eve = in_.getStringExtra("PARTY_NAME");
            }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title_eve);

        imagePost =(ImageView)findViewById(R.id.post_image);

        mAuth = FirebaseAuth.getInstance();
        mAuthLitsener =new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){


                    Intent loginIntent = new Intent (BlogActivity.this,MainActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }



            }
        };



        Intent inn = getIntent();
        try {
            if (null != inn) {
                eventname = inn.getStringExtra("PARTY_NAME");
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").child("SingleEvent").child(eventname);

            }
        }

        catch (Exception ex){
            System.out.println("Error "+ ex);


        }







        System.out.println("Output here   :  " + mDatabase);
   //     Toast.makeText(getApplicationContext(), "" + mDatabase, Toast.LENGTH_LONG).show();


        mDatabseUsers =FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabseUsers.keepSynced(true);
        try {
            mDatabase.keepSynced(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        mRecyclerview =(RecyclerView)findViewById(R.id.mRecyclerview);
        mRecyclerview.hasFixedSize();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        // imagePost  =(ImageView) findViewId(R.id.post_image);




  Load();

    }



void Load(){


    //checkUserExists();
    // mAuth.addAuthStateListener(mAuthLitsener);
    try {
        FirebaseRecyclerAdapter<Posts, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Posts, BlogViewHolder>(Posts.class,
                R.layout.item_activity,
                BlogViewHolder.class,
                mDatabase) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, final Posts model, final int position) {

                final String key_post = getRef(position).getKey();
                final String videoUrl = model.getEventVideo();

                try {
                    viewHolder.setTitle(model.getEventTitle());
                    viewHolder.setDesc(model.getEventDescription());
                    viewHolder.setImage(c, model.getEventImage());
                    viewHolder.setGif(c,model.getEventVideo());}
                catch (NullPointerException e){

                }


                viewHolder.videoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //          Toast.makeText(getApplicationContext()," position "+ position,Toast.LENGTH_LONG);
                        Intent i = new Intent(BlogActivity.this,VideoActivity.class);
                        i.putExtra("VideoUrl", videoUrl);
                        startActivity(i);
                    }
                });
                final String key_post_image = getRef(position).getKey();




            }
        };

        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
    }
    catch (Exception ex){

        System.out.println("Error "+ ex);
    }
}

    public  void SaveImage(ImageView img){
        img.buildDrawingCache();

        Bitmap bmp = img.getDrawingCache();

        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

        File file = new File(storageLoc, "Pixsar" + ".jpg");

        try{
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            scanFile(getApplicationContext(), Uri.fromFile(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Toast.makeText(getApplicationContext(),"Image Saved to gallery ",Toast.LENGTH_LONG).show();
    }
    //noinspection SimplifiableIfStatement
    private static void scanFile(Context context, Uri imageUri){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);

    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder{


        View mView;

        private ImageView imagePost;
        private  ImageView save_image_blog;
        private FullscreenVideoLayout videoLayout;
        private ImageView videoView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            imagePost =(ImageView)mView.findViewById(R.id.post_image);
           // save_image_blog =(ImageView)mView.findViewById(R.id.save_image_blog);
            videoLayout = (FullscreenVideoLayout) mView.findViewById(R.id.post_video);

        }
        public void setTitle(String title){

            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }

        public void setDesc(String desc){

            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }


        public void setImage(final Context c,final String imageUrl){


            int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
            //

            Picasso.with(c).load(imageUrl).error(R.mipmap.add_btn).placeholder(R.mipmap.add_btn)
                    .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .skipMemoryCache()
                    .networkPolicy(NetworkPolicy.OFFLINE).into(imagePost, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    //Reloading an image again ...
                    Picasso.with(c).load(imageUrl).error(R.mipmap.add_btn).placeholder(R.mipmap.add_btn)
                            .into(imagePost);
                }
            });

        }


        public void setGif(Context c, String videoUrl){

            String  url = "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogVideos%2Fplay_video.png?alt=media&token=48f8958a-51a3-4a0e-9073-26b5331a7f2e";

            if(videoUrl!=null){
                videoView.setVisibility(View.VISIBLE);
             //   Picasso.with(c).load(url).fit().into(videoView);

            }
            else{

                videoView.setVisibility(View.GONE);
            }
        }
        public void setVideo(final Context c, final String videoUrl){


            // videoLayout.setActivity(this);
            // videoLayout.setActivity(get);
            try {
                if (videoUrl!=null) {
                    try {
                        Uri videoUri = Uri.parse(videoUrl);
                        try {
                            videoLayout.setVideoURI(videoUri);
                        //    videoLayout.reset();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        System.out.println("Error :" + e);
                    }

                } else {
                    videoLayout.setVisibility(View.GONE);
                }
            }
            catch (Exception e){

            }


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


}
