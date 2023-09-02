package com.zybooks.diceroller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;



public class RollLengthDialogFragment extends DialogFragment {

    // interface schema
    public interface OnRollLengthSelectedListener {
        void onRollLengthClick(int which);
    }

    // interface object
    private OnRollLengthSelectedListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.pick_roll_length);
        builder.setItems(R.array.length_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // call the method from MainActivity through interface object
                mListener.onRollLengthClick(which);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnRollLengthSelectedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}