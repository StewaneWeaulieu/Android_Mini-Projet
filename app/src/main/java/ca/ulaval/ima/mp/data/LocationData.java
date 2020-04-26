package ca.ulaval.ima.mp.data;

public class LocationData {
    private double latitude;
    private double longitude;

    public LocationData(
            double pLatitude,
            double pLongitude) {
        latitude = pLatitude;
        longitude = pLongitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}