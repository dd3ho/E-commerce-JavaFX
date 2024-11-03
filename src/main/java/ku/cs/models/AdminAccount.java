package ku.cs.models;

public class AdminAccount extends Account {


    public AdminAccount(String username, String password, String status, String lastLogin, int countLogin) {
        super(username, password,status,lastLogin,countLogin);
        setType("Admin");
        setCountLogin(0);
    }

    @Override
    public String toCsv() {
        return getType()+","+getUsername()+","+getPassword()+","+getStatus()+","+getLastLogin()+","+getCountLogin();
    }


}
