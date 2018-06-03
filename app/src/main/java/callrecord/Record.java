package callrecord;

public class Record {
    private String name;
    private String telephone;
    private boolean talked;
    private String timeStart;
    private String timeEnd;

    public Record(String name, String telephone, String start, String end, boolean talked){
        this.name = name;
        this.telephone = telephone;
        this.timeStart = start;
        this.timeEnd = end;
        this.talked = talked;
    }

    public String getTelephone() {
        return telephone;
    }
    public String getName() {
        return name;
    }
    public String getTimeEnd() {
        return timeEnd;
    }
    public String getTimeStart() {
        return timeStart;
    }
    public boolean getTalked() {
        return talked;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }
    public void setTalked(boolean talked){
        this.talked = talked;
    }
}
