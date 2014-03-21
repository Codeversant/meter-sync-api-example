meter-sync-api-example
======================

Installing MeterSync
Please refer to the MeterSync User Guide for installation instructions
Communicating with MeterSync
MeterSync externally broadcasts various Intents to communicate with external programs. It also listens for certain Intents to allow your program to send commands to MeterSync. All communication is handled asynchronously. 

The advantage of this strategy is that if MeterSync is not installed on a user’s device, your app will still work as it should, except that it will receive no information from the taxi meter. 
Meter Events
MeterSync broadcasts four events via Intents:

Meter On 
    /**
     * Meter has been turned on, or is already on (if in response to a status query)
     */
    public static final String MeterOnIntentAction = "com.codeversant.MeterSync.MeterOn";
    
Meter On is broadcast when the meter is switched to hired and the fare begins to accrue.  Your app can listen for this Intent to know that the driver has loaded a fare. This event is also sent in response when an app sends a Query Status Intent and the meter is on.. 

Meter Off
    /**
     * Meter has been turned off, or is already off (if in response to a status query)
     */
    public static final String MeterOffIntentAction = "com.codeversant.MeterSync.MeterOff";

When Meter Off is broadcast as a result of the meter being switched to vacant, the Intent will contain the fare components as String extras.

    public static final String FARE = "FARE";
    public static final String EXTRAS = "EXTRAS";
    public static final String TAX = "TAX";
    public static final String DISTANCE = "DISTANCE";
    public static final String TOTAL = "TOTAL";

Centrodyne meters are currency agnostic, and simply deal with hundredths of a currency unit. In other words, cents in the US and Canada. Distance is expressed in terms of tenths of a mile or kilometer, depending on your meter configuration. MeterSync returns these values as unformatted strings, so that you may format them appropriately for your local currency.

MeterSync broadcasts Meter Off in response to a status query as well, but in those cases, the fare is not included. When the Meter Off Intent contains extras for the fare, tax, etc, your app can be sure that it was sent in response to the meter going vacant, and that it hasn’t already been broadcast for that trip.

Meter Time Off
    /**
     * Meter's timer has been turned off while hired
     */
    public static final String TimeOffIntentAction = "com.codeversant.MeterSync.MeterTimeOff";

The meter is on, but the time has been turned off.   
Meter Time On
    /**
     * Meter's timer has been turned back on while hired
      */
    public static final String TimeOnIntentAction = "com.codeversant.MeterSync.MeterTimeOn";

The meter is on, and the time was off, but has been turned back on
    
Meter Commands
Broadcast by your app to MeterSync

Query Status
    /**
     * Request that MeterSync send a status update.
     * It will respond by sending one of the above intents (ie., MeterOn or MeterOff)
     * Meter Off will not include fare extras if sent in response to this command.
     */
    public static final String QueryMeterStatusAction = "com.codeversant.MeterSync.QueryStatus";

Ask MeterSync to broadcast the current meter status. It will respond with either MeterOn or MeterOff. MeterOff in this case will not include fare extras.
