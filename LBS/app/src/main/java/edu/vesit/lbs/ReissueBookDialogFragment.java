package edu.vesit.lbs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ReissueBookDialogFragment extends DialogFragment
{
    public static ReissueBookDialogFragment newInstance(String oldReturnDate, String newReturnDate, String record_id)
    {
        ReissueBookDialogFragment f = new ReissueBookDialogFragment();
        Bundle args = new Bundle();
        args.putString("oldReturnDate", oldReturnDate);
        args.putString("newReturnDate", newReturnDate);
        args.putString("record_id", record_id);
        f.setArguments(args);
        return f;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String oldReturnDate = getArguments().getString("oldReturnDate");
        String newReturnDate = getArguments().getString("newReturnDate");
        final String record_id = getArguments().getString("record_id");
        builder.setTitle("Reissue Book");
        builder.setMessage("Are you sure you want to reissue the book ? \n\n" + "Old return date : " + oldReturnDate + "\nNew return date : " + newReturnDate);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                new ReissueBookTask(getActivity()).execute(record_id);
                dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dismiss();
            }
        });
        return builder.create();
    }

}
