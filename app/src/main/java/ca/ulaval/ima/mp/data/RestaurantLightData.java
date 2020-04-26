package ca.ulaval.ima.mp.data;

import java.util.ArrayList;

import ca.ulaval.ima.mp.KungryLocation;

public class RestaurantLightData {
    private String id;
    private String name;
    private CuisineData cuisine;
    private String type;
    private String review_count;
    private String review_average;
    private String image;
    private String distance;
    private LocationData location;

    public RestaurantLightData(
            String pId,
            String pName,
            CuisineData pCuisine,
            String pType,
            String pReviewCount,
            String pReviewAverage,
            String pImage,
            String pDistance,
            LocationData pLocation) {
        id = pId;
        name = pName;
        cuisine = pCuisine;
        type = pType;
        review_count = pReviewCount;
        review_average = pReviewAverage;
        image = pImage;
        distance = pDistance;
        location = pLocation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        String restaurantType;

        if(type.equals("RESTO")){
            restaurantType = "Restaurant";
        }
        else if(type.equals("BAR")){
            restaurantType = "Bar";
        }
        else if(type.equals("SNACK")){
            restaurantType = "Snack/Food";
        }
        else{
            restaurantType = "Erreur";
        }

        return restaurantType +  " • " + cuisine.getName();
    }

    public String getReviewCount() {
        return ("(" + review_count + ")");
    }

    public Float getReviewAverage() {
        return Float.parseFloat(review_average);
    }

    public String getImage() {
        return image;
    }

    public String getDistance() {

        LocationData location_user = KungryLocation.getLocationData();
        double distance_double = KungryLocation.getDistance(location_user,location);
        String distance_string = Double.toString((double)Math.round(distance_double * 10d) / 10d);

        return distance_string + " km";
    }

    public LocationData getLocation() {
        return location;
    }


}


