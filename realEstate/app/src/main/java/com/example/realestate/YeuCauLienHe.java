package com.example.realestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class YeuCauLienHe extends AppCompatActivity {

    EditText edtYC;
    TextView txtYC;
    Button btnYC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_cau_lien_he);

        edtYC = findViewById(R.id.edtYC);
        txtYC = findViewById(R.id.txtYC);
        btnYC = findViewById(R.id.buttonYC);
        

    }
}