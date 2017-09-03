package isgw.Server;

import android.os.AsyncTask;

import com.parse.ParseUser;

import java.util.Arrays;

/**
 * Created by dhruv on 20/1/17.
 */

public class SendToParse extends AsyncTask<Void, Void, Void> {

    private String date;
    private long time;
    private String appliance;
    private int unitsConsumed;
    private int hoursUsed;

    private String userName;

    public SendToParse(String date, long time, String appliance, int unitsConsumed, int hoursUsed) {
        this.date = date;
        this.time = time;
        this.appliance = appliance;
        this.hoursUsed = hoursUsed;
        this.unitsConsumed = unitsConsumed;
    }

    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected Void doInBackground(Void... voids) {
        currentUser.addAll("appliance", Arrays.asList(appliance));
        currentUser.addAll("unitsConsumed", Arrays.asList(unitsConsumed));
        currentUser.addAll("hoursUsed", Arrays.asList(hoursUsed));
        currentUser.addAll("date", Arrays.asList(date));
        currentUser.addAll("time", Arrays.asList(time));

        currentUser.saveInBackground();
        return null;
    }
}