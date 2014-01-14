package com.codeversant.MeterMateClientExample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class ExampleActivity extends Activity {
    public static final String MeterOnIntentAction = "com.codeversant.MeterMate.MeterOn";
    public static final String MeterOffIntentAction = "com.codeversant.MeterMate.MeterOff";

    public static final String QueryMeterStatusAction = "com.codeversant.MeterMate.QueryStatus";

    private TextView stateLabel;
    private TextView fareLabel;
    private TextView extraLabel;
    private TextView taxLabel;
    private TextView totalLabel;
    private TextView distanceLabel;


    private final BroadcastReceiver meterOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stateLabel.setText("Meter On");
        }
    };
    private final BroadcastReceiver meterOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stateLabel.setText("Meter Off");

            String lastFare = intent.getStringExtra("FARE");
            String lastDistance = intent.getStringExtra("DISTANCE");
            String lastExtra = intent.getStringExtra("EXTRAS");
            String lastTax = intent.getStringExtra("TAX");
            String lastTotal = intent.getStringExtra("TOTAL");

            fareLabel.setText(lastFare);
            extraLabel.setText(lastExtra);
            taxLabel.setText(lastTax);
            totalLabel.setText(lastTotal);
            distanceLabel.setText(lastDistance);
        }
    };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        stateLabel = (TextView)findViewById(R.id.stateValue);
        fareLabel = (TextView)findViewById(R.id.lastFareValue);
        extraLabel = (TextView)findViewById(R.id.lastExtraValue);
        taxLabel = (TextView)findViewById(R.id.lastTaxValue);
        totalLabel = (TextView)findViewById(R.id.lastTotalValue);

        distanceLabel = (TextView)findViewById(R.id.lastDistanceValue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(meterOnReceiver, new IntentFilter(MeterOnIntentAction));
        registerReceiver(meterOffReceiver,new IntentFilter(MeterOffIntentAction));

    }

    @Override
    protected void onStop() {
        unregisterReceiver(meterOnReceiver);
        unregisterReceiver(meterOffReceiver);

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendBroadcast(new Intent(QueryMeterStatusAction));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
