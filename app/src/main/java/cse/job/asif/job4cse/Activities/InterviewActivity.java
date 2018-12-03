package cse.job.asif.job4cse.Activities;

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
import java.util.List;

import cse.job.asif.job4cse.HelperClass.Interview;
import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.recyclerViews.InterviewAdapter;

public class InterviewActivity extends AppCompatActivity {

    private ArrayList<Interview> interviews;
    private RecyclerView rvJobs;
    private InterviewAdapter interviewAdapter;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);
        init();
        initRecyclerView();
        readData();
    }

    private void init(){

        interviews = new ArrayList<>();
        currentUser = ParseUser.getCurrentUser().getUsername();

    }

    private void initRecyclerView(){

        rvJobs = (RecyclerView) findViewById(R.id.interviewList);

        interviewAdapter = new InterviewAdapter(interviews);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvJobs.setLayoutManager(mLayoutManager);
        rvJobs.setItemAnimator(new DefaultItemAnimator());
        rvJobs.setAdapter(interviewAdapter);


    }

    private void readData(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("InterView");
        query.whereEqualTo("username",currentUser);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                for(ParseObject object : objects){

                    Interview interview = new Interview();

                    interview.setCompName(object.getString("CompName"));
                    interview.setDate(object.getString("Date"));
                    interview.setJobTitle(object.getString("Title"));

                    interviews.add(interview);

                }

                interviewAdapter.notifyDataSetChanged();

            }
        });


    }



}
