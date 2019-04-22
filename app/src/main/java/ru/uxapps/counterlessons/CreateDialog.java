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
        return new AlertDialog.Builder(requireContext())
                .setTitle("Create counter")
                .setPositiveButton("Create", (dialog, which) -> {
                    // TODO: 4/22/2019 filter illegal input
                    String name = ((EditText) view.findViewById(R.id.d_create_name))
                            .getText().toString();
                    Repo.getInstance(getContext()).addCounter(name);
                })
                .setView(view)
                .create();
    }
}
