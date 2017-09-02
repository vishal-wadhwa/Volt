package isgw;

import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vishal on 9/2/17 as a part of Charge 2.0.
 */

public class Appliance implements Serializable {
    private String name;
    private boolean status, allowed;
    private double consumption;
    private Date startTime, endTime;
    private ParseObject parseObject;


    public Appliance(String name, boolean status, boolean allowed, int consumption, Date startTime, Date endTime, ParseObject parseObject) {
        this.name = name;
        this.status = status;
        this.allowed = allowed;
        this.consumption = consumption;
        this.startTime = startTime;
        this.endTime = endTime;
        this.parseObject = parseObject;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public double getConsumption() {
        return consumption;
    }

    public String getName() {
        return name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setParseObject(ParseObject parseObject) {
        this.parseObject = parseObject;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public ParseObject getParseObject() {
        return parseObject;
    }
}
