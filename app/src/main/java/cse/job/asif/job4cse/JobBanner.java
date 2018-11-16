package cse.job.asif.job4cse;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

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
    private DatabaseHelper db;
    private int showDatabase = 0;

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

            showDatabase = 0;
            jobList.clear();
            showJobs("http://jobs.bdjobs.com/" ,"http://jobs.bdjobs.com/jobsearch.asp?fcatId=8&icatId=",currentPage);

        } else if (id == R.id.nav_slideshow) {

            showDatabase = 1;
            jobList.clear();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.getAllJobs(jobList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jobAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();

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
                if(showDatabase == 0) {
                    currentPage = currentPage + 1;
                    showJobs(origin, currentUrl, currentPage);
                }
            }
        });

        jobAdapter.setOnJobViewListener(new onJobViewListener() {
            @Override
            public void ViewJob(String url) {

                currentIntent = new Intent(getApplicationContext(),ViewJobDetails.class);
                currentIntent.putExtra("Url",url);
                currentIntent.putExtra("Origin",origin);
                startActivity(currentIntent);
            }
        });

        jobAdapter.setOnJobSaveListener(new OnJobSaveListener() {
            @Override
            public void onSave(JobDetails jobDetails) {
                db.insertJob(jobDetails);
            }
        });

    }

    private void init(){

        progressBar = findViewById(R.id.determinate);
        progressBar.setVisibility(View.GONE);
        db = new DatabaseHelper(this);

    }
}
