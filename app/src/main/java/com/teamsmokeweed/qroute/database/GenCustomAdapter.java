package com.teamsmokeweed.qroute.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.teamsmokeweed.qroute.R;

import java.util.ArrayList;

/**
 * Created by jongzazaal on 17/10/2559.
 */

public class GenCustomAdapter extends RecyclerSwipeAdapter<GenCustomAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList_Title;

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTitle;
        public SwipeLayout swipeLayout;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.titles);
            itemView.setTag(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
        }

    }

    public GenCustomAdapter(Context context, ArrayList<String> arrayList_Titles){
        this.context = context;
        this.arrayList_Title = arrayList_Titles;
    }

    @Override
    public GenCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gen_recycleview_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new GenCustomAdapter.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final GenCustomAdapter.ViewHolder viewHolder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Left
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

        viewHolder.mTitle.setText(arrayList_Title.get(position));

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return arrayList_Title.size();
    }




}
