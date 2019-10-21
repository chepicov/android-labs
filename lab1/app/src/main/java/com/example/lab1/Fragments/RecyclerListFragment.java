package com.example.lab1.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab1.Helpers.ItemTouchHelper.MyItemTouchHelperCallback;
import com.example.lab1.Adapters.OnStartDragListener;
import com.example.lab1.R;
import com.example.lab1.Adapters.RecyclerListAdapter;
import com.example.lab1.Layouts.SpanningGridLayoutManager;

public class RecyclerListFragment extends Fragment implements
        OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle icicle) {
        super.onViewCreated(view, icicle);

        RecyclerListAdapter adapter = new RecyclerListAdapter(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new SpanningGridLayoutManager(getContext(), getResources().getInteger(R.integer.grid_columns)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
