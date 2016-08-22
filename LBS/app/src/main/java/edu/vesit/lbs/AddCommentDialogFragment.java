package edu.vesit.lbs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCommentDialogFragment extends DialogFragment
{
    Button addCommentButton, cancelCommentButton;
    EditText commentBox;
    String account_no, book_id;

    public static AddCommentDialogFragment newInstance(String book_id)
    {
        AddCommentDialogFragment f = new AddCommentDialogFragment();
        Bundle args = new Bundle();
        args.putString("book_id", book_id);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.add_comment_dialog, null);
        builder.setView(rootView);
        SharedPreferences storedCredentials = getActivity().getSharedPreferences("STORED_CREDENTIALS", Context.MODE_PRIVATE);
        account_no = storedCredentials.getString("accountNo", "");
        book_id = getArguments().getString("book_id");
        addCommentButton = (Button) rootView.findViewById(R.id.commentButton);
        cancelCommentButton = (Button) rootView.findViewById(R.id.cancelCommentButton);
        commentBox = (EditText) rootView.findViewById(R.id.commentBox);
        cancelCommentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        addCommentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String enteredComment = commentBox.getText().toString();
                new AddCommentTask(getActivity()).execute(account_no, book_id, enteredComment);
                dismiss();
            }
        });
        return builder.create();
    }
}
