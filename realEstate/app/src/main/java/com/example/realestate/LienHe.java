package com.example.realestate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class LienHe extends AppCompatActivity {
    EditText edtEmail,edtNhapND;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        edtEmail = findViewById(R.id.edtEmail);
        edtNhapND = findViewById(R.id.edtNhapND);
        btnSend = findViewById(R.id.button);

        btnSend.setOnClickListener(view -> {
            if (edtEmail.getText().equals("")) {
                Toast.makeText(LienHe.this, "Xin hãy nhập email!", Toast.LENGTH_LONG).show();
            }
            if (edtNhapND.getText().equals("")) {
                Toast.makeText(LienHe.this, "Xin hãy nhập nội dung!", Toast.LENGTH_LONG).show();
            }
            else {
                Report report = new Report(edtEmail.getText().toString(), edtNhapND.getText().toString());
                //save to db
                MovieService service = MovieApi.getClient().create(MovieService.class);
                service.sendReport(report).enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        Response<String> data = response.body();
                        if (data == null) {
                            Toast.makeText(LienHe.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Log.e("TAG", "onResponse: " + data.data);
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<String>> call, Throwable t) {
                        Log.e("TAG", "Error " + t.getMessage());
                        Toast.makeText(LienHe.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(LienHe.this);
                builder.setCancelable(false);
                builder.setTitle("Gửi thành công");
                builder.setPositiveButton("OK", (dialogInterface, i) -> {
                    backHomePage();
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void backHomePage() {
        Intent intent = new Intent(LienHe.this, MainActivity.class);
        startActivity(intent);
    }
}