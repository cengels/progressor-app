package com.cengels.progressor.viewmodels

import androidx.lifecycle.ViewModel
import com.cengels.progressor.enums.ProgressType
import com.cengels.progressor.models.ProgressItem
import com.cengels.progressor.units.UnitValue

import java.util.ArrayList

class ProgressListViewModel : ViewModel() {
    private val _progressItems: MutableList<ProgressItem> = ArrayList()

    val progressItems: List<ProgressItem> = this._progressItems

    init {
        val progressItem1 = ProgressItem()
        progressItem1.label = "written on Book 1"
        progressItem1.value = UnitValue(50_000, "words", 0)
        progressItem1.goal = UnitValue(120_000, "words", 0)

        val progressItem2 = ProgressItem()
        progressItem2.label = "spent running today"
        progressItem2.value = UnitValue(600.0, ProgressType.TIME)
        progressItem2.goal = UnitValue(3_600.0, ProgressType.TIME)

        this._progressItems.add(progressItem1)
        this._progressItems.add(progressItem2)
    }
}
