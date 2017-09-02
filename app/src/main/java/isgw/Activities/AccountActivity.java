package isgw.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.parse.ParseUser;

import isgw.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().hide();

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
}
