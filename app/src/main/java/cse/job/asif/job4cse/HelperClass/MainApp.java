package cse.job.asif.job4cse.HelperClass;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("vqyIyWK30uqfB3vJb0VVXuje8mUtYtO2mFycgeLr")
                .clientKey("0YLqh2DSOxkOT1e3OJoPzFV2Ux0Yk5Yfpd2h2i2x")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
