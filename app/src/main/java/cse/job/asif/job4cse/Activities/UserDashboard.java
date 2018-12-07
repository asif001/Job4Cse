package cse.job.asif.job4cse.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

import cse.job.asif.job4cse.HelperClass.checkLogn;

import cse.job.asif.job4cse.R;

public class UserDashboard extends AppCompatActivity implements View.OnClickListener {

    private TextView btnSearchJob;
    private TextView btnSavedJobs;
    private TextView btnInterView;
    //private TextView btnNotification;
    private TextView btnProfile;
    private TextView btnAppliedJobs;
    private TextView textHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        init();

        textHello.setText("Hello, " + ParseUser.getCurrentUser().getUsername());

    }

    private void init(){


        btnSearchJob = findViewById(R.id.searchJob);
        btnSavedJobs = findViewById(R.id.savedJobs);
        btnInterView = findViewById(R.id.interview);
        //btnNotification = findViewById(R.id.notification);
        btnProfile = findViewById(R.id.userProfile);
        btnAppliedJobs = findViewById(R.id.savedApplied);
        textHello = findViewById(R.id.textHello);

        btnSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),JobBanner.class));
            }
        });

        btnSavedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ViewSavedJobs.class);
                intent.putExtra("isApplied","0");
                startActivity(intent);
            }
        });

        btnInterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InterviewActivity.class));
            }
        });

        btnProfile.setOnClickListener(this);
        btnAppliedJobs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.userProfile){

            startActivityForResult(new Intent(getApplicationContext(),user_profile.class),1);

        }

        else if(v.getId() == R.id.savedApplied){

            Intent intent = new Intent(getApplicationContext(),ViewSavedJobs.class);
            intent.putExtra("isApplied","1");
            startActivity(intent);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(checkLogn.isLogged == 0){

            setResult(RESULT_OK,new Intent());
            finish();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK,new Intent());
        finish();
    }
}
