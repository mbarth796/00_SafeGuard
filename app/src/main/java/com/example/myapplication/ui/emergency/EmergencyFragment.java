package com.example.myapplication.ui.emergency;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.ui.masterdata.DatabaseHelper1;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class EmergencyFragment extends Fragment {

    private EmergencyViewModel emergencyViewModel;

    private int accident = -1;
    private int trafficAccidentType = -1;
    private int amountHurt = -1;
    private int group = -1;
    private int squeezed = -1;
    private int fire = -1;
    private int unconscious = -1;
    private int fleshWound = -1;
    private int brokenBone = -1;
    private int strongBleed = -1;

    private TextView tvAccident, tvTrafficAccidentType, tvAmountHurt, tvGroup, tvDescription; //tvSpecialInformation
    private Button buttonTrafficAccident, buttonOtherAccident;
    private Button buttonCar, buttonBike, buttonPedestrian;
    private Button button1, button2, button3to5, button6to10, buttonMoreThenTen;
    private Button buttonAdults, buttonBabys, buttonChildren;
    private Button buttonSqueezed, buttonFire, buttonUnconscious, buttonFleshWound, buttonBrokenBone, buttonStrongBleeding;
    private EditText editTextSpecialInformation;

    private Button buttonBack, buttonEmergencyCall;
    DatabaseHelper1 myDB;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        emergencyViewModel =
                new ViewModelProvider(this).get(EmergencyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_emergency, container, false);
       /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        tvAccident = root.findViewById(R.id.textView_accident);
        tvTrafficAccidentType = root.findViewById(R.id.textView_trafficAccidentType);
        tvAmountHurt = root.findViewById(R.id.textView_amountHurt);
        tvGroup = root.findViewById(R.id.textView_Group);
        tvDescription = root.findViewById(R.id.textView_description);
        //tvSpecialInformation = findViewById(R.id.textview_specialInformation);

        buttonTrafficAccident = root.findViewById(R.id.button_trafficAccident);
        buttonOtherAccident = root.findViewById(R.id.button_otherAccident);

        buttonCar =  root.findViewById(R.id.button_car);
        buttonBike =  root.findViewById(R.id.button_bike);
        buttonPedestrian =  root.findViewById(R.id.button_pedestrian);
        setTrafficAccidentTypeInvisible();

        button1 =  root.findViewById(R.id.button_one);
        button2 =  root.findViewById(R.id.button_two);
        button3to5 =  root.findViewById(R.id.button_threeToFive);
        button6to10 =  root.findViewById(R.id.button_sixToTen);
        buttonMoreThenTen =  root.findViewById(R.id.button_moreThenTen);
        setAmountHurtInvisible();

        buttonAdults =  root.findViewById(R.id.button_adults);
        buttonBabys =  root.findViewById(R.id.button_babys);
        buttonChildren =  root.findViewById(R.id.button_children);
        setGroupInvisible();

        buttonSqueezed =  root.findViewById(R.id.button_squeezed);
        buttonFire =  root.findViewById(R.id.button_fire);
        buttonUnconscious =  root.findViewById(R.id.button_unconscious);
        buttonFleshWound =  root.findViewById(R.id.button_fleshWound);
        buttonBrokenBone =  root.findViewById(R.id.button_brokenBone);
        buttonStrongBleeding =  root.findViewById(R.id.button_strongBleeding);
        editTextSpecialInformation =  root.findViewById(R.id.editText_specialInformation);
        setDescriptionInvisible();

        buttonBack =  root.findViewById(R.id.button_back);
        buttonEmergencyCall =  root.findViewById(R.id.button_EmergencyCall);
        buttonEmergencyCall.setVisibility(View.GONE);



// Accident
        buttonTrafficAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accident != 0){
                    accident = 0;
                    buttonTrafficAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonOtherAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    //so wünscht android studio sich das
                    //buttonTrafficAccident.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.sup_rounded_corner_red,null));
                    //buttonOtherAccident.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.sup_rounded_corner_blue,null));
                    setTrafficAccidentTypeVisible();
                } else if(accident == 0){
                    accident = -1;
                    buttonTrafficAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonOtherAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accident = 1;
                buttonOtherAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                buttonTrafficAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                //Anzeigen eines Fehlerbildschirms bzw. Weiterleitung zum normalen Anruf
                Snackbar.make(view, "Bitte Rufen Sie den Notruf normal an, dies wird in der App noch nicht unterstützt", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

// Traffic Accident Type
        buttonCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trafficAccidentType != 0){
                    trafficAccidentType = 0;
                    buttonCar.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonBike.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonPedestrian.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setAmountHurtVisible();
                } else if (trafficAccidentType == 0){
                    trafficAccidentType = -1;
                    buttonCar.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trafficAccidentType != 1){
                    trafficAccidentType = 1;
                    buttonBike.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonCar.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonPedestrian.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setAmountHurtVisible();
                } else if (trafficAccidentType == 1){
                    trafficAccidentType = -1;
                    buttonBike.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonPedestrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trafficAccidentType != 2) {
                    trafficAccidentType = 2;
                    buttonPedestrian.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonCar.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonBike.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setAmountHurtVisible();
                } else if (trafficAccidentType == 2){
                    trafficAccidentType = -1;
                    buttonPedestrian.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });

