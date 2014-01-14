package com.codeversant.MeterMateClientExample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class ExampleActivity extends Activity {



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

            //Optional extra data on last fare
            //this is only present on the initial MeterOff intent
            //NOT in the response to QueryMeterStatus
            String lastFare = intent.getStringExtra(MeterMateApi.FARE);
            String lastDistance = intent.getStringExtra(MeterMateApi.DISTANCE);
            String lastExtra = intent.getStringExtra(MeterMateApi.EXTRAS);
            String lastTax = intent.getStringExtra(MeterMateApi.TAX);
            String lastTotal = intent.getStringExtra(MeterMateApi.TOTAL);

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
        registerReceiver(meterOnReceiver, new IntentFilter(MeterMateApi.MeterOnIntentAction));
        registerReceiver(meterOffReceiver, new IntentFilter(MeterMateApi.MeterOffIntentAction));

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
        sendBroadcast(new Intent(MeterMateApi.QueryMeterStatusAction));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
