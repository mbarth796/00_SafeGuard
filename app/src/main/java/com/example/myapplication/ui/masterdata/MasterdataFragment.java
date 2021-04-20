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
import com.google.android.material.snackbar.Snackbar;
import com.example.myapplication.R;

public class MasterdataFragment extends Fragment {

    //Variables that are relevant for this class
    DatabaseHelperMasterdata myDB;
    EditText editFirstname, editName, editTelephone, editBirthday, editPreconditions;
    String bloodgroupNames[] = {"A", "B", "AB", "0"}, rhesusfactorNames[] = {"+", "-"}, firstnameMasterdata, nameMasterdata, telephoneMasterdata, birthdayMasterdata,
            bloodgroupMasterdata, rhesusfactorMasterdata, preconditionsMasterdata, editBloodgroup, editRhesusfactor;
    Button buttonUpdate_Data, buttonDelete_Data;
    Spinner bloodgroupSpinner, rhesusfactorSpinner;
    ArrayAdapter<String> bloodgroupArrayAdapter, rhesusfactorArrayAdapter;

    private MasterdataViewModel masterdataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        masterdataViewModel =
                new ViewModelProvider(this).get(MasterdataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_masterdata, container, false);

        myDB = new DatabaseHelperMasterdata(getActivity());

        //referencing on the buttons and editTexts from fragment_masterdata
        editFirstname = (EditText) root.findViewById(R.id.editText_firstnameUser);
        editName = (EditText) root.findViewById(R.id.editText_lastnameUser);
        editTelephone = (EditText) root.findViewById(R.id.editText_telephoneUser);
        editBirthday = (EditText) root.findViewById(R.id.editText_birthdayUser);
        editPreconditions = (EditText) root.findViewById(R.id.editText_preconditionsUser);
        buttonUpdate_Data = (Button) root.findViewById(R.id.button_updateUser);
        buttonDelete_Data = (Button) root.findViewById(R.id.button_deleteUser);
        bloodgroupSpinner = (Spinner) root.findViewById(R.id.spinner_bloodGroup);
        rhesusfactorSpinner = (Spinner) root.findViewById(R.id.spinner_rhesusFactor);

        //execute methods
        setTextMasterdata();
        deleteMasterdata();
        updateMasterdata();

        return root;
    }


    //deleteMasterdata(): Deletes the data from the masterdata database, snackbar if action is successful or not
    public void deleteMasterdata() {
        buttonDelete_Data.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDeleted = myDB.deleteData("1");
                        if (isDeleted == true) {
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

    //updateMasterdata(): updating the first and only database entry to the parameters from the textEdits, snackbar if action is successful or not
    public void updateMasterdata() {
        buttonUpdate_Data.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDB.updateData("1", editFirstname.getText().toString(), editName.getText().toString(),
                                editTelephone.getText().toString(), editBirthday.getText().toString(), editBloodgroup,
                                editRhesusfactor, editPreconditions.getText().toString());
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

    //setTextMasterdata(): Gets all existing data from the database via cursor writes them with setText into the editTexts
    public void setTextMasterdata() {

        Cursor cursor = myDB.getMasterdata();

        if (cursor.moveToFirst()) {
            firstnameMasterdata = cursor.getString(1);
            editFirstname.setText(firstnameMasterdata);

            nameMasterdata = cursor.getString(2);
            editName.setText(nameMasterdata);

            telephoneMasterdata = cursor.getString(3);
            editTelephone.setText(telephoneMasterdata);

            birthdayMasterdata = cursor.getString(4);
            editBirthday.setText(birthdayMasterdata);

            //Spinner to limit Users choices, setSelection on the existing one in database
            bloodgroupMasterdata = cursor.getString(5);
            bloodgroupArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, bloodgroupNames);
            bloodgroupSpinner.setAdapter(bloodgroupArrayAdapter);

            if (bloodgroupMasterdata.equals("A")) {
                bloodgroupSpinner.setSelection(0);
            }
            if (bloodgroupMasterdata.equals("B")) {
                bloodgroupSpinner.setSelection(1);
            }
            if (bloodgroupMasterdata.equals("AB")) {
                bloodgroupSpinner.setSelection(2);
            }
            if (bloodgroupMasterdata.equals("0")) {
                bloodgroupSpinner.setSelection(3);
            }

            bloodgroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editBloodgroup = bloodgroupNames[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            //Spinner to limit Users choices, setSelection on the existing one in database
            rhesusfactorMasterdata = cursor.getString(6);
            rhesusfactorArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, rhesusfactorNames);
            rhesusfactorSpinner.setAdapter(rhesusfactorArrayAdapter);

            if (rhesusfactorMasterdata.equals("+")) {
                rhesusfactorSpinner.setSelection(0);
            }
            if (rhesusfactorMasterdata.equals("-")) {
                rhesusfactorSpinner.setSelection(1);
            }

            rhesusfactorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    editRhesusfactor = rhesusfactorNames[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            preconditionsMasterdata = cursor.getString(7);
            editPreconditions.setText(preconditionsMasterdata);
        }
    }
}