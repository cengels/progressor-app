package com.cengels.progressor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.cengels.progressor.R
import com.cengels.progressor.viewmodels.AddProgressItemViewModel


class AddProgressItemFragment : ActionBarFragment("Add progress item") {

    companion object {
        fun newInstance(): AddProgressItemFragment = AddProgressItemFragment()
    }

    private lateinit var viewModel: AddProgressItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_progress_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.viewModel = ViewModelProviders.of(this).get(AddProgressItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
