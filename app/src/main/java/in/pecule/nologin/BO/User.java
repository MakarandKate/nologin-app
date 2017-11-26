package in.pecule.nologin.BO;

/**
 * Created by makarand on 11/24/17.
 */

public class User {
    private String name;
    private String phone;
    private int age;
    private String email;


    private String url;

    public User(String name,String phone,String email,int age){
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.age=age;
    }

    public String toJson(){
        return "{"
                +"\"name\":\""+this.name+"\","
                +"\"phone\":\""+this.phone+"\","
                +"\"email\":\""+this.email+"\","
                +"\"url\":\""+this.url+"\","
                +"\"age\":"+this.age+""
                +"}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
