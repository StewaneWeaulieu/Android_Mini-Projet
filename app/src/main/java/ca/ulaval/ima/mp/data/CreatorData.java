package ca.ulaval.ima.mp.data;

public class CreatorData {
    private String firstName;
    private String lastName;

    public CreatorData(String pFirstName, String pLastName){
        firstName = pFirstName;
        lastName = pLastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
