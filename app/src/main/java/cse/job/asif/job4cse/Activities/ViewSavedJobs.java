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

import java.util.ArrayList;

import cse.job.asif.job4cse.HelperClass.JobDetails;
import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.broadcasts.MyBroadcastReceiver;
import cse.job.asif.job4cse.database.DatabaseHelper;
import cse.job.asif.job4cse.interfaces.OnJobDeleteListener;
import cse.job.asif.job4cse.interfaces.onJobViewListener;
import cse.job.asif.job4cse.recyclerViews.JobSavedAdapter;

public class ViewSavedJobs extends AppCompatActivity {

    private ArrayList<JobDetails> jobList;
    private JobSavedAdapter jobSavedAdapter;
    private RecyclerView rvJobs;
    private Intent currentIntent;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_jobs);
        init();
        initRecyclerView();
    }

    public void init(){

        db = new DatabaseHelper(this);
        jobList = new ArrayList<>();

    }

    private void initRecyclerView(){

        rvJobs = (RecyclerView) findViewById(R.id.JobSavedList);

        db.getAllJobs(jobList);

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

                db.deleteJob(jobDetails);

                jobList.clear();

                db.getAllJobs(jobList);

                jobSavedAdapter.notifyDataSetChanged();

            }
        });

    }
}
