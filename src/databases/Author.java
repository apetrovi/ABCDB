package databases;


public class Author {
    private String auID;
    private String fName;
    private String lName;
    private String phone;

    public Author() {
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuID() {
        return auID;
    }

    public void setAuID(String auID) {
        this.auID = auID;
    }
    
    public String toString(){
        return auID + " " + fName + " " + lName;
    }
    
}
