package ca.ulaval.ima.mp.data;

public class AccountLoginData {
    private String client_id = "STO4WED2NTDDxjLs8ODios5M15HwsrRlydsMa1t0";
    private String client_secret = "YOVWGpjSnHd5AYDxGBR2CIB09ZYM1OPJGnH3ijkKwrUMVvwLprUmLf6fxku06ClUKTAEl5AeZN36V9QYBYvTtrLMrtUtXVuXOGWleQGYyApC2a469l36TdlXFqAG1tpK";
    private String email;
    private String password;

    public AccountLoginData(
            String pEmail,
            String pPwassword){

        email = pEmail;
        password = pPwassword;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
