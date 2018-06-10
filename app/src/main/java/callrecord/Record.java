package callrecord;

public class Record {
    private String displayName;
    private String phone;
    private String date;
    private String type;
    private String durationTime;
    private String location;

    public Record(){
        this.displayName = null;
        this.phone = null;
        this.type = null;
        this.date = null;
        this.durationTime = null;
        this.location = null;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDurationTime() {
        return durationTime;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getDate() {
        return date;
    }
    public String getType() {
        return type;
    }
    public String getLocation() {
        return location;
    }
    public String getPhone() {
        return phone;
    }
}