// Amount Hurt
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amountHurt != 1) {
                    amountHurt = 1;
                    button1.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    button2.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button3to5.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button6to10.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonMoreThenTen.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setGroupVisible();
                } else if (amountHurt == 1){
                    amountHurt = -1;
                    button1.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amountHurt != 2) {
                    amountHurt = 2;
                    button2.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    button1.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button3to5.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button6to10.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonMoreThenTen.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setGroupVisible();
                } else if (amountHurt == 2){
                    amountHurt = -1;
                    button2.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        button3to5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amountHurt != 3) {
                    amountHurt = 3;
                    button3to5.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    button1.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button2.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button6to10.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonMoreThenTen.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setGroupVisible();
                } else if (amountHurt == 3){
                    amountHurt = -1;
                    button3to5.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        button6to10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amountHurt != 6) {
                    amountHurt = 6;
                    button6to10.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    button1.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button2.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button3to5.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonMoreThenTen.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setGroupVisible();
                } else if (amountHurt == 6){
                    amountHurt = -1;
                    button6to10.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonMoreThenTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amountHurt != 11) {
                    amountHurt = 11;
                    buttonMoreThenTen.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    button1.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button2.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button3to5.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    button6to10.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setGroupVisible();
                } else if (amountHurt == 11){
                    amountHurt = -1;
                    buttonMoreThenTen.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });

// Group
        buttonAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group != 0) {
                    group = 0;
                    buttonAdults.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonBabys.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonChildren.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setDescriptionVisible();
                } else if (group == 0){
                    group = -1;
                    buttonAdults.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonBabys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group != 1) {
                    group = 1;
                    buttonBabys.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonAdults.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonChildren.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setDescriptionVisible();
                } else if (group == 1){
                    group = -1;
                    buttonBabys.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group != 2) {
                    group = 2;
                    buttonChildren.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonAdults.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonBabys.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    setDescriptionVisible();
                } else if (group == 2){
                    group = -1;
                    buttonChildren.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });

