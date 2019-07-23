package com.cengels.progressor.viewmodels;

import androidx.lifecycle.ViewModel;
import com.cengels.progressor.enums.ProgressType;
import com.cengels.progressor.models.ProgressItem;
import com.cengels.progressor.units.UnitValue;

import java.util.ArrayList;
import java.util.List;

public class ProgressListViewModel extends ViewModel {
    private List<ProgressItem> progressItems = new ArrayList<>();

    public ProgressListViewModel() {
        ProgressItem progressItem1 = new ProgressItem();
        progressItem1.setLabel("Book 1");
        progressItem1.setValue(new UnitValue(50000, "words"));
        progressItem1.setGoal(new UnitValue(120000, "words"));

        ProgressItem progressItem2 = new ProgressItem();
        progressItem2.setLabel("Time spent running today");
        progressItem2.setValue(new UnitValue(10 * 60, ProgressType.TIME));
        progressItem2.setGoal(new UnitValue(60 * 60, ProgressType.TIME));

        this.progressItems.add(progressItem1);
        this.progressItems.add(progressItem2);
    }

    public List<ProgressItem> getProgressItems() {
        return progressItems;
    }
}
