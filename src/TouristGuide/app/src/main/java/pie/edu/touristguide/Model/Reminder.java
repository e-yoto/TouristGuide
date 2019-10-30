package pie.edu.touristguide.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.GetReminderDataTask;
import pie.edu.touristguide.Util.WeatherIconUtils;

/**
 * @author BoTao Yu
 * POJO object for Reminder
 */
public class Reminder implements Serializable, Cloneable {

    private String title;       //Title of reminder
    private String startTime;        //the time condition for reminder
    private String endTime;        //the time condition for reminder
    private int weatherIconId;
    private String location;
    private double latitude;
    private double longtitude;

    public Reminder(String title, String startTime, String endTime, int weatherIconId, String location, double latitude, double longtitude) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weatherIconId = weatherIconId;
        this.location = location;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    public void setWeatherIconId(int weatherIconId) {
        this.weatherIconId = weatherIconId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public Reminder clone() throws CloneNotSupportedException {
        return (Reminder) super.clone();
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", weatherIconId=" + weatherIconId +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                '}';
    }

    //TODO: currently the fields are all sorted by alphabet, the fields should be sorted by other ways.
    //If the Reminders are sorted by location, the reminder with closest location will display first (use Google Map)
    //If the Reminders are sorted by time, the reminder with the time that is closest to system time will display first.
    //if the Reminders are sorted by weather, the reminder with the most recent weather will display first.
    public static class LocationComparator implements Comparator<Reminder> {

        private static final String TAG = LocationComparator.class.getSimpleName();
        @Override
        public int compare(Reminder reminder1, Reminder reminder2) {
            //TODO: This current coordinates is set to Vanier College
            Double[] vanierCollegeCoordinates = {45.515156, -73.675942};
            Double[] thisCoordinates = {reminder1.getLatitude(), reminder1.getLongtitude()};
            Double[] thatCoordinates = {reminder2.getLatitude(), reminder2.getLongtitude()};
            double thisDistance =  distanceTo(vanierCollegeCoordinates, thisCoordinates);
            double thatDistance = distanceTo(vanierCollegeCoordinates, thatCoordinates);
            Log.d(TAG, "====== " +reminder1.getTitle() + " compare to " + reminder2.getTitle() + " ======");
            Log.d(TAG, thisDistance + "km compareTo " + thatDistance + " km");
            Log.d(TAG, thisCoordinates[0] + ", " + thisCoordinates[1] + " compareTo " +
                    thatCoordinates[0] + ", " + thatCoordinates[1]);
            return Double.compare(thisDistance, thatDistance);
        }

        /**
         * returns distance between 2 coordinates using Harversine formula.
         * reference: https://www.movable-type.co.uk/scripts/latlong.html
         * @param fromCoordinates for Harversine formula.
         * @param toCoordinates for Harversine formula.
         * @return distance between 2 Coordinates.
         */
        public double distanceTo(Double[] fromCoordinates, Double[] toCoordinates){
            final int radius = 6371; //in KM
            double fromLatitude = fromCoordinates[0];
            double fromLongitude = fromCoordinates[1];
            double toLatitude = toCoordinates[0];
            double toLongitude = toCoordinates[1];

            //Convert values into radian
            double fromLatitudeRadian = Math.toRadians(fromLatitude);
            double toLatitudeRadian = Math.toRadians(toLatitude);
            double deltaLatitudeRadian = Math.toRadians(fromLatitude-toLatitude);
            double deltaLongitudeRadian = Math.toRadians(fromLongitude-toLongitude);

            //Harversine formula
            double a = Math.pow(Math.sin(deltaLatitudeRadian/2),2) +
                    Math.cos(fromLatitudeRadian) * Math.cos(toLatitudeRadian) *
                            Math.pow(Math.sin(deltaLongitudeRadian/2), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

            return radius * c;
        }

    }

    public static class TimeComparator implements Comparator<Reminder> {

        @Override
        public int compare(Reminder reminder1, Reminder reminder2) {


            SimpleDateFormat _12HourFormat = new SimpleDateFormat("hh:mm a");

            try {
                String currentDateStr = _12HourFormat.format(new Date());
                Date currentTime = _12HourFormat.parse(currentDateStr);
                Date thisStartTime = _12HourFormat.parse(reminder1.getStartTime());
                Date thatStartTime = _12HourFormat.parse(reminder2.getStartTime());

                long thisDifference = currentTime.getTime() - thisStartTime.getTime();
                long thatDifference = currentTime.getTime() - thatStartTime.getTime();

                //Both reminder time have already pass for the day
                if(Long.signum(thisDifference) == -1 && Long.signum(thatDifference) == -1)
                    return Long.compare(thisDifference * -1, thatDifference* -1);
                else if(Long.signum(thisDifference) == 1 && Long.signum(thatDifference) == 1)
                    return Long.compare(thatDifference, thisDifference);
                else if(Long.signum(thisDifference) == 1 && Long.signum(thatDifference) == -1)
                    return 1;
                else if(Long.signum(thisDifference) == -1 && Long.signum(thatDifference) == 1)
                    return -1;

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public static class WeatherComparator implements Comparator<Reminder> {

        private static String TAG = WeatherComparator.class.getSimpleName();

        @Override
        public int compare(Reminder reminder1, Reminder reminder2) {
            int weatherIconId = WeatherIconUtils.getWeatherIconsWith(String.valueOf(45.515156), String.valueOf(-73.675942));
            Log.d(TAG, "Current weatherIconId: " + weatherIconId);
            if(reminder1.getWeatherIconId() == weatherIconId)
                return -1;
            else if(reminder2.getWeatherIconId() == weatherIconId)
                return 1;
            return 0;
        }


    }


}
