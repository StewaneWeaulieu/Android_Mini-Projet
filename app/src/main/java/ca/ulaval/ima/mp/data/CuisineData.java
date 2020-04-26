package ca.ulaval.ima.mp.data;

public class CuisineData {
    private String id;
    private String name;


    public CuisineData(
            String pId,
            String pName) {
        id = pId;
        name = pName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}



