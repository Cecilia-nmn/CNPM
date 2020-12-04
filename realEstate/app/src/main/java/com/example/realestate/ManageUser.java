package com.example.realestate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.realestate.network.MovieApi;
import com.example.realestate.network.MovieService;
import com.example.realestate.network.Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ManageUser extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MUAdapter adapter;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        recyclerView = findViewById(R.id.recyclerViewMU);
        users = new ArrayList<>();

        initReport();
    }

    private void initReport() {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.getAllUser().enqueue(new Callback<Response<List<User>>>() {
            @Override
            public void onResponse(Call<Response<List<User>>> call, retrofit2.Response<Response<List<User>>> response) {
                Response<List<User>> data = response.body();
                if (data == null) {
                    Toast.makeText(ManageUser.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    if (data.data != null) {
                        users = data.data;
                        //set adapter
                        adapter = new MUAdapter(ManageUser.this, users);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ManageUser.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(ManageUser.this, R.drawable.divider));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<User>>> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
                Toast.makeText(ManageUser.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();

            }
        });
    }
}