package ru.uxapps.counterlessons;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class ConfirmDeleteDialog extends AppCompatDialogFragment {

    public interface Host {

        void onConfirm();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Delete counter?")
                .setMessage("This action can't be undone")
                .setPositiveButton("Yes", (dialog, which) ->
                        ((Host) requireActivity()).onConfirm())
                .setNegativeButton("Cancel", null)
                .create();
    }
}
