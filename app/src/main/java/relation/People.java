package relation;

public class People {
    private String name;
    private String telephone;

    public People(String name, String telephone){
        setName(name);
        setTelephone(telephone);
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getName() {
        return name;
    }
    public String getTelephone() {
        return telephone;
    }
}
