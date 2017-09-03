package isgw.Server;

import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by dhruv on 21/1/17.
 */

public class GetFromParse extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "GetFromParse";
    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected Void doInBackground(Void... voids) {
        JSONArray dataArray = currentUser.getJSONArray("appliance");
        try {
            Log.d(TAG, "JSONDataArray(2): " + dataArray.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
