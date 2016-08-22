package edu.vesit.lbs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

class GetIssuedBookDetailsTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pDialog;
    Context mContext;
    TextView issued_book_details;
    LinearLayout rateButton, reissueButton;
    int bookNo;

    public GetIssuedBookDetailsTask(Context context, TextView issued_book_details, int bookNo, LinearLayout rateButton, LinearLayout reissueButton)
    {
        mContext = context;
        this.issued_book_details = issued_book_details;
        this.bookNo = bookNo;
        this.rateButton = rateButton;
        this.reissueButton = reissueButton;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading book details, please wait...");
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
        try
        {
            URL url = new URL("http://rhari.netne.net/get_issued_book_details.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            String urlParameters = "account_no=" + params[0];
            urlConnection.setDoOutput(true);
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
        return response;
    }

    @Override
    protected void onPostExecute(String response)
    {
        try
        {
            final JSONObject jsonResponse = new JSONObject(response);
            int no_of_books_issued = Integer.parseInt(jsonResponse.getString("no_of_books_issued"));
            if (no_of_books_issued == 0)
            {
                issued_book_details.setText("Not issued");
                rateButton.setVisibility(View.GONE);
                reissueButton.setVisibility(View.GONE);
            }
            else if (no_of_books_issued == 1)
            {
                if(bookNo == 1)
                {
                    String title = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("title");
                    String author = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("author");
                    String issue_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("issue_date");
                    String due_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("due_date");
                    String fine_amount = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("fine_amount");
                    issued_book_details.setText("Title : " + title + "\nAuthor : " + author + "\nIssued on : " + issue_date +
                            "\nReturn date : " + due_date + "\nFine : Rs " + fine_amount);
                }
                else if(bookNo == 2)
                {
                    issued_book_details.setText("Not issued");
                    rateButton.setVisibility(View.GONE);
                    reissueButton.setVisibility(View.GONE);
                }
            }
            else if (no_of_books_issued == 2)
            {
                if(bookNo == 1)
                {
                    String title = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("title");
                    String author = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("author");
                    String issue_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("issue_date");
                    String due_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("due_date");
                    String fine_amount = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("fine_amount");
                    issued_book_details.setText("Title : " + title + "\nAuthor : " + author + "\nIssued on : " + issue_date +
                            "\nReturn date : " + due_date + "\nFine : Rs " + fine_amount);
                }
                else if(bookNo == 2)
                {
                    String title = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("title");
                    String author = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("author");
                    String issue_date = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("issue_date");
                    String due_date = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("due_date");
                    String fine_amount = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("fine_amount");
                    issued_book_details.setText("Title : " + title + "\nAuthor : " + author + "\nIssued on : " + issue_date +
                            "\nReturn date : " + due_date + "\nFine : Rs " + fine_amount);
                }
            }
            pDialog.dismiss();
        }
        catch(JSONException e)
        {
            Log.e("JSON parsing error : ", e.getMessage());
        }
    }
}