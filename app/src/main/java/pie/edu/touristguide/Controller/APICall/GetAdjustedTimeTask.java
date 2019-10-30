package pie.edu.touristguide.Controller.APICall;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import pie.edu.touristguide.Util.LocationUtil;

/**
 * @author BoTao Yu
 */
public class GetAdjustedTimeTask extends AsyncTask<String, Integer, Long> {

    private final static String API_KEY = "AIzaSyB39YKrPTO_qtgonmwgS0nRI6XahULBheY";
    private final static String TAG = GetAdjustedTimeTask.class.getSimpleName();
    private static Context context;
    //errors
    public final static long COORDINATE_NOT_FOUND = -4041;
    public final static long RAW_OFF_SET_NOT_FOUND = -4042;

    public GetAdjustedTimeTask(Context context) {
        this.context = context;
    }

    /**
     * return the rawoffset given the specified location.
     * @param location
     * @return
     */
    private static long getRawOffSet(String location){

        long offset = 0;
        try{

            //"https://maps.googleapis.com/maps/api/geocode/json?address=" + city +"&key=AIzaSyB39YKrPTO_qtgonmwgS0nRI6XahULBheY"
            //get coordinates for city
            double[] coordinates = LocationUtil.getCoordinatesFrom(location, context);
            if(coordinates == null){
                Log.d(TAG, " cannot find coordinates for the given location.");
                return COORDINATE_NOT_FOUND;
            }
            double lat = coordinates[0];
            double lng = coordinates[1];
            Log.d(TAG, "lat: " + lat + "lng: " + lng);

            //get time zone off set Set based on coordinates
            //https://maps.googleapis.com/maps/api/timezone/json?location=45.536301,%20-73.604069&timestamp=1554735739&key=AIzaSyB39YKrPTO_qtgonmwgS0nRI6XahULBheY
            long timeStamp = System.currentTimeMillis()/1000;
            Log.d(TAG, "Testing: sys timestamp: " + String.valueOf(timeStamp));
            Uri.Builder timeZoneUriBuilder = new Uri.Builder();
            timeZoneUriBuilder.scheme("https")
                    .authority("maps.googleapis.com")
                    .appendPath("maps")
                    .appendPath("api")
                    .appendPath("timezone")
                    .appendPath("json")
                    .encodedQuery("location="+ lat + "," + lng)
                    .appendQueryParameter("timestamp", String.valueOf(timeStamp))
                    .appendQueryParameter("key", API_KEY);
            String timeZoneStrURL = timeZoneUriBuilder.build().toString();

            JSONObject timeZoneJson = JSONUtil.getJSONObjectFrom(timeZoneStrURL);
            String status = timeZoneJson.get("status").toString();
            if(!status.equals("OK")){
                Log.d(TAG, "cannot find rawOffSet for the given coordinates. status: " + status);
                return RAW_OFF_SET_NOT_FOUND;
            }else{
                long rawOffSet = Integer.parseInt(timeZoneJson.get("rawOffset").toString());
                long dstOffset = Integer.parseInt(timeZoneJson.get("dstOffset").toString());
                offset = rawOffSet + dstOffset;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return offset;
    }

    @Override
    protected Long doInBackground(String... strings){
        return getRawOffSet(strings[0]);
    }


    /**
     * This is another way to get the adjusted time. However, this is more rigid as ZoneId only
     * accepts major city */
//    ZoneId zoneId = ZoneId.of("America/Montreal");
//    Instant now = Instant.now();
//    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, zoneId);
//    String output = DateTimeFormatter.ofPattern("hh:mm a").format(zonedDateTime);



}
