package isgw.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.thefinestartist.finestwebview.FinestWebView;

import isgw.R;

public class CarbonActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon);

        getSupportActionBar().hide();

        new FinestWebView.Builder(this).show("https://kathylovan.github.io/carbon-footprint-calculator/");

    }


}
