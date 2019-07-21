package com.cengels.progressor.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ProgressItemRecyclerViewAdapter(List<ProgressItem> items, OnListFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_progress_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.idView.setText(String.valueOf(items.get(position).getId()));
        holder.contentView.setText(items.get(position).getLabel());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView idView;
        public final TextView contentView;
        public ProgressItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.idView = view.findViewById(R.id.item_number);
            this.contentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + this.contentView.getText() + "'";
        }
    }
}
