package com.allandroidprojects.ecomsample.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.allandroidprojects.ecomsample.MainActivity1;

/**
 * Created by eduxh on 30/06/2017.
 */

@SuppressLint("ValidFragment")
public class MyDialogFragment3 extends DialogFragment {
    private String Title;
    private String Message;
    Activity activity;

    @SuppressLint("ValidFragment")
    public MyDialogFragment3(String title, String message, Activity p_act)
    {
        this.Title = title;
        this.Message = message;
        this.activity = p_act;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Aceptar",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}