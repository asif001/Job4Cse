package cse.job.asif.job4cse.HelperClass;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("549cXJSdZP9L7yvLzTqIVUBZX6hgRgtd3CDuv9uZ")
                .clientKey("gkuTzz5KcQ7ZXp3mXOORY2pxIbSJofHNeyinItqy")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
