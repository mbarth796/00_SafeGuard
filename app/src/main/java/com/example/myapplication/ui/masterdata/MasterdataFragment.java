package com.example.myapplication.ui.masterdata;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.example.myapplication.R;

public class MasterdataFragment extends Fragment {


    //Variablen die für die Stammdaten relevant sind
    DatabaseHelper1 myDB;
    EditText editVorname, editName, editTelefonnummer, editGeburtsdatum, editVorerkrankungen;
    TextView vornameStammdatenView, nameStammdatenView, telefonnummerStammdatenView, geburtsdatumStammdatenView, vorerkrankungenStammdatenView;
    String blutgruppeNames[] ={"A","B","AB","0"}, rhesusfaktorNames[]={"+","-"}, vornameStammdaten, nameStammdaten, telefonnummerStammdaten, geburtsdatumStammdaten, blutgruppeStammdaten, rhesusfaktorStammdaten, vorerkrankungenStammdaten, editBlutgruppe, editRhesusfaktor;
    Button buttonUpdate_Data, buttonDelete_Data;
    Spinner blutgruppeSpinner, rhesusfaktorSpinner;
    ArrayAdapter<String> blutgruppeArrayAdapter, rhesusfaktorArrayAdapter;

    private MasterdataViewModel masterdataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        masterdataViewModel =
                new ViewModelProvider(this).get(MasterdataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_masterdata, container, false);

        myDB = new DatabaseHelper1(getActivity());

        editVorname = (EditText) root.findViewById(R.id.editText_VornameUser);
        editName = (EditText) root.findViewById(R.id.editText_NameUser);
        editTelefonnummer = (EditText) root.findViewById(R.id.editText_TelefonnummerUser);
        editGeburtsdatum = (EditText) root.findViewById(R.id.editText_GeburtsdatumUser);
        editVorerkrankungen = (EditText) root.findViewById(R.id.editText_VorerkrankungenUser);
        buttonUpdate_Data = (Button) root.findViewById(R.id.button_UpdateUser);
        buttonDelete_Data = (Button) root.findViewById(R.id.button_DeleteUser);
        vornameStammdatenView = (TextView) root.findViewById(R.id.editText_VornameUser);
        nameStammdatenView = (TextView) root.findViewById(R.id.editText_NameUser);
        telefonnummerStammdatenView = (TextView) root.findViewById(R.id.editText_TelefonnummerUser);
        geburtsdatumStammdatenView = (TextView) root.findViewById(R.id.editText_GeburtsdatumUser);
        vorerkrankungenStammdatenView = (TextView) root.findViewById(R.id.editText_VorerkrankungenUser);
        blutgruppeSpinner = (Spinner) root.findViewById(R.id.spinner_blutgruppe);
        rhesusfaktorSpinner = (Spinner) root.findViewById(R.id.spinner_rhesusfaktor);

        viewStammdatenUser();
        deleteData();
        updateStammdatenData();
        //getMasterDataString();

        return root;
    }


    //DelteData: Löscht die Daten von der angegebeben id, gibt ein Toast aus ob Vorgang erfolgreich
    public void deleteData() {
        buttonDelete_Data.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDeleted = myDB.deleteData("1");
                        if(isDeleted == true) {
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

    //UpdateStammdatenData: Updated die Daten mit den eingegebenen Parametern, gibt Toast aus ob Vorgang erfolgreich
    public void updateStammdatenData() {
        buttonUpdate_Data.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDB.updateData("1", editVorname.getText().toString(), editName.getText().toString(),
                                editTelefonnummer.getText().toString(), editGeburtsdatum.getText().toString(), editBlutgruppe,
                                editRhesusfaktor, editVorerkrankungen.getText().toString());
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

    //ViewStammdaten: Holt sich zunächst alle Daten und gibt diese dann mithilfe eines buffer aus; showmessage() zum anzeigen der Daten
    public void viewStammdatenUser() {

        Cursor cursor = myDB.getStammdatenData();

        if(cursor.moveToFirst()) {
            vornameStammdaten = cursor.getString(1);
            vornameStammdatenView.setText(vornameStammdaten);

            nameStammdaten = cursor.getString(2);
            nameStammdatenView.setText(nameStammdaten);

            telefonnummerStammdaten = cursor.getString(3);
            telefonnummerStammdatenView.setText(telefonnummerStammdaten);

            geburtsdatumStammdaten = cursor.getString(4);
            geburtsdatumStammdatenView.setText(geburtsdatumStammdaten);

            blutgruppeStammdaten = cursor.getString(5);

            blutgruppeArrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,blutgruppeNames);
            blutgruppeSpinner.setAdapter(blutgruppeArrayAdapter);

            if(blutgruppeStammdaten.equals("A")) {
                blutgruppeSpinner.setSelection(0);
            }
            if(blutgruppeStammdaten.equals("B")) {
                blutgruppeSpinner.setSelection(1);
            }
            if(blutgruppeStammdaten.equals("AB")) {
                blutgruppeSpinner.setSelection(2);
            }
            if(blutgruppeStammdaten.equals("0")) {
                blutgruppeSpinner.setSelection(3);
            }

            blutgruppeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editBlutgruppe = blutgruppeNames[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            rhesusfaktorStammdaten = cursor.getString(6);

            rhesusfaktorArrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,rhesusfaktorNames);
            rhesusfaktorSpinner.setAdapter(rhesusfaktorArrayAdapter);

            if(rhesusfaktorStammdaten.equals("+")) {
                rhesusfaktorSpinner.setSelection(0);
            }
            if(rhesusfaktorStammdaten.equals("-")) {
                rhesusfaktorSpinner.setSelection(1);
            }

            rhesusfaktorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editRhesusfaktor = rhesusfaktorNames[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            vorerkrankungenStammdaten = cursor.getString(7);
            vorerkrankungenStammdatenView.setText(vorerkrankungenStammdaten);
        }
    }

    /*public String getMasterDataString() {

        Cursor result = myDB.getMasterData();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()) {
            buffer.append("Vorname: "+result.getString(1)+"\n");
            buffer.append("Name: "+result.getString(2)+"\n");
            /*buffer.append("Telefonnummer: "+result.getString(3)+"\n");
            buffer.append("Geburtsdatum: "+result.getString(4)+"\n");
            buffer.append("Blutgruppe: "+result.getString(5));
            buffer.append("Rhesusfaktor: "+result.getString(6)+"\n");
            buffer.append("Vorerkrankungen: "+result.getString(7)+"\n");

        }
        return buffer.toString();
    }  */
}