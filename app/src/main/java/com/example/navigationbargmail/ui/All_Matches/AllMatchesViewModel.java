package com.example.navigationbargmail.ui.All_Matches;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllMatchesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllMatchesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}