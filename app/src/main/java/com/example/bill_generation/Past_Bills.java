package com.example.bill_generation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bill_generation.Adapter.Bill_Adapter;
import com.example.bill_generation.Model.modelviewbill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Past_Bills extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Bill_Adapter billAdapter;
    private List<modelviewbill> billModels;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_past_bills);
        recyclerView = findViewById(R.id.billview);
        progressBar = findViewById(R.id.billprogressb);


        billModels = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(Past_Bills.this));

        // Initialize adapter with empty list
        billAdapter = new Bill_Adapter(Past_Bills.this, billModels);
        recyclerView.setAdapter(billAdapter);

        fetchbilldata();

    }
    private void fetchbilldata() {
        // Show progress bar before starting data fetch
        progressBar.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient();
        String url = "http://www.testproject.info/BillGeneration/fetch_bill.php"; // Ensure this URL returns all FIR records

        okhttp3.Request request = new Request.Builder()
                .url(url)
                .build();

        new Thread(() -> {
            List<modelviewbill> list = new ArrayList<>();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    runOnUIThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Past_Bills.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }

                String responseBody = response.body().string();
                Log.d("API_RESPONSE", responseBody);

                JSONObject jsonObject = new JSONObject(responseBody);

                if (jsonObject.getString("status").equals("success")) {
                    JSONArray billArray = jsonObject.getJSONArray("Bills");

                    for (int i = 0; i < billArray.length(); i++) {
                        JSONObject billObject = billArray.getJSONObject(i);
                        modelviewbill bill = new modelviewbill();

                        bill .setbill_id(billObject.optString("ID", "Unknown"));
                        bill .setCustomername(billObject.optString("Customer_Name", "Unknown"));
                        bill .setCustomermobile(billObject.optString("Mobile_Number", "Unknown"));
                        bill .setVehicleno(billObject.optString("Vehicle_Number", "Unknown"));
                        bill .setVehicledriver(billObject.optString("Driver_Name", "Unknown"));
                        bill .setDrivermobileno(billObject.optString("Driver_Mobile", "Unknown"));
                        bill .setMaterialtype(billObject.optString("Material_Type", "Unknown"));
                        bill .setQuantity(billObject.optString("Quantity", "Unknown"));
                        bill .setAmountpay(billObject.optString("Amount_Paid", "Unknown"));
                        bill .setDueamt(billObject.optString("Due_amt", "Unknown"));
                        bill .setDeliveryloc(billObject.optString("Delivery_Location", "Unknown"));
                        bill .setstartdate(billObject.optString("Bill_Validation_Start", "Unknown"));
                        bill .setenddate(billObject.optString("Bill_Validation_End", "Unknown"));

                        list.add(bill );
                    }
                }

                runOnUIThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    if (list.isEmpty()) {
                        Toast.makeText(Past_Bills.this, "No FIR records found", Toast.LENGTH_SHORT).show();
                    } else {
                        billModels.clear();
                        billModels.addAll(list);
                        billAdapter.notifyDataSetChanged();
                    }
                });
            } catch (JSONException | IOException e) {
                runOnUIThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Past_Bills.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });
                e.printStackTrace();
            }
        }).start();
    }
    private void runOnUIThread(Runnable action) {
        if (Past_Bills.this != null) {
            Past_Bills.this.runOnUiThread(action);
        }
    }
}