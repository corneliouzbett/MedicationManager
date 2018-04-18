package com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.beliscosolutions.corneliouzbett.medmanager01.helpers.model.Medication;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.model.User;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.ConversionOfDates;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.GetMonthValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CorneliouzBett on 04/04/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "MedicationManager.db";
    // drop the database
    private String DROP_MEDICATION_TABLE = "DROP TABLE IF EXISTS " + MedicationContract.MedicationEntry.TABLE_NAME;
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * creates table medication
         */
        final String SQL_CREATE_TABLE_MEDICATION = "CREATE TABLE " +MedicationContract.MedicationEntry.TABLE_NAME+"("+
                MedicationContract.MedicationEntry._ID +" INTEGER NOT NULL,"+
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME+" TEXT NOT NULL,"+
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_DESCRIPTION+" TEXT NOT NULL,"+
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_INTERVAL+" TEXT NOT NULL,"+
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE+" TEXT NOT NULL,"+
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_ENDDATE+" TEXT NOT NULL,"+
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_PENDING_INTENT+" TEXT NOT NULL"+
                ");";
        final String SQL_CREATE_TABLE_USER = "CREATE TABLE " +UserContract.UserEntry.TABLE_NAME+"("+
                UserContract.UserEntry._ID +" INTEGER NOT NULL,"+
                UserContract.UserEntry.COLUMN_USER_NAME+" TEXT NOT NULL,"+
                UserContract.UserEntry.COLUMN_USER_PHONENUMBER+" TEXT NOT NULL,"+
                UserContract.UserEntry.COLUMN_USER_PASSWORD+" TEXT NOT NULL"+
                ");";
        db.execSQL(SQL_CREATE_TABLE_MEDICATION);
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * deletes tables if it exists
         */

        db.execSQL(DROP_MEDICATION_TABLE);
        db.execSQL(DROP_USER_TABLE);

        onCreate(db);

    }
    /**
     * a method to save medication into sqlite database
     */
    public int addMedication(Medication medication){
        long status;
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv_Medication = new ContentValues();
        cv_Medication.put(MedicationContract.MedicationEntry._ID, medication.getId());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME, medication.getName());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_DESCRIPTION, medication.getDescription());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_INTERVAL, medication.getInterval());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE, medication.getStart_date());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_ENDDATE, medication.getEnd_date());

        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_PENDING_INTENT, medication.getPendingIntent());

        status = database.insert(MedicationContract.MedicationEntry.TABLE_NAME,null,cv_Medication);
        database.close();
        return (int) status;
    }

    /**
     * updates the medication table
     * @param medication
     * @return
     */

    public boolean updateMedication(Medication medication){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv_Medication = new ContentValues();
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME, medication.getName());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_DESCRIPTION, medication.getDescription());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_INTERVAL, medication.getInterval());
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE, String.valueOf(medication.getStart_date()));
        cv_Medication.put(MedicationContract.MedicationEntry.COLUMN_MEDICATION_ENDDATE, String.valueOf(medication.getEnd_date()));

        database.update(MedicationContract.MedicationEntry.TABLE_NAME,cv_Medication,"id = ?",new String[]{String.valueOf(medication.getId())});
        return true;
    }

    /**
     * the method gets all medications from the sqlite database
     */
    public List<Medication> getAllMedications(){
        String columns [] = {
                MedicationContract.MedicationEntry._ID,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_DESCRIPTION,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_INTERVAL,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_ENDDATE
        };
        String sortingOrder = MedicationContract.MedicationEntry._ID+ " ASC";
        List <Medication> medicationList = new ArrayList <Medication>();
        SQLiteDatabase mdDatabase = getWritableDatabase();

        Cursor cursor = mdDatabase.query(MedicationContract.MedicationEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortingOrder);
        if (cursor.moveToFirst()){
            do {
               Medication medication = new Medication();
               medication.setName(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME)));
               medication.setDescription(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_DESCRIPTION)));
               medication.setInterval(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_INTERVAL)));
               medication.setStart_date(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE)));
               medication.setEnd_date(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_ENDDATE)));
               medicationList.add(medication);
            }while (cursor.moveToNext());
        }
        mdDatabase.close();
        cursor.close();

        return medicationList;
    }

    /**
     *  it checks if the medication name already exists in the database
     *  ensure the name is unique
     * @param name
     * @return
     */
    public boolean checkMedicationExistence(String name) {

        String[] columns = {MedicationContract.MedicationEntry._ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME + " = ?";
        String[] selectionArgs = {name};


        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * query user table with condition
         */
        Cursor cursor = db.query(MedicationContract.MedicationEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;

    }

    /**
     * this method removes the medication which it's id is passed
     * @param name
     * @return
     */
    public Integer deleteMedication (String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(MedicationContract.MedicationEntry.TABLE_NAME,
                "medication_name = '"+name+"' ",
                null);
    }

    /**
     * |
     * |
     * |CRUD Operations for table users
     * |
     * |
     * |
     */

    public long addUser(User user){
        long status;
        SQLiteDatabase userDatabase = getWritableDatabase();
        ContentValues cv_user = new ContentValues();
        cv_user.put(UserContract.UserEntry._ID,user.getId());
        cv_user.put(UserContract.UserEntry.COLUMN_USER_NAME,user.getName());
        cv_user.put(UserContract.UserEntry.COLUMN_USER_PHONENUMBER,user.getEmailAddress());
        cv_user.put(UserContract.UserEntry.COLUMN_USER_PASSWORD,user.getPassword());

       status = userDatabase.insert(UserContract.UserEntry.TABLE_NAME,null,cv_user);
        userDatabase.close();

        return status;
    }
    public List<User> getAllUsers(){
        SQLiteDatabase userDatabase = getReadableDatabase();
        String columns [] = {
                UserContract.UserEntry.COLUMN_USER_NAME,
                UserContract.UserEntry.COLUMN_USER_PHONENUMBER,
                UserContract.UserEntry.COLUMN_USER_PASSWORD
        };
        List<User> userList = new ArrayList <>();
        Cursor cursor = userDatabase.query(
                UserContract.UserEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME)));
                user.setEmailAddress(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PHONENUMBER)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    /**
     *   this method used to get all medicine by month
     * @param month
     * @return
     */

    public List<Medication> getAllMedicationsByMonth(int month ) {
        String columns [] = {
                MedicationContract.MedicationEntry._ID,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_DESCRIPTION,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_INTERVAL,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE,
                MedicationContract.MedicationEntry.COLUMN_MEDICATION_ENDDATE
        };
        String sortingOrder = MedicationContract.MedicationEntry._ID+ " ASC";
        List <Medication> medicationMonthList = new ArrayList <Medication>();
        SQLiteDatabase mdDatabase = getWritableDatabase();

        Cursor cursor = mdDatabase.query(MedicationContract.MedicationEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortingOrder);
        if (cursor.moveToFirst()){
            do {
                if (GetMonthValue.getMonthValue(ConversionOfDates.getDateFromString(
                        cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE))
                )) == month){
                    Medication medication = new Medication();
                    medication.setName(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_NAME)));
                    medication.setDescription(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_DESCRIPTION)));
                    medication.setInterval(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_INTERVAL)));
                    medication.setStart_date(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_STARTDATE)));
                    medication.setEnd_date(cursor.getString(cursor.getColumnIndex(MedicationContract.MedicationEntry.COLUMN_MEDICATION_ENDDATE)));
                    medicationMonthList.add(medication);
                }
            }while (cursor.moveToNext());
        }
        mdDatabase.close();
        cursor.close();

        return medicationMonthList;

    }
}
