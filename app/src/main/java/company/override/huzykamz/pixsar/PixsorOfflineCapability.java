package company.override.huzykamz.pixsar;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDex;

import com.google.firebase.database.FirebaseDatabase;
import com.pushbots.push.Pushbots;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by HUZY_KAMZ on 10/2/2016.
 */
public class PixsorOfflineCapability extends Application{
    private Context c;

    @Override
    public void onCreate() {
        super.onCreate();

        //Enabling Offline Capability...On strings and Images as well .....(Receiving Notifications)


         //pushbots initialisation
            Pushbots.sharedInstance().init(this);


            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
            Picasso built = builder.build();
            built.setIndicatorsEnabled(false);
            built.setLoggingEnabled(true);
            Picasso.setSingletonInstance(built);




    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void Me (){

    }
}
