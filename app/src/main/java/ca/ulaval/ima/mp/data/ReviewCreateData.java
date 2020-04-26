package ca.ulaval.ima.mp.data;

public class ReviewCreateData {
    private String restaurantID;
    private String stars;
    private String comment;

    public ReviewCreateData(
            String pRestaurantID,
            String pStars,
            String pComment) {
        restaurantID = pRestaurantID;
        stars = pStars;
        comment = pComment;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }
}
