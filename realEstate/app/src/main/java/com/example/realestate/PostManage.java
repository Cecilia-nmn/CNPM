package com.example.realestate;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PostManage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_manage);
        recyclerView = findViewById(R.id.recyclerViewPost);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        if (user.getRole() == 0) {
            initPost();
        }
        else {
            initSomePost();
        }
    }

    private void initSomePost() {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.getPost(user).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                Response<List<Post>> data = response.body();
                if (data == null) {
                    Toast.makeText(PostManage.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    if (data.data != null) {
                        List<Post> post = data.data;
                        //set adapter
                        adapter = new PostAdapter(PostManage.this, post);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PostManage.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(PostManage.this, R.drawable.divider));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<Post>>> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
                Toast.makeText(PostManage.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initPost() {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.getAllPost().enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                Response<List<Post>> data = response.body();
                if (data == null) {
                    Toast.makeText(PostManage.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    if (data.data != null) {
                        List<Post> post = data.data;
                        //set adapter
                        adapter = new PostAdapter(PostManage.this, post);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PostManage.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(PostManage.this, R.drawable.divider));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<Post>>> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
                Toast.makeText(PostManage.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.add == item.getItemId()) {
            Intent intent = new Intent(PostManage.this, AddPost.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}