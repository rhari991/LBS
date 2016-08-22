package edu.vesit.lbs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

/**
 * Created by Admin on 2/29/2016.
 */
public class IssuedBookFragment extends Fragment
{
    int bookNo;
    TextView issued_book_details;
    LinearLayout rateButton, reissueButton;
    String account_no, book_id = "1", old_return_date, new_return_date, record_id;
    Fragment currentFragment;

    public IssuedBookFragment newInstance(int bookNo)
    {
        IssuedBookFragment newFragment = new IssuedBookFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("bookNo", bookNo);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.issued_book_tab, container, false);
        bookNo = getArguments().getInt("bookNo");
        SharedPreferences storedCredentials = getActivity().getSharedPreferences("STORED_CREDENTIALS", Context.MODE_PRIVATE);
        account_no = storedCredentials.getString("accountNo", "");
        issued_book_details = (TextView) view.findViewById(R.id.issued_book_details);
        rateButton = (LinearLayout) view.findViewById(R.id.rateButton);
        reissueButton = (LinearLayout) view.findViewById(R.id.reissueButton);
        try
        {
            String serverResponse = new GetIssuedBookDetailsTask(getActivity(), issued_book_details, bookNo, rateButton, reissueButton).execute(account_no).get();
            final JSONObject jsonResponse = new JSONObject(serverResponse);
            int no_of_books_issued = Integer.parseInt(jsonResponse.getString("no_of_books_issued"));
            if((no_of_books_issued == 1) && (bookNo == 1))
            {
                book_id = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("book_id");
                old_return_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("due_date");
                new_return_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("new_return_date");
                record_id = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("record_id");
            }
            else if((no_of_books_issued == 2) && (bookNo == 1))
            {
                book_id = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("book_id");
                old_return_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("due_date");
                new_return_date = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("new_return_date");
                record_id = jsonResponse.getJSONArray("book_1_details").getJSONObject(0).getString("record_id");
            }
            else if((no_of_books_issued == 2) && (bookNo == 2))
            {
                book_id = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("book_id");
                old_return_date = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("due_date");
                new_return_date = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("new_return_date");
                record_id = jsonResponse.getJSONArray("book_2_details").getJSONObject(0).getString("record_id");
            }
        }
        catch(Exception e)
        {
            Log.e("Exception : ", e.getMessage());
        }
        rateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), BookDetailsActivity.class);
                i.putExtra("book_id", book_id);
                startActivity(i);
            }
        });
        currentFragment = this;
        reissueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator.forSupportFragment(currentFragment).initiateScan();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String receivedCode = scanResult.getContents();
        if(receivedCode.equals(book_id))
        {
            ReissueBookDialogFragment newFragment = ReissueBookDialogFragment.newInstance(old_return_date, new_return_date, record_id);
            newFragment.show(getActivity().getSupportFragmentManager(), "reissue");
        }
        else
        {
            Toast.makeText(getActivity(), "Incorrect book", Toast.LENGTH_SHORT).show();
        }
    }
}
