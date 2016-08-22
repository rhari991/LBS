package edu.vesit.lbs;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService extends IntentService
{
    String account_no;
    public RegistrationIntentService()
    {
        super("Aa");
    }

    @Override
    public void onHandleIntent(Intent intent)
    {
        try
        {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            SharedPreferences storedCredentials = getSharedPreferences("STORED_CREDENTIALS", Context.MODE_PRIVATE);
            account_no = storedCredentials.getString("accountNo", "");
            new StoreGCMIdTask().execute(account_no, token);
        }
        catch(IOException e)
        {
            Log.e("Error in token : ", e.getMessage());
        }
    }
}
