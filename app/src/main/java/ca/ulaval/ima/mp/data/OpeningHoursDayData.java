package ca.ulaval.ima.mp.data;

import ca.ulaval.ima.mp.R;

public class OpeningHoursDayData {
    private String id;
    private String openingHour;
    private String closingHour;
    private String day;

    public OpeningHoursDayData (
            String pId,
            String pOpeningHour,
            String pClosingHour,
            String pDay){
        id = pId;
        openingHour = pOpeningHour;
        closingHour = pClosingHour;
        day = pDay;
    }

    public String getId() {
        return id;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public String getDay() {
        return day;
    }

    public String getDayFromated() {
        if (openingHour.equals("null") && closingHour.equals("null")){
            return "Fermé";
        }
        else{
            return openingHour.substring(0,5) + " à " + closingHour.substring(0,5);
        }
    }
}
