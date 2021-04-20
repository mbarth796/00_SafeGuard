package com.example.myapplication.ui.contact;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class ContactFragment extends Fragment {

    //Variables that are relevant for this class
    DatabaseHelperContact myDB;
    EditText editID, editFirstname, editName, editTelephone, editRelation;
    Button buttonAdd_Contact, buttonViewAll_Contact, buttonUpdate_Contact, buttonDelete_Contact;

    private ContactViewModel contactViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactViewModel =
                new ViewModelProvider(this).get(ContactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        myDB = new DatabaseHelperContact(getActivity());

        //referencing on the buttons and editTexts from fragment_contact
        editID = (EditText) root.findViewById(R.id.editText_idContact);
        editFirstname = (EditText) root.findViewById(R.id.editText_firstnameContact);
        editName = (EditText) root.findViewById(R.id.editText_lastnameContact);
        editTelephone = (EditText) root.findViewById(R.id.editText_telephoneContact);
        editRelation = (EditText) root.findViewById(R.id.editText_relationContact);
        buttonAdd_Contact = (Button) root.findViewById(R.id.button_addContact);
        buttonViewAll_Contact = (Button) root.findViewById(R.id.button_viewContact);
        buttonUpdate_Contact = (Button) root.findViewById(R.id.button_updateContact);
        buttonDelete_Contact = (Button) root.findViewById(R.id.button_deleteContact);

        //execute methods
        addContact();
        viewAllContact();
        updateContact();
        deleteContact();

        return root;
    }

    //deleteContact(): Deletes the data from the contact database, snackbar if action is successful or not
    public void deleteContact() {
        buttonDelete_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDB.deleteData(editID.getText().toString());
                        if (deletedRows > 0) {
                            Snackbar.make(requireView(), "Data is Deleted", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(requireView(), "Data is not Deleted", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }
        );
    }

    //updateContact(): Updating the data from the contact with the given id, snackbar if action is successful or not
    public void updateContact() {
        buttonUpdate_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDB.updateData(editID.getText().toString(), editFirstname.getText().toString(),
                                editName.getText().toString(), editTelephone.getText().toString(),
                                editRelation.getText().toString());
                        if (isUpdated == true) {
                            Snackbar.make(requireView(), "Data is Updated", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(requireView(), "Data is not Updated", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }
        );
    }

    // viewAllContact(): Gets all data and displays it via buffer, showMessage() to show the buffer
    public void viewAllContact() {
        buttonViewAll_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = myDB.getAllData();
                        if (result.getCount() == 0) {
                            showMessage("Error", "Nothing found.");
                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (result.moveToNext()) {
                                buffer.append("ID: " + result.getString(0) + "\n");
                                buffer.append("Vorname: " + result.getString(1) + "\n");
                                buffer.append("Name: " + result.getString(2) + "\n");
                                buffer.append("Telefonnummer: " + result.getString(3) + "\n");
                                buffer.append("Beziehung: " + result.getString(4) + "\n");
                            }
                            showMessage("Ansprechpartner", buffer.toString());
                        }
                    }
                }
        );
    }

    //ShowMessage: to show the buffer from viewAllContact()
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //AddData: takes parameter from editText and puts them into database
    public void addContact() {
        buttonAdd_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(editFirstname.getText().toString(),
                                editName.getText().toString(), editTelephone.getText().toString(),
                                editRelation.getText().toString());
                        if (isInserted == true) {
                            Snackbar.make(requireView(), "Data is Inserted", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(requireView(), "Data is not Inserted", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }
        );
    }
}