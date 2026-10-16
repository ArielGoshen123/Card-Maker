package com.example.carder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Key constants for Intent Extras
    public static final String KEY_NAME = "EXTRA_NAME";
    public static final String KEY_ROLE = "EXTRA_ROLE";
    public static final String KEY_PHONE = "EXTRA_PHONE";
    public static final String KEY_WEBSITE = "EXTRA_WEBSITE";

    private EditText etFullName;
    private EditText etRole;
    private EditText etPhone;
    private EditText etWebsite;
    private Button btnCreateCard;
    private Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFullName = findViewById(R.id.etFullName);
        etRole = findViewById(R.id.etRole);
        etPhone = findViewById(R.id.etPhone);
        etWebsite = findViewById(R.id.etWebsite);
        btnCreateCard = findViewById(R.id.btnCreateCard);
        btnAbout = findViewById(R.id.btnInfo);

        //Navigate to AboutActivity
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        //Secret data fill
        btnCreateCard.setOnLongClickListener(v -> {
            etFullName.setText("Angel Williams");
            etRole.setText("Zoo Critic");
            etPhone.setText("0573348167");
            etWebsite.setText("thebestzooyouthink.com");
            return true;
        });

        btnCreateCard.setOnClickListener(v -> {
            String name = etFullName.getText().toString().trim();
            String role = etRole.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String website = etWebsite.getText().toString().trim();

            boolean isValid = true;

            //Validate Name
            if (name.isEmpty()) {
                etFullName.setError("Please enter a name");
                isValid = false;
            } else if (name.matches(".*\\d.*")) { //Checks for a digits
                etFullName.setError("Name cannot contain numbers");
                isValid = false;
            } else if (!name.contains(" ")) {
                etFullName.setError("Please enter both first and last name");
                isValid = false;
            }

            // Validate Role
            if (role.isEmpty()) {
                etRole.setError("Please enter a role");
                isValid = false;
            }

            // Validate Phone
            if (phone.isEmpty()) {
                etPhone.setError("Please enter a phone number");
                isValid = false;
            }

            // alidate Website
            if (website.isEmpty()) {
                etWebsite.setError("Please enter a website");
                isValid = false;
            }

            //Stop execution if any field is invalid
            if (!isValid) {
                return;
            }

            //Navigate to CardActivity using public key constants
            Intent intent = new Intent(MainActivity.this, CardActivity.class);
            intent.putExtra(KEY_NAME, name);
            intent.putExtra(KEY_ROLE, role);
            intent.putExtra(KEY_PHONE, phone);
            intent.putExtra(KEY_WEBSITE, website);
            startActivity(intent);
        });
    }
}