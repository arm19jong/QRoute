package com.teamsmokeweed.qroute.database;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.model.Step;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.genqr.DateQr;
import com.teamsmokeweed.qroute.readqr.ResultReadQrActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jongzazaal on 17/10/2559.
 */

public class GenCustomAdapter extends RecyclerSwipeAdapter<GenCustomAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList_Title;
    ArrayList<String> arrayList_PlaceName;
    ArrayList<String> arrayList_PlaceType;
    ArrayList<String> arrayList_Des;
    ArrayList<String> arrayList_WebPage;
    ArrayList<Float> arrayList_Lat;
    ArrayList<Float> arrayList_Lng;
    ArrayList<Integer> arrayList_Id;

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTitle, mPlaceName, tvDel;
        public SwipeLayout swipeLayout;
        CardView cardView;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.titles);
            mPlaceName = (TextView) v.findViewById(R.id.placeName);
            cardView = (CardView) v.findViewById(R.id.card_view);
            tvDel = (TextView) v.findViewById(R.id.tvDelete);
            itemView.setTag(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
        }

    }

    public GenCustomAdapter(Context context, ArrayList<String> arrayList_Titles,
                            ArrayList<String> arrayList_PlaceName, ArrayList<String> arrayList_PlaceType,
                            ArrayList<String> arrayList_Des, ArrayList<String> arrayList_WebPage,
                            ArrayList<Float> arrayList_Lat, ArrayList<Float> arrayList_Lng,
                            ArrayList<Integer> arrayList_Id){
        this.context = context;
        this.arrayList_Title = arrayList_Titles;
        this.arrayList_PlaceName = arrayList_PlaceName;
        this.arrayList_PlaceType = arrayList_PlaceType;
        this.arrayList_Des = arrayList_Des;
        this.arrayList_WebPage = arrayList_WebPage;
        this.arrayList_Lat = arrayList_Lat;
        this.arrayList_Lng = arrayList_Lng;
        this.arrayList_Id = arrayList_Id;
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
        //viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

        viewHolder.mTitle.setText(arrayList_Title.get(position));
        viewHolder.mPlaceName.setText(arrayList_PlaceName.get(position));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> sqr = Arrays.asList(arrayList_Lat.get(position).toString(),
                        arrayList_Lng.get(position).toString(), arrayList_Title.get(position),
                        arrayList_PlaceName.get(position), arrayList_PlaceType.get(position),
                        arrayList_Des.get(position), arrayList_WebPage.get(position)
                        );
                startResultActivity(context, sqr);
            }
        });
        viewHolder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemLabel = arrayList_Title.get(position);

                // Remove the item on remove/button click
                DateQr dateQr = new DateQr();
                dateQr.setId(arrayList_Id.get(position));

                DelDatabaseQr delDatabaseQr = new DelDatabaseQr(dateQr, context);
                delDatabaseQr.DelDb();

                arrayList_Title.remove(position);
                arrayList_Lat.remove(position);
                arrayList_Lng.remove(position);
                arrayList_PlaceName.remove(position);
                arrayList_PlaceType.remove(position);
                arrayList_Des.remove(position);
                arrayList_WebPage.remove(position);
                arrayList_Id.remove(position);



                notifyItemRemoved(position);

                notifyItemRangeChanged(position, arrayList_Title.size());

                // Show the removed item label
                Toast.makeText(context,"Removed : " + itemLabel,Toast.LENGTH_SHORT).show();
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return arrayList_Title.size();
    }

    public static void startResultActivity(Context context, List<String> sqr) {
        Intent intent = new Intent(context, ResultReadQrActivity.class);
        //String[] sqr = sqr;
        //ArrayList<String> sqrr = new ArrayList<String>(sqr);
        String[] sqrr = (String[]) sqr.toArray(new String[sqr.size()]);
        intent.putExtra("sQr", sqrr);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
