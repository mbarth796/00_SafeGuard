package com.example.myapplication.ui.emergency;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.ui.masterdata.DatabaseHelperMasterdata;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class EmergencyFragment extends Fragment implements LocationListener {

    private EmergencyViewModel emergencyViewModel;

    // define variables for the text generation and set them on -1
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
    // define emergency telephone number (currently from the developers)
    private String phonenumberWhatsApp = "+4915209521563";
    private String phonenumberSMS = "+491749823050";

    // define variables for the xml files
    private TextView tvAccident, tvTrafficAccidentType, tvAmountHurt, tvGroup, tvDescription; //tvSpecialInformation
    private Button buttonTrafficAccident, buttonOtherAccident;
    private Button buttonCar, buttonBike, buttonPedestrian;
    private Button button1, button2, button3to5, button6to10, buttonMoreThenTen;
    private Button buttonAdults, buttonBabys, buttonChildren;
    private Button buttonSqueezed, buttonFire, buttonUnconscious, buttonFleshWound, buttonBrokenBone, buttonStrongBleeding;
    private EditText editTextSpecialInformation;

    private Button buttonBack, buttonEmergencyCall;
    private ScrollView scrollView;
    DatabaseHelperMasterdata myDB;

    private LocationManager locationManager = null;
    String gps = "";
    private static final long MIN_TIME_TO_REFRESH = 10L;
    private static final float MIN_DISTANCE_TO_REFRESH = 1F;


    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        emergencyViewModel =
                new ViewModelProvider(this).get(EmergencyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_emergency, container, false);

        //initialize variables and set all of the buttons invisble, except the first set
        tvAccident = root.findViewById(R.id.textView_accident);
        tvTrafficAccidentType = root.findViewById(R.id.textView_trafficAccidentType);
        tvAmountHurt = root.findViewById(R.id.textView_amountHurt);
        tvGroup = root.findViewById(R.id.textView_Group);
        tvDescription = root.findViewById(R.id.textView_description);

        buttonTrafficAccident = root.findViewById(R.id.button_trafficAccident);
        buttonOtherAccident = root.findViewById(R.id.button_otherAccident);

        buttonCar = root.findViewById(R.id.button_car);
        buttonBike = root.findViewById(R.id.button_bike);
        buttonPedestrian = root.findViewById(R.id.button_pedestrian);
        setTrafficAccidentTypeInvisible();

        button1 = root.findViewById(R.id.button_one);
        button2 = root.findViewById(R.id.button_two);
        button3to5 = root.findViewById(R.id.button_threeToFive);
        button6to10 = root.findViewById(R.id.button_sixToTen);
        buttonMoreThenTen = root.findViewById(R.id.button_moreThenTen);
        setAmountHurtInvisible();

        buttonAdults = root.findViewById(R.id.button_adults);
        buttonBabys = root.findViewById(R.id.button_babys);
        buttonChildren = root.findViewById(R.id.button_children);
        setGroupInvisible();

        buttonSqueezed = root.findViewById(R.id.button_squeezed);
        buttonFire = root.findViewById(R.id.button_fire);
        buttonUnconscious = root.findViewById(R.id.button_unconscious);
        buttonFleshWound = root.findViewById(R.id.button_fleshWound);
        buttonBrokenBone = root.findViewById(R.id.button_brokenBone);
        buttonStrongBleeding = root.findViewById(R.id.button_strongBleeding);
        editTextSpecialInformation = root.findViewById(R.id.editText_specialInformation);
        setDescriptionInvisible();

        buttonBack = root.findViewById(R.id.button_back);
        buttonEmergencyCall = root.findViewById(R.id.button_EmergencyCall);
        buttonEmergencyCall.setVisibility(View.GONE);

        scrollView = root.findViewById(R.id.scrollView_emergency);

        //ask for permissons if not granted
        askPermissionSMS();
        askPermissionsWhatsApp();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_TO_REFRESH, MIN_DISTANCE_TO_REFRESH, EmergencyFragment.this);
        }

        // set onClickListeners or the buttons and Edit-Texts
        // A click on one of the buttons sets the next question and the next set of Buttons visible and marks/removes the mark of the clicked button and sets the value for the variables used
