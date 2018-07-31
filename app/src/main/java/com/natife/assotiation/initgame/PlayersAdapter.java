package com.natife.assotiation.initgame;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natife.assotiation.R;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private LayoutInflater inflater;
    private List<String> list = new ArrayList<>();
    private List<Integer> listColor = new ArrayList<>();
    private Context context;


    public PlayersAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
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
        ViewHolder holder = new ViewHolder(view);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.set(holder.getAdapterPosition(), charSequence.toString());
                Log.d("ddd", "onTextChanged list = " + list);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        holder.editTextPlayerName.addTextChangedListener(textWatcher);

        return holder;
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
        Log.d("ddd", "onBindViewHolder list = " + list);

        holder.editTextPlayerName.setHint(context.getResources().getString(R.string.name_player) + " " + (position + 1));
        holder.editTextPlayerName.setText(list.get(position));

        holder.imageColor.setColorFilter(ContextCompat.getColor(context, listColor.get(position)));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        holder.imageVoice.setOnClickListener(listener);
    }//onBindViewHolder

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {

    }

    public void setData(List<String> list, List<Integer> listColor) {
        Log.d("ddd", "setData this.list = " + this.list);
        Log.d("ddd", "setData list = " + list);
        this.list = list;
        this.listColor = listColor;
        notifyDataSetChanged();
    }


    public void deleteFromListAdapter(int position) {
            list.remove(position);

            Log.d("ddd", "deleteFromListAdapter list = " + list);
            notifyItemRemoved(position);//updates after removing Item at position
            notifyDataSetChanged();
    }//deleteFromListAdapter

}//class AdapterProductList