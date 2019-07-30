package com.cengels.progressor.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cengels.progressor.R;
import com.cengels.progressor.fragments.ProgressListFragment.OnListFragmentInteractionListener;
import com.cengels.progressor.models.ProgressItem;

import java.util.List;

public class ProgressItemRecyclerViewAdapter extends RecyclerView.Adapter<ProgressItemRecyclerViewAdapter.ViewHolder> {

    private final List<ProgressItem> items;
    private final OnListFragmentInteractionListener listener;

    public ProgressItemRecyclerViewAdapter(final List<ProgressItem> items, final OnListFragmentInteractionListener listener) {
        super();
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_progress_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item = this.items.get(position);
        holder.valueView.setText(holder.item.getValue().getFormattedValue());
        holder.labelView.setText(holder.item.getUnitLabel());
        holder.progressBar.setProgress(holder.item.getProgress());
        holder.maxValueView.setText(holder.item.getGoal().getFormattedValue(holder.item.getValue().getBestUnit()));

        holder.view.setOnClickListener(v -> {
            if (this.listener != null) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                this.listener.onListFragmentInteraction(holder.item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView valueView;
        public final TextView labelView;
        public final ProgressBar progressBar;
        public final TextView maxValueView;
        public ProgressItem item;

        public ViewHolder(final View view) {
            super(view);
            this.view = view;
            this.valueView = view.findViewById(R.id.item_value);
            this.labelView = view.findViewById(R.id.item_label);
            this.progressBar = view.findViewById(R.id.progress_bar);
            this.maxValueView = view.findViewById(R.id.max_value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + this.progressBar.getProgress() + "'";
        }
    }
}
