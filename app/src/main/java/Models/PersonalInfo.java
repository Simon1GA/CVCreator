package Models;

public class PersonalInfo {
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String age;
    private String status;
    private String hobbies;

    public PersonalInfo(){ }
    public PersonalInfo(String name, String email, String phone, String gender, String age, String status, String hobbies){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.status = status;
        this.hobbies = hobbies;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getAge(){
        return age;
    }

    public void setAge(String age){
        this.age = age;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getHobbies(){
        return hobbies;
    }

    public void setHobbies(){
        this.hobbies = hobbies;
    }


}
