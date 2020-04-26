package ca.ulaval.ima.mp.data;

public class AccountData {
    private String total_review_count;
    private String last_name;
    private String first_name;
    private String email;
    private String created;
    private String updated;
    private String user;


    public AccountData(
            String pTotal_review_count,
            String pLast_name,
            String pFirst_name,
            String pEmail,
            String pCreated,
            String pUpdated,
            String pUser){
        total_review_count = pTotal_review_count;
        last_name = pLast_name;
        first_name = pFirst_name;
        email = pEmail;
        created = pCreated;
        updated = pUpdated;
        user = pUser;
    }

    public String getTotal_review_count() {
        return total_review_count;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getName() {
        return first_name + " " + last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getUser() {
        return user;
    }


}

