package com.github.dragon925.rickandmorty.ui.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener

class ListScrollListener(private val loadMoreData: LoadMoreData) : OnScrollListener() {

    private var isScrollingUp = false
    private var isScrollingDown = true

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (isScrollingDown && newState == RecyclerView.SCROLL_STATE_IDLE) {
            val manager = recyclerView.layoutManager as LinearLayoutManager
            val position = manager.findLastVisibleItemPosition()
            val adapter = recyclerView.adapter ?: return
            if (position == adapter.itemCount - 1) {
                loadMoreData.load()
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0) {
            isScrollingUp = true
            isScrollingDown = false
        } else if (dy > 0) {
            isScrollingUp = false
            isScrollingDown = true
        }
    }
}