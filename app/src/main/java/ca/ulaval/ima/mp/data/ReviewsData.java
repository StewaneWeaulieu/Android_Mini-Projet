package ca.ulaval.ima.mp.data;



import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReviewsData {
    private String id;
    private CreatorData creator;
    private String stars;
    private String image;
    private String comment;
    private String date;

    public ReviewsData(
            String pId,
            CreatorData pCreator,
            String pStars,
            String pImage,
            String pComment,
            String pDate){
        id = pId;
        creator = pCreator;
        stars = pStars;
        image = pImage;
        comment = pComment;
        date  = pDate;
    }

    public String getId() {
        return id;
    }

    public String getCreator() {
        return creator.getFirstName() + " " + creator.getLastName() ;
    }

    public float getStars() {
        return  Float.parseFloat(stars);
    }

    public String getImage() {
        return image;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {

        String day = date.substring(8,10);
        String month = date.substring(5,7);
        String year = date.substring(0,4);

        String[] months = {
                "Janvier",
                "Février",
                "Mars",
                "Avril",
                "Mai",
                "Juin",
                "Juillet",
                "Août",
                "Septembre",
                "Octobre",
                "Novembre",
                "Décembre"};

        return day + " " + months[Integer.parseInt(month)-1] + " " + year;
    }


}
