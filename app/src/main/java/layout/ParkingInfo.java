package layout;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import fit5120.seniorparking.CarImage;
import fit5120.seniorparking.CarInfo;
import fit5120.seniorparking.DatabaseHelper;
import fit5120.seniorparking.DatabaseImg;
import fit5120.seniorparking.GPSTracker;
import fit5120.seniorparking.R;
import fit5120.seniorparking.SpinnerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParkingInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParkingInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParkingInfo extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Spinner sLevel,sNumber, sLetter, sHour, sMin;
    private String resLevel, resColor,resNumber,resLetter, resHour,resMin, resTime, resLat, resLon;
    private Button resSave;
    private ImageView img;
    private RadioGroup rGroup;

    private GPSTracker gps;

    //handle db
    private DatabaseHelper dbHelper;

    //an object of carInfo to store related info
    private CarInfo carInfoStoroe = new CarInfo();


    public ParkingInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ParkingInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static ParkingInfo newInstance() {
        ParkingInfo fragment = new ParkingInfo();
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
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1647);


        // Inflate the layout for this fragment
        View parkingInfoView =  inflater.inflate(R.layout.fragment_parking_info, container, false);

        this.sLevel = (Spinner) parkingInfoView.findViewById(R.id.spinner_level);
        this.sNumber = (Spinner) parkingInfoView.findViewById(R.id.spinner_number);
        this.sLetter = (Spinner) parkingInfoView.findViewById(R.id.spinner_letter);
        this.sHour = (Spinner) parkingInfoView.findViewById(R.id.spinner_hour);
        this.sMin = (Spinner) parkingInfoView.findViewById(R.id.spinner_minute);
        this.resSave = (Button) parkingInfoView.findViewById(R.id.save);
        this.rGroup = (RadioGroup) parkingInfoView.findViewById(R.id.radioGroup);

        this.img = (ImageView) parkingInfoView.findViewById(R.id.car_image) ;

        this.gps = new GPSTracker(getActivity());

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
                    img.setImageBitmap(bitmap);
                }
                catch (Exception e)
                {

                }

            }
        }

        //set level adapter
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.level, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sLevel.setAdapter(levelAdapter);
        sLevel.setOnItemSelectedListener(new SpinnerActivity());


        //set hour adapter
        ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.hour, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sHour.setAdapter(hourAdapter);
        sHour.setOnItemSelectedListener(new SpinnerActivity());

        //set hour adapter
        ArrayAdapter<CharSequence> minAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.minute, android.R.layout.simple_spinner_item);
        minAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMin.setAdapter(minAdapter);
        sMin.setOnItemSelectedListener(new SpinnerActivity());


        //set number adapter
        ArrayAdapter<CharSequence> numberAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.number, android.R.layout.simple_spinner_item);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sNumber.setAdapter(numberAdapter);
        sNumber.setOnItemSelectedListener(new SpinnerActivity());

        //set letter adapter
        ArrayAdapter<CharSequence> letterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.letter, android.R.layout.simple_spinner_item);
        letterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLetter.setAdapter(letterAdapter);
        sLetter.setOnItemSelectedListener(new SpinnerActivity());



        //set radio group
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radio_blue:
                        resColor = "Blue";
                        break;
                    case R.id.radio_red:
                        resColor = "Red";
                        break;
                    case R.id.radio_yellow:
                        resColor = "Yellow";
                        break;
                    default:
                        resColor = "Not selected";
                }
            }
        });



        resSave.setOnClickListener(new View.OnClickListener(){
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

                resLevel = String.valueOf(sLevel.getSelectedItem());
                resHour = sHour.getSelectedItem().toString();
                resMin = sMin.getSelectedItem().toString();
                resNumber = sNumber.getSelectedItem().toString();
                resLetter = sLetter.getSelectedItem().toString();

                if(gps.canGetLocation()) {
                    resLat = String.valueOf(gps.getLatitude());
                    resLon = String.valueOf(gps.getLongitude());
                }
                else
                {
                    resLat = "-90.8770";
                    resLon = "145.0443";
                }
                //set values from user input
                carInfoStoroe.setLevel(resLevel);
                carInfoStoroe.setColor(resColor);
                carInfoStoroe.setLetter(resLetter);
                carInfoStoroe.setNumber(resNumber);
                carInfoStoroe.setLatitude(resLat);
                carInfoStoroe.setLongitude(resLon);

                System.out.println(resLat + "=========");
                System.out.println(resLon + "=========");

                int resTimeMins;
                if(resHour.equals("--"))
                {
                    if(resMin.equals("--"))
                    {
                        resTimeMins = 0;
                    }
                    else
                    {
                        resTimeMins = Integer.valueOf(resMin);
                    }
                }
                else
                {
                    if(resMin.equals("--"))
                    {
                        resTimeMins = Integer.valueOf(resHour)*60;
                    }
                    else
                    {
                        resTimeMins = Integer.valueOf(resHour)*60 + Integer.valueOf(resMin);
                    }
                }
                carInfoStoroe.setTime(resTimeMins + "");

                dbHelper.AddCarInfo(carInfoStoroe);

                Toast.makeText(getActivity(), "Save successfully ", Toast.LENGTH_SHORT).show();

                dbHelper.close();

            }
        });



        return  parkingInfoView;
    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_blue:
                if (checked)
                    resColor = "blue";
                break;
            case R.id.radio_yellow:
                if (checked)
                    resColor = "Yellow";
                break;
            case R.id.radio_red:
                if (checked)
                    resColor = "Red";
                break;
            default:
                resColor = "No Color Selected";

        }
    }


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
