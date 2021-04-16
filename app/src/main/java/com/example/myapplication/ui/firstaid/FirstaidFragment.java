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
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.masterdata.MasterdataViewModel;
import com.google.android.material.snackbar.Snackbar;

public class FirstaidFragment extends Fragment {

    private FirstaidViewModel firdAidViewModel;

    public static FirstaidFragment newInstance() {
        return new FirstaidFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        firdAidViewModel =
                new ViewModelProvider(this).get(FirstaidViewModel.class);
        View root = inflater.inflate(R.layout.fragment_firstaid, container, false);

        Button seitenlage = (Button) root.findViewById(R.id.button_Seitenlage);
        Button herzdruck = (Button) root.findViewById(R.id.button_Herzdruck);
        Button behandlung = (Button) root.findViewById(R.id.button_Behandlung);
        Button psybetreuung = (Button) root.findViewById(R.id.button_PsyBetreuung);

        seitenlage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(requireView(), "Future content - Coming soon!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        }
                }
        );

        herzdruck.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(requireView(), "Future content - Coming soon!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );

        behandlung.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(requireView(), "Future content - Coming soon!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );

        psybetreuung.setOnClickListener(
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