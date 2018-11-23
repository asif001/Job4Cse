package cse.job.asif.job4cse.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.parse.ParseUser;
import cse.job.asif.job4cse.HelperClass.checkLogn;

import cse.job.asif.job4cse.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnUser;
    private Button btnCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        createNotificationChannel();

        if(ParseUser.getCurrentUser() != null){
            checkLogn.isLogged = 1;
            startActivityForResult(new Intent(getApplicationContext(),UserDashboard.class),1);
        }


    }

    private void init(){

        btnUser = findViewById(R.id.buttonUser);
        btnCompany = findViewById(R.id.buttonCompany);

        btnUser.setOnClickListener(this);

        btnCompany.setOnClickListener(this);

    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "Android";
            String description = "It is the first channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("Channel Two",name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.deleteNotificationChannel("Channel Two");
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.buttonUser){

            startActivityForResult(new Intent(this,UserLogin.class),1);


        }

        else if(v.getId() == R.id.buttonCompany){

            startActivityForResult(new Intent(this,CompanyLogin.class),1);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(checkLogn.isLogged == 1)
            finish();

    }
}