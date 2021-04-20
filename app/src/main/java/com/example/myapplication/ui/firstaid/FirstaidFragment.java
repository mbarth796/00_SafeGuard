package com.example.myapplication.ui.firstaid;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class FirstaidFragment extends Fragment {

    private FirstaidViewModel firstaidViewModel;

    public static FirstaidFragment newInstance() {
        return new FirstaidFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        firstaidViewModel =
                new ViewModelProvider(this).get(FirstaidViewModel.class);
        View root = inflater.inflate(R.layout.fragment_firstaid, container, false);

        Button latPos = (Button) root.findViewById(R.id.button_latPos);
        Button heartPress = (Button) root.findViewById(R.id.button_heartPress);
        Button woundCare = (Button) root.findViewById(R.id.button_woundCare);
        Button psyCare = (Button) root.findViewById(R.id.button_psyCare);

        latPos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(requireView(), "Future content - Coming soon!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        }
                }
        );

        heartPress.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(requireView(), "Future content - Coming soon!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );

        woundCare.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(requireView(), "Future content - Coming soon!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );

        psyCare.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(requireView(), "Future content - Coming soon!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );
        return root;
    }
}