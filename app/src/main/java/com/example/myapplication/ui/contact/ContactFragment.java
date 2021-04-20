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

    //Variablen die für die Stammdaten relevant sind
    DatabaseHelper2 myDB;
    EditText editid, editVorname, editName, editTelefonnummer, editBeziehung;
    Button buttonAdd_Contact, buttonViewAll_Contact, buttonUpdate_Contact, buttonDelete_Contact;

    private ContactViewModel contactViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactViewModel =
                new ViewModelProvider(this).get(ContactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        myDB = new DatabaseHelper2(getActivity());

                //Cast für die Eingabefelder
        editid = (EditText) root.findViewById(R.id.editText_idContact);
        editVorname = (EditText) root.findViewById(R.id.editText_prenameContact);
        editName = (EditText) root.findViewById(R.id.editText_lastnameContact);
        editTelefonnummer = (EditText) root.findViewById(R.id.editText_telephoneContact);
        editBeziehung = (EditText) root.findViewById(R.id.editText_relationContact);
        buttonAdd_Contact = (Button) root.findViewById(R.id.button_addContact);
        buttonViewAll_Contact = (Button) root.findViewById(R.id.button_viewContact);
        buttonUpdate_Contact = (Button) root.findViewById(R.id.button_updateContact);
        buttonDelete_Contact = (Button) root.findViewById(R.id.button_deleteContact);

        //Ausführen aller Datenbankmethoden
        AddData();
        ViewAll();
        UpdateData();
        DeleteData();

        return root;
    }

    //DeleteData: Löscht die Daten von der angegebeben id, gibt ein Snackbar aus ob Vorgang erfolgreich
    public void DeleteData() {
        buttonDelete_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDB.deleteData(editid.getText().toString());
                        if(deletedRows > 0) {
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

    //UpdateData: Updated die Daten mit den eingegebenen Parametern, gibt Snackbar aus ob Vorgang erfolgreich
    public void UpdateData() {
        buttonUpdate_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDB.updateData(editid.getText().toString(), editVorname.getText().toString(),
                                editName.getText().toString(), editTelefonnummer.getText().toString(),
                                editBeziehung.getText().toString());
                        if(isUpdated == true) {
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

    // ViewAll: Holt sich zunächst alle Daten und gibt diese dann mithilfe eines buffer aus; showmessage() zum anzeigen der Daten
    public void ViewAll() {
        buttonViewAll_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = myDB.getAllData();
                        if(result.getCount() == 0) {
                            ShowMessage("Error","Nothing found.");
                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (result.moveToNext()) {
                                buffer.append("ID: "+result.getString(0)+"\n");
                                buffer.append("Vorname: "+result.getString(1)+"\n");
                                buffer.append("Name: "+result.getString(2)+"\n");
                                buffer.append("Telefonnummer: "+result.getString(3)+"\n");
                                buffer.append("Beziehung: "+result.getString(4)+"\n");
                            }
                            ShowMessage("Ansprechpartner", buffer.toString());
                        }
                    }
                }
        );
    }

    //ShowMessage: Zum Anzeigen der Daten für ViewAll
    public void ShowMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //AddData: nimmt Parameter entgegen und fügt diese ein, Snackbar ob Vorgang erfolgreich
    public void AddData() {
        buttonAdd_Contact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(editVorname.getText().toString(),
                                editName.getText().toString(), editTelefonnummer.getText().toString(),
                                editBeziehung.getText().toString());
                        if (isInserted == true) {
                            Snackbar.make(requireView(), "Data is Inserted", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        else {
                            Snackbar.make(requireView(), "Data is not Inserted", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }
        );
    }
}