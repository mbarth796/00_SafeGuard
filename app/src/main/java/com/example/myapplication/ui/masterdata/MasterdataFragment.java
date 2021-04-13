package com.example.myapplication.ui.masterdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.example.myapplication.R;

public class MasterdataFragment extends FragmentActivity {


    //Variablen die für die Stammdaten relevant sind
    DatabaseHelper1 myDB;
    EditText editVorname, editName, editTelefonnummer, editGeburtsdatum, editVorerkrankungen;
    String blutgruppeNames[] ={"A","B","AB","0"}, rhesusfaktorNames[]={"+","-"}, vornameStammdaten, nameStammdaten, telefonnummerStammdaten, geburtsdatumStammdaten, blutgruppeStammdaten, rhesusfaktorStammdaten, vorerkrankungenStammdaten, editBlutgruppe, editRhesusfaktor;
    Button buttonUpdate_Data, buttonDelete_Data;
    ImageButton button_navigation, button_home;
    Spinner blutgruppeSpinner, rhesusfaktorSpinner;
    ArrayAdapter<String> blutgruppeArrayAdapter, rhesusfaktorArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_masterdata);
         myDB = new DatabaseHelper1(this);

        editVorname = (EditText) findViewById(R.id.editText_VornameUser);
        editName = (EditText) findViewById(R.id.editText_NameUser);
        editTelefonnummer = (EditText)findViewById(R.id.editText_TelefonnummerUser);
        editGeburtsdatum = (EditText)findViewById(R.id.editText_GeburtsdatumUser);
        editVorerkrankungen = (EditText) findViewById(R.id.editText_VorerkrankungenUser);
        buttonUpdate_Data = (Button) findViewById(R.id.button_UpdateUser);
        buttonDelete_Data = (Button) findViewById(R.id.button_DeleteUser);

        ViewStammdatenUser();
        DeleteData();
        updateStammdatenData();
/*
        button_navigation = (ImageButton) findViewById(R.id.imageButton4);
        button_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                openNavigation();
            }
        });

        button_home = (ImageButton) findViewById(R.id.imageButton);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                openHome();
            }
        });*/
    }
/*
    public void openNavigation () {
        Intent navigation = new Intent(this, MainActivity_Navigation.class);
        startActivity(navigation);
    }

    public void openHome () {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }*/

    //DelteData: Löscht die Daten von der angegebeben id, gibt ein Toast aus ob Vorgang erfolgreich
    public void DeleteData() {
        buttonDelete_Data.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDeleted = myDB.deleteData("1");
                        if(isDeleted == true) {
                            Toast.makeText(MasterdataFragment.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MasterdataFragment.this, "Data is not Deleted", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(MasterdataFragment.this, "Data Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MasterdataFragment.this, "Data is not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    //ViewStammdaten: Holt sich zunächst alle Daten und gibt diese dann mithilfe eines buffer aus; showmessage() zum anzeigen der Daten
    public void ViewStammdatenUser() {

        Cursor cursor = myDB.getStammdatenData();
        TextView vornameStammdatenView = (TextView) findViewById(R.id.editText_VornameUser);
        TextView nameStammdatenView = (TextView) findViewById(R.id.editText_NameUser);
        TextView telefonnummerStammdatenView= (TextView) findViewById(R.id.editText_TelefonnummerUser);
        TextView geburtsdatumStammdatenView= (TextView) findViewById(R.id.editText_GeburtsdatumUser);
        TextView vorerkrankungenStammdatenView = (TextView) findViewById(R.id.editText_VorerkrankungenUser);

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
            blutgruppeSpinner = (Spinner) findViewById(R.id.spinner_blutgruppe);
            blutgruppeArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,blutgruppeNames);
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
            rhesusfaktorSpinner = (Spinner) findViewById(R.id.spinner_rhesusfaktor);
            rhesusfaktorArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,rhesusfaktorNames);
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
}