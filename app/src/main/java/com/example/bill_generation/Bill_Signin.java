package com.example.bill_generation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Bill_Signin extends AppCompatActivity {
    EditText customer_name, mobile_number, vehicle_number, driver_name, driver_mobile, material_type, quantity, amount_paid,due_amount, delivery_location, bill_validationstart,bill_validationend;
    Button signin;
    RequestQueue requestQueue;
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_signin);

        // Initialize UI components
        customer_name = findViewById(R.id.customer_name);
        mobile_number = findViewById(R.id.customer_mobile);
        vehicle_number = findViewById(R.id.vehicle_no);
        driver_name = findViewById(R.id.driver_name);
        driver_mobile = findViewById(R.id.driver_mob);
        material_type = findViewById(R.id.material);
        quantity = findViewById(R.id.quant);
        amount_paid = findViewById(R.id.amt_paid);
        due_amount = findViewById(R.id.due_amt);
        delivery_location = findViewById(R.id.delivery_loc);
        bill_validationstart = findViewById(R.id.bill_validate);
        bill_validationend = findViewById(R.id.bill_valid);
        signin = findViewById(R.id.btn_signin);
         // Ensure you have a ProgressBar in XML

        // Initialize RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        signin.setOnClickListener(view -> {
            String Cname = customer_name.getText().toString().trim();
            String Cmobile = mobile_number.getText().toString().trim();
            String Vnumber = vehicle_number.getText().toString().trim();
            String Dname = driver_name.getText().toString().trim();
            String Dmobile = driver_mobile.getText().toString().trim();
            String Mtype = material_type.getText().toString().trim();
            String Quant = quantity.getText().toString().trim();
            String Amt_paid = amount_paid.getText().toString().trim();
            String Due = due_amount.getText().toString().trim();
            String D_loc = delivery_location.getText().toString().trim();
            String Bill_Validates = bill_validationstart.getText().toString().trim();
            String Bill_Validend = bill_validationend .getText().toString().trim();

            if (Cname.isEmpty() || Cmobile.isEmpty() || Vnumber.isEmpty() || Dname.isEmpty() || Dmobile.isEmpty() ||
                    Mtype.isEmpty() || Quant.isEmpty() || Amt_paid.isEmpty() || Due.isEmpty() || D_loc.isEmpty() || Bill_Validates.isEmpty() || Bill_Validend.isEmpty()) {
                Toast.makeText(Bill_Signin.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (Cmobile.length() != 10 || !Cmobile.matches("\\d+") || Dmobile.length() != 10 || !Dmobile.matches("\\d+")) {
                Toast.makeText(Bill_Signin.this, "Mobile number must be 10 digits", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Bill_Generate(Cname, Cmobile, Vnumber, Dname, Dmobile, Mtype, Quant, Amt_paid,Due, D_loc, Bill_Validates,Bill_Validend);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(Bill_Signin.this, "Encoding error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Bill_Generate(String cname, String cmobile, String vnumber, String dname, String dmobile, String mtype,
                               String quant, String amtPaid,String Due, String dLoc, String billValidates,String billValidateend) throws UnsupportedEncodingException {



        // Proper URL encoding
        String url = "http://www.testproject.info/BillGeneration/bill_generate.php?" +
                "Customer_Name=" + URLEncoder.encode(cname, "UTF-8") +
                "&Mobile_Number=" + URLEncoder.encode(cmobile, "UTF-8") +
                "&Vehicle_Number=" + URLEncoder.encode(vnumber, "UTF-8") +
                "&Driver_Name=" + URLEncoder.encode(dname, "UTF-8") +
                "&Driver_Mobile=" + URLEncoder.encode(dmobile, "UTF-8") +
                "&Material_Type=" + URLEncoder.encode(mtype, "UTF-8") +
                "&Quantity=" + URLEncoder.encode(quant, "UTF-8") +
                "&Amount_Paid=" + URLEncoder.encode(amtPaid, "UTF-8") +
                "&Due_amt=" + URLEncoder.encode(Due, "UTF-8") +
                "&Delivery_Location=" + URLEncoder.encode(dLoc, "UTF-8") +
                "&Bill_Validation_Start=" + URLEncoder.encode(billValidates, "UTF-8") +
                "&Bill_Validation_End=" + URLEncoder.encode(billValidateend, "UTF-8");

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.e("response", response);


                    if (response.equalsIgnoreCase("Inserted")) {
                        new AlertDialog.Builder(Bill_Signin.this)
                                .setTitle("Success")
                                .setMessage("Bill Generated  successful")
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
                    } else {
                        Toast.makeText(Bill_Signin.this, "Registration failed: " + response, Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Log.e("Volley Error", "Error: " + error.toString());
            Toast.makeText(Bill_Signin.this, "Something went wrong. Try later.", Toast.LENGTH_SHORT).show();

        });

        requestQueue.add(request);
    }

}
