package com.example.bill_generation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bill_generation.Model.modelviewbill;
import com.example.bill_generation.R;

import java.util.List;

public class customerbill_adapter extends  RecyclerView.Adapter<customerbill_adapter.ViewHolder>{
    private Context context;
    private List<modelviewbill> custombill;
    public customerbill_adapter(Context context, List<modelviewbill> custombill) {
        this.context = context;
        this.custombill= custombill;
    }
    @NonNull
    @Override
    public customerbill_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.customer_bill_design,parent,false);
        return new customerlist_adapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull customerbill_adapter.ViewHolder holder, int position) {
        modelviewbill temp = custombill.get(position);
        holder. billid .setText(temp.getbill_id());
        holder.customername.setText(temp.getCustomername() );
        holder.customermobile.setText(temp.getCustomermobile());
        holder.dueamt.setText(temp.getDueamt());

    }

    @Override
    public int getItemCount() {
        return custombill.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView billid,customername,customermobile,drivermobileno,dueamt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billid = itemView.findViewById(R.id.text_billid);
            customername = itemView.findViewById(R.id.text_custname);
            customermobile = itemView.findViewById(R.id.text_custmob);

            drivermobileno = itemView.findViewById(R.id.text_drivermobile);

            dueamt = itemView.findViewById(R.id.text_dueamt);


        }
    }
}
