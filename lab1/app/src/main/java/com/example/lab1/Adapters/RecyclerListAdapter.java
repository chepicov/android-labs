package com.example.lab1.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab1.Helpers.ItemTouchHelper.ItemTouchHelperAdapter;
import com.example.lab1.Helpers.ItemTouchHelper.ItemTouchHelperViewHolder;
import com.example.lab1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private static final int[] LEVEL = new int[]{
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background
    };

    private final List<Integer> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;

    public RecyclerListAdapter(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        for (int i = 0; i < LEVEL.length; i++) {
            mItems.add(i, LEVEL[i]);
        }
        Collections.shuffle(mItems);
    }

    private boolean checkWin() {
        boolean isWinner = true;
        for (int i = 0; i < LEVEL.length; i++) {
            if (mItems.get(i) != LEVEL[i]) {
                isWinner = false;
                break;
            }
        }
        return isWinner;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.handleView.setImageResource(mItems.get(position));
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Integer prev = mItems.remove(fromPosition);
        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        if (checkWin()) {
            Log.d("Win", "Winner");
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}