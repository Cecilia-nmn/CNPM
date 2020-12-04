package com.example.realestate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText txtUsername, txtEmail, txtPassword, txtConfirmPassword;
    private RadioButton buyer, seller;
    private CheckBox chbTermsPrivacy;

    private MovieService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        chbTermsPrivacy = findViewById(R.id.chbTermsPrivacy);
        buyer = findViewById(R.id.Buyer);
        seller = findViewById(R.id.seller);
    }

    public void CreateAccount(View view) {
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPass = txtConfirmPassword.getText().toString();
        int role = 0;
        if (buyer.isChecked()) {
            role = 1;
            Log.d("TAG", "CreateAccount: " + role);
        }
        if (seller.isChecked()) {
            role = 2;
            Log.d("TAG", "CreateAccount: " + role);
        }
        if (username.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Họ và tên", Toast.LENGTH_SHORT).show();
        }
        else if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
        }
        else if (password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else if (confirmPass.isEmpty()) {
            Toast.makeText(this, "Vui lòng Xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmPass)) {
            Toast.makeText(this, "Xác nhận mật khẩu không trùng với mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else if (!chbTermsPrivacy.isChecked()) {
            Toast.makeText(this, "Vui lòng Xác nhận điều khoản", Toast.LENGTH_SHORT).show();
        }
        else {
            User user = new User(username, password, email, role);
            service = MovieApi.getClient().create(MovieService.class);
            service.registerUser(user).enqueue(new Callback<Response<String>>() {
                @Override
                public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                    Response<String> data = response.body();
                    if (data == null) {
                        Toast.makeText(SignUpActivity.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (data.message.equals("Email tồn tại!")) {
                            Toast.makeText(SignUpActivity.this, "Email tồn tại! Vui lòng nhập Email khác", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setCancelable(false);
                            builder.setTitle("Đăng ký thành công");
                            builder.setMessage("Bạn đã đăng ký thành công! Nhấn OK để quay về trang đăng nhập");
                            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                finish();
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response<String>> call, Throwable t) {
                    Log.e("TAG", "Error " + t.getMessage());
                }
            });
        }
    }
}