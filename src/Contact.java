import java.io.Serializable;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L; // Khuyến nghị thêm serialVersionUID

    String phoneNumber;
    String group;
    String name;
    String gender;
    String address;
    String dateOfBirth;
    String email;

    public Contact() {
    }

    public Contact(String phoneNumber, String group, String name, String gender, String address, String dateOfBirth, String email) {
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{Số điện thoại: " + phoneNumber +
                ", Nhóm: " + group +
                ", Họ tên: " + name +
                ", Giới tính: " + gender +
                ", Địa chỉ: " + address +
                ", Ngày sinh: " + dateOfBirth +
                ", Email: " + email + "}";
    }
}