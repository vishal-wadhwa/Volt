package isgw.Graphs;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.parse.ParseException;
import com.parse.ParseUser;

import isgw.Activities.AppliancesActivity;
import isgw.Activities.ElectricityActivity;
import isgw.Activities.GraphZoomActivity;
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

    public double kwhAC = 1.8;
    public double kwhRefr = 0.08;
    public double kwhWashingM = 2;
    public double kwhTV = 0.113;
    public double kwhHeater = 0.1;
    public double kwhSmartMeter = 0.1;
    public double kwhLight = 0.038;

    public static double totalConsum;

    public Realtime() {
        Log.d(TAG, "Realtime: ctor called");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ParseUser currUser = ParseUser.getCurrentUser();
        try {
            currUser = currUser.fetch();
        } catch (ParseException e) {
            e.printStackTrace();
        }

       super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rView = inflater.inflate(R.layout.fragment_realtime, container, false);
        GraphView gView = (GraphView) rView.findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();
        series1.setDrawDataPoints(true);
        series1.setDrawBackground(true);
        gView.addSeries(series1);
//        setGraphTitle(gView);
        gView.getViewport().setXAxisBoundsManual(true);
        gView.getViewport().setMinX(0);
        gView.getViewport().setMaxX(30);

        gView.getGridLabelRenderer().setLabelVerticalWidth(100);
        if (getActivity() instanceof ElectricityActivity || getActivity() instanceof AppliancesActivity) {
            gView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), GraphZoomActivity.class)
                            .setAction(INTENT_ACTION));
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
                lastXVal += 0.5;
//                new LiveRequestToParse().execute();
                mHandler.postDelayed(this, 10 * 1000);
            }
        };
        mHandler.postDelayed(t1, 0);
        Log.d(TAG, "onResume: called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
        mHandler.removeCallbacks(t1);

    }


    // TODO: 1/20/17 to be removed: complete
//    private double lastRandVal = 2d;
//    Random r = new Random();
//
//    private double getRand() {
//        return lastRandVal = (lastRandVal + r.nextDouble() * 0.7 - 0.25);
//    }


    public class LiveRequestToParse extends AsyncTask<Void, Void, Void> {

        ParseUser currUser = ParseUser.getCurrentUser();

        @Override
        protected Void doInBackground(Void... voids) {

            totalConsum = 0;
            try {
                currUser = currUser.fetch();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String airconditioner = currUser.getString("Airconditioner");
            Log.d(TAG, "air::: " + airconditioner);
            if (airconditioner.equals("1")) {
//                String timeUse = currUser.getString("timeStatus");
                if (currUser.getString("BtnAirconditioner").equals("1")) {
                    totalConsum += kwhAC;
                }
                Log.d(TAG, "aircond" + totalConsum);
            }
//            currUser = ParseUser.getCurrentUser();
            String SmartMeter = "1";
                    //currUser.getString("SmartMeter");
            if (SmartMeter.equals("1")) {
//                String timeUse = currUser.getString("timeStatus");
//                int time = Integer.parseInt(timeUse);
//                            totalConsum += time * kwhSmartMeter;
                if (currUser.getString("SmartMeter").equals("1")) {
                    totalConsum += kwhSmartMeter;
                } else {
                    totalConsum = 0;
                }
            }

//            currUser = ParseUser.getCurrentUser();
            String Fridge = currUser.getString("Fridge");
            if (Fridge.equals("1")) {
//                String timeUse = currUser.getString("timeStatus");
//                int time = Integer.parseInt(timeUse);
//                            totalConsum += time * kwhRefr;
                if (currUser.getString("BtnFridge").equals("1")) {
                    totalConsum += kwhRefr;
                }
                Log.d(TAG, "doInBackground: " + totalConsum);
            }

//            currUser = ParseUser.getCurrentUser();
            String Lighting = currUser.getString("Lighting");
            if (Lighting.equals("1")) {
//                String timeUse = currUser.getString("timeStatus");
//                int time = Integer.parseInt(timeUse);
//                            totalConsum += time * kwhLight;
                if (currUser.getString("BtnLighting").equals("1")) {

                    totalConsum += kwhLight;
                }
            }

//            currUser = ParseUser.getCurrentUser();
            String WashingMachine = currUser.getString("WashingMachine");
            if (WashingMachine.equals("1")) {
//                String timeUse = currUser.getString("timeStatus");
//                int time = Integer.parseInt(timeUse);
//                            totalConsum += time * kwhWashingM;
                if (currUser.getString("BtnWashingM").equals("1")) {
                    totalConsum += kwhWashingM;
                }
            }

//            currUser = ParseUser.getCurrentUser();
            String TV = currUser.getString("TV");
            if (TV.equals("1")) {
//                String timeUse = currUser.getString("timeStatus");
//                int time = Integer.parseInt(timeUse);
//                            totalConsum += time * kwhTV;
                if (currUser.getString("BtnTV").equals("1")) {
                    totalConsum += kwhTV;
                }
            }

            String Heater = currUser.getString("Heater");
            if (Heater.equals("1")) {
//                String timeUse = currUser.getString("timeStatus");
//                int time = Integer.parseInt(timeUse);
//                            totalConsum += time * kwhHeater;
                if (currUser.getString("BtnHeater").equals("1")) {
                    totalConsum += kwhHeater;
                }
            }

            Log.d(TAG, "doInBackground: sjsj" + totalConsum);
            series1.appendData(new DataPoint(lastXVal, totalConsum), true, 30);
            return null;
        }
    }
}