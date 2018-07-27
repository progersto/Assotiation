package com.natife.assotiation.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natife.assotiation.R;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<String> list;
    private List<Integer> listColor;
    private Context context;

    public PlayersAdapter(Context context, List<String> list, List<Integer> listColor) {
        this.inflater = LayoutInflater.from(context);
        this.list = new ArrayList<>(list);
        this.listColor = new ArrayList<>(listColor);
        this.context = context;
    }//AdapterProductList

    @Override
    public int getItemCount() {
        return list.size();
    }//getItemCount

    @Override
    public long getItemId(int position) {
        return position;
    }//getItemId

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_player, parent, false);
        return new ViewHolder(view);
    } // onCreateViewHolder

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraint_item_player;
        ImageView imageColor;
        TextView editTextPlayerName;
        RelativeLayout imageVoice;

        private ViewHolder(View view) {
            super(view);
            constraint_item_player = view.findViewById(R.id.item_player_constraint);
            imageColor = view.findViewById(R.id.imageColor);
            imageVoice = view.findViewById(R.id.imageVoice);
            editTextPlayerName = view.findViewById(R.id.editTextPlayerName);
        }//ViewHolder
    }//class ViewHolder

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.editTextPlayerName.setHint(list.get(position));
        holder.imageColor.setColorFilter(ContextCompat.getColor(context, listColor.get(position)));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        holder.imageVoice.setOnClickListener(listener);

    }//onBindViewHolder

    public void deleteFromListAdapter(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);//updates after removing Item at position
        notifyItemRangeChanged(pos, list.size());//updates the items of the following items
    }//deleteFromListAdapter
}//class AdapterProductList