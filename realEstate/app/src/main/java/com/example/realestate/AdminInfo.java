package com.example.realestate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminInfo extends AppCompatActivity {
    private TextView tvUsername, tvEmail, btnLogout, btnExit, btnHistory, btnChange, btnUsers, btnNotify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_info_layout);

        tvUsername = findViewById(R.id.tvUsername);
        btnLogout = findViewById(R.id.btnLogout);
        btnHistory = findViewById(R.id.btnHistory);
        btnExit = findViewById(R.id.btnExit);
        btnChange = findViewById(R.id.btnChange);
        btnNotify = findViewById(R.id.btnNotify);
        btnUsers = findViewById(R.id.btnUsers);

        //Exit
        btnExit.setOnClickListener(view -> {
            finish();
        });


        //Receive Intent from MainActivity
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        tvUsername.setText(user.getUsername());

        btnChange.setOnClickListener(view -> {
            Intent selfInfo = new Intent(AdminInfo.this, SelfInfo.class);
            selfInfo.putExtra("user", user);
            startActivity(selfInfo);
        });

        btnHistory.setOnClickListener(view -> {
            Intent postManage = new Intent(AdminInfo.this, PostManage.class);
            postManage.putExtra("user", user);
            startActivity(postManage);
        });

        btnUsers.setOnClickListener(view -> {
            Intent manageUser = new Intent(AdminInfo.this, ManageUser.class);
            startActivity(manageUser);
        });


        btnNotify.setOnClickListener(view -> {
            Intent notifyPage = new Intent(AdminInfo.this, Notify.class);
            startActivity(notifyPage);
        });

        btnLogout.setOnClickListener(view -> {
            Intent intent1 = new Intent();
            intent1.putExtra("user", (Bundle) null);

            setResult(RESULT_OK, intent1);

            AlertDialog.Builder builder = new AlertDialog.Builder(AdminInfo.this);
            builder.setCancelable(false);
            builder.setTitle("Đăng xuất ?");
            builder.setMessage("Bạn chắc chắn muốn đăng xuất ?");
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                Toast.makeText(AdminInfo.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
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
