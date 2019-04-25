package ru.uxapps.counterlessons;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class CreateDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.d_create, null, false);
        EditText nameEt = view.findViewById(R.id.d_create_name);
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .setTitle("Create counter")
                .setPositiveButton("Create", (dialog, which) -> {
                    String name = nameEt.getText().toString();
                    Repo.getInstance(getContext()).addCounter(name);
                })
                .setView(view)
                .create();
        InputFilters.nameFilter(nameEt, alertDialog);
        return alertDialog;
    }
}
