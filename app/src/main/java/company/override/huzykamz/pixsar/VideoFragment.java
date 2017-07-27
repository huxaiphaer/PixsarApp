package company.override.huzykamz.pixsar;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import company.override.huzykamz.pixsar.model.Posts;
import pixsor.app.huzykamz.pixoradmin.R;
import youtube.api.key.Config;

public class VideoFragment extends Fragment { public VideoFragment() {
    // Required empty public constructor
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_video_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.youtube_blog, container, false);


        check_video_txt = (TextView) v.findViewById(R.id.check_video_txt);


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
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").child("SingleEvent").child(eventname).child("Videos");


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


        mRecyclerYoutube = (RecyclerView) v.findViewById(R.id.mRecyclerYoutube);
        mRecyclerYoutube.hasFixedSize();
        mRecyclerYoutube.setLayoutManager(new LinearLayoutManager(getActivity()));

        Load();


        return v;
    }


    final static Context cn = null;
    private static String eventname = "";
    public Context c;
    TextView check_video_txt;
    private DatabaseReference mDatabseUsers;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;
    private String title_eve;
    private RecyclerView mRecyclerYoutube;


    public void Load() {

        FirebaseRecyclerAdapter<Posts, VideoInfoHolder> firebaseRecyclerAdapter_ = new FirebaseRecyclerAdapter<Posts, VideoInfoHolder>(Posts.class,
                R.layout.item_youtube,
                VideoInfoHolder.class,
                mDatabase) {
            @Override
            protected void populateViewHolder(final VideoInfoHolder viewHolder, final Posts model, final int position) {


                if (model.getEventVideo() != null) {

                     check_video_txt.setVisibility(View.GONE);

                    final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                            //   Toast.makeText(getActivity(), "Error in connectivity...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailView.setVisibility(View.VISIBLE);
                            viewHolder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                        }
                    };


                    viewHolder.youTubeThumbnailView.initialize(Config.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                            youTubeThumbnailLoader.setVideo(model.getEventVideo());
                            youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                        }

                        @Override
                        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                            //  Toast.makeText(getActivity(), "Error in connectivity...", Toast.LENGTH_SHORT).show();
                            //write something for failure
                        }
                    });

                    viewHolder.post_desc.setText(model.getEventDescription());
                    viewHolder.post_title.setText(model.getEventTitle());


                    viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //    Toast.makeText(getActivity(),"Me",Toast.LENGTH_LONG).show();
                            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) getContext(), Config.YOUTUBE_API_KEY, model.getEventVideo());
                            getContext().startActivity(intent);
                        }
                    });
                } else {

                    check_video_txt.setVisibility(View.VISIBLE);

                }


            }
        };

        mRecyclerYoutube.setAdapter(firebaseRecyclerAdapter_);


    }

    /**
     * Created by ofaroque on 8/13/15.
     */


    public static class VideoInfoHolder extends RecyclerView.ViewHolder {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        protected ImageView playButton;
        YouTubeThumbnailView youTubeThumbnailView;
        private Context ctx;
        private View mView;
        private TextView post_title;
        private TextView post_desc;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            mView = itemView;
            playButton = (ImageView) mView.findViewById(R.id.btnYoutube_player);
            //  playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) mView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) mView.findViewById(R.id.youtube_thumbnail);

            post_title = (TextView) mView.findViewById(R.id.headlines_video_txt);
            post_desc = (TextView) mView.findViewById(R.id.description_video_txt);

        }


        public void setTitle(String title) {

            TextView post_title = (TextView) mView.findViewById(R.id.headlines_video_txt);
            post_title.setText(title);

        }


        public void setDesc(String desc) {

            TextView post_desc = (TextView) mView.findViewById(R.id.description_video_txt);
            post_desc.setText(desc);
        }

        /*

            @Override
            public void onClick(View v) {

                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, Config.YOUTUBE_API_KEY, VideoID[getLayoutPosition()]);
                ctx.startActivity(intent);
            }*/


    }


}
