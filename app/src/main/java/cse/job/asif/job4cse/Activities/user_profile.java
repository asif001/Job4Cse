package cse.job.asif.job4cse.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import cse.job.asif.job4cse.HelperClass.checkLogn;

import cse.job.asif.job4cse.R;

public class user_profile extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
    }

    private void init(){

        imageView = findViewById(R.id.logout);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {

                        ParseUser.logOut();
                        checkLogn.isLogged = 0;
                        setResult(RESULT_OK,new Intent());
                        finish();

                    }
                });

            }
        });

    }
}
