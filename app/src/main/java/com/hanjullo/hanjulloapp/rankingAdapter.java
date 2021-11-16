package com.hanjullo.hanjulloapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class rankingAdapter extends RecyclerView.Adapter<rankingAdapter.CustomViewHolder>{

    private ArrayList<rankingData> arrayList;

    public rankingAdapter(ArrayList<rankingData> arraylist) {
        this.arrayList = arraylist;
    }

    @NonNull
    @Override
    public rankingAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull rankingAdapter.CustomViewHolder holder, int position) {

        holder.onBind(arrayList.get(position));

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    void addItem(rankingData data){
        arrayList.add(data);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView ranking;
        private TextView ranking_name;
        private TextView ranking_point;

        CustomViewHolder(View itemView) {
            super(itemView);

            ranking =(TextView) itemView.findViewById(R.id.ranking);
            ranking_name =(TextView) itemView.findViewById(R.id.ranking_name);
            ranking_point =(TextView) itemView.findViewById(R.id.ranking_point);
        }

        void onBind(rankingData data){
            ranking.setText(data.getRanking());
            ranking_name.setText(data.getRanking_name());
            ranking_point.setText(data.getRanking_point());

        }
    }
}
