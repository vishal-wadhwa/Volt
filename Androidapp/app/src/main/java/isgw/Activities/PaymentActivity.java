package isgw.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thefinestartist.finestwebview.FinestWebView;

import isgw.R;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().hide();

        Intent mIntent = getPackageManager().getLaunchIntentForPackage("com.phonepe.app");

        final Context ctx = this;

        if (mIntent != null) {
            try {
                startActivity(mIntent);
            } catch (ActivityNotFoundException err) {

                new FinestWebView.Builder(ctx).show("https://play.google.com/store/apps/details?id=com.phonepe.app");

            }
        } else {

            new AlertDialog.Builder(ctx)
                    .setTitle("Extra Portal Needed")
                    .setMessage("Are you sure you want to download payment feature ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.phonepe.app")));
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

    }
}
