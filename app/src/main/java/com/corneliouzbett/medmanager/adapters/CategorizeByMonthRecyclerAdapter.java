package com.corneliouzbett.medmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corneliouzbett.medmanager.R;
import com.corneliouzbett.medmanager.views.MonthDetailedActivity;

/**
 * Created by CorneliouzBett on 08/04/2018.
 */

public class CategorizeByMonthRecyclerAdapter extends RecyclerView.Adapter<CategorizeByMonthRecyclerAdapter.ViewHolder> {

    private String [] months;
    private LayoutInflater layoutInflater;
    public static String month_selected;

    public CategorizeByMonthRecyclerAdapter(String[] months, Context context) {
        this.months = months;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_month_category,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String month = months[position];
        holder.monthTextView.setText(month);
        holder.numberOfMedicationTextView.setText(String.valueOf(getItemCount()));

    }

    @Override
    public int getItemCount() {
        return months.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView monthTextView;
        TextView numberOfMedicationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            monthTextView = itemView.findViewById(R.id.tv_month);
            numberOfMedicationTextView = itemView.findViewById(R.id.tv_numberOfMedication);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent monthDetailsIntent = new Intent(v.getContext().getApplicationContext(), MonthDetailedActivity.class);
                    monthDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    monthDetailsIntent.putExtra("month",position);
                    v.getContext().startActivity(monthDetailsIntent);
                }
            });
        }
    }
}
