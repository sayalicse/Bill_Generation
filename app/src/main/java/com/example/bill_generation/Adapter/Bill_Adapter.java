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

public class Bill_Adapter extends  RecyclerView.Adapter<Bill_Adapter.ViewHolder>{
    private Context context;
    private List<modelviewbill> billlist;
    public Bill_Adapter(Context context, List<modelviewbill> billlist) {
        this.context = context;
        this.billlist= billlist;
    }

    @NonNull
    @Override
    public Bill_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.bill_design,parent,false);
        return new Bill_Adapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Bill_Adapter.ViewHolder holder, int position) {
        modelviewbill temp = billlist.get(position);
        holder. billid .setText(temp.getbill_id());
        holder.customername.setText(temp.getCustomername() );
        holder.customermobile.setText(temp.getCustomermobile());
        holder.vehicleno.setText(temp.getVehicleno());
        holder.vehicledriver.setText(temp.getVehicledriver());
        holder.drivermobileno.setText(temp.getDrivermobileno());
        holder. materialtype .setText(temp.getMaterialtype());
        holder.quantity.setText(temp.getQuantity() );
        holder.amountpay.setText(temp.getAmountpay() );
        holder.dueamt.setText(temp.getDueamt());
        holder. deliveryloc.setText(temp.getDeliveryloc());
        holder. startdate.setText(temp.getstartdate());
        holder.enddate.setText(temp.getenddate());







    }

    @Override
    public int getItemCount() {
        return billlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView billid,customername,customermobile,vehicleno,vehicledriver,drivermobileno,materialtype,quantity,amountpay,dueamt,deliveryloc,startdate,enddate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billid = itemView.findViewById(R.id.text_billid);
            customername = itemView.findViewById(R.id.text_custname);
            customermobile = itemView.findViewById(R.id.text_custmob);
            vehicleno = itemView.findViewById(R.id.text_vehicleno);
            vehicledriver = itemView.findViewById(R.id.text_vehicledriver);
            drivermobileno = itemView.findViewById(R.id.text_drivermobile);
            materialtype = itemView.findViewById(R.id.text_materialtype);
            quantity = itemView.findViewById(R.id.text_quantity);
            amountpay = itemView.findViewById(R.id.text_amtpaid);
            dueamt = itemView.findViewById(R.id.text_dueamt);
            deliveryloc = itemView.findViewById(R.id.text_location);
            startdate = itemView.findViewById(R.id.text_validationstart);
            enddate = itemView.findViewById(R.id.text_validationend);

        }
    }
}
