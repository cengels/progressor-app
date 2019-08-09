package com.cengels.progressor.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cengels.progressor.R
import com.cengels.progressor.fragments.ProgressListFragment.OnListFragmentInteractionListener
import com.cengels.progressor.models.ProgressItem
import kotlin.math.roundToInt

class ProgressItemRecyclerViewAdapter(
        private val items: List<ProgressItem>,
        private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ProgressItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_progress_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = this.items[position]
        holder.valueView.text = holder.item.value.formattedValue
        holder.labelView.text = holder.item.unitLabel
        holder.progressBar.progress = holder.item.progress.get().roundToInt()
        holder.maxValueView.text = holder.item.goal.getFormattedValue(holder.item.value.getBestUnit())

        holder.view.setOnClickListener { _ ->
            if (this.listener != null) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                this.listener.onListFragmentInteraction(holder.item)
            }
        }
    }

    override fun getItemCount(): Int = this.items.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val valueView: TextView = view.findViewById(R.id.item_value)
        val labelView: TextView = view.findViewById(R.id.item_label)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        val maxValueView: TextView = view.findViewById(R.id.max_value)
        lateinit var item: ProgressItem

        override fun toString(): String = "${super.toString()} '${this.progressBar.progress}'"
    }
}
