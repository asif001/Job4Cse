package cse.job.asif.job4cse.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cse.job.asif.job4cse.HelperClass.HtmlParse;
import cse.job.asif.job4cse.HelperClass.JobDetails;
import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.broadcasts.MyBroadcastReceiver;
import cse.job.asif.job4cse.database.DatabaseHelper;
import cse.job.asif.job4cse.interfaces.OnBottomReachedListener;
import cse.job.asif.job4cse.interfaces.OnJobSaveListener;
import cse.job.asif.job4cse.interfaces.onJobViewListener;
import cse.job.asif.job4cse.recyclerViews.JobAdapter;

public class JobBanner extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<JobDetails> jobList;
    private JobAdapter jobAdapter;
    private RecyclerView rvJobs;
    private ProgressBar progressBar;
    private Integer currentPage = 1;
    private String currentUrl;
    private String origin;
    private Intent currentIntent;
    //private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_banner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        initRecyclerView();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.job_banner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            jobList.clear();
            showJobs("http://jobs.bdjobs.com/" ,"http://jobs.bdjobs.com/jobsearch.asp?fcatId=8&icatId=",currentPage);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showJobs(final String origin, final String url, final Integer currentPage){

        this.currentUrl = url;
        this.origin = origin;

        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });

                HtmlParse htmlParse = new HtmlParse();

                try {
                    htmlParse.Start(origin, url,currentPage.toString(),jobList);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressBar.setVisibility(View.GONE);
                        jobAdapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    private void initRecyclerView(){

        rvJobs = (RecyclerView) findViewById(R.id.JobList);

        jobList = new ArrayList<>();

        jobAdapter = new JobAdapter(jobList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvJobs.setLayoutManager(mLayoutManager);
        rvJobs.setItemAnimator(new DefaultItemAnimator());
        rvJobs.setAdapter(jobAdapter);

        jobAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {

                    currentPage = currentPage + 1;
                    showJobs(origin, currentUrl, currentPage);
            }
        });

        jobAdapter.setOnJobViewListener(new onJobViewListener() {
            @Override
            public void ViewJob(String url) {

                currentIntent = new Intent(getApplicationContext(),ViewJobDetails.class);
                currentIntent.putExtra("Url",url);
                startActivity(currentIntent);
            }
        });

        jobAdapter.setOnJobSaveListener(new OnJobSaveListener() {
            @Override
            public void onSave(JobDetails jobDetails) {

                SaveToCloud(jobDetails);

            }
        });

    }

    private void init(){

        progressBar = findViewById(R.id.determinate);
        progressBar.setVisibility(View.GONE);
        //db = new DatabaseHelper(this);

    }


    private void createNotification(JobDetails details){

        Intent notificationIntent = new Intent(this,MyBroadcastReceiver.class);
        notificationIntent.putExtra("id",details.getId());
        notificationIntent.putExtra("title",details.getTitle());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, details.getId() ,notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMills = SystemClock.elapsedRealtime() + 15000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,futureInMills,pendingIntent);

    }

    private void SaveToCloud(final JobDetails details){

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Jobs");

        parseQuery.whereEqualTo("CompName",details.getCompname());
        parseQuery.whereEqualTo("Title",details.getTitle());
        parseQuery.whereEqualTo("Dead",details.getDeadline());

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(objects.size() > 0){

                    return;

                }
                else{

                    ParseObject object = new ParseObject("Jobs");

                    object.put("Id",details.getId());
                    object.put("CompName",details.getCompname());
                    object.put("Title",details.getTitle());
                    object.put("Location",details.getLocation());
                    object.put("Exp",details.getExp());
                    object.put("Dead",details.getDeadline());
                    object.put("Url",details.getJobUrl());
                    object.put("Logo",details.getLogo());

                    String result = "";

                    for(String qualifications : details.getQualifications()){

                        result =  result + "\n" + qualifications;

                    }

                    object.put("Qualifications",result);

                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            createNotification(details);

                        }
                    });


                }

            }
        });

    }
}
