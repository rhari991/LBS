package edu.vesit.lbs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class SearchBooksTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pDialog;
    Context mContext;
    String bookData;

    public SearchBooksTask(Context context)
    {
        mContext = context;
        bookData = "";
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Searching...");
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
            URL url = new URL("http://rhari.netne.net/search_books.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            String urlParameters = "search_string=" + params[0];
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
        try
        {
            JSONObject jsonResponse = new JSONObject(response);
            for(int i = 0;i < jsonResponse.getJSONArray("books").length(); i++)
            {
                String title = jsonResponse.getJSONArray("books").getJSONObject(i).getString("title");
                String author = jsonResponse.getJSONArray("books").getJSONObject(i).getString("author");
                String book_id = jsonResponse.getJSONArray("books").getJSONObject(i).getString("book_id");
                String is_issued = jsonResponse.getJSONArray("books").getJSONObject(i).getString("is_issued");
                bookData += (title + "<<>>" + author + "<<>>" + book_id + "<<>>" + is_issued);
                if(i != jsonResponse.getJSONArray("books").length() - 1)
                    bookData += "||";
            }
        }
        catch (JSONException e)
        {
            Log.e("JSON parsing error : ", e.getMessage());
        }
        return bookData;
    }

    @Override
    protected void onPostExecute(String response)
    {
        pDialog.dismiss();
    }
}