package com.example.customcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<DayModel> daysOfMonth;
    private final OnItemListener onItemListener;
    private int lastSelectedPosition = -1;
    private Context context;


    public CalendarAdapter(ArrayList<DayModel> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.1666666);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        if (daysOfMonth.get(position).isSelected) {
            holder.parentLayout.setBackgroundDrawable(context.getDrawable(R.drawable.selected_cell_bg));
            holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.white));
            holder.textViewBullet.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.parentLayout.setBackgroundDrawable(context.getDrawable(R.drawable.cell_bg));
            holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.textColor));
            holder.textViewBullet.setTextColor(context.getResources().getColor(R.color.textColor));

        }
        if (daysOfMonth.get(position).dayOfMonth.equals("")){
            holder.parentLayout.setBackgroundDrawable(context.getDrawable(R.drawable.cell_bg_disable));
        }
        holder.setDayOfMonth(daysOfMonth.get(position));

    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }


    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dayOfMonth, textViewBullet;
        public final ConstraintLayout parentLayout;
        DayModel dayModel;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            textViewBullet = itemView.findViewById(R.id.textViewEventBullet);
            parentLayout = itemView.findViewById(R.id.parentLayout);

            itemView.setOnClickListener(this);
        }

        public void setDayOfMonth(DayModel dayOfMonth) {
            dayModel = dayOfMonth;
            this.dayOfMonth.setText(dayModel.dayOfMonth);


        }


        @Override
        public void onClick(View v) {


            if (!dayModel.dayOfMonth.isEmpty()) {
                if (lastSelectedPosition == -1) {
                    dayModel.isSelected = true;
                    onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
                    lastSelectedPosition = getAdapterPosition();
                } else {
                    if (lastSelectedPosition != getAdapterPosition()) {
                        dayModel.isSelected = true;
                        daysOfMonth.get(lastSelectedPosition).isSelected = false;
                        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
                        lastSelectedPosition = getAdapterPosition();
                    }

                }

                notifyDataSetChanged();
            }
        }
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);

    }
}
