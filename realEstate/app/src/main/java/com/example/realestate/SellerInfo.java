package com.example.realestate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SellerInfo extends AppCompatActivity {
    private TextView tvUsername, btnLogout, btnExit, btnHistory, btnChange, btnNotify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_info_layout);

        tvUsername = findViewById(R.id.tvUsername);
        btnLogout = findViewById(R.id.btnLogout);
        btnHistory = findViewById(R.id.btnHistory);
        btnExit = findViewById(R.id.btnExit);
        btnChange = findViewById(R.id.btnChange);
        btnNotify = findViewById(R.id.btnNotify);

        //Exit
        btnExit.setOnClickListener(view -> {
            finish();
        });

        //Receive Intent from MainActivity
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        tvUsername.setText(user.getUsername());

        btnHistory.setOnClickListener(view -> {
            Intent postManage = new Intent(SellerInfo.this, PostManage.class);
            postManage.putExtra("user", user);
            startActivity(postManage);
        });

        btnChange.setOnClickListener(view -> {
            Intent selfInfo = new Intent(SellerInfo.this, SelfInfo.class);
            selfInfo.putExtra("user", user);
            startActivity(selfInfo);
        });

        btnLogout.setOnClickListener(view -> {
            Intent intent1 = new Intent();
            intent1.putExtra("user", (Bundle) null);

            setResult(RESULT_OK, intent1);

            AlertDialog.Builder builder = new AlertDialog.Builder(SellerInfo.this);
            builder.setCancelable(false);
            builder.setTitle("Đăng xuất ?");
            builder.setMessage("Bạn chắc chắn muốn đăng xuất ?");
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                Toast.makeText(SellerInfo.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
            builder.setNegativeButton("Huỷ", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        });

        btnNotify.setOnClickListener(view -> {
            Intent notifyPage = new Intent(SellerInfo.this, Contact.class);
            notifyPage.putExtra("user", user);
            startActivity(notifyPage);
        });
    }
}
