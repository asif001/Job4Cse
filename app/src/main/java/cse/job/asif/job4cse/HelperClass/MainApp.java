package cse.job.asif.job4cse.HelperClass;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("f3WTPJX92L1c8CykB6b6NVAcl9T08CLxCFm3SSpG")
                .clientKey("9xxXGrjYdtuueXP8eRzYFfGDiUJ9OaNcRYhKB1JW")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
