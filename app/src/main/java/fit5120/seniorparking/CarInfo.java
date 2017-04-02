package fit5120.seniorparking;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liyunhong on 30/3/17.
 */

public class CarInfo implements Parcelable {

    //Database Constants
    public static final String TABLE_NAME = "carinfo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_LETTER = "letter";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    //public static final String COLUMN_IMG = "img";

    //Table Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " +
            TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_LEVEL + " TEXT  NULL, " +
            COLUMN_NUMBER + " INTEGER  NULL, " +
            COLUMN_COLOR + " TEXT  NULL, " +
            COLUMN_LETTER + " TEXT  NULL, " +
            COLUMN_TIME + " TEXT  NULL, " +
            COLUMN_LATITUDE + " TEXT NULL, " +
            COLUMN_LONGITUDE + " TEXT NULL" +
           // COLUMN_IMG + " TEXT NULL" +
            ")";

    private long _id;
    private String level, number, color, letter,time, latitude, longitude;
    //private String img;

    public CarInfo()
    {
        level = "";
        number = "";
        color = "";
        letter = "";
        time = "";
    }

    public CarInfo(String duration)
    {
        level = "";
        number = "";
        color = "";
        letter = "";
        time = duration;
    }

    public CarInfo(Long id, String lvl, String num, String col, String let, String dur)
    {
        this._id = id;
        this.level = lvl;
        this.number = num;
        this.color = col;
        this.letter = let;
        this.time = dur;
    }

    public CarInfo(Long id, String lvl, String num, String col, String let, String dur, String lat, String lon)
    {
        this._id = id;
        this.level = lvl;
        this.number = num;
        this.color = col;
        this.letter = let;
        this.time = dur;
        this.latitude = lat;
        this.longitude = lon;
    }

   /* public CarInfo(Long id, String lvl, String num, String col, String let, String dur, String lat, String lon, String img)
    {
        this._id = id;
        this.level = lvl;
        this.number = num;
        this.color = col;
        this.letter = let;
        this.time = dur;
        this.latitude = lat;
        this.longitude = lon;
        this.img = img;
    }*/



    protected CarInfo(Parcel in) {
        level = in.readString();
        number = in.readString();
        color = in.readString();
        letter = in.readString();
        time = in.readString();
        latitude = in.readString();
        longitude = in.readString();
       // img = in.readString();
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public static final Creator<CarInfo> CREATOR = new Creator<CarInfo>() {
        @Override
        public CarInfo createFromParcel(Parcel in) {
            return new CarInfo(in);
        }

        @Override
        public CarInfo[] newArray(int size) {
            return new CarInfo[size];
        }
    };

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

   /* public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.level);
        dest.writeString(this.color);
        dest.writeString(this.number);
        dest.writeString(this.letter);
        dest.writeString(this.time);
    }
}
