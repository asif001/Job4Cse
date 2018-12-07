package cse.job.asif.job4cse.HelperClass;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("WePkcG9ExNbANADAfYUsJgXg7nSpaE9VKnAlRLoi")
                .clientKey("nmBRdUrKgN3K2kd68aKCoj1AOP4X40iZkhWAEUid")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
