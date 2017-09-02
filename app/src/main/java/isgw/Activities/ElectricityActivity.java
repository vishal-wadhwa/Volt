package isgw.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.parse.ParseUser;

import isgw.R;

public class ElectricityActivity extends AppCompatActivity {

    private static final String TAG = "ElectricityActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        getSupportActionBar().hide();

        final TextView name = (TextView) findViewById(R.id.textView7);
        final TextView bill = (TextView) findViewById(R.id.textView9);
        final TextView consumption = (TextView) findViewById(R.id.textView10);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();

        new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                progressDialog.dismiss();

                if (ParseUser.getCurrentUser() != null) {
                    if (ParseUser.getCurrentUser().get("name").equals("sid")) {


                    } else {
                        name.setText("");
                        bill.setText("0.00");
                        consumption.setText("0.00");
                    }
                }
            }
        }.start();

        if (savedInstanceState == null) {
            loadBarGraph();
        }

    }


    private void loadBarGraph() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction txn = manager.beginTransaction();
        txn.add(R.id.bar_graph_holder, new isgw.Graphs.BarGraph());
        txn.commit();
    }

}
