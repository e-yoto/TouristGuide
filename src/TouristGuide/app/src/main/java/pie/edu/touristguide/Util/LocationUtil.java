package pie.edu.touristguide.Util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtil {

    public static double[] getCoordinatesFrom(String location, Context context){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addressList == null || addressList.isEmpty()){
            return null;
        }
        Address address = addressList.get(0);
        double latitude = address.getLatitude();
        double longitude= address.getLongitude();

        return new double[]{latitude, longitude};
    }

    public static boolean isLocationValid(String location, Context context){
        if(!location.isEmpty()){
            double[] coordinates = LocationUtil.getCoordinatesFrom(location, context);
            if(coordinates == null){
                return false;
            }
        }
        return true;
    }


}
