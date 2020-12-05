package com.example.realestate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class XemBaiViet extends AppCompatActivity {
    private static int LOGIN_REQUEST_CODE = 1;
    private static int LOGOUT_REQUEST_CODE = 2;
    TextView txtND,txtTT,txtKV,txtSP, txtCD, contact;
    private ImageView img;
    private User user;
    private Post post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_bai_viet);

        txtND = findViewById(R.id.txtND);
        txtTT = findViewById(R.id.txtTitle);
        txtKV = findViewById(R.id.txtKV1);
        txtSP = findViewById(R.id.txtSP1);
        txtCD = findViewById(R.id.txtCD1);
        img = findViewById(R.id.detailImage);

        contact = findViewById(R.id.contact);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        post = intent.getParcelableExtra("post");

        String imagePath = "http://10.0.2.2:8888/movie_api/movie_poster/" + post.getImage();
        Picasso.with(this).load(imagePath).into(img);
        txtND.setText(post.getDescript());
        txtTT.setText(post.getTitle());
        txtCD.setText(post.getSize());
        txtKV.setText(post.getArea());
        txtSP.setText(post.getRooms());
    }

    public void contact(View view) {
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
        }
        else {
            if (user.getRole() == 2) {
                Intent intent = new Intent(this, YeuCauLienHe.class);
                intent.putExtra("user", user);
                intent.putExtra("receiver", post.getUsername());
                startActivityForResult(intent, LOGOUT_REQUEST_CODE);
            }
            else {
                Toast.makeText(this, "Bạn không thể sử dụng chức năng này", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                user = data.getParcelableExtra("user");
            }
        }
        if (requestCode == LOGOUT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                user = data.getParcelableExtra("user");
            }
        }
    }
}