package layout;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fit5120.seniorparking.CarImage;
import fit5120.seniorparking.CarInfo;
import fit5120.seniorparking.DatabaseHelper;
import fit5120.seniorparking.DatabaseImg;
import fit5120.seniorparking.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Car.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Car#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Car extends Fragment implements OnMapReadyCallback {

    //private Car.OnFragmentInteractionListener mListener;
    private ImageView carImage;
    private TextView tCarLevel, tCarColor, tCarNumber,tCarLetter, tCarTime;
    private String sCarLevel, sCarColor, sCarNumber,sCarLetter,sCarTime,sLat,sLon;
    private Button bCarCancel;
    private LatLng cur = new LatLng(-37.8770, 145.0443);

    //handle db
    private DatabaseHelper dbHelper;
    private List<CarInfo> carInfos= new ArrayList<CarInfo>();

    private GoogleMap map;
    private Location loc;

    public static Car newInstance() {
        Car fragment = new Car();
        Bundle args = new Bundle();
        return fragment;
    }


    //a constraint to transfer data from parking information

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View carView = inflater.inflate(R.layout.fragment_car, container, false);
        initializeMap();

        this.tCarLevel = (TextView) carView.findViewById(R.id.car_level);
        this.tCarColor = (TextView) carView.findViewById(R.id.car_color);
        this.tCarNumber = (TextView) carView.findViewById(R.id.car_number);
        this.tCarLetter = (TextView) carView.findViewById(R.id.car_letter);
        this.tCarTime = (TextView) carView.findViewById(R.id.car_timer);
        this.bCarCancel = (Button) carView.findViewById(R.id.car_cancel);
        this.carImage = (ImageView) carView.findViewById(R.id.car_image);


        DatabaseImg dbImg = new DatabaseImg(getActivity());
        if(dbImg.GetAllCarImg() != null) {
            ArrayList<CarImage> carImg = new ArrayList<>(dbImg.GetAllCarImg().values());
            if(carImg.size() > 0) {
                String imgString = carImg.get(0).getImg();

                try {

                    File imgFile = new  File(imgString);
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    if(bitmap == null)
                        System.out.println("123452");
                    carImage.setImageBitmap(bitmap);
                }
                catch (Exception e)
                {
                }
            }
        }


        //get db handler
        dbHelper = new DatabaseHelper(getActivity());

        //alert if no data exits
        carInfos =new ArrayList<>(dbHelper.GetAllCarInfo().values());
        if(carInfos.size() == 0)
        {
            //display an alert
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("There has no car info stored yet");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else
        {
            CarInfo car = carInfos.get(0);
            this.tCarLevel.setText("Level: " + car.getLevel());
            this.tCarColor.setText("Color: " + car.getColor());
            this.tCarLetter.setText("Letter: " + car.getLetter());
            this.tCarNumber.setText("Number: " + car.getNumber());
            this.tCarTime.setText(car.getTime() + " mins left");
            this.sLat = car.getLatitude();
            this.sLon = car.getLongitude();
            if((sLat != null) && (sLon != null)) {
                cur = new LatLng(Double.parseDouble(sLat), Double.parseDouble(sLon));
                System.out.println("CUR" + cur + "   ==============");
            }
            else
            {
                cur = new LatLng(-73.8770, 145.0443);
                System.out.println("NO " + cur + "   ++++++++++++++");

            }
            dbHelper.close();
        }


        bCarCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {


                //get db handler
                dbHelper = new DatabaseHelper(getActivity());

                //delete existing car info if car is existing in db.
                if(dbHelper.GetAllCarInfo().size() != 0)
                {
                    dbHelper.RemoveAllCar();
                }

                dbHelper.RemoveAllCar();

                Toast.makeText(getActivity(), "Cancel successfully ", Toast.LENGTH_SHORT).show();

                dbHelper.close();

            }
        });



        return carView;
    }


    private void initializeMap() {
        if (map == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cur,15));
        System.out.println("CUR" + cur + "   ==============");
        map.addMarker(new MarkerOptions().position(cur).title("Parking"));
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
