package edu.vesit.lbs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class VerifyLoginTask extends AsyncTask<String, Void, String>
{
    private ProgressDialog pDialog;
    Context mContext;
    public VerifyLoginTask(Context context)
    {
        mContext = context;
    }
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Verifing credentials, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        if (params.length == 0)
            return null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String response = null;
        try {
            URL url = new URL("http://rhari.netne.net/verify_login.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            String urlParameters = "account_no=" + params[0] + "&password=" + params[1];
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.e("Error : ", "Input Stream is null");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line + "\n");
            if (buffer.length() == 0) {
                Log.e("Error : ", "Buffer is null");
                return null;
            }
            response = buffer.toString();
        } catch (IOException e) {
            Log.e("Error : ", e.getMessage());
            return null;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Error closing stream : ", e.getMessage());
                }
            }
        }
        if (response == null)
            Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
        else
        {
            try
            {
                JSONObject jsonResponse = new JSONObject(response);
                String status = jsonResponse.getString("success");
                if (status.equals("0"))
                    Toast.makeText(mContext, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                else if (status.equals("1"))
                {
                    Intent i = new Intent(mContext, MainScreenActivity.class);
                    SharedPreferences storedCredentials = mContext.getSharedPreferences("STORED_CREDENTIALS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = storedCredentials.edit();
                    editor.putString("loggedIn", "yes");
                    editor.putString("accountNo", params[0]);
                    editor.putString("password", params[1]);
                    editor.commit();
                    mContext.startActivity(i);
                }
                else
                    Toast.makeText(mContext, "Connection Error", Toast.LENGTH_SHORT).show();
            }
            catch (JSONException e)
            {
                Log.e("Parsing error : ", e.getMessage());
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response)
    {
        pDialog.dismiss();
    }
}
