package cse.job.asif.job4cse.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cse.job.asif.job4cse.HelperClass.AppliedJobDetails;
import cse.job.asif.job4cse.HelperClass.JobDetails;
import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.broadcasts.MyBroadcastReceiver;
import cse.job.asif.job4cse.interfaces.OnJobAcceptListener;
import cse.job.asif.job4cse.interfaces.OnJobDeleteListener;
import cse.job.asif.job4cse.interfaces.OnJobRejectListener;
import cse.job.asif.job4cse.interfaces.OnJobisAppliedListener;
import cse.job.asif.job4cse.interfaces.onJobApplyListener;
import cse.job.asif.job4cse.interfaces.onJobViewListener;
import cse.job.asif.job4cse.recyclerViews.JobAppliedAdapter;
import cse.job.asif.job4cse.recyclerViews.JobSavedAdapter;

public class company_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_dashboard);
        init();
        initRecyclerView();
        readData();
    }


    private ArrayList<AppliedJobDetails> jobList;
    private JobAppliedAdapter jobAppliedAdapter;
    private RecyclerView rvJobs;
    private String currentUser;

    public void init(){

        jobList = new ArrayList<>();
        currentUser = ParseUser.getCurrentUser().getUsername();

    }

    private void initRecyclerView(){

        rvJobs = (RecyclerView) findViewById(R.id.AppliedList);

        jobAppliedAdapter = new JobAppliedAdapter(jobList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvJobs.setLayoutManager(mLayoutManager);
        rvJobs.setItemAnimator(new DefaultItemAnimator());
        rvJobs.setAdapter(jobAppliedAdapter);


        jobAppliedAdapter.setOnJobAcceptListener(new OnJobAcceptListener() {
            @Override
            public void onAccept(AppliedJobDetails details) {

                accept(details);

            }
        });

        jobAppliedAdapter.setOnJobRejectListener(new OnJobRejectListener() {
            @Override
            public void onReject(AppliedJobDetails details) {

                DeleteParse(details);

            }
        });

    }

    private void readData(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Jobs");
        query.whereEqualTo("CompName",currentUser);
        query.whereEqualTo("isApplied",1);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                for(ParseObject object : objects){

                    AppliedJobDetails details = new AppliedJobDetails();

                    details.setId(object.getInt("Id"));
                    details.setApplierName(object.getString("name"));
                    details.setJobTitle(object.getString("Title"));
                    details.setUsername(object.getString("username"));

                    jobList.add(details);

                }

                jobAppliedAdapter.notifyDataSetChanged();

            }
        });


    }

    private void DeleteParse(AppliedJobDetails jobDetails){


        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Jobs");

        parseQuery.whereEqualTo("Id",jobDetails.getId());

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                for(ParseObject object : objects){

                    try {
                        object.delete();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    object.saveInBackground();

                }

                jobList.clear();

                readData();

            }
        });


    }

    private void accept(AppliedJobDetails details){

        ParseObject parseObject = new ParseObject("InterView");

        parseObject.put("username",details.getUsername());
        parseObject.put("Title",details.getJobTitle());
        parseObject.put("CompName",currentUser);

        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(getApplicationContext(),"Successfully accepted",Toast.LENGTH_LONG).show();
            }
        });

        DeleteParse(details);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK,new Intent());
        finish();
    }

}
