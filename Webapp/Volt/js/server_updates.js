/**
 * Created by siddh on 9/3/2017.
 */

var alive = true;

Parse.serverURL = 'https://parseapi.back4app.com';
Parse.initialize("tHngQYBJmifYY141pqCbB2UePtRUlAHaazDVyIkd", "M3HBMDFEjhF7Ck9imZiDlUwbV2QpdzzXVvqqJF5P");

var pubnub = new PubNub({
    subscribeKey: "sub-c-79a67ba4-4d07-11e7-a368-0619f8945a4f",
    publishKey: "pub-c-89e2ce34-7839-47ba-b9b7-3dbc30374038",
    ssl: true
});

function pubnub_update() {
    pubnub.publish(
        {
            message: {
                led: 1
            },
            channel: 'disco',
            sendByPost: false, // true to send via post
            storeInHistory: false, //override default storage options
            meta: {
                "cool": "meta"
            }   // publish extra meta with the request
        },
        function (status, response) {
            if (status.error) {
                // handle error
                console.log(status)
            } else {
                console.log("message Published w/ timetoken", response.timetoken)
            }
        }
    );
}

function parse_update(is_alive) {

    var Appliances = Parse.Object.extend("Appliances");
    var query = new Parse.Query(Appliances);

    if (is_alive) {
        query.find({
            success: function (results) {
                //alert("Successfully retrieved " + results.length + " scores.");
                // Do something with the returned Parse.Object values
                for (var i = 0; i < results.length; i++) {
                    var object = results[i];
                    //alert(object.id + ' - ' + object.get('Name'));
                    object.set('Allowed', false).save();
                    object.set('Status', false).save();
                }
            },
            error: function (error) {
                alert("Error: " + error.code + " " + error.message);
            }

        });
    }else {
        query.find({
            success: function (results) {
                //alert("Successfully retrieved " + results.length + " scores.");
                // Do something with the returned Parse.Object values
                for (var i = 0; i < results.length; i++) {
                    var object = results[i];
                    //alert(object.id + ' - ' + object.get('Name'));
                    object.set('Allowed', true).save();
                }
            },
            error: function (error) {
                alert("Error: " + error.code + " " + error.message);
            }

        });
    }
}

var btn = document.getElementById("take_it_down");
btn.onclick = function(){

    if (alive) {
       pubnub_update();
        btn.innerHTML = "Take Up";
        btn.style.background = "green";
        btn.parentElement.nextElementSibling.innerHTML = "InActive";
        btn.parentElement.nextElementSibling.style.color = "red";
    } else {
        btn.innerHTML = "Take down";
        btn.style.background = "#ff0000";
        btn.parentElement.nextElementSibling.innerHTML = "Active";
        btn.parentElement.nextElementSibling.style.color = "green";
    }

   parse_update(alive);

    alive = !alive;
};


