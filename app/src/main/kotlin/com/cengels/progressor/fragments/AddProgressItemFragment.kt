package com.cengels.progressor.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cengels.progressor.R
import com.cengels.progressor.viewmodels.AddProgressItemViewModel


class AddProgressItemFragment : Fragment() {

    companion object {
        fun newInstance() = AddProgressItemFragment()
    }

    private lateinit var viewModel: AddProgressItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_progress_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddProgressItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
