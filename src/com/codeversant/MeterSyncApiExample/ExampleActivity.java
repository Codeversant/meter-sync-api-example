package com.codeversant.MeterSyncApiExample;

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
    private TextView tripUuidLabel;


    private final BroadcastReceiver meterOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stateLabel.setText("Meter On/Time On");
        }
    };
    private final BroadcastReceiver timeOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stateLabel.setText("Meter On/Time Off");
        }
    };
    private final BroadcastReceiver timeOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stateLabel.setText("Meter On/Time On");
        }
    };



    private final BroadcastReceiver tripCompletedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stateLabel.setText("Meter Off");

            //Optional extra data on last fare
            //this is only present on the initial MeterOff intent
            //NOT in the response to QueryMeterStatus
            String lastFare = intent.getStringExtra(MeterSyncApi.FARE);
            String lastDistance = intent.getStringExtra(MeterSyncApi.DISTANCE);
            String lastExtra = intent.getStringExtra(MeterSyncApi.EXTRAS);
            String lastTax = intent.getStringExtra(MeterSyncApi.TAX);
            String lastTotal = intent.getStringExtra(MeterSyncApi.TOTAL);
            String tripUuid = intent.getStringExtra(MeterSyncApi.TRIP_UUID);

            fareLabel.setText(lastFare);
            extraLabel.setText(lastExtra);
            taxLabel.setText(lastTax);
            totalLabel.setText(lastTotal);
            distanceLabel.setText(lastDistance);
            tripUuidLabel.setText(tripUuid);
        }
    };
    private final BroadcastReceiver meterOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stateLabel.setText("Meter Off");

            //Optional extra data on last fare
            //this is only present on the initial MeterOff intent
            //NOT in the response to QueryMeterStatus
            /*String lastFare = intent.getStringExtra(MeterSyncApi.FARE);
            String lastDistance = intent.getStringExtra(MeterSyncApi.DISTANCE);
            String lastExtra = intent.getStringExtra(MeterSyncApi.EXTRAS);
            String lastTax = intent.getStringExtra(MeterSyncApi.TAX);
            String lastTotal = intent.getStringExtra(MeterSyncApi.TOTAL);

            fareLabel.setText(lastFare);
            extraLabel.setText(lastExtra);
            taxLabel.setText(lastTax);
            totalLabel.setText(lastTotal);
            distanceLabel.setText(lastDistance);*/
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
        tripUuidLabel = (TextView)findViewById(R.id.tripUuid);
        distanceLabel = (TextView)findViewById(R.id.lastDistanceValue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register the receivers for MeterSync messages.
        //We do this in onStart instead of onResume so that we
        //continue to receive messages while we're in the background.
        registerReceiver(meterOnReceiver, new IntentFilter(MeterSyncApi.MeterOnIntentAction));
        registerReceiver(meterOffReceiver, new IntentFilter(MeterSyncApi.MeterOffIntentAction));
        registerReceiver(timeOffReceiver, new IntentFilter(MeterSyncApi.TimeOffIntentAction));
        registerReceiver(timeOnReceiver, new IntentFilter(MeterSyncApi.TimeOnIntentAction));
        registerReceiver(tripCompletedReceiver, new IntentFilter(MeterSyncApi.TripCompletedIntentAction));

    }

    @Override
    protected void onStop() {
        //Unregister the MeterSync message receivers
        unregisterReceiver(meterOnReceiver);
        unregisterReceiver(meterOffReceiver);
        unregisterReceiver(timeOffReceiver);
        unregisterReceiver(timeOnReceiver);
        unregisterReceiver(tripCompletedReceiver);

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Request that MeterSync send a status update
        //indicating whether the meter is on or off
        sendBroadcast(new Intent(MeterSyncApi.QueryMeterStatusAction));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
