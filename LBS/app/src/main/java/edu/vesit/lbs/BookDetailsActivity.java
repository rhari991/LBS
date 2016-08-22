package edu.vesit.lbs;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity
{
    String book_id, account_no;
    RatingBar bookRating;
    ListView commentsList;
    FloatingActionButton addCommentButton;
    ArrayList<Comment> comments;
    CommentsAdapter commentsAdapter;
    TextView bookDetails;
    private Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bookdetails);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("Book Details");
        book_id = getIntent().getStringExtra("book_id");
        bookRating = (RatingBar) findViewById(R.id.bookRating);
        commentsList = (ListView) findViewById(R.id.commentsList);
        addCommentButton = (FloatingActionButton) findViewById(R.id.addCommentButton);
        bookDetails = (TextView) findViewById(R.id.bookDetails);
        comments = new ArrayList<Comment>();
        commentsAdapter = new CommentsAdapter(BookDetailsActivity.this, comments);
        commentsList.setAdapter(commentsAdapter);
        new LoadBookDetailsTask(BookDetailsActivity.this, commentsList, commentsAdapter, comments, bookDetails).execute(book_id);
        Drawable drawable = bookRating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FF8C00"), PorterDuff.Mode.SRC_ATOP);
        addCommentButton.setImageResource(R.drawable.add_comment_icon);
        bookRating.setRating(2.5f);
        addCommentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment addCommentDialog = AddCommentDialogFragment.newInstance(book_id);
                addCommentDialog.show(getSupportFragmentManager(), "add");
            }
        });
    }
}
