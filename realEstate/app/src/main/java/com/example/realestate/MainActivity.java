package com.example.realestate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static int LOGIN_REQUEST_CODE = 1;
    private static int LOGOUT_REQUEST_CODE = 2;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.user_account == item.getItemId()) {
            userLogin();
        }
        if (R.id.contact == item.getItemId()) {
            Intent contactLayout = new Intent(MainActivity.this, LienHe.class);
            startActivity(contactLayout);
        }
        return super.onOptionsItemSelected(item);
    }
    private void userLogin() {
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
        }
        else {
            if (user.getRole() == 1) {
                Intent intent = new Intent(this, SellerInfo.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, LOGOUT_REQUEST_CODE);
            }
            if (user.getRole() == 2) {
                Intent intent = new Intent(this, UserInfo.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, LOGOUT_REQUEST_CODE);
            }
            if (user.getRole() == 0){
                Intent intent = new Intent(this, AdminInfo.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, LOGOUT_REQUEST_CODE);
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