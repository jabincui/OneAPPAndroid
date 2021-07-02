package com.oneapp.oneappandroidapp.activity.fragment.assistant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssistantViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AssistantViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}