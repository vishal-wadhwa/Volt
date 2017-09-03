package isgw.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.Random;

import isgw.R;

public class RewardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        getSupportActionBar().hide();

        final TextView currentUsage = (TextView) findViewById(R.id.textView8);
        final TextView limitUsage = (TextView) findViewById(R.id.textView12);
        final TextView rewards = (TextView) findViewById(R.id.textView15);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();

        final Context ctx = this;

        new CountDownTimer(2000,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();

                if (ParseUser.getCurrentUser()!=null){
                    if (ParseUser.getCurrentUser().get("name").equals("sid")){

                    } else {
                        currentUsage.setText("0.00 kW");
                        limitUsage.setText("0.00 kW");
                        rewards.setText("0");
                    }
                }

                ImageView phonepe = (ImageView) findViewById(R.id.paymentapp);
                phonepe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ctx,PaymentActivity.class);
                        startActivity(intent);
                    }
                });

                ImageView paytm = (ImageView) findViewById(R.id.paytm);
                paytm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int random = new Random().nextInt(10);
                        new AlertDialog.Builder(ctx)
                                .setTitle("Code Generated")
                                .setMessage("Your code is #EZ" + String.valueOf(random) + String.valueOf(random+2) + String.valueOf(random-2))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                        finish();
                                    }
                                })
                                .setIcon(R.drawable.phonepe)
                                .show();
                    }
                });

                ImageView uber = (ImageView) findViewById(R.id.uber);
                uber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int random = new Random().nextInt(10);
                        new AlertDialog.Builder(ctx)
                                .setTitle("Code Generated")
                                .setMessage("Your code is #EZ" + String.valueOf(random) + String.valueOf(random+2) + String.valueOf(random-2))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                        finish();
                                    }
                                })
                                .setIcon(R.drawable.phonepe)
                                .show();
                    }
                });
            }
        }.start();


    }
}
