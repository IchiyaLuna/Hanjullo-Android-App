package com.hanjullo.hanjulloapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CalendarItem> items = new ArrayList<>();

    public CalendarAdapter(ArrayList<CalendarItem> items, Context context) {
        this.context = context;
        addItems(items);
    }

    @Override
    public int getItemViewType(int position) {
        CalendarItem item = items.get(position);

        return 6 - item.getDoW() % 7;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_calendar_1, viewGroup, false);
                return new CalendarViewHolder1(view);
            case 1:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_calendar_2, viewGroup, false);
                return new CalendarViewHolder2(view);
            case 2:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_calendar_3, viewGroup, false);
                return new CalendarViewHolder3(view);
            case 3:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_calendar_4, viewGroup, false);
                return new CalendarViewHolder4(view);
            case 4:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_calendar_5, viewGroup, false);
                return new CalendarViewHolder5(view);
            case 5:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_calendar_6, viewGroup, false);
                return new CalendarViewHolder6(view);
            case 6:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_calendar_7, viewGroup, false);
                return new CalendarViewHolder7(view);
        }

        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_calendar_1, viewGroup, false);

        return new CalendarViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final CalendarItem model = items.get(i);

        switch (6 - model.getDoW() % 7) {
            case 0:
                CalendarViewHolder1 holder1 = (CalendarViewHolder1) holder;
                holder1.dateTextView.setText(model.getDateString("/"));
                break;
            case 1:
                CalendarViewHolder2 holder2 = (CalendarViewHolder2) holder;
                holder2.dateTextView.setText(model.getDateString("/"));
                break;
            case 2:
                CalendarViewHolder3 holder3 = (CalendarViewHolder3) holder;
                holder3.dateTextView.setText(model.getDateString("/"));
                break;
            case 3:
                CalendarViewHolder4 holder4 = (CalendarViewHolder4) holder;
                holder4.dateTextView.setText(model.getDateString("/"));
                break;
            case 4:
                CalendarViewHolder5 holder5 = (CalendarViewHolder5) holder;
                holder5.dateTextView.setText(model.getDateString("/"));
                break;
            case 5:
                CalendarViewHolder6 holder6 = (CalendarViewHolder6) holder;
                holder6.dateTextView.setText(model.getDateString("/"));
                break;
            case 6:
                CalendarViewHolder7 holder7 = (CalendarViewHolder7) holder;
                holder7.dateTextView.setText(model.getDateString("/"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CalendarItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<CalendarItem> items) {
        this.items = items;
    }

    public void clear() {
        items.clear();
    }

    public static class CalendarViewHolder1 extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public CalendarViewHolder1(@NonNull final View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.c_item1_date);
        }
    }

    public static class CalendarViewHolder2 extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public CalendarViewHolder2(@NonNull final View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.c_item2_date);
        }
    }

    public static class CalendarViewHolder3 extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public CalendarViewHolder3(@NonNull final View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.c_item3_date);
        }
    }

    public static class CalendarViewHolder4 extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public CalendarViewHolder4(@NonNull final View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.c_item4_date);
        }
    }

    public static class CalendarViewHolder5 extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public CalendarViewHolder5(@NonNull final View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.c_item5_date);
        }
    }

    public static class CalendarViewHolder6 extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public CalendarViewHolder6(@NonNull final View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.c_item6_date);
        }
    }

    public static class CalendarViewHolder7 extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public CalendarViewHolder7(@NonNull final View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.c_item7_date);
        }
    }
}
