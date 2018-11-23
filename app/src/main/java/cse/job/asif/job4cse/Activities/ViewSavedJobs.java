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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cse.job.asif.job4cse.HelperClass.JobDetails;
import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.broadcasts.MyBroadcastReceiver;
//import cse.job.asif.job4cse.database.DatabaseHelper;
import cse.job.asif.job4cse.interfaces.OnJobDeleteListener;
import cse.job.asif.job4cse.interfaces.onJobViewListener;
import cse.job.asif.job4cse.recyclerViews.JobSavedAdapter;

public class ViewSavedJobs extends AppCompatActivity {

    private ArrayList<JobDetails> jobList;
    private JobSavedAdapter jobSavedAdapter;
    private RecyclerView rvJobs;
    private Intent currentIntent;
    private String currentUser;
    //private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_jobs);
        init();
        initRecyclerView();
        readData();
    }

    public void init(){

        //db = new DatabaseHelper(this);
        jobList = new ArrayList<>();
        currentUser = ParseUser.getCurrentUser().getUsername();

    }

    private void initRecyclerView(){

        rvJobs = (RecyclerView) findViewById(R.id.JobSavedList);

        jobSavedAdapter = new JobSavedAdapter(jobList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvJobs.setLayoutManager(mLayoutManager);
        rvJobs.setItemAnimator(new DefaultItemAnimator());
        rvJobs.setAdapter(jobSavedAdapter);

        jobSavedAdapter.setOnJobViewListener(new onJobViewListener() {
            @Override
            public void ViewJob(String url) {

                currentIntent = new Intent(getApplicationContext(),ViewJobDetails.class);
                currentIntent.putExtra("Url",url);
                startActivity(currentIntent);
            }
        });

        jobSavedAdapter.setOnJobDeleteListener(new OnJobDeleteListener() {
            @Override
            public void onDelete(JobDetails jobDetails) {

                Context ctx = getApplicationContext();

                AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

                Intent cancelIntent = new Intent(ctx,MyBroadcastReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx,jobDetails.getId(),cancelIntent,0);

                am.cancel(pendingIntent);

                //db.deleteJob(jobDetails);

                DeleteParse(jobDetails);

            }
        });

    }

    private void readData(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Jobs");
        query.whereEqualTo("username",currentUser);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                for(ParseObject object : objects){

                    JobDetails details = new JobDetails();

                    details.setId(object.getInt("Id"));
                    details.setCompname(object.getString("CompName"));
                    details.setDeadline(object.getString("Dead"));
                    details.setExp(object.getString("Exp"));
                    details.setJobUrl(object.getString("Url"));
                    details.setLocation(object.getString("Location"));
                    details.setLogo(object.getString("Logo"));
                    details.setTitle(object.getString("Title"));
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(object.getString("Qualifications").split("\n")));
                    details.setQualifications(list);

                    jobList.add(details);

                }

                jobSavedAdapter.notifyDataSetChanged();

            }
        });


    }

    private void DeleteParse(JobDetails jobDetails){


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
}
