package com.example.realestate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private static int LOGIN_REQUEST_CODE = 1;
    private static int LOGOUT_REQUEST_CODE = 2;
    public static User user;
    private List<Post> posts;
    private HomeAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        ImageView imageView = findViewById(R.id.image);

        String imagePath = "http://10.0.2.2:8888/movie_api/movie_poster/" + "building.jpg";
        Picasso.with(this).load(imagePath).into(imageView);

        initPost();

    }

    private void initPost() {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.getAllPost().enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                Response<List<Post>> data = response.body();
                if (data == null) {
                    Toast.makeText(MainActivity.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    if (data.data != null) {
                        posts = data.data;
                        //set adapter
                        adapter = new HomeAdapter(MainActivity.this, posts, user);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.divider));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<Post>>> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
                Toast.makeText(MainActivity.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();

            }
        });
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
        if (R.id.search == item.getItemId()) {
            Intent searchLayout = new Intent(MainActivity.this, TimKiem.class);
            startActivity(searchLayout);
        }
        return super.onOptionsItemSelected(item);
    }
    public void userLogin() {
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