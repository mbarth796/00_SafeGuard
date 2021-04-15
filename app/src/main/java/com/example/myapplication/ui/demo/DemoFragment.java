package com.example.myapplication.ui.demo;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.example.myapplication.R;
import com.example.myapplication.ui.masterdata.MasterdataViewModel;

public class DemoFragment extends Fragment {

    private DemoViewModel demoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        demoViewModel =
                new ViewModelProvider(this).get(DemoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_demo, container, false);

        askPermissions();

        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    askPermissions();
                } else {
                    String ID = "015206417298";
                    if (ID != null) {
                        videoCall(ID);
                    } else {
                        Toast.makeText(v.getContext(), "Number not found or can't accept video call!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return root;
    }

    // Get required permissions
    public void askPermissions() {
        // Ask permissions
        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    //Video Call Intent
    public void videoCall(String ID) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String data = "content://com.android.contacts/data/" + ID;
        String type = "vnd.android.cursor.item/vnd.com.whatsapp.video.call";
        intent.setDataAndType(Uri.parse(data), type);
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }


}