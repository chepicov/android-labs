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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab1.Helpers.ItemTouchHelper.ItemTouchHelperAdapter;
import com.example.lab1.Helpers.ItemTouchHelper.ItemTouchHelperViewHolder;
import com.example.lab1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private static final int[][] LEVELS = new int[][]{
        {
            R.drawable.cat9,
            R.drawable.cat8,
            R.drawable.cat7,
            R.drawable.cat6,
            R.drawable.cat5,
            R.drawable.cat4,
            R.drawable.cat3,
            R.drawable.cat2,
            R.drawable.cat1
        },
        {
            R.drawable.apple9,
            R.drawable.apple8,
            R.drawable.apple7,
            R.drawable.apple6,
            R.drawable.apple5,
            R.drawable.apple4,
            R.drawable.apple3,
            R.drawable.apple2,
            R.drawable.apple1
        }
    };

    private final List<Integer> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private Button button;
    private Context context;
    private int currentLevelIndex = 0;
    private int[] currentLevel = LEVELS[currentLevelIndex];

    public RecyclerListAdapter(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        loadLevel();
    }

    public void loadLevel() {
        mItems.clear();
        for (int i = 0; i < currentLevel.length; i++) {
            mItems.add(i, currentLevel[i]);
        }
        do {
            Collections.shuffle(mItems);
        } while (checkWin());
        notifyDataSetChanged();
    }

    public void setOnWinButton(Button v) {
        button = v;
    }

    private boolean checkWin() {
        boolean isWinner = true;
        for (int i = 0; i < currentLevel.length; i++) {
            if (mItems.get(i) != currentLevel[i]) {
                isWinner = false;
                break;
            }
        }
        return isWinner;
    }

    private void showWin() {
        if (!checkWin()) {
            return;
        }
        Toast toast = Toast.makeText(context, "YOU WON!", Toast.LENGTH_SHORT);
        toast.show();
        currentLevel = LEVELS[++currentLevelIndex];
        button.setText("Next Level");
        button.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        context = parent.getContext();
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
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onSelectedChanged() {
        showWin();
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