package com.cengels.progressor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    public final LiveData<Boolean> useDarkMode = new MutableLiveData<>();
}
