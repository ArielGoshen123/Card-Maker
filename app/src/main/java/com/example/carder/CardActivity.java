package com.example.carder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CardActivity extends AppCompatActivity {

    private TextView tvCardName;
    private TextView tvCardRole;
    private TextView tvCardPhone;
    private TextView tvCardWebsite;

    private Button btnDial;
    private Button btnWebsite;
    private Button btnShare;
    private Button btnBack;

    private String name = "";
    private String role = "";
    private String phone = "";
    private String website = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvCardName = findViewById(R.id.tvCardName);
        tvCardRole = findViewById(R.id.tvCardRole);
        tvCardPhone = findViewById(R.id.tvCardPhone);
        tvCardWebsite = findViewById(R.id.tvCardWebsite);

        btnDial = findViewById(R.id.btnDial);
        btnWebsite = findViewById(R.id.btnWebsite);
        btnShare = findViewById(R.id.btnShare);
        btnBack = findViewById(R.id.btnBack);

        //Get extras from MainActivity using static key constants
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra(MainActivity.KEY_NAME);
            role = intent.getStringExtra(MainActivity.KEY_ROLE);
            phone = intent.getStringExtra(MainActivity.KEY_PHONE);
            website = intent.getStringExtra(MainActivity.KEY_WEBSITE);
        }

        //Display data
        tvCardName.setText(name);
        tvCardRole.setText(role);
        tvCardPhone.setText("📞 " + phone);
        tvCardWebsite.setText("🌐 " + website);

        //Call Button
        btnDial.setOnClickListener(v -> {
            if (phone != null && !phone.isEmpty()) {
                try {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(dialIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(CardActivity.this, "No app available to place phone calls", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CardActivity.this, "No phone number available", Toast.LENGTH_SHORT).show();
            }
        });

        //Website Button
        btnWebsite.setOnClickListener(v -> {
            if (website != null && !website.isEmpty()) {
                String formattedUrl = website;
                if (!formattedUrl.startsWith("http://") && !formattedUrl.startsWith("https://")) {
                    formattedUrl = "https://" + formattedUrl;
                }
                try {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl));
                    startActivity(webIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(CardActivity.this, "No web browser available", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CardActivity.this, "No website URL available", Toast.LENGTH_SHORT).show();
            }
        });

        // Share Button
        btnShare.setOnClickListener(v -> {
            String shareBody = "Business Card:\n" +
                    "Name: " + name + "\n" +
                    "Role: " + role + "\n" +
                    "Phone: " + phone + "\n" +
                    "Website: " + website;

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(shareIntent, "Share Business Card Via"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(CardActivity.this, "No app available to share content", Toast.LENGTH_SHORT).show();
            }
        });

        // Back Button
        btnBack.setOnClickListener(v -> finish());
    }
}