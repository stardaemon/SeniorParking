package fit5120.seniorparking;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liyunhong on 1/4/17.
 */

public class CarImage implements Parcelable {

    public static final String IMG_TABLE_NAME = "carimg";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "img";

    //Table Create Statement
    public static final String CREATE_STATEMENT = "CREATE TABLE " +
            IMG_TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_IMG + " TEXT NULL" +
            ")";

    private long _id;
    private String img;


    public CarImage(String img)
    {
        this.img = img;
    }

    public CarImage(long id, String img)
    {
        this._id = id;
        this.img = img;
    }

    protected CarImage(Parcel in) {
        img = in.readString();
    }


    public static final Creator<CarImage> CREATOR = new Creator<CarImage>() {
        @Override
        public CarImage createFromParcel(Parcel in) {
            return new CarImage(in);
        }

        @Override
        public CarImage[] newArray(int size) {
            return new CarImage[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
    }
}
