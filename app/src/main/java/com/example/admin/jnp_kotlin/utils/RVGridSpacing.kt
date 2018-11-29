package com.example.admin.jnp_kotlin.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RVGridSpacing(spanCount: Int, spacing: Int, includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    // How to implement
    // <RecyclerView>.addItemDecoration(new RVGridSpacing(<spanCount>, <spacing>, true));
    private var spanCount: Int = 0
    private var spacing: Int = 0
    private var includeEdge: Boolean = false

    init {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f /
            // spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f /
            // spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column +
            // 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }


}