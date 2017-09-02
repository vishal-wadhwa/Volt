package isgw.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import isgw.Appliance;
import isgw.Graphs.Realtime;
import isgw.R;

public class AppliancesActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String GRAPH_TYPE = "graph_type";
    public static final String AIR_CONDITIONER = "Air Conditioner";
    public static final String WASHING_MACHINE = "Washing Machine";
    public static final String LIGHTING = "Lighting";
    public static final String HEATER = "Heater";
    public static final String TELEVISION = "Television";
    public static final String REFRIGERATOR = "Refrigerator";
    private int totalConsumption = 0;

    private Pubnub pubnub;
//private Pubnub pubnub;
    private final Handler mHandler = new Handler();
    private Runnable t1;

    private ImageView acIW;
    private ImageView fridgeIW;
    private ImageView tvIW;
    private ImageView heaterIW;
    private ImageView wmIW;
    private ImageView lightIW;
    private ProgressDialog pdg;
    private List<Appliance> applList;
    private static final String TAG = "ApplianceAct";
    public static final HashMap<String, Double> consumptionMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);

        getSupportActionBar().hide();

        String publish = "pub-c-89e2ce34-7839-47ba-b9b7-3dbc30374038";
        String subscribe = "sub-c-79a67ba4-4d07-11e7-a368-0619f8945a4f";
        pubnub = new Pubnub(publish,subscribe);


        consumptionMap.put(AIR_CONDITIONER, Realtime.kwhAC);
        consumptionMap.put(REFRIGERATOR, Realtime.kwhRefr);
        consumptionMap.put(WASHING_MACHINE, Realtime.kwhWashingM);
        consumptionMap.put(TELEVISION, Realtime.kwhTV);
        consumptionMap.put(HEATER, Realtime.kwhHeater);
        consumptionMap.put(LIGHTING, Realtime.kwhLight);

        pdg = new ProgressDialog(this);
        pdg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdg.setCancelable(false);
        pdg.setCanceledOnTouchOutside(false);
        pdg.setIndeterminate(true);
        pdg.setMessage("Loading data...");
        pdg.setTitle("Please Wait...");
        pdg.show();

        if (savedInstanceState == null) {
           // loadRealtimeGraph(applList);
        }


        acIW = (ImageView) findViewById(R.id.air_c);
        heaterIW = (ImageView) findViewById(R.id.heater);
        wmIW = (ImageView) findViewById(R.id.wash_m);
        lightIW = (ImageView) findViewById(R.id.bulb);
        tvIW = (ImageView) findViewById(R.id.comp);
        fridgeIW = (ImageView) findViewById(R.id.ref);
//        mainsButton = (Button) findViewById(R.id.mains);

        acIW.setOnClickListener(this);
        heaterIW.setOnClickListener(this);
        wmIW.setOnClickListener(this);
        lightIW.setOnClickListener(this);
        tvIW.setOnClickListener(this);
        fridgeIW.setOnClickListener(this);

        applList = new ArrayList<>();
        inflateData(savedInstanceState);

    }


    private void loadRealtimeGraph(ArrayList<Appliance> applList) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction txn = manager.beginTransaction();
        Bundle b = new Bundle();
        b.putSerializable(Realtime.DEVICES, applList);
        Realtime f = Realtime.getInstance(b);
