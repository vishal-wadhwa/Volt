package isgw;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("tHngQYBJmifYY141pqCbB2UePtRUlAHaazDVyIkd")
                .clientKey("SSTTaBiNXpnBqBdbvalUeytwyM3JSkYErPOLZoRA")
                .server("https://parseapi.back4app.com/").build()
        );

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("AAAAVqJmGpM:APA91bHeNKZYF-u2qpsVil0IqQl9sOhG5e7AA9GkeZpyGJQ1BiQMD47mUOuuTFfYPBwtPu20VGyZOjm7mLl4bVbluo8iARFpIhQ4rWbm0UD7oHfMyZcSIMP5muJshWRzzEuq6npKmq_C", "372091787923");
        installation.saveInBackground();

        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
