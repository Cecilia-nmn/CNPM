package com.example.realestate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private TextView signUp;
    private TextInputEditText txtEmail, txtPassword;

    private MovieService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        signUp = findViewById(R.id.signUp);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    public void Login(View view) {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
        }
        else if (password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else {
            User user = new User(email, password);
            service = MovieApi.getClient().create(MovieService.class);
            service.loginUser(user).enqueue(new Callback<Response<List<User>>>() {
                @Override
                public void onResponse(Call<Response<List<User>>> call, retrofit2.Response<Response<List<User>>> response) {
                    Response<List<User>> data = response.body();
                    if (data == null) {
                        Toast.makeText(LoginActivity.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (data.message.equals("Sai email hoặc mật khẩu")) {
                            Toast.makeText(LoginActivity.this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            List<User> user = data.data;

                            Intent intent = new Intent();
                            for (User u: user) {
                                intent.putExtra("user", u);
                            }

                            setResult(RESULT_OK, intent);

                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response<List<User>>> call, Throwable t) {
                    Log.e("TAG", "Error " + t.getMessage());
                }
            });
        }
    }
}
