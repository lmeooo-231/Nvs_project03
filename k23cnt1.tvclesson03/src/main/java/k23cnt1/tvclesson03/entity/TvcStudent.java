package k23cnt1.tvclesson03.entity;
//id, name, age, gender, address, phone, email
public class TvcStudent {
    long tvcId;
    String tvcName;
    int tvcAge;
    boolean tvcGender;
    String tvcAddress;
    String tvcPhone;
    String tvcEmail;

    public TvcStudent() {
    }

    public TvcStudent(long tvcId, String tvcName, int tvcAge, boolean tvcGender, String tvcAddress, String tvcPhone, String tvcEmail) {
        this.tvcId = tvcId;
        this.tvcName = tvcName;
        this.tvcAge = tvcAge;
        this.tvcGender = tvcGender;
        this.tvcAddress = tvcAddress;
        this.tvcPhone = tvcPhone;
        this.tvcEmail = tvcEmail;
    }

    public long getTvcId() {
        return tvcId;
    }

    public void setTvcId(long tvcId) {
        this.tvcId = tvcId;
    }

    public String getTvcName() {
        return tvcName;
    }

    public void setTvcName(String tvcName) {
        this.tvcName = tvcName;
    }

    public int getTvcAge() {
        return tvcAge;
    }

    public void setTvcAge(int tvcAge) {
        this.tvcAge = tvcAge;
    }

    public boolean isTvcGender() {
        return tvcGender;
    }

    public void setTvcGender(boolean tvcGender) {
        this.tvcGender = tvcGender;
    }

    public String getTvcAddress() {
        return tvcAddress;
    }

    public void setTvcAddress(String tvcAddress) {
        this.tvcAddress = tvcAddress;
    }

    public String getTvcPhone() {
        return tvcPhone;
    }

    public void setTvcPhone(String tvcPhone) {
        this.tvcPhone = tvcPhone;
    }

    public String getTvcEmail() {
        return tvcEmail;
    }

    public void setTvcEmail(String tvcEmail) {
        this.tvcEmail = tvcEmail;
    }
}
