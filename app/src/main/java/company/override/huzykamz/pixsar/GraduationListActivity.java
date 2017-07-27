package company.override.huzykamz.pixsar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.internal.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;

import company.override.huzykamz.pixsar.model.Event;
import pixsor.app.huzykamz.pixoradmin.R;




public class GraduationListActivity extends AppCompatActivity {


    private RecyclerView mRecyclerview_event_view;
    private DatabaseReference mDatabase;
    private Context c;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitsener;
    Query queryRef;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;
   // public   FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter_;
   private boolean defaultAdapter = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduation);

        //setting an action bar..
       getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Graduations");


        mRecyclerview_event_view =(RecyclerView) findViewById(R.id.mRecyclerview_event_view);
        mRecyclerview_event_view.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerview_event_view.setLayoutManager(layoutManager);



//        mAuth = FirebaseAuth.getInstance();

        try {

        mDatabase = FirebaseDatabase.getInstance().getReference("Blog").child("Events");
         queryRef = mDatabase.orderByChild("EventType").equalTo("Graduation Party");

        // mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pixsor-acb86.firebaseio.com/Blog/Events");
        System.out.println("Url of firebase "+mDatabase);

        //  mDatabase.keepSynced(true);

            mDatabase.keepSynced(true);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Check your data connection!",Toast.LENGTH_LONG).show();
        }


  Load();


    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
                // ????????
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //??????


                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/


    @Override
    protected void onStop() {
        super.onStop();
     //   Load();
    }

    public     void Load(){
        //   mAuth.addAuthStateListener(mAuthLitsener);

    FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter_ = new FirebaseRecyclerAdapter<Event, GraduationListActivity.EventViewHolder>(Event.class,
                R.layout.item_event_view,
                GraduationListActivity.EventViewHolder.class,
          queryRef) {
            @Override
            protected void populateViewHolder(GraduationListActivity.EventViewHolder viewHolder, final Event model, final int position) {
                final String key_post = getRef(position).getKey();
                Bitmap b = null;
               // Bitmap attachedbitmap = ((BitmapDrawable)  viewHolder.setEventPic(c, model.getEventImage()).getBitmap();
              //  final Bitmap attachedbitmap = ((BitmapDrawable) viewHolder.event_img.getDrawable()).getBitmap();
                viewHolder.setEventLocation(model.getEventLocation());
                viewHolder.setEventName(model.getEventName());
                viewHolder.setEventType(model.getEventType());
                viewHolder.setEventDate(model.getEventDate());
                viewHolder.setEventPic(c, model.getEventImage(),b);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Toast.makeText(EventViewActivity.this,"This is : "+position,Toast.LENGTH_LONG).show();

                        Intent loadMainActivity = new Intent(GraduationListActivity.this,BlogScrolling.class);
                        loadMainActivity.putExtra("PARTY_NAME", model.getEventName());
                      loadMainActivity.putExtra("PIC",model.getEventImage());

                        startActivity(loadMainActivity);

                    }
                });





            }
        };

        mRecyclerview_event_view.setAdapter(firebaseRecyclerAdapter_);
    }

    //Search functionality ...




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                 /*
                if (query.length() > 3) {
                    query = query.toLowerCase();

                    Query queryRef_ = mDatabase.orderByChild("EventName")
                            .startAt(query.toUpperCase()).endAt(query + "~")
                            .limitToFirst(3);

                    FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>
                            (Event.class,
                                    R.layout.item_event_view,
                                    GraduationListActivity.EventViewHolder.class,
                                    queryRef_) {
                        @Override
                        protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {

                        }
                    };


                    mRecyclerview_event_view.swapAdapter(firebaseRecyclerAdapter,true);
                    defaultAdapter = false;

                    return true;
                }
            */



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 3) {
                    newText = newText.toUpperCase();
                    // queryRef = mDatabase.orderByChild("EventType").equalTo("Graduation Party");
                    Query queryRef_ = mDatabase.orderByChild("EventName")
                           .startAt(newText.toUpperCase()).endAt(newText.toUpperCase() + "~")
                          .limitToLast(3);

                    FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>
                            (Event.class,
                                    R.layout.item_event_view,
                                    GraduationListActivity.EventViewHolder.class,
                                    queryRef_) {
                        @Override
                        protected void populateViewHolder(EventViewHolder viewHolder,final Event model, int position) {
                            Bitmap b = null;
                            viewHolder.setEventLocation(model.getEventLocation());
                            viewHolder.setEventName(model.getEventName());
                            viewHolder.setEventType(model.getEventType());
                            viewHolder.setEventDate(model.getEventDate());
                            viewHolder.setEventPic(c, model.getEventImage(),b);
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Toast.makeText(EventViewActivity.this,"This is : "+position,Toast.LENGTH_LONG).show();

                                    Intent loadMainActivity = new Intent(GraduationListActivity.this,BlogScrolling.class);
                                    loadMainActivity.putExtra("PARTY_NAME", model.getEventName());
                                    loadMainActivity.putExtra("PIC",model.getEventImage());

                                    startActivity(loadMainActivity);

                                }
                            });

                        }
                    };


                    mRecyclerview_event_view.swapAdapter(firebaseRecyclerAdapter,true);
                    defaultAdapter = false;

                    return true;
                }


                else if (newText.length() == 0){
                    newText = newText.toUpperCase();
                    FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event,EventViewHolder>
                            (Event.class,
                                    R.layout.item_event_view,
                                    EventViewHolder.class,
                                    queryRef) {
                        @Override
                        protected void populateViewHolder(EventViewHolder viewHolder, final Event model, int position) {
                            Bitmap b = null;
                            viewHolder.setEventLocation(model.getEventLocation());
                            viewHolder.setEventName(model.getEventName());
                            viewHolder.setEventType(model.getEventType());
                            viewHolder.setEventDate(model.getEventDate());
                            viewHolder.setEventPic(c, model.getEventImage(),b);
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Toast.makeText(EventViewActivity.this,"This is : "+position,Toast.LENGTH_LONG).show();

                                    Intent loadMainActivity = new Intent(GraduationListActivity.this,BlogScrolling.class);
                                    loadMainActivity.putExtra("PARTY_NAME", model.getEventName());
                                    loadMainActivity.putExtra("PIC",model.getEventImage());

                                    startActivity(loadMainActivity);

                                }
                            });



                                }
                    };


                    mRecyclerview_event_view.swapAdapter(firebaseRecyclerAdapter,true);


                }
                return true;
            }
        });

        return true;
    }




    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }
    private static final class EventViewHolder extends RecyclerView.ViewHolder{


        private    View mView;
        public static String KEY_PARTY_NAME = "Huzy";
        public ImageView event_img;
        public EventViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            event_img = (ImageView)mView.findViewById(R.id.event_img);



        }
        public void setEventName(String EventName){

            TextView eventname = (TextView)mView.findViewById(R.id.item_eventname_txt);
            eventname.setText(EventName);

        }

        public void setEventType(String Eventtype){

            TextView eventype = (TextView)mView.findViewById(R.id.item_event_type_txt);
            eventype.setText(Eventtype);
        }


        public void setEventLocation(String EventLocation){
            TextView even_location = (TextView)mView.findViewById(R.id.item_eventlocation_txt);
            even_location.setText(EventLocation);
        }
        public void setEventDate(String EventDate){
            TextView even_date = (TextView)mView.findViewById(R.id.date_event_txt);
            even_date.setText(EventDate);
        }



        public void setEventPic(final Context c,final String imageUrl, Bitmap attachedbitmap){

            if(imageUrl!=null) {
                Picasso.with(c).load(imageUrl).error(R.mipmap.add_btn).placeholder(R.mipmap.add_btn)
                        .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                        .skipMemoryCache()
                        .networkPolicy(NetworkPolicy.OFFLINE).into(event_img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                        //Reloading an image again ...
                        Picasso.with(c).load(imageUrl).error(R.mipmap.ic_person).placeholder(R.mipmap.ic_person)
                                .into(event_img);
                    }
                });
            }
            else {
                Picasso.with(c).load(R.mipmap.ic_person).error(R.mipmap.ic_person).placeholder(R.mipmap.ic_person)
                        .into(event_img);

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

    private void logout() {
        mAuth.signOut();
    }






}