// Description
        buttonSqueezed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(squeezed != 1) {
                    squeezed = 1;
                    buttonSqueezed.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                } else if (squeezed == 1){
                    group = -1;
                    buttonSqueezed.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fire != 1) {
                    fire = 1;
                    buttonFire.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                } else if (fire == 1){
                    group = -1;
                    buttonFire.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonUnconscious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(unconscious != 1) {
                    unconscious = 1;
                    buttonUnconscious.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                } else if (unconscious == 1){
                    unconscious = -1;
                    buttonUnconscious.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonFleshWound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fleshWound != 1) {
                    fleshWound = 1;
                    buttonFleshWound.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                } else if (fleshWound == 1){
                    fleshWound = -1;
                    buttonFleshWound.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonBrokenBone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brokenBone != 1) {
                    brokenBone = 1;
                    buttonBrokenBone.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                } else if (brokenBone == 1){
                    brokenBone = -1;
                    buttonBrokenBone.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });
        buttonStrongBleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(strongBleed != 1) {
                    strongBleed = 1;
                    buttonStrongBleeding.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                } else if (strongBleed == 1){
                    strongBleed = -1;
                    buttonStrongBleeding.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                }
            }
        });

        buttonEmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSpecialInformation.setText(generateText());
                sendEmergencyMessage();

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFlags();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
            }
        });

        myDB = new DatabaseHelper1(getActivity());

        return root;
    }



    //setzt alle Flags zurück, auf ungültige Werte
    public void resetFlags(){
        accident = -1;
        trafficAccidentType = -1;
        amountHurt = -1;
        group = -1;
        squeezed = -1;
        fire = -1;
        unconscious = -1;
        fleshWound = -1;
        brokenBone = -1;
        strongBleed = -1;
    }

    //setzt den Block zur Angabe des Verkehrsunfalltyps aus Überschrift und Buttons auf sichtar
    public void setTrafficAccidentTypeVisible(){
        tvTrafficAccidentType.setVisibility(View.VISIBLE);
        buttonCar.setVisibility(View.VISIBLE);
        buttonBike.setVisibility(View.VISIBLE);
        buttonPedestrian.setVisibility(View.VISIBLE);
    }

    //setzt den Block zur Angabe des Verkehrsunfalltyps aus Überschrift und Buttons auf unsichtbar
    public void setTrafficAccidentTypeInvisible(){
        tvTrafficAccidentType.setVisibility(View.GONE);
        buttonCar.setVisibility(View.GONE);
        buttonBike.setVisibility(View.GONE);
        buttonPedestrian.setVisibility(View.GONE);
    }

    //setzt den Block zur Anzahl der verletzten Personen aus Überschrift und Buttons auf sichtbar
    public void setAmountHurtVisible(){
        tvAmountHurt.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3to5.setVisibility(View.VISIBLE);
        button6to10.setVisibility(View.VISIBLE);
        buttonMoreThenTen.setVisibility(View.VISIBLE);
    }

    //setzt den Block zur Anzahl der verletzten Personen aus Überschrift und Buttons auf unsichtbar
    public void setAmountHurtInvisible(){
        tvAmountHurt.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3to5.setVisibility(View.GONE);
        button6to10.setVisibility(View.GONE);
        buttonMoreThenTen.setVisibility(View.GONE);
    }

    //setzt den Block zur Personengruppe aus Überschrift und Buttons auf sichtbar
    public void setGroupVisible(){
        tvGroup.setVisibility(View.VISIBLE);
        buttonAdults.setVisibility(View.VISIBLE);
        buttonBabys.setVisibility(View.VISIBLE);
        buttonChildren.setVisibility(View.VISIBLE);
    }

    //setzt den Block zur Personengruppe aus Überschrift und Buttons auf unsichtbar
    public void setGroupInvisible(){
        tvGroup.setVisibility(View.GONE);
        buttonAdults.setVisibility(View.GONE);
        buttonBabys.setVisibility(View.GONE);
        buttonChildren.setVisibility(View.GONE);
    }

    //setzt den Block zur Beschreibung aus Überschrift, Buttons und EditTextauf sichtbar
    public void setDescriptionVisible(){
        tvDescription.setVisibility(View.VISIBLE);
        buttonSqueezed.setVisibility(View.VISIBLE);
        buttonFire.setVisibility(View.VISIBLE);
        buttonUnconscious.setVisibility(View.VISIBLE);
        buttonFleshWound.setVisibility(View.VISIBLE);
        buttonBrokenBone.setVisibility(View.VISIBLE);
        buttonStrongBleeding.setVisibility(View.VISIBLE);
        editTextSpecialInformation.setVisibility(View.VISIBLE);
    }

    //setzt den Block zur Beschreibung aus Überschrift, Buttons und EditText auf unsichtbar
    public void setDescriptionInvisible(){
        tvDescription.setVisibility(View.GONE);
        buttonSqueezed.setVisibility(View.GONE);
        buttonFire.setVisibility(View.GONE);
        buttonUnconscious.setVisibility(View.GONE);
        buttonFleshWound.setVisibility(View.GONE);
        buttonBrokenBone.setVisibility(View.GONE);
        buttonStrongBleeding.setVisibility(View.GONE);
        editTextSpecialInformation.setVisibility(View.GONE);
    }

    //Erstellt einen String aus den Flags des Notrufs, welcher durch das Klicken der Buttons gesetzt wurden
    public String generateText(){
        String ret="";

        Cursor result = myDB.getMasterData();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()) {
            buffer.append(result.getString(1) + " ");
            buffer.append(result.getString(2) + "\n");
        }
        ret = "Anrufer: " + buffer.toString();

        //Unfalltyp
        if(accident == 0){
            ret = ret + "Verkehrsunfall: ";
        } else {
            ret = "Fehler, accident ist nicht 1(=Verkehrsunfall) ";
            return "Fehler, accident ist nicht 1";
        }
        //Art des Verkehrsunfalls
        if(trafficAccidentType == 0){
            ret=ret + "Autounfall, ";
        } else if(trafficAccidentType == 1){
            ret = ret + "Motorrad bzw. Fahrradunfall, ";
        }else if(trafficAccidentType == 1){
            ret = ret + "Fußgänger, ";
        }
        //Anzahl Verletzte
        if(amountHurt == 1){
            ret = ret + "eine verletzte Person, ";
        } else if(amountHurt == 2){
            ret = ret + "zwei verletzte Personen, ";
        } else if(amountHurt == 3){
            ret = ret + "3 bis 5 verletzte Personen, ";
        } else if(amountHurt == 6){
            ret = ret + "6 bis 10 verletzte Personen, ";
        } else if(amountHurt == 11){
            ret = ret + "mehr als 10 verletzte Personen, ";
        }
        //Personengruppe
        if(group == 0){
            ret = ret + "nur erwachsene Personen, " + "\n";
        } else if(group == 1){
            ret = ret + "inklusive Kleinkind(er)) (unter 6 Jahre), " + "\n";
        } else if(group == 2){
            ret = ret + "inklusive Kinder, " + "\n";
        }
        //Beschreibung
        if(squeezed == 1){
            ret= ret + "Eingequetschte Person(en)" + "\n";
        }
        if(fire == 1){
            ret= ret + "Feuer" + "\n";
        }
        if(unconscious == 1){
            ret= ret + "Ohnmächtig" + "\n";
        }
        if(fleshWound == 1){
            ret= ret + "Fleischwunde" + "\n";
        }
        if(brokenBone == 1){
            ret= ret + "gebrochene Knochen" + "\n";
        }
        if(strongBleed == 1){
            ret= ret + "starke Blutung" + "\n";
        }
        //Textfeld genauere Beschreibung
        /*if(!editTextSpecialInformation.getText().equals("")){
            ret = ret + "Genauere Beschreibung:" + "\n" +  editTextSpecialInformation.getText();
        }*/

        return ret;
    }

    //absenden der SMS mit den Notrufdaten
    public void sendEmergencyMessage(){
        //Check ob Permissions da sind
        if (ActivityCompat.checkSelfPermission(getActivity()/*this.getContext()*/, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            //Senden von einer SMS mit den Daten und einer weiteren mit der genaueren Beschreibung
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+491749823050", null, generateText(), null, null);
            if(!editTextSpecialInformation.getText().equals("")){
                smsManager.sendTextMessage("+491749823050", null, editTextSpecialInformation.getText().toString(), null, null);
            }
            Snackbar.make(this.getView(), "Notruf wurde versendet", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
           // Snackbar.make(this.getView(), "Bitte erteilen Sie die Berechtigung für SMS in den Einstellungen", Snackbar.LENGTH_LONG)
             //       .setAction("Action", null).show();
            askPermission();
        }
    }

    //prüft, ob die Berechtigung zur SMS-Versendung gegeben wurde
    public void askPermission() {
        // Ask permissions
        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
    }
}