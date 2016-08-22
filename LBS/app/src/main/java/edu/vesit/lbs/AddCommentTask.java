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

public class AddCommentTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pDialog;
    Context mContext;

    public AddCommentTask(Context context)
    {
        mContext = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Adding Comment...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String response = null;
        try
        {
            URL url = new URL("http://rhari.netne.net/add_comment.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            String urlParameters = "account_no=" + params[0] + "&book_id=" + params[1] + "&comment_body=" + params[2];
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
        Log.e("JSON response : ", response);
        return response;
    }

    @Override
    protected void onPostExecute(String response)
    {
        try
        {
            ((Activity) mContext).recreate();
            JSONObject jsonResponse = new JSONObject(response);
            String status = jsonResponse.getString("success");
            if (status.equals("0"))
                Toast.makeText(mContext, "Connection error", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, "Comment added successfully", Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
        }
        catch(JSONException e)
        {
            Log.e("JSON parsing error : ", e.getMessage());
        }
    }
}