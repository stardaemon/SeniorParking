package layout;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import fit5120.seniorparking.CarImage;
import fit5120.seniorparking.DatabaseHelper;
import fit5120.seniorparking.DatabaseImg;
import fit5120.seniorparking.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Parking.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Parking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Parking extends Fragment {

    private ImageButton cam;
    private Button no;


    //define camera data
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private String realImageStr;
    private Bitmap bitmap;

    private OnFragmentInteractionListener mListener;

    public static Parking newInstance() {
        Parking fragment = new Parking();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parkingView =  inflater.inflate(R.layout.fragment_parking, container, false);

        this.cam = (ImageButton)parkingView.findViewById(R.id.camera);
        this.no = (Button)parkingView.findViewById(R.id.no);

        no.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ParkingInfo.newInstance());
                transaction.commit();
            }

        });

        cam.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try{
                            if ((getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) ||
                                    (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED)  ||
                                    (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED)) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA},
                                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2323);
                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4747);
                            }

                            File storageDir = Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES);
                            String pictureImagePath = storageDir.getAbsolutePath() + "/parking.png";
                            File file = new File(pictureImagePath);
                            Uri outputFileUri = Uri.fromFile(file);


                            System.out.println(pictureImagePath +"111111");

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                            //Parking.this.
                            //takePhoto();
                        }
                        catch (Exception e)
                        {
                        }
                        /**finally {
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, ParkingInfo.newInstance());
                            transaction.commit();
                        }**/



                    }
                });



        return parkingView;
    }



    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Car.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }



/**    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("in in in in in in in i");

        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            realImageStr = new String("");
            try {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                String s = "";
                for(byte b : byteArray)
                {
                    s += b;
                }

                // convert byte array to Bitmap

                bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                //get db handler
                DatabaseImg dbHelper = new DatabaseImg(getActivity());

                //delete existing car info if car is existing in db.
                if(dbHelper.GetAllCarImg().size() != 0)
                {
                    dbHelper.RemoveAllImg();
                }

                CarImage carImageStore = new CarImage(s);
                dbHelper.AddCarImg(carImageStore);
                dbHelper.close();

                System.out.println(s + "bbbbbbbbb");

            } catch (Exception e)
            {
            }
        }

        else
        {
            System.out.print("cccccccc");
        }
    }
**/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
