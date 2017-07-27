package company.override.huzykamz.pixsar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import company.override.huzykamz.pixsar.model.Posts;
import pixsor.app.huzykamz.pixoradmin.R;

public class PhotoFragment extends Fragment {


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
   // private static String EventName= "";
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    private String title_eve;
    private String pic_;
    private ImageView image;





    public PhotoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_photo_fragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.content_blog_scrolling, container, false);


        Intent in_ = getActivity().getIntent();

        if (null != in_) {
            title_eve = in_.getStringExtra("PARTY_NAME");

        }
        // Top image attachment ...
        Bundle extras = getActivity().getIntent().getExtras();
        String imageString = extras.getString("PIC");


        Intent inn = getActivity().getIntent();
        try {
            if (null != inn) {
                eventname = inn.getStringExtra("PARTY_NAME");
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").child("SingleEvent").child(eventname);


            }
        } catch (Exception ex) {
            System.out.println("Error " + ex);


        }


        System.out.println("Output here   :  " + mDatabase);
        //     Toast.makeText(getApplicationContext(), "" + mDatabase, Toast.LENGTH_LONG).show();


        mDatabseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabseUsers.keepSynced(true);
        try {
            mDatabase.keepSynced(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRecyclerview = (RecyclerView) v.findViewById(R.id.mRecyclerview_two);
        mRecyclerview.hasFixedSize();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //   mRecyclerview.setNestedScrollingEnabled(false);
        // imagePost  =(ImageView) findViewId(R.id.post_image);


        try {
            Load();
        } catch (OutOfMemoryError e) {

            System.out.println("Here is the Error Huzy :" + e);
        }

        return v;
    }




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
                                Intent i = new Intent(getContext(), ViewSingleImage.class);
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





    public static class BlogViewHolder extends RecyclerView.ViewHolder{


        View mView;
        ImageView imagePost;
        private FullscreenVideoLayout videoLayout;
        private Context cx;
        ImageView videoView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            imagePost =(ImageView)mView.findViewById(R.id.post_image);

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


}