// Accident
        buttonTrafficAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accident != 0) {
                    accident = 0;
                    //buttonTrafficAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_red));
                    //buttonOtherAccident.setBackground(getResources().getDrawable(R.drawable.sup_rounded_corner_blue));
                    buttonTrafficAccident.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonOtherAccident.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setTrafficAccidentTypeVisible();
                    scrollToEnd();
                } else {
                    accident = -1;
                    buttonTrafficAccident.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonOtherAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accident = 1;
                buttonOtherAccident.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                buttonTrafficAccident.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                //Anzeigen eines Fehlerbildschirms bzw. Weiterleitung zum normalen Anruf
                popUpOtherAccident();
            }
        });

// Traffic Accident Type
        buttonCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trafficAccidentType != 0) {
                    trafficAccidentType = 0;
                    buttonCar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonBike.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonPedestrian.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setAmountHurtVisible();
                    scrollToEnd();
                } else {
                    trafficAccidentType = -1;
                    buttonCar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trafficAccidentType != 1) {
                    trafficAccidentType = 1;
                    buttonBike.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonCar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonPedestrian.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setAmountHurtVisible();
                    scrollToEnd();
                } else {
                    trafficAccidentType = -1;
                    buttonBike.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonPedestrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trafficAccidentType != 2) {
                    trafficAccidentType = 2;
                    buttonPedestrian.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonCar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonBike.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setAmountHurtVisible();
                    scrollToEnd();
                } else {
                    trafficAccidentType = -1;
                    buttonPedestrian.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });

// Amount Hurt
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountHurt != 1) {
                    amountHurt = 1;
                    button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button3to5.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button6to10.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonMoreThenTen.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setGroupVisible();
                    scrollToEnd();
                } else {
                    amountHurt = -1;
                    button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountHurt != 2) {
                    amountHurt = 2;
                    button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button3to5.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button6to10.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonMoreThenTen.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setGroupVisible();
                    scrollToEnd();
                } else {
                    amountHurt = -1;
                    button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        button3to5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountHurt != 3) {
                    amountHurt = 3;
                    button3to5.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button6to10.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonMoreThenTen.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setGroupVisible();
                    scrollToEnd();
                } else {
                    amountHurt = -1;
                    button3to5.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        button6to10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountHurt != 6) {
                    amountHurt = 6;
                    button6to10.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button3to5.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonMoreThenTen.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setGroupVisible();
                    scrollToEnd();
                } else {
                    amountHurt = -1;
                    button6to10.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonMoreThenTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountHurt != 11) {
                    amountHurt = 11;
                    buttonMoreThenTen.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    button1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button2.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button3to5.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    button6to10.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setGroupVisible();
                    scrollToEnd();
                } else {
                    amountHurt = -1;
                    buttonMoreThenTen.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });

