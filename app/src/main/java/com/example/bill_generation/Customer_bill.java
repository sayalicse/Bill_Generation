package com.example.bill_generation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bill_generation.Adapter.customerlist_adapter;
import com.example.bill_generation.Model.customermodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Customer_bill extends AppCompatActivity {
    private RecyclerView recyclerView;
    private customerlist_adapter customerAdapter;
    private List<customermodel> customermodel;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_bill);
        recyclerView = findViewById(R.id.srecview); // Ensure recview ID is correct in layout
        progressBar = findViewById(R.id.sdynamicProgressBar);
        customermodel = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getcustomerlist();

    }
    private void getcustomerlist() {
        // Show progress bar before starting data fetch
        progressBar.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient();
        String url = "http://www.testproject.info/BillGeneration/customerlist.php"; // Update with your correct API URL

        Request request = new Request.Builder()
                .url(url)
                .build();

        // Create a new thread to fetch data (to avoid blocking UI thread)
        new Thread(() -> {
            List<customermodel> list = new ArrayList<>();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Customer_bill.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }

                String responseBody = response.body().string();
                // Log the response for debugging purposes
                Log.d("API_RESPONSE", responseBody);

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(responseBody);
                String status = jsonResponse.optString("status");

                if ("success".equals(status)) {
                    // Extract customer name and mobile number arrays
                    JSONArray customerNames = jsonResponse.optJSONArray("customername");
                    JSONArray customerMobiles = jsonResponse.optJSONArray("customermobile");

                    if (customerNames != null && customerMobiles != null) {
                        for (int i = 0; i < customerNames.length(); i++) {
                            String name = customerNames.optString(i, "No customer available");
                            String mobile = customerMobiles.optString(i, "No mobile no available");

                            customermodel customer = new customermodel();
                            customer.setcustomername(name);
                            customer.setcustomermobile(mobile);

                            list.add(customer);
                        }
                    }

                    // Update RecyclerView with the fetched data
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);  // Hide progress bar once data is fetched
                        if (list.isEmpty()) {
                            Toast.makeText(Customer_bill.this, "No customers available", Toast.LENGTH_SHORT).show();
                        } else {
                            customermodel = list;
                            customerAdapter = new customerlist_adapter(Customer_bill.this, customermodel);
                            recyclerView.setAdapter(customerAdapter);
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Customer_bill.this, "No customers available", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (JSONException | IOException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Customer_bill.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });
                e.printStackTrace();
            }
        }).start();
    }

}