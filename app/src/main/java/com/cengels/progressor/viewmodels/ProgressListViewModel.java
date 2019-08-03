package com.cengels.progressor.viewmodels;

import androidx.lifecycle.ViewModel;
import com.cengels.progressor.enums.ProgressType;
import com.cengels.progressor.models.ProgressItem;
import com.cengels.progressor.units.UnitValue;

import java.util.ArrayList;
import java.util.List;

public class ProgressListViewModel extends ViewModel {
    private final List<ProgressItem> progressItems = new ArrayList<>();

    public ProgressListViewModel() {
        super();
        final ProgressItem progressItem1 = new ProgressItem();
        progressItem1.setLabel("written on Book 1");
        progressItem1.setValue(new UnitValue(50000, "words", 0));
        progressItem1.setGoal(new UnitValue(120000, "words", 0));

        final ProgressItem progressItem2 = new ProgressItem();
        progressItem2.setLabel("spent running today");
        progressItem2.setValue(new UnitValue(600, ProgressType.TIME));
        progressItem2.setGoal(new UnitValue(3600, ProgressType.TIME));

        this.progressItems.add(progressItem1);
        this.progressItems.add(progressItem2);
    }

    public List<ProgressItem> getProgressItems() {
        return this.progressItems;
    }
}
