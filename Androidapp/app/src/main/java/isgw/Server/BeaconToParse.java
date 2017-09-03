package isgw.Server;

import com.parse.ParseUser;

/**
 * Created by dhruv on 21/1/17.
 */

public class BeaconToParse {

    int appliance_id;
    int deviceStatus;
    String timeStatus;
    String appliance;
    String date;

    // send the appliance id according to below

    public BeaconToParse(int appliance_id, int deviceStatus, String timeStatus,String date) {
        this.appliance_id = appliance_id;
        this.timeStatus = timeStatus;
        this.deviceStatus = deviceStatus;
        this.date = date;
    }

    public void sendToParse() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        switch (appliance_id) {
            case 1:
                appliance = "Airconditioner";
                break;
            case 2:
                appliance = "SmartMeter";
                break;
            case 3:
                appliance = "Fridge";
                break;
            case 4:
                appliance = "Lighting";
                break;
            case 5:
                appliance = "WashingMachine";
                break;
            case 6:
                appliance = "TV";
                break;
            case 7:
                appliance = "Heater";
                break;
        }

        currentUser.put(appliance, deviceStatus);
        currentUser.put("deviceStatus", deviceStatus);
        currentUser.put("timeStatus", timeStatus);
        currentUser.put("date",date);
        currentUser.saveInBackground();
    }
}