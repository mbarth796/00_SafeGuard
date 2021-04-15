package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Button emergencyCall, firstAid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        emergencyCall = root.findViewById(R.id.button_emergencyCall);
        firstAid = root.findViewById(R.id.button_firstAid);

        emergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fragment fragment =  Fragment.instantiate(getActivity(), GameFragment.class.getName(), null);
                //fragtrans = getActivity().getSupportFragmentManager().beginTransaction();
                //fragtrans.replace(R.id.container, fragment, null);
                /*
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EmergencyFragment efragment = new EmergencyFragment();
                fragmentTransaction.replace(R.id.container, efragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_emergency);

            }
        });
        firstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_firstaid);
            }
        });
        return root;
    }
}