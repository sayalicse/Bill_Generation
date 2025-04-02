package com.example.bill_generation.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bill_generation.Model.customermodel;
import com.example.bill_generation.R;

import java.util.List;
import java.util.Random;

public class customerlist_adapter extends RecyclerView.Adapter<customerlist_adapter.ViewHolder> {
    private Context context;
    private List<customermodel> cutsomerlist;
    private Random random = new Random();
    public customerlist_adapter(Context context, List<customermodel> cutsomerlist) {
        this.context = context;
        this.cutsomerlist = cutsomerlist;
    }
    @NonNull
    @Override
    public customerlist_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cutomerlist, parent, false);
        return new ViewHolder(view);
    }
    private int[] colors = {
            Color.parseColor("#FF5733"), // Red
            Color.parseColor("#33FF57"), // Green
            Color.parseColor("#3357FF"), // Blue
            Color.parseColor("#FF33A8"), // Pink
            Color.parseColor("#FFBD33"), // Yellow
            Color.parseColor("#8D33FF")  // Purple
    };
    @Override
    public void onBindViewHolder(@NonNull customerlist_adapter.ViewHolder holder, int position) {
        customermodel mn =cutsomerlist.get(position);

        holder.cname.setText(mn.getcustomername());
        holder.cmobile.setText(mn.getcustomermobile());

        int randomColor = colors[random.nextInt(colors.length)];

        // Set the background color of CardView
        holder.cutomernameCard.setCardBackgroundColor(randomColor);

    }

    @Override
    public int getItemCount() {
         return cutsomerlist.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cutomernameCard;
        TextView cname,cmobile; // Define the TextView here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cutomernameCard = itemView.findViewById(R.id.custom_card);
            cname= itemView.findViewById(R.id.customer_name);
            cmobile= itemView.findViewById(R.id.mobile_number);
            // Bind the TextView from your layout
        }
    }
}
