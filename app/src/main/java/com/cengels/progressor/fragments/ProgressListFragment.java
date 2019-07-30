package com.cengels.progressor.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cengels.progressor.R.layout;
import com.cengels.progressor.models.ProgressItem;
import com.cengels.progressor.viewmodels.ProgressListViewModel;

public class ProgressListFragment extends Fragment {
    private OnListFragmentInteractionListener listener;
    private ProgressListViewModel viewModel;

    public static ProgressListFragment newInstance() {
        return new ProgressListFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(layout.fragment_progress_list, container, false);

        this.viewModel = ViewModelProviders.of(this).get(ProgressListViewModel.class);

        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ProgressItemRecyclerViewAdapter(this.viewModel.getProgressItems(), this.listener));
        }

        return view;
    }


    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            this.listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @FunctionalInterface
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ProgressItem item);
    }
}
