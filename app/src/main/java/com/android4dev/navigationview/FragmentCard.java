package com.android4dev.navigationview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

public class FragmentCard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the View
        View v = inflater.inflate(R.layout.fragment_card, container, false);


        // Setup Snackbar on Button click
        Button buttonCard = (Button) v.findViewById(R.id.button_card);
        buttonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Snackbar di prova", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        return v;
    }
}