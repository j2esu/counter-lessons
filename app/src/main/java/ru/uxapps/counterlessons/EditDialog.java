package ru.uxapps.counterlessons;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditDialog extends AppCompatDialogFragment {

    public static EditDialog create(long counterId) {
        Bundle args = new Bundle();
        args.putLong(ARG_ID, counterId);
        EditDialog dialog = new EditDialog();
        dialog.setArguments(args);
        return dialog;
    }

    private static final String ARG_ID = "ARG_ID";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.d_edit, null, false);
        EditText nameEt = view.findViewById(R.id.d_edit_name);
        long counterId = getArguments().getLong(ARG_ID);
        Repo repo = Repo.getInstance(getContext());
        Counter counter = repo.getCounter(counterId);
        nameEt.setText(counter.name);

        ColorPicker colorPicker = new ScrollColorPicker(view, ColorPicker.COLORS);
        colorPicker.setColor(counter.color);

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .setTitle("Edit counter")
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = nameEt.getText().toString();
                    repo.changeName(counterId, name);
                    int color = colorPicker.getColor();
                    repo.changeColor(counterId, color);
                })
                .setView(view)
                .create();
        InputFilters.nameFilter(nameEt, alertDialog);
        return alertDialog;
    }
}
