package edu.vesit.lbs;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter
{
    private View view;
    public static ViewHolder holder;
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Book> data;

    public SearchAdapter(Context context, ArrayList<Book> data)
    {
        mContext = context;
        this.data = data;
    }

    public static class ViewHolder
    {
        TextView bookTitle, authorName;
        ImageView issuedImage;
    }

    public void notifyWithNewDataSet(ArrayList<Book> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Book getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        if (view == null)
        {
            this.view = view;
            holder = new ViewHolder();
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_result, null);
            holder.bookTitle = (TextView) view.findViewById(R.id.resultTitle);
            holder.authorName = (TextView) view.findViewById(R.id.authorName);
            holder.issuedImage = (ImageView) view.findViewById(R.id.issuedImage);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Book currentRowData = data.get(position);
        holder.bookTitle.setText(currentRowData.getTitle());
        holder.authorName.setText(currentRowData.getAuthor());
        if(currentRowData.getIsIssued() == true)
            holder.issuedImage.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.not_available));
        else
            holder.issuedImage.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.available_icon));
        return view;
    }
}
