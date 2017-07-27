package company.override.huzykamz.pixsar;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pushbots.push.Pushbots;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import company.override.huzykamz.pixsar.model.Advert;
import company.override.huzykamz.pixsar.model.Event;
import pixsor.app.huzykamz.pixoradmin.About;
import pixsor.app.huzykamz.pixoradmin.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView  mRecyclerview_event_view;
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitsener;
    SliderLayout sliderShow;
    Query queryRef;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    private Context c;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("PixsarApp");
       // toolbar.setLogo(R.drawable.ic_icon);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sliderShow = (SliderLayout) findViewById(R.id.slider);

   //     mRecyclerview_event_view =(RecyclerView) findViewById(R.id.mRecyclerview_main);
      //  mRecyclerview_event_view.hasFixedSize();
     //   mRecyclerview_event_view.setLayoutManager(new LinearLayoutManager(this));

        // Slider images
/*
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Graduations", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fgrad.jpg?alt=media&token=cfdaa3e3-e906-4e0f-aafa-56b0c1884950");
        url_maps.put("Wedding Parties", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fwedding.jpg?alt=media&token=2174363e-9836-4ba9-8aee-2f147ea57b57");
        url_maps.put("Birthday Parties", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fbirthday.jpg?alt=media&token=eff4115d-7420-4796-a882-fb4e52e88b9f");
        url_maps.put("Introduction Parties", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fintroduction.jpg?alt=media&token=bbb31e16-0e8f-42f6-9670-468a7fb6f6ec");




        sliderShow = (SliderLayout) findViewById(R.id.slider);



        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name));



            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderShow.addSlider(textSliderView);
        }
     //   sliderShow.addSlider(textSliderView);
        sliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        sliderShow.setDuration(10000);
     //   sliderShow.addOnPageChangeListener(this);
     */
        mDatabase = FirebaseDatabase.getInstance().getReference("Blog").child("Adverts");

       // System.out.println("Results "+ mDatabase);
        //Toast.makeText(getApplicationContext(),""+mDatabase,Toast.LENGTH_LONG).show();

        //GetAdverts();
        Slider();




        Pushbots.sharedInstance().registerForRemoteNotifications();


        Pushbots.sharedInstance().setCustomHandler(customHandler.class);



       // setupWindowAnimations();
       /* if (android.os.Build.VERSION.SDK_INT >= 21) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide();
            fade.setDuration(1000);
            getWindow().setReturnTransition(slide);
        }*/
    }


 /*   @TargetApi(21)
    private void setupWindowAnimations() {
       Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        fade.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }*/


    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    private void addNotification(String title, String text) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_icon)
                        .setContentTitle(title)
                        .setContentText(text);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    void Slider(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Advert> td = new HashMap<String, Advert>();
                ArrayList<Advert> values = new ArrayList<>(td.values());


                HashMap<String,String> url_maps = new HashMap<String, String>();
                // using the dataSnapShot ..
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    Advert ad = postSnapshot.getValue(Advert.class);

                    // notifications coming soon.
                 //   addNotification("PixsarApp Function Alert",ad.getTitle());

                  //  Toast.makeText(getApplicationContext(),""+ad.getImageAdvert(),Toast.LENGTH_LONG).show();
                    url_maps.put(ad.getTitle(),ad.getImageAdvert());

                }







               // List<String> keys = new ArrayList<String>(td.keySet());
