package com.example.realestate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class Notify extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotifyAdapter adapter;
    private List<Report> reports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        recyclerView = findViewById(R.id.recyclerViewNoti);
        reports = new ArrayList<>();

        initReport();
    }

    private void initReport() {
        MovieService service = MovieApi.getClient().create(MovieService.class);
        service.getReport().enqueue(new Callback<Response<List<Report>>>() {
            @Override
            public void onResponse(Call<Response<List<Report>>> call, retrofit2.Response<Response<List<Report>>> response) {
                Response<List<Report>> data = response.body();
                if (data == null) {
                    Toast.makeText(Notify.this, "Không kết nối được đến server", Toast.LENGTH_LONG).show();
                }
                else {
                    if (data.data != null) {
                        reports = data.data;
                        //set adapter
                        adapter = new NotifyAdapter(Notify.this, reports);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Notify.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(Notify.this, R.drawable.divider));
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<Report>>> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
                Toast.makeText(Notify.this, "Không kết nối được đến server! Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}