package com.example.realestate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class YeuCauLienHe extends AppCompatActivity {

    private EditText edtYC;
    private Button btnYC;
    private ContactRequest request;
    private User user;
    private String  receiverId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_cau_lien_he);

        edtYC = findViewById(R.id.edtYC);
        btnYC = findViewById(R.id.buttonYC);

        Intent intent = getIntent();
        receiverId = intent.getStringExtra("receiver");
        user = intent.getParcelableExtra("user");

        btnYC.setOnClickListener(view -> {
            if (edtYC.getText().toString().equals("")) {
                Toast.makeText(YeuCauLienHe.this, "Xin hãy nhập nội dung!", Toast.LENGTH_LONG).show();
            }
            else {
                userLogin();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                user = data.getParcelableExtra("user");
            }
        }
    }
    public void userLogin() {
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        else {
            request = new ContactRequest(user.getUsername(), receiverId, edtYC.getText().toString());
            //save to db
            MovieService service = MovieApi.getClient().create(MovieService.class);
            service.sendRequest(request).enqueue(new Callback<Response<String>>() {
                @Override
                public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                    Response<String> data = response.body();
                    if (data == null) {
                        Toast.makeText(YeuCauLienHe.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.e("TAG", "onResponse: " + data.data);
                        AlertDialog.Builder builder = new AlertDialog.Builder(YeuCauLienHe.this);
                        builder.setCancelable(false);
                        builder.setTitle("Gửi thành công");
                        builder.setPositiveButton("OK", (dialogInterface, i) -> {
                            finish();
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<Response<String>> call, Throwable t) {
                    Log.e("TAG", "Error " + t.getMessage());
                    Toast.makeText(YeuCauLienHe.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}