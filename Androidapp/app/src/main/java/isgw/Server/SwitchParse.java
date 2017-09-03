package isgw.Server;

import com.parse.ParseUser;

/**
 * Created by dhruv on 22/1/17.
 */

public class SwitchParse {
    int appliance_id;
    String onOff;
    String appliance;

    public SwitchParse(int appliance_id, String onOff) {
        this.appliance_id = appliance_id;
        this.onOff = onOff;
    }

    public void sendStatus()
    {
        final ParseUser currentUser = ParseUser.getCurrentUser();
        switch (appliance_id)
        {
            case 1:
                appliance = "BtnAirconditioner";
                break;
            case 2:
                appliance = "BtnFridge";
                break;
            case 3:
                appliance = "BtnLighting";
                break;
            case 4:
                appliance = "BtnWashingMachine";
                break;
            case 5:
                appliance = "BtnTV";
                break;
            case 6:
                appliance = "BtnHeater";
                break;
        }
        currentUser.put(appliance,onOff);
        currentUser.saveInBackground();
    }

}
