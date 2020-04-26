package ca.ulaval.ima.mp;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import ca.ulaval.ima.mp.data.LocationData;


public class KungryLocation {

    static private LocationData location_data = new LocationData(0,0);
    static private LocationManager  locationManager ;
    static private Context context ;

    public static void InitKungryLocation(LocationManager pLocationManager,Context pContext) {
        locationManager = pLocationManager;
        context = pContext;
    }

    public static LocationData getLocationData() {

//        Criteria criteria = new Criteria();
//        String provider = locationManager.getBestProvider(criteria, true);
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//        }
//        else{
//            Location location = locationManager.getLastKnownLocation(provider);
//            try{
//                location_data.setLatitude(location.getLatitude());
//                location_data.setLongitude(location.getLongitude());
//            }
//            catch (Exception e){
//            }
//        }

        // Hard coder, car la réception ne fonctionnaitpas sur l'api 16, mais fonctionnait sur l'api 21 lors des tests.
        location_data.setLatitude(46.78320166666667);
        location_data.setLongitude(-71.28049333333334);


        return location_data;
    }

    // return distance between two points in kilometers.
    public static double getDistance(LocationData location_1, LocationData location_2) {

        Location location_A = new Location("point A");
        location_A.setLatitude(location_1.getLatitude());
        location_A.setLongitude(location_1.getLongitude());

        Location location_B = new Location("point B");
        location_B.setLatitude(location_2.getLatitude());
        location_B.setLongitude(location_2.getLongitude());

        float distance = location_A.distanceTo(location_B);

        return distance/1000;
    }


}
