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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natife.assotiation.R;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Player> playerList = new ArrayList<>();
    private Context context;
    private OnItemVoiceIconListener voiceIconListener;


    public PlayersAdapter(Context context, OnItemVoiceIconListener voiceIconListener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.voiceIconListener = voiceIconListener;
    }//AdapterProductList

    @Override
    public int getItemCount() {
        return playerList.size();
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
                playerList.get(holder.getAdapterPosition()).setName(charSequence.toString());
                Log.d("ddd", "onTextChanged list = " + playerList);
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
        EditText editTextPlayerName;
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
        Log.d("ddd", "onBindViewHolder list = " + playerList);

        holder.editTextPlayerName.setHint(context.getResources().getString(R.string.name_player) + " " + (position + 1));
        holder.editTextPlayerName.setText(playerList.get(position).getName());

        holder.imageColor.setColorFilter(ContextCompat.getColor(context, playerList.get(position).getColor()));

        View.OnClickListener listener =
                v -> voiceIconListener.onItemVoiceIconClick(holder.getAdapterPosition(), holder.editTextPlayerName);
        holder.imageVoice.setOnClickListener(listener);
    }//onBindViewHolder

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {

    }

    public void setData(List<Player> playerList) {
        Log.d("ddd", "setData this.list = " + this.playerList);
        this.playerList = playerList;
        notifyDataSetChanged();
    }


    public void deleteFromListAdapter(int position) {
            playerList.remove(position);

            Log.d("ddd", "deleteFromListAdapter list = " + playerList);
            notifyItemRemoved(position);//updates after removing Item at position
            notifyDataSetChanged();
    }//deleteFromListAdapter

}//class AdapterProductList