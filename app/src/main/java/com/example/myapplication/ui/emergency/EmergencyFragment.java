package com.example.myapplication.ui.emergency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

public class EmergencyFragment extends Fragment {

    private EmergencyViewModel emergencyViewModel;

    int accident = -1;
    int trafficAccidentType = -1;
    int amountHurt = -1;
    int group = -1;
    int squeezed = -1;
    int fire = -1;
    int unconscious = -1;
    int fleshWound = -1;
    int brokenBone = -1;
    int strongBleed = -1;

    TextView tvAccident, tvTrafficAccidentType, tvAmountHurt, tvGroup, tvDescription; //tvSpecialInformation
    Button buttonTrafficAccident, buttonOtherAccident;
    Button buttonCar, buttonBike, buttonPedestrian;
    Button button1, button2, button3to5, button6to10, buttonMoreThenTen;
    Button buttonAdults, buttonBabys, buttonChildren;
    Button buttonSqueezed, buttonFire, buttonUnconscious, buttonFleshWound, buttonBrokenBone, buttonStrongBleeding;
    //EditText etSpecialInformation;
    Button buttonBack, buttonEmergencyCall;

    EditText editTextOutput;

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
        setDescriptionInvisible();

        buttonBack =  root.findViewById(R.id.button_back);
        buttonEmergencyCall =  root.findViewById(R.id.button_EmergencyCall);
        buttonEmergencyCall.setVisibility(View.GONE);

        editTextOutput =  root.findViewById(R.id.editText_output);


// Accident
        buttonTrafficAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accident != 0){
                    accident = 0;
                    buttonTrafficAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    buttonOtherAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
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
                editTextOutput.setText(generateText());
            }
        });



/**
 setListener(buttonTrafficAccident,1, "accident");
 setListener(buttonOtherAccident,0, "accident"); //Sonderfall: Meldung bzw. Starten Telefonat 112
 setListener(buttonCar,0, "trafficAccidentType");
 setListener(buttonBike,1, "trafficAccidentType");
 setListener(buttonOtherAccident,2, "trafficAccidentType"); //Sonderfall: Meldung bzw. Starten Telefonat 112

 setListener(button1,1, "AmountHurt");
 setListener(button2,2, "AmountHurt");
 setListener(button3to5,3, "AmountHurt");
 setListener(button6to10,6, "AmountHurt");
 setListener(buttonMoreThenTen,11, "AmountHurt");
 setListener(buttonAdults,0, "group");
 setListener(buttonBabys,1, "group");
 setListener(buttonChildren,2, "group");
 setListener(buttonSqueezed,1, "squeezed");
 setListener(buttonFire,1, "fire");
 setListener(buttonUnconscious,1, "unconscious");
 setListener(buttonFleshWound,1, "fleshWound");
 setListener(buttonBrokenBone,1, "brokenBone");
 setListener(buttonStrongBleeding,1, "strongBleed");
 */
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFlags();
            }
        });



        return root;
    }

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

    public void setTrafficAccidentTypeVisible(){
        tvTrafficAccidentType.setVisibility(View.VISIBLE);
        buttonCar.setVisibility(View.VISIBLE);
        buttonBike.setVisibility(View.VISIBLE);
        buttonPedestrian.setVisibility(View.VISIBLE);
    }
    public void setTrafficAccidentTypeInvisible(){
        tvTrafficAccidentType.setVisibility(View.GONE);
        buttonCar.setVisibility(View.GONE);
        buttonBike.setVisibility(View.GONE);
        buttonPedestrian.setVisibility(View.GONE);
    }

    public void setAmountHurtVisible(){
        tvAmountHurt.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3to5.setVisibility(View.VISIBLE);
        button6to10.setVisibility(View.VISIBLE);
        buttonMoreThenTen.setVisibility(View.VISIBLE);
    }
    public void setAmountHurtInvisible(){
        tvAmountHurt.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3to5.setVisibility(View.GONE);
        button6to10.setVisibility(View.GONE);
        buttonMoreThenTen.setVisibility(View.GONE);
    }

    public void setGroupVisible(){
        tvGroup.setVisibility(View.VISIBLE);
        buttonAdults.setVisibility(View.VISIBLE);
        buttonBabys.setVisibility(View.VISIBLE);
        buttonChildren.setVisibility(View.VISIBLE);
    }
    public void setGroupInvisible(){
        tvGroup.setVisibility(View.GONE);
        buttonAdults.setVisibility(View.GONE);
        buttonBabys.setVisibility(View.GONE);
        buttonChildren.setVisibility(View.GONE);
    }

    public void setDescriptionVisible(){
        tvDescription.setVisibility(View.VISIBLE);
        buttonSqueezed.setVisibility(View.VISIBLE);
        buttonFire.setVisibility(View.VISIBLE);
        buttonUnconscious.setVisibility(View.VISIBLE);
        buttonFleshWound.setVisibility(View.VISIBLE);
        buttonBrokenBone.setVisibility(View.VISIBLE);
        buttonStrongBleeding.setVisibility(View.VISIBLE);
    }
    public void setDescriptionInvisible(){
        tvDescription.setVisibility(View.GONE);
        buttonSqueezed.setVisibility(View.GONE);
        buttonFire.setVisibility(View.GONE);
        buttonUnconscious.setVisibility(View.GONE);
        buttonFleshWound.setVisibility(View.GONE);
        buttonBrokenBone.setVisibility(View.GONE);
        buttonStrongBleeding.setVisibility(View.GONE);
    }

    public String generateText(){
        String ret="";
        if(accident == 0){
            ret = "Verkehrsunfall: ";
        } else {
            ret = "Fehler, accident ist nicht 1(=Verkehrsunfall) ";
        }

        if(trafficAccidentType == 0){
            ret=ret + "Autounfall, ";
        } else if(trafficAccidentType == 1){
            ret = ret + "Motorrad bzw. Fahrradunfall, ";
        }else if(trafficAccidentType == 1){
            ret = ret + "Fußgänger, ";
        }

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

        if(group == 0){
            ret = ret + "nur erwachsene Personen, ";
        } else if(group == 1){
            ret = ret + "inklusive Kleinkind(er)) (unter 6 Jahre), ";
        } else if(group == 2){
            ret = ret + "inklusive Kinder, ";
        }

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

        return ret;
    }
}
