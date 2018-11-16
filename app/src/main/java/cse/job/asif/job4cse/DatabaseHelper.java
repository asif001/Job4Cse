package cse.job.asif.job4cse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

import cse.job.asif.job4cse.JobDetails;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_jobs";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JobDetails.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + JobDetails.TABLE_NAME);

        onCreate(db);

    }

    public long insertJob(JobDetails jobDetails){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(JobDetails.COLUMN_COMPNAME,jobDetails.getCompname());
        values.put(JobDetails.COLUMN_TITLE,jobDetails.getTitle());
        values.put(JobDetails.COLUMN_LOCATION,jobDetails.getLocation());
        values.put(JobDetails.COLUMN_EXP,jobDetails.getExp());
        values.put(JobDetails.COLUMN_DEAD,jobDetails.getDeadline());
        values.put(JobDetails.COLUMN_URL,jobDetails.getJobUrl());
        values.put(JobDetails.COLUMN_LOGO,jobDetails.getLogo());

        String result = "";

        for(String qualifications : jobDetails.getQualifications()){

            result =  result + "\n" + qualifications;

        }
        values.put(JobDetails.COLUMN_QUALIFIACTIONS,result);

        long id = db.insert(JobDetails.TABLE_NAME,null,values);

        db.close();

        return id;


    }

    public void getAllJobs(ArrayList<JobDetails> jobList) {

        ArrayList<JobDetails> jobs = jobList;

        String selectQuery = "SELECT  * FROM " + JobDetails.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {

            do {

                JobDetails jobDetails = new JobDetails();

                jobDetails.setId(cursor.getInt(cursor.getColumnIndex(JobDetails.COLUMN_ID)));
                jobDetails.setCompname(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_COMPNAME)));
                jobDetails.setJobUrl(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_URL)));
                jobDetails.setTitle(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_TITLE)));
                jobDetails.setLocation(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_LOCATION)));
                jobDetails.setDeadline(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_DEAD)));
                jobDetails.setExp(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_EXP)));
                jobDetails.setLogo(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_LOGO)));

                ArrayList<String> list = new ArrayList<>(Arrays.asList(cursor.getString(cursor.getColumnIndex(JobDetails.COLUMN_QUALIFIACTIONS)).split("\n")));

                jobDetails.setQualifications(list);

                jobs.add(jobDetails);

            } while (cursor.moveToNext());

        }

        db.close();

    }

}
