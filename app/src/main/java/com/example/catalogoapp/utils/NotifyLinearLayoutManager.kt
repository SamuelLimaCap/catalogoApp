package com.example.catalogoapp.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * This class calls [mCallback] (instance of [OnLayoutCompleteCallback]) when all layout
 * calculations are complete, e.g. following a call to
 * [RecyclerView.Adapter.notifyDataSetChanged()] (or related methods).
 *
 * In a paginated listing, we will decide if load more needs to be called in the said callback.
 */
class NotifyingLinearLayoutManager(context: Context) : LinearLayoutManager(context, VERTICAL, false) {
    var mCallback: OnLayoutCompleteCallback? = null

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        mCallback?.onLayoutComplete()
    }

    fun isLastItemCompletelyVisible() = findLastCompletelyVisibleItemPosition() == itemCount - 1

    interface OnLayoutCompleteCallback {
        fun onLayoutComplete()
    }
}
