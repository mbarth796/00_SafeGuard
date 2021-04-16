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
                    public void onClick(View v) {
                        //Check ob Permissions da sind
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            askPermissions();
                        } else {
                            // Maxis Nummer
                            String eText = "+491749823050";
                            Long _ID = getContactIdUsingNumber(eText, v.getContext());
                            videoCall(_ID);
                        }
                    }
                });
        return root;
    }

    // Get required permissions
    public void askPermissions() {
        // Ask permissions
        requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS}, 1);
    }

    public Long getContactIdUsingNumber(String phoneNumber, Context context) {

        // Search contact using phone number
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);

        // Store ID of the contact we are searching
        long contactId = 0L;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.PhoneLookup.CONTACT_ID));
        } else
            return null;

        // Make array of 1 element
        String[] selectionArgs = {Long.toString(contactId)};
        // Select clause to search for contact I
        String selectionClause = ContactsContract.Data.CONTACT_ID + " = ? ";

        Long _ID = null;
        cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, selectionClause, selectionArgs, null);
        // Cursor can't be null but anyway...
        if (cursor != null)
            while (cursor.moveToNext()) {
                String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                if (mimeType.equals("vnd.android.cursor.item/vnd.com.whatsapp.video.call")) {
                    _ID = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
                }
            }
        else
            return null;
        cursor.close();
        return _ID;
    }

    //Video Call Intent
    public void videoCall(Long ID) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String data = "content://com.android.contacts/data/" + Long.toString(ID);
        String type = "vnd.android.cursor.item/vnd.com.whatsapp.video.call";
        intent.setDataAndType(Uri.parse(data), type);
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }
}