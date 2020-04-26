package ca.ulaval.ima.mp.data;

import java.util.ArrayList;
import java.util.List;

public class RestaurantData extends RestaurantLightData{

    private OpeningHoursData openingHours;
    private List<ReviewsData> reviewsList;
    private String website;
    private String phoneNumber;


    public RestaurantData(
            String pId,
            String pName,
            CuisineData pCuisine,
            String pType,
            String pReviewCount,
            String pReviewAverage,
            String pImage,
            String pDistance,
            LocationData pLocation,
            OpeningHoursData pOpeningHours,
            List<ReviewsData> pReviews,
            String pWebsite,
            String pPhoneNumber) {
        super(pId,pName,pCuisine,pType,pReviewCount,pReviewAverage,pImage,pDistance,pLocation);
        openingHours = pOpeningHours;
        reviewsList = pReviews;
        website = pWebsite;
        phoneNumber = pPhoneNumber;

    }

    public OpeningHoursData getOpeningHours() {
        return openingHours;
    }

    public List<ReviewsData> getReviews() {
        return reviewsList;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhoneNumber() {

        return "(" +
                phoneNumber.substring(0,3) +
                ") " +
                phoneNumber.substring(3,6) +
                "-" +
                phoneNumber.substring(6,10);
    }

    public String getPhoneNumberForIntent() {
        return "tel:" + phoneNumber;
    }







}






