package com.example.bill_generation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ImageView gifImageView = findViewById(R.id.image);

        // Load the GIF from drawable resource
        Glide.with(this)
                .asGif()
                .load(R.raw.billing_gif)  // Ensure your drawable is a GIF file
                .into(gifImageView);

        // Delay for 3 seconds before starting the next activity
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Bill_Dashboard.class);
                startActivity(intent);
                finish(); // Optionally finish the current activity
            }
        }, 3000);


    }
}