package company.override.huzykamz.pixsar;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import company.override.huzykamz.pixsar.model.Posts;
import pixsor.app.huzykamz.pixoradmin.R;

public class BlogScrolling extends AppCompatActivity {



    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabseUsers;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;
    public Context c;
    final static Context cn= null;
    private   static  String eventname="";
    private FirebaseAuth mAuth;
    private String EName="Huzy";
    private FirebaseAuth.AuthStateListener mAuthLitsener;
    private static String EventName= "";
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    private String title_eve;
    private String pic_;
    private ImageView image;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private int[] tabIcons = {
            R.drawable.stack_of_photos,
            R.drawable.video_playlist
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_scrolling);


        Intent inn = getIntent();
        title_eve = inn.getStringExtra("PARTY_NAME");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_two);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_eventName);
        mTitle.setText(title_eve);


    //      image =(ImageView) findViewById(R.id.image_scroll);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }



    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }

/*
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }*/

    void Load(){


        //checkUserExists();
        // mAuth.addAuthStateListener(mAuthLitsener);
        try {
            FirebaseRecyclerAdapter<Posts, BlogScrolling.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Posts, BlogScrolling.BlogViewHolder>(Posts.class,
                    R.layout.item_activity,
                    BlogScrolling.BlogViewHolder.class,
                    mDatabase) {
                @Override
                protected void populateViewHolder(final BlogScrolling.BlogViewHolder viewHolder , final Posts model, final int position) {

                    final String key_post = getRef(position).getKey();
                    final String key_post_image = getRef(position).getKey();
                    final String videoUrl = model.getEventVideo();
                    final String Image_Url = model.getEventImage();
                        viewHolder.setTitle(model.getEventTitle());
                        viewHolder.setDesc(model.getEventDescription());
                        viewHolder.setImage(c, model.getEventImage());








                   try {
                       viewHolder.imagePost.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               final Bitmap attachedbitmap = ((BitmapDrawable) viewHolder.imagePost.getDrawable()).getBitmap();
                               Intent i = new Intent(BlogScrolling.this, ViewSingleImage.class);
                               i.putExtra("ImageUrl", viewHolder.encodeTobase64(attachedbitmap));
                               startActivity(i);
                               //  Toast.makeText(BlogScrolling.this," position "+ position,Toast.LENGTH_LONG);
                           }
                       });
                   }
                   catch (OutOfMemoryError e){
                       e.printStackTrace();
                   }

                //    final String key_post_image = getRef(position).getKey();




                }
            };

            mRecyclerview.setAdapter(firebaseRecyclerAdapter);
        }
        catch (Exception ex){

            System.out.println("Error "+ ex);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PhotoFragment(), "Photos");
//        adapter.addFragment(new VideoFragment(), "Videos");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public static class BlogViewHolder extends RecyclerView.ViewHolder{


         View mView;
         ImageView imagePost;
        private FullscreenVideoLayout videoLayout;
        private Context cx;
         ImageView videoView;
      //  IRoomViewClick mListener;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            imagePost =(ImageView)mView.findViewById(R.id.post_image);






        }
        public void setTitle(String title){

            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }




        public void setDesc(String desc){

            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }


        public void setImage(final Context c,final String imageUrl) {


       //     int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
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
        public static String encodeTobase64(Bitmap image) {

            Bitmap immagex = image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] b = baos.toByteArray();
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);


                return imageEncoded;


            // Log.e("LOOK", imageEncoded);

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
