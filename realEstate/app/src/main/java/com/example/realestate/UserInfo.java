package com.example.realestate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UserInfo extends AppCompatActivity {
    private TextView tvUsername, tvEmail, btnLogout, btnExit, btnHistory, btnChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);

        tvUsername = findViewById(R.id.tvUsername);
        btnLogout = findViewById(R.id.btnLogout);
        btnExit = findViewById(R.id.btnExit);
        btnChange = findViewById(R.id.btnChange);

        //Exit
        btnExit.setOnClickListener(view -> {
            finish();
        });


        //Receive Intent from MainActivity
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        tvUsername.setText(user.getUsername());
        
        btnLogout.setOnClickListener(view -> {
            Intent intent1 = new Intent();
            intent1.putExtra("user", (Bundle) null);

            setResult(RESULT_OK, intent1);

            AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
            builder.setCancelable(false);
            builder.setTitle("Đăng xuất ?");
            builder.setMessage("Bạn chắc chắn muốn đăng xuất ?");
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                Toast.makeText(UserInfo.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
            builder.setNegativeButton("Huỷ", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        });
    }
}
