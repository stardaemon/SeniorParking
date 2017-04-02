package fit5120.seniorparking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import layout.Car;
import layout.Navigation;
import layout.Parking;
import layout.ParkingInfo;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_navigation:
                    selectedFragment = Navigation.newInstance();
                    break;
                case R.id.navigation_parking:
                    selectedFragment = Parking.newInstance();
                    break;
                case R.id.navigation_find:
                    selectedFragment = Car.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Navigation.newInstance());
        transaction.commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {  //requestCode == 67424 &&
            try {
                //Bitmap bmp = (Bitmap) data.getExtras().get("data");
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //bmp.compress(Bitmap.CompressFormat.PNG, 100, stream); //stream);

                //get db handler
                DatabaseImg dbHelper = new DatabaseImg(this);

                //delete existing car info if car is existing in db.
                if(dbHelper.GetAllCarImg().size() != 0)
                {
                    dbHelper.RemoveAllImg();
                }

                File storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                String pictureImagePath = storageDir.getAbsolutePath() + "/parking.png";
                CarImage carImageStore = new CarImage(pictureImagePath);
                dbHelper.AddCarImg(carImageStore);
                dbHelper.close();

            } catch (Exception e)
            {
                System.out.println("ddddddddddddd");
            }
            finally {

                System.out.println("ddddddddddddd2222");

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ParkingInfo.newInstance());
                //transaction.commit();
                transaction.commitAllowingStateLoss();
            }

        }
        else
        {
            System.out.println("cccccccc");
        }


    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
