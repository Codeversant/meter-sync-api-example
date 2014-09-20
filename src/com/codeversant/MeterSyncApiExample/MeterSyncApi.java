package com.codeversant.MeterSyncApiExample;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 1/14/14
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MeterSyncApi {
    //SENT BY METERSYNC

    /**
     * Meter has been turned on, or is already on (if in response to a status query)
     */
    public static final String MeterOnIntentAction = "com.codeversant.MeterSync.MeterOn";
    /**
     * Meter has been turned off, or is already off (if in response to a status query)
     */
    public static final String MeterOffIntentAction = "com.codeversant.MeterSync.MeterOff";

    /**
     * Meter's timer has been turned off while hired
     */
    public static final String TimeOffIntentAction = "com.codeversant.MeterSync.MeterTimeOff";
    /**
     * Meter's timer has been turned back on while hired
      */
    public static final String TimeOnIntentAction = "com.codeversant.MeterSync.MeterTimeOn";
    public static final String TripCompletedIntentAction = "com.codeversant.MeterSync.TripCompleted";

    //BROADCAST TO REQUEST STATUS UPDATE FROM METERSYNC
    /**
     * Request that MeterSync send a status update.
     * It will respond by sending one of the above intents (ie., MeterOn or MeterOff)
     */
    public static final String QueryMeterStatusAction = "com.codeversant.MeterSync.QueryStatus";

    //Intent Extras Keys found in MeterOff messages
    //that are sent when the meter is turned off.
    //Not included in MeterOff messages that are
    //sent in response to QueryMeterStatusAction!
    public static final String FARE = "FARE";
    public static final String EXTRAS = "EXTRAS";
    public static final String TAX = "TAX";
    public static final String DISTANCE = "DISTANCE";
    public static final String TOTAL = "TOTAL";
    public static final String TRIP_UUID = "TRIP_UUID";

}
