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

public class FirstaidFragment extends Fragment {

    private FirstaidViewModel firdAidViewModel;
    Button seitenlage, herzdruck, behandlung, psybetreuung;



    public static FirstaidFragment newInstance() {
        return new FirstaidFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        firdAidViewModel =
                new ViewModelProvider(this).get(FirstaidViewModel.class);
        View root = inflater.inflate(R.layout.fragment_masterdata, container, false);

        seitenlage = (Button) root.findViewById(R.id.button_Seitenlage);
        herzdruck = (Button) root.findViewById(R.id.button_Herzdruck);
        behandlung = (Button) root.findViewById(R.id.button_Behandlung);
        psybetreuung = (Button) root.findViewById(R.id.button_PsyBetreuung);

        seitenlage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Toast.makeText(getActivity(), "Fehlt noch ", Toast.LENGTH_LONG).show();
                        }
                }
        );

        herzdruck.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );

        behandlung.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );

        psybetreuung.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FirstaidViewModel.class);
        // TODO: Use the ViewModel
    }

}