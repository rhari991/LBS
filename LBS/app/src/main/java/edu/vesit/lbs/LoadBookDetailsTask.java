package edu.vesit.lbs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 3/12/2016.
 */
public class LoadBookDetailsTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pDialog;
    Context mContext;
    ListView commentsList;
    CommentsAdapter commentsAdapter;
    TextView bookDetails;
    ArrayList<Comment> commentData;

    public LoadBookDetailsTask(Context context, ListView commentsList, CommentsAdapter commentsAdapter, ArrayList<Comment> commentData, TextView bookDetails)
    {
        mContext = context;
        this.commentsList = commentsList;
        this.commentsAdapter = commentsAdapter;
        this.bookDetails = bookDetails;
        this.commentData = commentData;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
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
            URL url = new URL("http://rhari.netne.net/get_book_details.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            String urlParameters = "book_id=" + params[0];
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
        pDialog.dismiss();
        try
        {
            JSONObject jsonResponse = new JSONObject(response);
            String title = jsonResponse.getString("title");
            String author = jsonResponse.getString("author");
            String edition = jsonResponse.getString("edition");
            String averageRating = jsonResponse.getString("average_rating");
            bookDetails.setText("Title : " + title + "\nAuthor : " + author + "\nedition : " +
                    edition + "\nAverage Rating : " + averageRating);
            commentData.clear();
            for(int i = 0;i < jsonResponse.getJSONArray("comments").length(); i++)
            {
                String commenterName = jsonResponse.getJSONArray("comments").getJSONObject(i).getString("commenter_name");
                String commentBody = jsonResponse.getJSONArray("comments").getJSONObject(i).getString("comment_body");
                String postedAt = jsonResponse.getJSONArray("comments").getJSONObject(i).getString("posted_at");
                Comment comment = new Comment(commenterName, commentBody, postedAt);
                commentData.add(comment);
            }
            commentsAdapter.notifyWithNewDataSet(commentData);
        }
        catch (JSONException e)
        {
            Log.e("JSON parsing error : ", e.getMessage());
        }
    }
}
