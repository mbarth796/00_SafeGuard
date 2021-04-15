package com.example.myapplication.ui.demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DemoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DemoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is emergency fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}