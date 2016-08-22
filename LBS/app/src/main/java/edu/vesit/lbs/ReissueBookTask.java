package edu.vesit.lbs;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

class ReissueBookTask extends AsyncTask<String, Void, String>
{
    Context mContext;
    ProgressDialog pDialog;

    public ReissueBookTask(Context context)
    {
        mContext = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Reissuing Book, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String response = null;
        try {
            URL url = new URL("http://rhari.netne.net/reissue_book.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            String urlParameters = "record_id=" + params[0];
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
            {
                Log.e("Error : ", "Input Stream is null");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0)
            {
                Log.e("Error : ", "Buffer is null");
                return null;
            }
            response = buffer.toString();
            Log.e("response : ", response);
            return response;
        }
        catch (IOException e)
        {
            Log.e("Error : ", e.getMessage());
            return null;
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (final IOException e)
                {
                    Log.e("Error closing stream : ",e.getMessage());
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String response)
    {
        ((Activity)mContext).recreate();
        try
        {
            JSONObject jsonResponse = new JSONObject(response);
            final String message = jsonResponse.getString("message");
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
        catch(JSONException e)
        {
            Log.e("JSON parsing error : ", e.getMessage());
        }
        pDialog.dismiss();
    }
}