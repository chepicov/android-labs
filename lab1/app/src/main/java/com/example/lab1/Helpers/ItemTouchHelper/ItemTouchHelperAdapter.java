package com.example.lab1.Helpers.ItemTouchHelper;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

    void onSelectedChanged();
}