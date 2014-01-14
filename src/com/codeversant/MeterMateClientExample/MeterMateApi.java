package com.codeversant.MeterMateClientExample;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 1/14/14
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MeterMateApi {
    //SENT BY METERMATE
    public static final String MeterOnIntentAction = "com.codeversant.MeterMate.MeterOn";
    public static final String MeterOffIntentAction = "com.codeversant.MeterMate.MeterOff";

    //BROADCAST TO REQUEST STATUS UPDATE FROM METERMATE
    public static final String QueryMeterStatusAction = "com.codeversant.MeterMate.QueryStatus";

    //Intent Extras Keys
    public static final String DISTANCE_UNIT = "DISTANCE_UNIT";
    public static final String FARE = "FARE";
    public static final String EXTRAS = "EXTRAS";
    public static final String TAX = "TAX";
    public static final String DISTANCE = "DISTANCE";
    public static final String PAID_DISTANCE = "PAID_DISTANCE";
    public static final String TRIPS = "TRIPS";
    public static final String TOTAL = "TOTAL";

}
