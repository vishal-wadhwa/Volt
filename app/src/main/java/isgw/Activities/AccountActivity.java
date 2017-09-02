package isgw.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.ParseUser;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import isgw.R;

public class AccountActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    Pubnub pubnub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        String publish = "pub-c-89e2ce34-7839-47ba-b9b7-3dbc30374038";
        String subscribe = "sub-c-79a67ba4-4d07-11e7-a368-0619f8945a4f";
        pubnub = new Pubnub(publish,subscribe);

        getSupportActionBar().hide();

        ImageView micImg = (ImageView)findViewById(R.id.fab);
        micImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        LinearLayout electricity = (LinearLayout) findViewById(R.id.elec);
        LinearLayout carbon = (LinearLayout) findViewById(R.id.carbon);
        LinearLayout rewards = (LinearLayout) findViewById(R.id.reward);
        LinearLayout profile = (LinearLayout) findViewById(R.id.profile);
        LinearLayout payment = (LinearLayout) findViewById(R.id.payments);
        LinearLayout appliances = (LinearLayout) findViewById(R.id.appliances);

        Button signout = (Button) findViewById(R.id.signout);

        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, ElectricityActivity.class);
                startActivity(intent);
            }
        });

        carbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, CarbonActivity.class);
                startActivity(intent);
            }
        });

        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,RewardsActivity.class);
                startActivity(intent);            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        appliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, AppliancesActivity.class);
                startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ParseUser.getCurrentUser() != null) {
                    ParseUser.logOut();
                }

                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech prompt");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String command = result.get(0);
                    Log.d("CMD", command);

                    if (command.contains("on")){
                        Log.d("CMD", "ON");
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
                    }else if (command.contains("off")){
                        Log.d("CMD","OFF");
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
                }

            }

        }
    }
}