//        f.setArguments(b);
        txn.replace(R.id.real_graph_holder, f);
        txn.commit();
    }

    void inflateData(final Bundle savedInstanceState) {
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("Appliances");
        pq.whereEqualTo("User", ParseUser.getCurrentUser());

        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                pdg.dismiss();
                if (e != null) {
                    Log.d(TAG, "done: " + e.getMessage());
                    return;
                }

                for (ParseObject appliance : objects) {

                    String name = appliance.getString("Name");
                    boolean status = appliance.getBoolean("Status");
                    Date startTime = appliance.getDate("StartTime");
                    Date endTime = appliance.getDate("EndTime");
                    int consumption = appliance.getInt("Consumption");
                    boolean allowed = appliance.getBoolean("Allowed");

                    applList.add(new Appliance(name, status, allowed, 0, startTime, endTime, appliance));
                    changeImage(status, name);

                    if (savedInstanceState == null) {
                        loadRealtimeGraph((ArrayList<Appliance>) applList);
                    }
                }


            }
        });
    }

    void changeImage(boolean status, String name) {
        switch (name) {
            case AIR_CONDITIONER:

                if (!status) acIW.setImageResource(R.drawable.acbw);
                else acIW.setImageResource(R.drawable.ac);
                break;
            case WASHING_MACHINE:
                if (!status) wmIW.setImageResource(R.drawable.wmbw);
                else wmIW.setImageResource(R.drawable.wm);
                break;
            case TELEVISION:
                if (!status) tvIW.setImageResource(R.drawable.computerbw);
                else tvIW.setImageResource(R.drawable.computer);
                break;
            case HEATER:
                if (!status) heaterIW.setImageResource(R.drawable.fireplacebw);
                else heaterIW.setImageResource(R.drawable.fireplace);
                break;
            case LIGHTING:
                if (!status){
                    lightIW.setImageResource(R.drawable.bulbw);

                    Callback callback = new Callback() {
                        public void successCallback(String channel, Object response) {
                            Log.d("Check","working");
                            System.out.println(response.toString());
                        }
                        public void errorCallback(String channel, PubnubError error) {
                            Log.d("Check",error.toString());
                            System.out.println(error.toString());
                        }
                    };
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("led",1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray arr = new JSONArray();
                    arr.put(obj);
                    pubnub.publish("disco",obj, callback);

                }
                else{
                    lightIW.setImageResource(R.drawable.bulb);

                    Callback callback = new Callback() {
                        public void successCallback(String channel, Object response) {
                            Log.d("Check","working");
                            System.out.println(response.toString());
                        }
                        public void errorCallback(String channel, PubnubError error) {
                            Log.d("Check",error.toString());
                            System.out.println(error.toString());
                        }
                    };
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("led",0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray arr = new JSONArray();
                    arr.put(obj);
                    pubnub.publish("disco",obj, callback);

                }
                break;
            case REFRIGERATOR:
                if (!status) fridgeIW.setImageResource(R.drawable.refbw);
                else fridgeIW.setImageResource(R.drawable.ref);
                break;

            default:
        }
    }

    Appliance getAppliance(String appl) {
        for (int i = 0; i < applList.size(); i++) {
            if (appl.equals(applList.get(i).getName())) {
                return applList.get(i);
            }
        }
        return null;
    }

    void changeStatus(final String appl) {
        final Appliance applObj = getAppliance(appl);

        if (!applObj.isAllowed()) {
            Toast.makeText(this, "Not allowed.", Toast.LENGTH_SHORT).show();
            return;
        }

        applObj.getParseObject().put("Status", !applObj.getStatus());

        applObj.getParseObject().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(AppliancesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                applObj.setStatus(!applObj.getStatus());
                changeImage(applObj.getStatus(), appl);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.air_c:
                changeStatus(AIR_CONDITIONER);
                break;
            case R.id.ref:
                changeStatus(REFRIGERATOR);
                break;
            case R.id.bulb:
                changeStatus(LIGHTING);
                break;
            case R.id.wash_m:
                changeStatus(WASHING_MACHINE);
                break;
            case R.id.heater:
                changeStatus(HEATER);
                break;
            case R.id.comp:
                changeStatus(TELEVISION);
                break;
//               break;
            default:
        }
    }

//    void setButtons() {
//        if (acBeacon.equals("0")) {
//            acIW.setImageResource(R.drawable.acbw);
//            acIW.setOnClickListener(null);
//        } else {
//            if (acStat.equals("1"))
//                acIW.setImageResource(R.drawable.ac);
//            else
//                acIW.setImageResource(R.drawable.acbw);
//            acIW.setOnClickListener(this);
//        }
//        if (heaterBeacon.equals("0")) {
//            heaterIW.setImageResource(R.drawable.fireplacebw);
//            heaterIW.setOnClickListener(null);
//        } else {
//            if (heaterStat.equals("1"))
//                heaterIW.setImageResource(R.drawable.fireplace);
//            else
//                heaterIW.setImageResource(R.drawable.fireplacebw);
//            heaterIW.setOnClickListener(this);
//        }
//        if (wmBeacon.equals("0")) {
//            wmIW.setImageResource(R.drawable.wmbw);
//            wmIW.setOnClickListener(null);
//        } else {
//            if (wmStat.equals("1"))
//                wmIW.setImageResource(R.drawable.wm);
//            else
//                wmIW.setImageResource(R.drawable.wmbw);
//            wmIW.setOnClickListener(this);
//        }
//        if (lightBeacon.equals("0")) {
//            lightIW.setImageResource(R.drawable.bulbw);
//            lightIW.setOnClickListener(null);
//        } else {
//            if (lightStat.equals("1"))
//                lightIW.setImageResource(R.drawable.bulb);
//            else
//                lightIW.setImageResource(R.drawable.bulbw);
//            lightIW.setOnClickListener(this);
//        }
//        if (tvBeacon.equals("0")) {
//            tvIW.setImageResource(R.drawable.computerbw);
//            tvIW.setOnClickListener(null);
//        } else {
//            if (tvStat.equals("1"))
//                tvIW.setImageResource(R.drawable.computer);
//            else
//                tvIW.setImageResource(R.drawable.computerbw);
//            tvIW.setOnClickListener(this);
//        }
//        if (fridgeBeacon.equals("0")) {
//            fridgeIW.setImageResource(R.drawable.refbw
//            );
//            fridgeIW.setOnClickListener(null);
//        } else {
//            if (fridgeStat.equals("1"))
//                fridgeIW.setImageResource(R.drawable.ref);
//            else
//                fridgeIW.setImageResource(R.drawable.refbw);
//            fridgeIW.setOnClickListener(this);
//        }
//        if (smartMeterBeacon.equals("0")) {
//            mainsButton.setBackgroundColor(Color.GRAY);
//            mainsButton.setOnClickListener(null);
//        } else {
//            if (meterStat.equals("1")) {
//                mainsButton.setBackgroundColor(Color.RED);
//            } else {
//                mainsButton.setBackgroundColor(Color.GREEN);
//            }
//            mainsButton.setOnClickListener(this);
//        }
//    }
//
//    private String invertStatus(String i) {
//        String s;
//        if (i.equals("0")) {
//            s = "1";
//        } else {
//            s = "0";
//        }
//        return s;
//    }
//
//    public class getBeaconStatus extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                currUser = currUser.fetch();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            acBeacon = currUser.getString("Airconditioner");
//            fridgeBeacon = currUser.getString("Fridge");
//            lightBeacon = currUser.getString("Lighting");
//            heaterBeacon = currUser.getString("Heater");
//            tvBeacon = currUser.getString("TV");
//            wmBeacon = currUser.getString("WashingMachine");
//            smartMeterBeacon = currUser.getString("SmartMeter");
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            setButtons();
//        }
//    }


}
