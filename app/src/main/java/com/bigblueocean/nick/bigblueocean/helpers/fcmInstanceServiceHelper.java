package com.bigblueocean.nick.bigblueocean.helpers;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by nick on 11/2/17.
 */

public class fcmInstanceServiceHelper extends FirebaseInstanceIdService {

    private final String TAG = "TOKEN:";

    @Override
    public void onTokenRefresh(){
//         Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
//
//         If you want to send messages to this application instance or
//         manage this apps subscriptions on the server side, send the
//         Instance ID token to your app server.
//         sendRegistrationToServer(refreshedToken);
    }
}
