package com.example.bill_generation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bill_PDF extends AppCompatActivity {
    TextView customer_name, Mobile, Vehicleno, Drivername, Drivermobile, Materialtype, Quantity, amountpaid, dueamount, deliveryloc, billvalidstart, billvalidend;
    Button generatepdf;
    String pdfFileName = "Bill_Invoice.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_pdf);

        // Initialize UI Components
        customer_name = findViewById(R.id.text_customername);
        Mobile = findViewById(R.id.text_mobileno);
        Vehicleno = findViewById(R.id.text_vehicleno);
        Drivername = findViewById(R.id.text_drivername);
        Drivermobile = findViewById(R.id.text_drivemobile);
        Materialtype = findViewById(R.id.text_materialtype);
        Quantity = findViewById(R.id.text_quantity);
        amountpaid = findViewById(R.id.text_amountpaid);
        dueamount = findViewById(R.id.text_dueamt);
        deliveryloc = findViewById(R.id.text_deliveryloc);
        billvalidstart = findViewById(R.id.text_billvalidation);
        billvalidend = findViewById(R.id.text_billend);
        generatepdf = findViewById(R.id.generatepdf);

        // Get Data from Intent
        Intent intent = getIntent();
        customer_name.setText(intent.getStringExtra("customer_name"));
        Mobile.setText(intent.getStringExtra("mobile_number"));
        Vehicleno.setText(intent.getStringExtra("vehicle_number"));
        Drivername.setText(intent.getStringExtra("driver_name"));
        Drivermobile.setText(intent.getStringExtra("driver_mobile"));
        Materialtype.setText(intent.getStringExtra("material_type"));
        Quantity.setText(intent.getStringExtra("quantity"));
        amountpaid.setText(intent.getStringExtra("amount_paid"));
        dueamount.setText(intent.getStringExtra("due_amount"));
        deliveryloc.setText(intent.getStringExtra("delivery_location"));
        billvalidstart.setText(intent.getStringExtra("bill_validation_start"));
        billvalidend.setText(intent.getStringExtra("bill_validation_end"));

        // Request Storage Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        // Generate PDF Button Click Listener
        generatepdf.setOnClickListener(view -> generatePDF());
    }

    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        // Create Page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 700, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Write Data on PDF
        int x = 50, y = 50;
        paint.setTextSize(14);
        canvas.drawText("Bill Invoice", x + 150, y, paint);
        y += 30;
        canvas.drawText("Customer Name: " + customer_name.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Mobile Number: " + Mobile.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Vehicle Number: " + Vehicleno.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Driver Name: " + Drivername.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Driver Mobile: " + Drivermobile.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Material Type: " + Materialtype.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Quantity: " + Quantity.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Amount Paid: " + amountpaid.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Due Amount: " + dueamount.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Delivery Location: " + deliveryloc.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Bill Validation Start: " + billvalidstart.getText().toString(), x, y, paint);
        y += 20;
        canvas.drawText("Bill Validation End: " + billvalidend.getText().toString(), x, y, paint);
        y += 40;
        canvas.drawText("Signature: ___________", x, y, paint);

        // Finish the Page
        pdfDocument.finishPage(page);

        // Save the PDF
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), pdfFileName);
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();
            Toast.makeText(this, "PDF Generated: " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while generating PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