/*
                for (Advert ad: values) {

                  //  Toast.makeText(getApplicationContext(),""+ad.getImageAdvert(),Toast.LENGTH_LONG).show();
                    url_maps.put(ad.getTitle(),ad.getImageAdvert());


                }*/

                for(String name : url_maps.keySet()){
                    TextSliderView textSliderView = new TextSliderView(getApplicationContext());
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(url_maps.get(name));
                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra",name);
                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                            //Toast.makeText(getApplicationContext(),
                                  //  "Advertise Here ",Toast.LENGTH_LONG).show();
                        }
                    });

                    sliderShow.addSlider(textSliderView);
                }

                //   sliderShow.addSlider(textSliderView);
                sliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderShow.setCustomAnimation(new DescriptionAnimation());
                sliderShow.setDuration(10000);






            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        sliderShow = (SliderLayout) findViewById(R.id.slider);




    }

    public void GetAdverts()
    {


        FirebaseRecyclerAdapter<Advert,MainActivity.EventViewHolder> firebaseRecyclerAdapter_ = new FirebaseRecyclerAdapter<Advert, MainActivity.EventViewHolder>(Advert.class,
                R.layout.slider_image,
                MainActivity.EventViewHolder.class,
                mDatabase) {
            @Override
            protected void populateViewHolder(MainActivity.EventViewHolder viewHolder, final Advert model, final int position) {

            try{


                HashMap<String,String> url_maps = new HashMap<String, String>();
                List<Advert> itemlist = new ArrayList<>();






                   // url_maps.put(model.getTitle(), model.getImageAdvert());
                url_maps.put("Graduations", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fgrad.jpg?alt=media&token=cfdaa3e3-e906-4e0f-aafa-56b0c1884950");
                url_maps.put("Wedding Parties", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fwedding.jpg?alt=media&token=2174363e-9836-4ba9-8aee-2f147ea57b57");
                url_maps.put("Birthday Parties", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fbirthday.jpg?alt=media&token=eff4115d-7420-4796-a882-fb4e52e88b9f");
                url_maps.put("Introduction Parties", "https://firebasestorage.googleapis.com/v0/b/pixsor-acb86.appspot.com/o/BlogImages%2Fintroduction.jpg?alt=media&token=bbb31e16-0e8f-42f6-9670-468a7fb6f6ec");






                for(String name : url_maps.keySet()){
                    TextSliderView textSliderView = new TextSliderView(getApplicationContext());
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(url_maps.get(name));
                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra",name);
                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                            Toast.makeText(getApplicationContext(),
                                    "News Details ",Toast.LENGTH_LONG).show();
                        }
                    });

                    sliderShow.addSlider(textSliderView);
                }

                //   sliderShow.addSlider(textSliderView);
                sliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderShow.setCustomAnimation(new DescriptionAnimation());
                sliderShow.setDuration(10000);






            }
            catch (Exception e){

                e.printStackTrace();
            }




            }
        };


    }

    public void OnGraduation(View v){
        Intent i = new Intent(MainActivity.this,GraduationListActivity.class);
        startActivity(i);


    }

    public void OnIntroduction(View v){
        Intent i = new Intent(MainActivity.this,IntroductionListActivity.class);
        startActivity(i);


    }
    public void OnBirthday(View v){
        Intent i = new Intent(MainActivity.this,BirthdayListActivity.class);
        startActivity(i);


    }

    public void OnWedding(View v){
        Intent i = new Intent(MainActivity.this,WeddingListActivity.class);
        startActivity(i);


    }


    void Load(){
    FirebaseRecyclerAdapter<Event,MainActivity.EventViewHolder> firebaseRecyclerAdapter_ = new FirebaseRecyclerAdapter<Event, MainActivity.EventViewHolder>(Event.class,
            R.layout.item_event_view,
            MainActivity.EventViewHolder.class,
            queryRef) {
        @Override
        protected void populateViewHolder(MainActivity.EventViewHolder viewHolder, final Event model, final int position) {







        }
    };



}

/*
    @Override
    protected void onStart() {
        super.onStart();
        //   mAuth.addAuthStateListener(mAuthLitsener);

        FirebaseRecyclerAdapter<Event,MainActivity.EventViewHolder> firebaseRecyclerAdapter_ = new FirebaseRecyclerAdapter<Event, MainActivity.EventViewHolder>(Event.class,
                R.layout.item_event_view,
                MainActivity.EventViewHolder.class,
                queryRef) {
            @Override
            protected void populateViewHolder(MainActivity.EventViewHolder viewHolder, final Event model, final int position) {
                final String key_post = getRef(position).getKey();
                viewHolder.setEventLocation(model.getEventLocation());
                viewHolder.setEventName(model.getEventName());
                viewHolder.setEventType(model.getEventType());
                viewHolder.setEventDate(model.getEventDate());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent loadMainActivity = new Intent(MainActivity.this,BlogActivity.class);
                        loadMainActivity.putExtra("PARTY_NAME", model.getEventName());
                        startActivity(loadMainActivity);

                    }
                });





            }
        };

        mRecyclerview_event_view.setAdapter(firebaseRecyclerAdapter_);


    }*/


    public static class EventViewHolder extends RecyclerView.ViewHolder{


        private    View mView;
        private ImageView event_img;


        public EventViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            //event_img = (ImageView)mView.findViewById(R.id.event_img);



        }



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_graduation) {
            // Handle the camera action
            Intent i = new Intent(MainActivity.this,GraduationListActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_wedding) {
            Intent i = new Intent(MainActivity.this,WeddingListActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Share your friends memories such as Birthday, Graduation, Wedding and Introduction parties  " +
                    "however remotely you may be  download  PixsarApp Better Moments https://play.google.com/store/apps/details?id=pixsor.app.huzykamz.pixoradmin&hl=en";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));




        }
        else if (id == R.id.nav_introduction) {
            Intent i = new Intent(MainActivity.this,IntroductionListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_birthday) {
            Intent i = new Intent(MainActivity.this,BirthdayListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {

            Intent i = new Intent(MainActivity.this, About.class);
            startActivity(i);

        }
        else if(id==R.id.nav_what){
            Intent i = new Intent(MainActivity.this, WhatWeAreAbout.class);
            startActivity(i);
        }
        else if (id ==R.id.nav_contact){
            Intent i = new Intent(MainActivity.this, ContactUs.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
