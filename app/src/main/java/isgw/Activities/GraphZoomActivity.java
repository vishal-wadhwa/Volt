package isgw.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import isgw.Graphs.BarGraph;
import isgw.Graphs.Realtime;
import isgw.R;

public class GraphZoomActivity extends AppCompatActivity {

    private static final String TAG = "GraphZoomActivity";
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_zoom);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i = getIntent();
        manager = getSupportFragmentManager();

        if (savedInstanceState == null)
            switch (i.getAction()) {
                case isgw.Graphs.Realtime.INTENT_ACTION:
                    initRealtime(i.getIntExtra(AppliancesActivity.GRAPH_TYPE,-1));
                    break;
                case isgw.Graphs.BarGraph.INTENT_ACTION:
                    initBarGraph();
                    break;
                default:
                    Log.d(TAG, "onCreate: no match found");
            }
    }

    private void initBarGraph() {
        FragmentTransaction txn1 = manager.beginTransaction();
        txn1.replace(R.id.activity_graph_zoom, new BarGraph());
        txn1.commit();
        Log.d(TAG, "onCreate: matched " + BarGraph.INTENT_ACTION);
    }

    private void initRealtime(int gType) {
        Fragment rt=new Realtime();
        Bundle bundle=new Bundle();
        bundle.putInt(AppliancesActivity.GRAPH_TYPE,gType);
        rt.setArguments(bundle);
        FragmentTransaction txn = manager.beginTransaction();
        txn.replace(R.id.activity_graph_zoom, rt);
        txn.commit();
        Log.d(TAG, "onCreate: matched " + Realtime.INTENT_ACTION);

    }
}
