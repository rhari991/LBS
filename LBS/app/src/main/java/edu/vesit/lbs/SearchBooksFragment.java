package edu.vesit.lbs;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchBooksFragment extends Fragment
{
    private SearchAdapter resultsAdapter;
    ListView searchResults;
    String bookData;
    ArrayList<Book> data;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_searchbooks, container, false);
        searchResults = (ListView)view.findViewById(R.id.searchResults);
        context = getActivity();
        data = new ArrayList<Book>();
        resultsAdapter = new SearchAdapter(context, data);
        searchResults.setAdapter(resultsAdapter);
        Intent intent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            try
            {
                String query = intent.getStringExtra(SearchManager.QUERY).trim();
                bookData = new SearchBooksTask(context).execute(query).get();
                Log.e("Converted response : ", bookData);
                data.clear();
                String[] books = bookData.split("||");
                for(int i = 0;i < books.length; i++)
                {
                    String[] bookDetails = books[i].split("<<>>");
                    if(bookDetails.length == 4)
                    {
                        boolean isIssued = (bookDetails[2].equals("true")) ? true : false;
                        Book b = new Book(bookDetails[0], bookDetails[1], isIssued, bookDetails[3]);
                        data.add(b);
                    }
                }
                Log.e("data array : ", data.toString());
                resultsAdapter.notifyWithNewDataSet(data);
            }
            catch(Exception e)
            {
                Log.e("Error in search : ", e.getMessage());
            }
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.search_activity_menu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.search_books_button).getActionView();
        searchView.setMaxWidth(1000000);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
    }
}