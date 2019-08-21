package com.cengels.progressor.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.cengels.progressor.R
import com.cengels.progressor.activities.MainActivity
import com.cengels.progressor.extensions.includes
import java.lang.Exception

abstract class ActionBarFragment(val title: String, val options: Int = SHOW_BACK_BUTTON) : Fragment() {
    val mainActivity: MainActivity
            get() = this.activity as MainActivity

    var actionBarOptions: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.setupActionBar(view, this.options)
    }

    private fun setupActionBar(view: View? = this.view, options: Int = 0): Toolbar {
        if (view == null) {
            throw IllegalArgumentException("View is null.")
        }

        this.actionBarOptions = options

        return view.findViewById<Toolbar?>(R.id.toolbar).also {
            if (it == null) {
                throw IllegalArgumentException("View does not contain a toolbar. Please make sure that a toolbar with id +id/toolbar exists in the specified view.")
            }

            this.mainActivity.setSupportActionBar(it)
            this.mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(options.includes(SHOW_BACK_BUTTON))
            it.title = this.title
        }!!
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.findItem(R.id.action_view_main_to_view_settings)!!.isVisible = this.actionBarOptions.includes(SHOW_SETTINGS_BUTTON)
    }

    companion object {
        const val SHOW_BACK_BUTTON: Int = 1
        const val SHOW_SETTINGS_BUTTON: Int = 2
    }
}