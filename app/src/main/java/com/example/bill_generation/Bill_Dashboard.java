package com.example.bill_generation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Bill_Dashboard extends AppCompatActivity {
    CardView home,customer,bill,pastbill,settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_dashboard);
        home=findViewById(R.id.cardHome);
        customer=findViewById(R.id.cardCustomer);
        bill=findViewById(R.id.cardBill);
        pastbill=findViewById(R.id.cardpastbill);
        settings=findViewById(R.id.cardSettings);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Bill_Dashboard.this,Home.class);
                startActivity(intent);
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Bill_Dashboard.this,Customer_bill.class);
                startActivity(intent);

            }
        });
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Bill_Dashboard.this,Bill_Signin.class);
                startActivity(intent);
            }
        });
        pastbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Bill_Dashboard.this,Past_Bills.class);
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Bill_Dashboard.this,Settings.class);
                startActivity(intent);
            }
        });

    }
}