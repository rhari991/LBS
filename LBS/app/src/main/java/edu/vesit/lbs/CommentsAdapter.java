package edu.vesit.lbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentsAdapter extends BaseAdapter
{
    private View view;
    public static ViewHolder holder;
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Comment> data;

    public CommentsAdapter(Context context, ArrayList<Comment> data)
    {
        mContext = context;
        this.data = data;
    }

    public static class ViewHolder
    {
        TextView posterName, postBody, postedTime;
    }

    public void notifyWithNewDataSet(ArrayList<Comment> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Comment getItem(int position)
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
            view = inflater.inflate(R.layout.comment_item, null);
            holder.posterName = (TextView) view.findViewById(R.id.posterName);
            holder.postBody = (TextView) view.findViewById(R.id.postBody);
            holder.postedTime = (TextView) view.findViewById(R.id.postedTime);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Comment currentRowData = data.get(position);
        holder.posterName.setText(currentRowData.getPosterName());
        holder.postBody.setText(currentRowData.getPostBody());
        holder.postedTime.setText(currentRowData.getPostedTime());
        return view;
    }
}
