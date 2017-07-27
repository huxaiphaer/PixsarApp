package company.override.huzykamz.pixsar;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pixsor.app.huzykamz.pixoradmin.R;

public class ViewSingleImage extends AppCompatActivity {


    private DatabaseReference mDatabase;

    // String ImageUrl;
    private Context c;
    private ImageView mImageSingle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_view);
        //setting an action bar..
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Picture");
        try {
        mImageSingle = (ImageView) findViewById(R.id.imageView_single);


        Bundle extras = getIntent().getExtras();
        String ImageUrl = extras.getString("ImageUrl");



            Bitmap incomingBitmap = decodeBase64(ImageUrl);

            //Resizing a bitmap
            Bitmap.createScaledBitmap(incomingBitmap, 50, 50, false);
            mImageSingle.setImageBitmap(incomingBitmap);

           // Bitmap.createScaledBitmap(_yourImageBitmap, _size, _size, false);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

    }


    public static Bitmap viewToBitmap(View v, int width, int height) {

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;


    }



    public static Bitmap decodeBase64(String input) {


        byte[] decodedByte = Base64.decode(input, 0);

        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.singel_image_download, menu);
        return true;
    }

    void SaveImage() {
        mImageSingle.buildDrawingCache();

        Bitmap bmp = mImageSingle.getDrawingCache();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

        File file = new File(storageLoc, timeStamp+"PixsarApp" + ".jpg");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            scanFile(getApplicationContext(), Uri.fromFile(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         catch (NullPointerException e){
             e.printStackTrace();
         }

        Toast.makeText(getApplicationContext(), "Image Saved ", Toast.LENGTH_LONG).show();
    }

    //noinspection SimplifiableIfStatement
    private static void scanFile(Context context, Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.image_save:
                SaveImage();
                break;
           /* case R.id.image_wallpaper:
                startWall();

                break;

            case R.id.image_share:
                startShare();
                break;*/
        }
        return super.onOptionsItemSelected(item);


    }

    private void startShare() {

        // Share to Social Media

       // ImageView imageView = (ImageView) feedItemView.findViewById(R.id.image);
        Drawable mDrawable = mImageSingle.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(c.getContentResolver(),
                mBitmap, "Design", null);

        Uri uri = Uri.parse(path);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, "PixsarApp");
        c.startActivity(Intent.createChooser(share, "share better moments with your beloved one's simply " +
                "download PixsarApp " +
                "https://play.google.com/store/apps/details?id=pixsor.app.huzykamz.pixoradmin&hl=en"));
    }

    private void startWall() {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.setBitmap(viewToBitmap(mImageSingle, mImageSingle.getWidth(), mImageSingle.getMaxHeight()));
            Toast.makeText(getApplicationContext(),"Image set as a wallpaper",Toast.LENGTH_LONG).show();
        }
        catch (IOException e){

        }
    }


}
