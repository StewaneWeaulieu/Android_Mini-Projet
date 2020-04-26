package ca.ulaval.ima.mp.data;

import java.util.ArrayList;

public class OpeningHoursData {

    private ArrayList<OpeningHoursDayData> openingHoursDays;

    public OpeningHoursData(ArrayList<OpeningHoursDayData> pOpeningHoursDays){
        openingHoursDays = pOpeningHoursDays;
    }

    public String getMonday() {
        return openingHoursDays.get(1).getDayFromated();
    }

    public String getTuesday() {
        return openingHoursDays.get(2).getDayFromated();
    }

    public String getWednesday() {
        return openingHoursDays.get(3).getDayFromated();
    }

    public String getThursday() {
        return openingHoursDays.get(4).getDayFromated();
    }

    public String getFriday() {
        return openingHoursDays.get(5).getDayFromated();
    }

    public String getSaturday() {
        return openingHoursDays.get(6).getDayFromated();
    }

    public String getSunday() {
        return openingHoursDays.get(0).getDayFromated();
    }

    public Boolean isClose() {
        return (openingHoursDays.size() == 0);
    }



}
