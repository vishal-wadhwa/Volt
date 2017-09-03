package isgw.Graphs;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import isgw.Activities.AppliancesActivity;
import isgw.Activities.ElectricityActivity;
import isgw.Appliance;
import isgw.R;


/**
 * Created by vishal on 1/20/17.
 */

public class Realtime extends android.support.v4.app.Fragment {

    public static final String INTENT_ACTION = "Realtime";
    private static final String TAG = "Realtime";
    private final Handler mHandler = new Handler();
    private Runnable t1;
    private LineGraphSeries<DataPoint> series1;
    private double lastXVal = 0;

    public static double kwhAC = 1.8;
    public static double kwhRefr = 0.08;
    public static double kwhWashingM = 2;
    public static double kwhTV = 0.113;
    public static double kwhHeater = 0.1;
    public double kwhSmartMeter = 0.1;
    public static double kwhLight = 0.015;

    public static double totalConsum;

    public static final String DEVICES = "appliances";
    private ArrayList<Appliance> appList;

    public Realtime() {
        Log.d(TAG, "Realtime: ctor called");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            appList = (ArrayList<Appliance>) getArguments().getSerializable(DEVICES);
        else appList = (ArrayList<Appliance>) savedInstanceState.getSerializable(DEVICES);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DEVICES, appList);

    }

    public static Realtime getInstance(Bundle b) {
        Realtime r = new Realtime();
        r.setArguments(b);
        return r;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rView = inflater.inflate(R.layout.fragment_realtime, container, false);
        GraphView gView = (GraphView) rView.findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();

        series1.setDrawDataPoints(true);
        series1.setDrawBackground(true);

        series1.appendData(new DataPoint(0, 0), true, 30);
        gView.addSeries(series1);
//        setGraphTitle(gView);
        gView.getViewport().setXAxisBoundsManual(true);
        gView.getViewport().setMinX(0);
        gView.getViewport().setMaxX(30);
        gView.getViewport().setMaxY(10);
        gView.getGridLabelRenderer().setLabelVerticalWidth(100);
        if (getActivity() instanceof ElectricityActivity || getActivity() instanceof AppliancesActivity) {
            gView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(getContext(), GraphZoomActivity.class)
//                            .setAction(INTENT_ACTION));
                }
            });
        } else {
            gView.getViewport().setScrollable(true);
            gView.getViewport().setScrollableY(true);
            gView.getViewport().setScalable(true);
            gView.getViewport().setScalableY(true);
        }
        Log.d(TAG, "onCreateView: called");
        return rView;
    }

//    private void setGraphTitle(GraphView gView) {
//        int args;
//        if (getArguments() != null) args = getArguments().getInt(AppliancesActivity.GRAPH_TYPE);
//        else args = -1;
//        String st = "";
//        switch (args) {
//            case AppliancesActivity.AIR:
//                st += "AC ";
//                break;
//            case AppliancesActivity.FRIDGE:
//                st += "REFRIGERATOR ";
//                break;
//            case AppliancesActivity.WASH_M:
//                st += "WASHING MACHINE ";
//                break;
//            case AppliancesActivity.BULB:
//                st += "BULB ";
//                break;
//            case AppliancesActivity.COMP:
//                st += "TV ";
//                break;
//            case AppliancesActivity.HEATER:
//                st += "HEATER ";
//                break;
//            default:
//                st += "MONTHLY ";
//        }
//        st += "UNIT CONSUMPTION";
//        gView.setTitle(st);
//    }

    @Override
    public void onResume() {
        super.onResume();
        t1 = new Runnable() {
            @Override
            public void run() {
                lastXVal += 2;
                updateConsumption();
                mHandler.postDelayed(this, 5 * 1000);
            }
        };
        mHandler.postDelayed(t1, 0);
        Log.d(TAG, "onResume: called");
    }

    private void updateConsumption() {
        double totalCon = 0;

        for (Appliance app : appList) {
            if (app.getStatus()) {

                double calc = AppliancesActivity.consumptionMap.get(app.getName()) * 0.004166; //15sec in hours
                app.setConsumption(app.getConsumption() + calc);
                app.getParseObject().put("Consumption", app.getParseObject().getDouble("Consumption") + calc);
                app.getParseObject().saveEventually();
                totalCon += calc;

            }
        }
        totalCon *= 1000;
        Log.d(TAG, "updateConsumption: " + totalCon);

        series1.appendData(new DataPoint(lastXVal, totalCon), true, 30);
    }

    private void getConsumption() {
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("Appliances");
        pq.whereEqualTo("User", ParseUser.getCurrentUser());

        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "done: error");
                    return;
                }

                for (ParseObject obj : objects) {

                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
        mHandler.removeCallbacks(t1);

    }
}

