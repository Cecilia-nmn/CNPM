package com.example.realestate;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SelfInfo extends AppCompatActivity {
    private EditText edtPhone, edtPass, edtConf;
    private Button btnSaveInf, btnSavePass;
    private TextView txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_info);
        edtPhone = findViewById(R.id.edtPhone);
        edtPass = findViewById(R.id.edtNewPass);
        edtConf = findViewById(R.id.edtConfPass);
        btnSaveInf = findViewById(R.id.btnSaveInfo);
        btnSavePass = findViewById(R.id.btnSavePass);
        txtEmail = findViewById(R.id.txtEmail);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");

        edtPhone.setText(user.getPhone());
        txtEmail.setText(user.getEmail());

        btnSaveInf.setOnClickListener(view -> {
            if (!edtPhone.getText().toString().equals(user.getPhone())) {
                user.setPhone(edtPhone.getText().toString());
                MovieService service = MovieApi.getClient().create(MovieService.class);
                service.updateInfo(user).enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        Response<String> data = response.body();
                        if (data == null) {
                            Toast.makeText(SelfInfo.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelfInfo.this);
                            builder.setCancelable(false);
                            builder.setTitle("Đã lưu");
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
                        Toast.makeText(SelfInfo.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                Toast.makeText(SelfInfo.this, "Không có sự thay đổi", Toast.LENGTH_LONG).show();
            }
        });

        btnSavePass.setOnClickListener(view -> {
            if (edtPass.getText().toString().equals("") || edtConf.getText().toString().equals("")) {
                Toast.makeText(SelfInfo.this, "Hãy nhập đủ thông tin!", Toast.LENGTH_LONG).show();
            }
            else  if (!edtPass.getText().toString().equals(edtConf.getText().toString())) {
                Toast.makeText(SelfInfo.this, "Mật khẩu xác nhận không đúng!", Toast.LENGTH_LONG).show();
            }
            else {
                user.setPassword(edtPass.getText().toString());
                MovieService service = MovieApi.getClient().create(MovieService.class);
                service.updatePass(user).enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        Response<String> data = response.body();
                        if (data == null) {
                            Toast.makeText(SelfInfo.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelfInfo.this);
                            builder.setCancelable(false);
                            builder.setTitle("Đã lưu");
                            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                finish();
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<String>> call, Throwable t) {
                        Toast.makeText(SelfInfo.this, "Không có sự thay đổi", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}