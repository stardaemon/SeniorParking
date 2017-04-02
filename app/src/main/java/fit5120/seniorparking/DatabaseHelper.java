package fit5120.seniorparking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by liyunhong on 30/3/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //set database properties
    public static final String DATABASE_NAME = "CarDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CarInfo.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CarInfo.TABLE_NAME);
        onCreate(db);
    }

    public void AddCarInfo(CarInfo car)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CarInfo.COLUMN_LEVEL, car.getLevel());
        values.put(CarInfo.COLUMN_COLOR, car.getColor());
        values.put(CarInfo.COLUMN_LETTER, car.getLetter());
        values.put(CarInfo.COLUMN_NUMBER, car.getNumber());
        values.put(CarInfo.COLUMN_TIME,car.getTime());
        values.put(CarInfo.COLUMN_LATITUDE, car.getLatitude());
        values.put(CarInfo.COLUMN_LONGITUDE,car.getLongitude());
        db.insert(CarInfo.TABLE_NAME, null, values);
        db.close();
    }


    public void UpdateCarInfo(CarInfo car)
    {
        String[] selectionArgs = {String.valueOf(CarInfo.COLUMN_ID)};
        String selection = CarInfo.COLUMN_ID + " LIKE ?";

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CarInfo.COLUMN_LEVEL, car.getLevel());
        values.put(CarInfo.COLUMN_COLOR, car.getColor());
        values.put(CarInfo.COLUMN_LETTER, car.getLetter());
        values.put(CarInfo.COLUMN_NUMBER, car.getNumber());
        values.put(CarInfo.COLUMN_TIME,car.getTime());
        values.put(CarInfo.COLUMN_LATITUDE, car.getLatitude());
        values.put(CarInfo.COLUMN_LONGITUDE,car.getLongitude());
        db.update(CarInfo.TABLE_NAME,values,selection, selectionArgs);
        db.close();
    }


    public HashMap<Long,CarInfo> GetAllCarInfo()
    {
        HashMap<Long,CarInfo> carInfo = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CarInfo.TABLE_NAME, null);

        //add each car info to hashmap
        if(cursor.moveToFirst())
        {
            do {
                CarInfo carInfoNew = new CarInfo(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6),cursor.getString(7));
                carInfo.put(carInfoNew.get_id(), carInfoNew);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return carInfo;
    }


    public void RemoveCarInfo(CarInfo carInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CarInfo.TABLE_NAME, CarInfo.COLUMN_ID + " = ?",
                new String[] {String.valueOf(carInfo.get_id())});
    }



    public void RemoveAllCar()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ CarInfo.TABLE_NAME);
    }

    public void Close()
    {
        this.close();
    }




}