// Group
        buttonAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (group != 0) {
                    group = 0;
                    buttonAdults.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonBabys.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonChildren.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setDescriptionVisible();
                    scrollToEnd();
                } else {
                    group = -1;
                    buttonAdults.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonBabys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (group != 1) {
                    group = 1;
                    buttonBabys.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonAdults.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonChildren.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setDescriptionVisible();
                    scrollToEnd();
                } else {
                    group = -1;
                    buttonBabys.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (group != 2) {
                    group = 2;
                    buttonChildren.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonAdults.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    buttonBabys.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                    setDescriptionVisible();
                    scrollToEnd();
                } else {
                    group = -1;
                    buttonChildren.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });

// Description
        buttonSqueezed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (squeezed != 1) {
                    squeezed = 1;
                    buttonSqueezed.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                    scrollToEnd();
                } else {
                    squeezed = -1;
                    buttonSqueezed.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fire != 1) {
                    fire = 1;
                    buttonFire.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                    scrollToEnd();
                } else {
                    fire = -1;
                    buttonFire.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonUnconscious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unconscious != 1) {
                    unconscious = 1;
                    buttonUnconscious.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                    scrollToEnd();
                } else {
                    unconscious = -1;
                    buttonUnconscious.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonFleshWound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fleshWound != 1) {
                    fleshWound = 1;
                    buttonFleshWound.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                    scrollToEnd();
                } else {
                    fleshWound = -1;
                    buttonFleshWound.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonBrokenBone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (brokenBone != 1) {
                    brokenBone = 1;
                    buttonBrokenBone.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                    scrollToEnd();
                } else {
                    brokenBone = -1;
                    buttonBrokenBone.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });
        buttonStrongBleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strongBleed != 1) {
                    strongBleed = 1;
                    buttonStrongBleeding.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_red, null));
                    buttonEmergencyCall.setVisibility(View.VISIBLE);
                    scrollToEnd();
                } else {
                    strongBleed = -1;
                    buttonStrongBleeding.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.sup_rounded_corner_blue, null));
                }
            }
        });

        editTextSpecialInformation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonEmergencyCall.setVisibility(View.VISIBLE);
                scrollToEnd();
            }
        });


        buttonEmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFlags()) {
                    sendEmergencyMessage();
                    //cecks permissions for the WhatsApp call
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        askPermissionsWhatsApp();
                    } else {
                        //Starts the WhatsApp call
                        String eText = phonenumberWhatsApp;
                        Long _ID = getContactIdUsingNumber(eText, view.getContext());
                        videoCall(_ID);
                    }
                }
                editTextSpecialInformation.setText(generateText() + generateTextDescription() + gps);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navigates to the home fragment
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_home);
            }
        });

        myDB = new DatabaseHelperMasterdata(getActivity());

        return root;
    }

    //resets all flags
    public void resetFlags() {
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

    // sets the block of the trafficAccidentType visible
    public void setTrafficAccidentTypeVisible() {
        tvTrafficAccidentType.setVisibility(View.VISIBLE);
        buttonCar.setVisibility(View.VISIBLE);
        buttonBike.setVisibility(View.VISIBLE);
        buttonPedestrian.setVisibility(View.VISIBLE);
    }

    // sets the block of the trafficAccidentType invisible
    public void setTrafficAccidentTypeInvisible() {
        tvTrafficAccidentType.setVisibility(View.GONE);
        buttonCar.setVisibility(View.GONE);
        buttonBike.setVisibility(View.GONE);
        buttonPedestrian.setVisibility(View.GONE);
    }

    // sets the block of amountHurt visible
    public void setAmountHurtVisible() {
        tvAmountHurt.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3to5.setVisibility(View.VISIBLE);
        button6to10.setVisibility(View.VISIBLE);
        buttonMoreThenTen.setVisibility(View.VISIBLE);
    }

    // sets the block of amountHurt invisible
    public void setAmountHurtInvisible() {
        tvAmountHurt.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3to5.setVisibility(View.GONE);
        button6to10.setVisibility(View.GONE);
        buttonMoreThenTen.setVisibility(View.GONE);
    }

    // sets the block of the group visible
    public void setGroupVisible() {
        tvGroup.setVisibility(View.VISIBLE);
        buttonAdults.setVisibility(View.VISIBLE);
        buttonBabys.setVisibility(View.VISIBLE);
        buttonChildren.setVisibility(View.VISIBLE);
    }

    // sets the block of the group invisible
    public void setGroupInvisible() {
        tvGroup.setVisibility(View.GONE);
        buttonAdults.setVisibility(View.GONE);
        buttonBabys.setVisibility(View.GONE);
        buttonChildren.setVisibility(View.GONE);
    }

    // sets the block of the description visible
    public void setDescriptionVisible() {
        tvDescription.setVisibility(View.VISIBLE);
        buttonSqueezed.setVisibility(View.VISIBLE);
        buttonFire.setVisibility(View.VISIBLE);
        buttonUnconscious.setVisibility(View.VISIBLE);
        buttonFleshWound.setVisibility(View.VISIBLE);
        buttonBrokenBone.setVisibility(View.VISIBLE);
        buttonStrongBleeding.setVisibility(View.VISIBLE);
        editTextSpecialInformation.setVisibility(View.VISIBLE);
    }

    // sets the block of the description invisible
    public void setDescriptionInvisible() {
        tvDescription.setVisibility(View.GONE);
        buttonSqueezed.setVisibility(View.GONE);
        buttonFire.setVisibility(View.GONE);
        buttonUnconscious.setVisibility(View.GONE);
        buttonFleshWound.setVisibility(View.GONE);
        buttonBrokenBone.setVisibility(View.GONE);
        buttonStrongBleeding.setVisibility(View.GONE);
        editTextSpecialInformation.setVisibility(View.GONE);
    }

    //returns true, if any category has at least one valid value
    public boolean checkFlags() {
        if (accident == 1) {
            Snackbar.make(this.requireView(), "Nur f??r Verkehrsunfall verf??gbar, w??hlen Sie den Notruf!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        if (accident != -1 && trafficAccidentType != -1 && amountHurt != -1 && group != -1 &&
                !(squeezed == -1 && fire == -1 && unconscious == -1 && fleshWound == -1 && brokenBone == -1 && strongBleed == -1 &&
                        editTextSpecialInformation.getText().toString().equals(""))) {
            return true;
        } else {
            Snackbar.make(this.requireView(), "Bitte machen Sie zu jeder Kategorie eine Angabe", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
    }

    //returns a String out of the set flags for the sms
    public String generateText() {
        String ret;

        Cursor result = myDB.getMasterdata();
        StringBuffer buffer = new StringBuffer();
        while (result.moveToNext()) {
            buffer.append(result.getString(1) + " ");
            buffer.append(result.getString(2) + "\n");
        }
        ret = "Anrufer: " + buffer.toString();

        //Unfalltyp
        if (accident == 0) {
            ret = ret + "Verkehrsunfall: ";
        } else {
            return "Fehler, accident ist nicht 1";
        }
        //Art des Verkehrsunfalls
        if (trafficAccidentType == 0) {
            ret = ret + "Autounfall, " + "\n";
        } else if (trafficAccidentType == 1) {
            ret = ret + "Motorrad bzw. Fahrradunfall, " + "\n";
        } else if (trafficAccidentType == 2) {
            ret = ret + "Fu??g??nger, " + "\n";
        }
        //Anzahl Verletzte
        if (amountHurt == 1) {
            ret = ret + "eine verletzte Person, " + "\n";
        } else if (amountHurt == 2) {
            ret = ret + "zwei verletzte Personen, " + "\n";
        } else if (amountHurt == 3) {
            ret = ret + "3 bis 5 verletzte Personen, " + "\n";
        } else if (amountHurt == 6) {
            ret = ret + "6 bis 10 verletzte Personen, " + "\n";
        } else if (amountHurt == 11) {
            ret = ret + "mehr als 10 verletzte Personen, " + "\n";
        }
        //Personengruppe
        if (group == 0) {
            ret = ret + "nur erwachsene Personen, " + "\n";
        } else if (group == 1) {
            ret = ret + "inklusive Kleinkind(er)) (unter 6 Jahre), " + "\n";
        } else if (group == 2) {
            ret = ret + "inklusive Kinder, " + "\n";
        }
        /*
        //Beschreibung
        if (squeezed == 1) {
            ret = ret + "Eingequetschte Person(en)" + "\n";
        }
        if (fire == 1) {
            ret = ret + "Feuer" + "\n";
        }
        if (unconscious == 1) {
            ret = ret + "Ohnm??chtig" + "\n";
        }
        if (fleshWound == 1) {
            ret = ret + "Fleischwunde" + "\n";
        }
        if (brokenBone == 1) {
            ret = ret + "gebrochene Knochen" + "\n";
        }
        if (strongBleed == 1) {
            ret = ret + "starke Blutung" + "\n";
        }
        */

        ret = ret;

        //Textfeld genauere Beschreibung
        /*if(!editTextSpecialInformation.getText().equals("")){
            ret = ret + "Genauere Beschreibung:" + "\n" +  editTextSpecialInformation.getText();
        }*/

        return ret;
    }

    // returns a String out of the set flags of the description
    public String generateTextDescription(){
        String ret="";
        if (squeezed == 1) {
            ret = ret + "Eingequetschte Person(en)" + "\n";
        }
        if (fire == 1) {
            ret = ret + "Feuer" + "\n";
        }
        if (unconscious == 1) {
            ret = ret + "Ohnm??chtig" + "\n";
        }
        if (fleshWound == 1) {
            ret = ret + "Fleischwunde" + "\n";
        }
        if (brokenBone == 1) {
            ret = ret + "gebrochene Knochen" + "\n";
        }
        if (strongBleed == 1) {
            ret = ret + "starke Blutung" + "\n";
        }
        return ret;
    }

    //sends sms with the generated texts
    public void sendEmergencyMessage() {
        //Check ob Permissions da sind
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            //Senden von einer SMS mit den Daten und einer weiteren mit der genaueren Beschreibung
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumberSMS, null, generateText(), null, null);
            smsManager.sendTextMessage(phonenumberSMS, null, generateTextDescription(), null, null);
            if (!editTextSpecialInformation.getText().toString().equals("")) {
                smsManager.sendTextMessage(phonenumberSMS, null, editTextSpecialInformation.getText().toString(), null, null);
            }
            if(!gps.equals("")){
                smsManager.sendTextMessage(phonenumberSMS, null, gps, null, null);
            }
            Snackbar.make(this.requireView(), "Notruf wurde versendet", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            askPermissionSMS();
        }
    }

    //returns number for the WhatsApp call
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

    //checks if the permission for sms is granted
    public void askPermissionSMS() {
        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
    }

    // Get required permissions
    public void askPermissionsWhatsApp() {
        // Ask permissions
        requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS}, 1);
    }

    // generates an alert popup for the button buttonOtherAccident
    public void popUpOtherAccident(){
        new AlertDialog.Builder(requireContext())
                .setTitle("Nicht in der App verf??gbar")
                .setMessage("Bitte rufen Sie den Notruf normal an" + "\n" + "Durch Klicken auf 'Anrufen' werden Sie direkt weitergeleitet")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("anrufen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        phoneCall();
                    }
                })
                .setNegativeButton("zur??ck", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //starts a normal phone call
    public void phoneCall() {
        String number = phonenumberWhatsApp;

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));

        //< check: phone permission >
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            askPermissionsWhatsApp();
        } else {
            startActivity(intent);
        }
    }

    //scrolls to the end of the page
    public void scrollToEnd(){
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(0, scrollView.getBottom());
            }
        });
    }

    @Override
    public void onProviderEnabled (String provider) {

    }

    @Override
    public void onStatusChanged (String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderDisabled (String provider) {
    }

    //sets the variables of the location
    @Override
    public void onLocationChanged (Location location) {

        String fullAddress = null;
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                fullAddress = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            fullAddress = "unknown";
            e.printStackTrace();
        }
        gps = "Adresse: " + fullAddress + "\n" + "Breitengrad: " + String.valueOf(location.getLatitude() + "\n" + "L??ngengrad: " + String.valueOf(location.getLongitude()) + "\n");
        locationManager.removeUpdates(EmergencyFragment.this);
    }
}