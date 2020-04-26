package ca.ulaval.ima.mp.data;

public class AccountCreateData extends AccountLoginData{
    private String first_name;
    private String last_name;


    public AccountCreateData(
                       String pEmail,
                       String pPwassword,
                       String pFirst_name,
                       String pLast_name){
        super(pEmail,pPwassword);
        first_name = pFirst_name;
        last_name = pLast_name;

    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

}
