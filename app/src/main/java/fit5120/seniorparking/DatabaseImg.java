package fit5120.seniorparking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by liyunhong on 1/4/17.
 */

public class DatabaseImg extends SQLiteOpenHelper {

    //set database properties
    public static final String DATABASE_NAME = "CarImg";
    public static final int DATABASE_VERSION = 1;

    public DatabaseImg(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CarImage.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CarImage.IMG_TABLE_NAME);
        onCreate(db);
    }


    public void AddCarImg(CarImage car)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CarImage.COLUMN_IMG,car.getImg());
        db.insert(CarImage.IMG_TABLE_NAME,null,values);
    }


    public void UpdateCarImg(CarImage car)
    {
        String[] selectionArgs = {String.valueOf(CarInfo.COLUMN_ID)};
        String selection = CarInfo.COLUMN_ID + " LIKE ?";

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CarImage.COLUMN_IMG,car.getImg());
        db.update(CarImage.IMG_TABLE_NAME,values,selection,selectionArgs);
    }


    public HashMap<Long,CarImage> GetAllCarImg()
    {
        HashMap<Long,CarImage> carImg = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CarImage.IMG_TABLE_NAME, null);

        //add each car info to hashmap
        if(cursor.moveToFirst())
        {
            do {
                CarImage carImgNew = new CarImage(cursor.getLong(0), cursor.getString(1));
                carImg.put(carImgNew.get_id(), carImgNew);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return carImg;
    }



    public void RemoveCarIng(CarImage carImg)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CarImage.IMG_TABLE_NAME, CarImage.COLUMN_ID + " = ?",
                new String[] {String.valueOf(carImg.get_id())});
    }



    public void RemoveAllImg()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ CarImage.IMG_TABLE_NAME);
    }
    public void Close()
    {
        this.close();
    }




}
