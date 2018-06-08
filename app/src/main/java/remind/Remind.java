package remind;

import android.widget.TextView;

public class Remind {
    private String remindTime;
    private String remindEvent;
    private String finished;

    public Remind(String remindTime, String remindEvent, String finished){
        this.remindTime = remindTime;
        this.remindEvent = remindEvent;
        this.finished = finished;
    }
    public Remind(){
        
    };

    public void setRemindEvent(String remindEvent) {
        this.remindEvent = remindEvent;
    }
    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getRemindEvent() {
        return remindEvent;
    }
    public String getRemindTime() {
        return remindTime;
    }
    public String getFinished(){
        return finished;
    }
}
