package com.cengels.progressor.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cengels.progressor.R
import com.cengels.progressor.R.layout
import com.cengels.progressor.models.ProgressItem
import com.cengels.progressor.viewmodels.ProgressListViewModel

class ProgressListFragment : Fragment() {
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var viewModel: ProgressListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layout.fragment_progress_list, container, false)

        this.viewModel = ViewModelProviders.of(this).get(ProgressListViewModel::class.java)

        view.findViewById<RecyclerView>(R.id.progress_item_list).let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = ProgressItemRecyclerViewAdapter(this.viewModel.progressItems, this.listener)
        }

        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            this.listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.listener = null
    }

    @FunctionalInterface
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: ProgressItem)
    }

    companion object {
        fun newInstance(): ProgressListFragment = ProgressListFragment()
    }
}
