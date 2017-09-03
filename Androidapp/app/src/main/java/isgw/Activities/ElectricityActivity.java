package isgw.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import isgw.R;

public class ElectricityActivity extends AppCompatActivity {

    private static final String TAG = "ElectricityActivity";
    private Button btnAddUnits;
    private TextView unitsRemaining;
    private TextView consumption;
    private ProgressDialog progressDialog;
    private PieChart pieChart;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        getSupportActionBar().hide();

        final TextView name = (TextView) findViewById(R.id.textView7);
        pieChart = (PieChart) findViewById(R.id.pie_chart);
        unitsRemaining = (TextView) findViewById(R.id.textView9);
        consumption = (TextView) findViewById(R.id.textView10);
        btnAddUnits = (Button) findViewById(R.id.add_units);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        fetchData();
//        new CountDownTimer(2000, 1000) {
//
//            @Override
//            public void onTick(long l) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//                progressDialog.dismiss();
//
//                if (ParseUser.getCurrentUser() != null) {
//                    if (ParseUser.getCurrentUser().get("name").equals("sid")) {
//
//
//                    } else {
//                        name.setText("");
//                        unitsRemaining.setText("0.00");
//                        consumption.setText("0.00");
//                    }
//                }
//            }
//        }.start();


        btnAddUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ElectricityActivity.this);
                builder.setTitle("Purchase units");
                builder.setMessage("Unit cost: Rs. 8.00/unit");
                final EditText et = new EditText(ElectricityActivity.this);
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                builder.setView(et);
                builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (et.getText().toString().isEmpty()) {
                            Toast.makeText(ElectricityActivity.this, "Invalid amount.", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.show();
                            int amt = Integer.valueOf(et.getText().toString());
                            final double units = (double) amt / 8.0;


                            ParseQuery<ParseObject> pq = ParseQuery.getQuery("Units");
                            pq.whereEqualTo("User", ParseUser.getCurrentUser());
                            pq.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    if (e != null) {
                                        progressDialog.dismiss();
                                        return;
                                    }
                                    object.put("Total", object.getDouble("Total") + units);
                                    object.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) fetchData();
                                            else progressDialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
                AlertDialog ab = builder.create();

                ab.show();

            }
        });

    }

    void fetchData() {


        ParseQuery<ParseObject> pq2 = ParseQuery.getQuery("Appliances");
        pq2.whereEqualTo("User", ParseUser.getCurrentUser());
        pq2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    progressDialog.dismiss();
                    return;
                }
                double tcons = 0;
                for (ParseObject po : objects) {
                    tcons += po.getDouble("Consumption");
                }
                String num = String.format(Locale.ENGLISH, "%.2f", tcons);
                consumption.setText("Units Consumed\n\t\t\t" + num + "kWh");


                ParseQuery<ParseObject> pq1 = ParseQuery.getQuery("Units");
                pq1.whereEqualTo("User", ParseUser.getCurrentUser());
                final double finalTcons = tcons;
                pq1.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        progressDialog.dismiss();
                        if (e != null) return;

                        String num = String.format(Locale.ENGLISH, "%.2f", object.getDouble("Total") - finalTcons);
                        unitsRemaining.setText("Units Remaining\n\t\t\t\t" + num + "kWh");

                        pieChart.clear();
                        PieEntry pe0 = new PieEntry((float) finalTcons, "Consumption");
                        PieEntry pe1 = new PieEntry((float) (object.getDouble("Total") - finalTcons), "Remaining");
                        PieDataSet pd = new PieDataSet(Arrays.asList(pe0, pe1), "");

                        pd.setColors(Color.parseColor("#ffff8800"), Color.parseColor("#ff669900"));
                        PieData pieData = new PieData(pd);
                        pieData.setValueTextColor(Color.WHITE);
                        pieData.setValueTextSize(12);

                        Description dp = new Description();
                        dp.setText("");
                        pieChart.setDescription(dp);
//                        pieChart.setEntryLabelColor(Color.BLACK);
                        pieChart.setEntryLabelTextSize(10);
                        pieChart.setData(pieData);
                    }
                });
            }
        });


    }


    private void loadBarGraph() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction txn = manager.beginTransaction();
        txn.replace(R.id.chart_holder, new isgw.Graphs.BarGraph());
        txn.commit();
    }

}
