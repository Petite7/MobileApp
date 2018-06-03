package addpeople;

public class Information {
    private String item;
    private String information;

    public Information(String item, String information){
        this.item = item;
        this.information = information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public String getInformation() {
        return information;
    }
    public String getItem() {
        return item;
    }
}
