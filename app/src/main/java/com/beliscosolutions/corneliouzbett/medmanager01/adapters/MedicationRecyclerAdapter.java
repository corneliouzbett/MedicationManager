package com.beliscosolutions.corneliouzbett.medmanager01.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql.DatabaseHelper;
import com.beliscosolutions.corneliouzbett.medmanager01.model.Medication;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.ConversionOfDates;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.DatesDifferences;
import com.beliscosolutions.corneliouzbett.medmanager01.views.MedicationDetailsActivity;

import java.util.ArrayList;

/**
 * Created by CorneliouzBett on 04/04/2018.
 */

public class MedicationRecyclerAdapter extends RecyclerView.Adapter<MedicationRecyclerAdapter.MedicationViewHolder> {

    private ArrayList<Medication> listMedication;
    private Context mContext;

    public MedicationRecyclerAdapter(ArrayList <Medication> listMedication, Context mContext) {
        this.listMedication = listMedication;
        this.mContext = mContext;
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView descriptionTextView;
        TextView startDateTextView;
        ImageView medicineImageView;

        public MedicationViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_name);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            startDateTextView = itemView.findViewById(R.id.tv_numberOfDays);
            medicineImageView = itemView.findViewById(R.id.civ_medicine);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                   /* Toast.makeText(v.getContext(),"Clicked Item"+listMedication.get(position).getName(),
                            Toast.LENGTH_LONG).show(); */
                    Intent detailsActivityIntent = new Intent(mContext, MedicationDetailsActivity.class);
                    detailsActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    detailsActivityIntent.putExtra("title",listMedication.get(position).getName());
                    detailsActivityIntent.putExtra("description",listMedication.get(position).getDescription());
                    detailsActivityIntent.putExtra("interval",listMedication.get(position).getInterval());
                    detailsActivityIntent.putExtra("startDate",listMedication.get(position).getStart_date());
                    detailsActivityIntent.putExtra("endDate",listMedication.get(position).getEnd_date());

                    mContext.startActivity(detailsActivityIntent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();

/*                    Toast.makeText(v.getContext(),"Clicked Item"+listMedication.get(pos).getDescription(),
                            Toast.LENGTH_LONG).show();*/

                    android.app.AlertDialog.Builder alertDialogbuilder = new android.app.AlertDialog.Builder(v.getContext());

                    alertDialogbuilder.setTitle("Med-Manager Message");
                    alertDialogbuilder
                            .setMessage("Are you sure you want to delete "+listMedication.get(pos).getName())
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_cancel_delete)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseHelper helper = new DatabaseHelper(v.getContext());
                                    helper.deleteMedication(listMedication.get(pos).getName());
                                    listMedication.remove(pos);
                                    notifyDataSetChanged();
                                }
                            });
                    alertDialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    android.app.AlertDialog alertDialog = alertDialogbuilder.create();
                    alertDialog.show();

                    return true;
                }
            });
        }
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication_rows,parent,false);

        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicationViewHolder holder, int position) {
        holder.nameTextView.setText(listMedication.get(position).getName());
        holder.descriptionTextView.setText(listMedication.get(position).getDescription());

      long numberOfDays =  DatesDifferences.getNumberOfDays(ConversionOfDates.getDateFromString(listMedication.get(position).getStart_date()),
                ConversionOfDates.getDateFromString(listMedication.get(position).getEnd_date()));

        holder.startDateTextView.setText( String.valueOf(numberOfDays) +" Days remaining");

       if (position % 3 == 0){
           holder.medicineImageView.setImageResource(R.drawable.image_tablets);
       } else if (position % 3 == 1){
           holder.medicineImageView.setImageResource(R.drawable.image_pills);
       } else if (position % 3 == 2){
           holder.medicineImageView.setImageResource(R.drawable.image_medicine);
       }



    }

    @Override
    public int getItemCount() {
        return listMedication.size();
    }

    public void setFilter(ArrayList<Medication> newList) {
        listMedication = new ArrayList <>();
        listMedication.addAll(newList);
        notifyDataSetChanged();

    }
}
